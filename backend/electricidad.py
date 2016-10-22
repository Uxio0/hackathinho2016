#!/usr/bin/env python3
import datetime
import time
import requests
from pymongo import MongoClient
from bson import json_util
mongo = MongoClient()
hack_db = mongo.hackathinho

iso_codes = open('iso_codes.txt').read().splitlines()
cities_with_codes = []
for iso_code in iso_codes:
    code, city, community = iso_code.split('\t')
    cities_with_codes.append({'iso': code.strip(),
                              'city': city.strip().lower(),
                              'community': community.strip()})

dateformat = '%Y-%m-%dT%H:%M:%S'


def query_ree(indicator):
    end = (datetime.datetime.now() - datetime.timedelta(days=30)).strftime(dateformat)
    start = (datetime.datetime.now() - datetime.timedelta(days=31)).strftime(dateformat)
    headers = get_headers('5c7f9ca844f598ab7b86bffcad08803f78e9fc5bf3036eef33b5888877a04e38')
    url = 'https://api.esios.ree.es/indicators/{}?start_date={}&end_date={}'.format(indicator, start, end)
    print(url)
    response = requests.get(url, headers=headers)
    return response.json()


def get_headers(token):
    headers = dict()
    headers['Accept'] = 'application/json; application/vnd.esios-api-v1+json'
    headers['Content-Type'] = 'application/json'
    headers['Host'] = 'api.esios.ree.es'
    headers['Authorization'] = 'Token token=\"{}\"'.format(token)
    headers['Cookie'] = ''
    return headers


def get_and_store_google_city_data(geo_id, name):
    city = hack_db.cities.find({'geo_id': geo_id})
    if city:
        city
    else:
        url = 'http://maps.googleapis.com/maps/api/geocode/json?address=' + name + ',spain'
        response = requests.get(url)
        time.sleep(1)
        j = response.json()
        location = j['results'][0]['geometry']['location']
        result = {'geo_id': geo_id,
                  'geo_name': name,
                  'lat': location['lat'],
                  'lng': location['lng']
                  }
        hack_db.cities.insert(result)
        return hack_db.cities.find({'geo_name': name})


def get_and_store_province_code(geo_id, geo_name):
    city = hack_db.cities.find_one({'geo_id': geo_id})
    if not city:
        city = {'geo_id': geo_id,
                'geo_name': geo_name}
        for city_with_codes in cities_with_codes:
            if geo_name.strip().lower() in city_with_codes['city']:
                city['iso'] = city_with_codes['iso']
                break
        hack_db.cities.insert(city)
    return city


def get_info_and_cities(indicator):
    result = query_ree(indicator)

    cities = {city['geo_id']: get_and_store_province_code(city['geo_id'],
                                                          city['geo_name'])
              for city in result['indicator']['geos']}
    values = result['indicator']['values']
    if values:
        collection = hack_db['indicator-' + str(indicator)]
        if collection.find_one(values[0]):
            print('Repeated values for indicator: ' + str(indicator))
        for value in values:
            value['iso'] = cities[value['geo_id']]['iso']
        collection.insert_many(values)
    return values


def get_from_mongo(indicator):
    collection = hack_db['indicator-' + str(indicator)]
    return [value for value in collection.find({})]


def populate_all(indicators):
    for indicator in indicators:
        get_info_and_cities(indicator)


if __name__ == '__main__':
    generacion_medida_solar = 10205
    generacion_medida_hidraulica = 10035
    generacion_medida_eolica = 10037

    get_info_and_cities(generacion_medida_solar)
    get_info_and_cities(generacion_medida_hidraulica)
    get_info_and_cities(generacion_medida_eolica)
