#!/usr/bin/env python3
import json
import datetime
import time
import requests
from pymongo import MongoClient
mongo = MongoClient()
hack_db = mongo.hackathinho

dateformat = '%Y-%m-%dT%H:%M:%S'


def query_ree(indicator):
    end = (datetime.datetime.now() - datetime.timedelta(days=30)).strftime(dateformat)
    start = (datetime.datetime.now() - datetime.timedelta(days=31)).strftime(dateformat)
    headers = get_headers('5c7f9ca844f598ab7b86bffcad08803f78e9fc5bf3036eef33b5888877a04e38')
    url = "https://api.esios.ree.es/indicators/{}?start_date={}&end_date={}".format(indicator, start, end)
    print(url)
    response = requests.get(url, headers=headers)
    return response.json()

def get_headers(token):
    """
    Prepares the CURL headers
    :return:
    """
    # Prepare the arguments of the call
    headers = dict()
    headers['Accept'] = 'application/json; application/vnd.esios-api-v1+json'
    headers['Content-Type'] = 'application/json'
    headers['Host'] = 'api.esios.ree.es'
    headers['Authorization'] = 'Token token=\"{}\"'.format(token)
    headers['Cookie'] = ''
    return headers


def get_google_city_data(geo_id, name):
    city = hack_db.cities.find({'geo_name': name})
    if city:
        city
    else:
        url = "http://maps.googleapis.com/maps/api/geocode/json?address=" + name + ",spain"
        time.sleep(1)
        response = requests.get(url)
        j = response.json()
        location = j['results'][0]['geometry']['location']
        result = {'geo_id': geo_id, 'geo_name': name, 'lat': location['lat'], 'lng': location['lng']}
        hack_db.cities.insert(result)
        return hack_db.cities.find({'geo_name': name})


def get_info_and_cities(indicator):
    result = query_ree(indicator)
    for city in result['indicator']['geos']:
        get_google_city_data(city['geo_id'], city['geo_name'])
    values = result['indicator']['values']
    if values:
        hack_db['indicator-{}'.format(indicator)].insert_many(values)

generacion_medida_solar = 10205
generacion_medida_hidraulica = 10035
generacion_medida_eolica = 10037

get_info_and_cities(generacion_medida_solar)
get_info_and_cities(generacion_medida_hidraulica)
get_info_and_cities(generacion_medida_eolica)
