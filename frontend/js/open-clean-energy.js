$(function () {

  var that = this
  
  // Weather service
  var weatherService = "http://10.20.25.86:8080/api/weather-energies"
  
  // Current weather
  
  var weather = []
  
  $.ajax({
     url: weatherService,
     dataType: 'jsonp',
     success: function (json) {
      console.debug('weather')
      that.weather = data
      that.weather.sort(function (a, b) { a.iso < b.iso })
      that.configWeatherChart()
     },
     error: function () {
      console.error('No weather metrics available')
     }
  })
  
  var weatherOptions = {
    title: {
      text: 'Current weather'
    },
    xAxis: {
      categories: [],
      min: 0,
      max:9
    },
    scrollbar: {
      enabled: true
    },
    series: [
      {
        type: 'column',
        name: 'Wind speed (m/s)',
        color: 'Grey',
        data: []
      },
      {
        type: 'column',
        name: 'Rainfall (l/m2 3h)',
        color: 'Blue',
        data: []
      },
      {
        type: 'column',
        name: 'Min. temp (ºC)',
        color: 'Yellow',
        data: []
      },
      {
        type: 'column',
        name: 'Temp (ºC)',
        color: 'Orange',
        data: []
      },
      {
        type: 'column',
        name: 'Max. temp (ºC)',
        color: 'Red',
        data: []
      },
      {
        type: 'column',
        name: 'Sun hours',
        color: 'Green',
        data: []
      },
      {
        type: 'column',
        name: 'Clouds (%)',
        color: 'Cyan',
        data: []
      }
    ]
  }
  
  // Chart config
  
  var configWeatherChart = function () {
    
    // Provinces
    var provinces = weather.map(province => province.iso.substring(3))
    weatherOptions.xAxis.categories = provinces

    // Wind speed
    weatherOptions.series[0].data = weather.map(province => province.windSpeed)
    // Rainfall
    weatherOptions.series[1].data = weather.map(province => province.rain)
    // Min temp
    weatherOptions.series[2].data = weather.map(province => province.minTemp)
    // Temp
    weatherOptions.series[3].data = weather.map(province => province.temp)
    // Max temp
    weatherOptions.series[4].data = weather.map(province => province.maxTemp)
    // Sun hours
    weatherOptions.series[5].data = weather.map(province => province.sunset - province.sunrise)
    // Clouds
    weatherOptions.series[6].data = weather.map(province => province.clouds)
    
    $('weatherChart').highcharts(weatherOptions);
  }
  
  // Init
  
  configWeatherChart()
  
  /***************************************************************************/
  
  // Energy service

  var energyService = "http://79.157.159.179:5000/api/energy/"

  // Energy production

  var eolicEnergy = []
  var hydraulicEnergy = []
  var solarEnergy = []
  
  $.ajax({
     url: energyService + 'eolic',
     dataType: 'jsonp',
     success: function (json) {
      console.debug('eolic')
      that.eolicEnergy = data
      that.eolicEnergy.sort(function (a, b) { a.iso < b.iso })
      that.configEnergyChart()
     },
     error: function () {
      console.error('No eolic energy metrics available')
     }
  })
  
  $.ajax({
     url: energyService + 'hydraulic',
     dataType: 'jsonp',
     success: function (json) {
      console.debug('hydraulic')
      that.hydraulicEnergy = data
      that.hydraulicEnergy.sort(function (a, b) { a.iso < b.iso })
      that.configEnergyChart()
     },
     error: function () {
      console.error('No hydraulic energy metrics available')
     }
  })
  
  $.ajax({
     url: energyService + 'solar',
     dataType: 'jsonp',
     success: function (json) {
      console.debug('solar')
      that.solarEnergy = data
      that.solarEnergy.sort(function (a, b) { a.iso < b.iso })
      that.configEnergyChart()
     },
     error: function () {
      console.error('No solar energy metrics available')
     }
  })

  // Chart

  var energyOptions = {
    title: {
      text: 'Energy production'
    },
    xAxis: {
      categories: [],
      min: 0,
      max:9
    },
    scrollbar: {
      enabled: true
    },
    series: [
      {
        type: 'column',
        name: 'Eolic (MW)',
        color: 'Grey',
        data: []
      },
      {
        type: 'column',
        name: 'Hydraulic (MW)',
        color: 'Blue',
        data: []
      },
      {
        type: 'column',
        name: 'Solar (MW)',
        color: 'Orange',
        data: []
      },
      {
        type: 'spline',
        name: 'Est. solar (MW)',
        color: 'Orange',
        data: [],
        marker: {
          lineWidth: 2,
          lineColor: Highcharts.getOptions().colors[3],
          fillColor: 'White'
        }
      },
      {
        type: 'spline',
        name: 'Est. hydraulic (MW)',
        color: 'Blue',
        data: [],
        marker: {
          lineWidth: 2,
          lineColor: Highcharts.getOptions().colors[3],
          fillColor: 'White'
        }
      },
      {
        type: 'spline',
        name: 'Est. eolic (MW)',
        color: 'Grey',
        data: [],
        marker: {
          lineWidth: 2,
          lineColor: Highcharts.getOptions().colors[3],
          fillColor: 'White'
        }
      }
    ]
  }

  // Chart config

  var configEnergyChart = function () {
    
    // Provinces
    var provinces = eolicEnergy.map(province => province.iso.substring(3))
    energyOptions.xAxis.categories = provinces

    // Produced eolic energy
    energyOptions.series[0].data = eolicEnergy.map(province => province.value)
    // Produced hydraulic energy
    energyOptions.series[1].data = hydraulicEnergy.map(province => province.value)
    // Produced solar energy
    energyOptions.series[2].data = solarEnergy.map(province => province.value)

    // Estimated eolic energy
    energyOptions.series[3].data = eolicEnergy.map(province => province.value * Math.random() * 2)
    // Estimated hydraulic energy
    energyOptions.series[4].data = hydraulicEnergy.map(province => province.value * Math.random() * 2)
    // Estimated solar energy
    energyOptions.series[5].data = solarEnergy.map(province => province.value * Math.random() * 2)
    
    $('#energyChart').highcharts(energyOptions);
  }

  // Init
  
  configEnergyChart()
  
});
