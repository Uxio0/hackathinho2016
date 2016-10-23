var csv = require("fast-csv");
var request = require("request")
var wait = require('wait-promise');

var url = "http://maps.googleapis.com/maps/api/geocode/json?sensor=false&address="
var csvFile = "provinces.csv"

var count = 1
var results = []
function fetchProvinceInfo(isoCode){
  //console.log('GET '+url+isoCode)
  return new Promise(function(resolve, reject){
    setTimeout(function(){
      request({
          url: url+isoCode,
          json: true
      }, function (error, response, body) {
          var result = body.results[0]
          if (result){
            var location = result.geometry.location
            var bounds = result.geometry.bounds
            if (!error && response.statusCode === 200) {
              //console.log(result)
              resolve({
                isoCode: isoCode,
                latitude: location.lat,
                longitude: location.lng,
                bounds: {
                  northeast: {
                    latitude: bounds.northeast.lat,
                    longitude: bounds.northeast.long
                  },
                  southwest: {
                    latitude: bounds.southwest.lat,
                    longitude: bounds.southwest.long
                  }
                },
                name: result.address_components[0].long_name
              })
            } else {
              reject(error)
            }
          } else {
            console.error('ERROR: '+url+isoCode, body);
          }
      })
    }, (count++)*300)
  })

}


var promises = []
csv.fromPath(csvFile)
 .on("data", function(data){
     data.forEach(function(val){
       if (val.startsWith('ES-')){
         //getInfo(val)
         //console.log(getInfo(val));
         promises.push(fetchProvinceInfo(val))
       }
     })
 }).on("end", function(){
      //console.log('csv end');
      Promise.all(promises).then(function(results){
          //console.log('promises end');
          console.log("%j", results);
      })
  });
  //console.log('end');
