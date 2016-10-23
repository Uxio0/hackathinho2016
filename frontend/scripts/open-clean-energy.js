$(function () {

  // Energy server

  var energy = "http://83.36.17.177:5000/api/energy/"

  // Energy production

  var eolicEnergy = []
  var hydraulicEnergy = []
  var solarEnergy = []

  var that = this
  $.get(energy + 'eolic', function (data) {
    that.eolicEnergy = data
    that.configChart()
    console.debug('eolic')
  })
  $.get(energy + 'hydraulic', function (data) {
    that.hydraulicEnergy = data
    that.configChart()
    console.debug('hydraulic')
  })
  $.get(energy + 'solar', function (data) {
    that.solarEnergy = data
    that.configChart()
    console.debug('solar')
  })

  // Data

  eolicEnergy.sort(function (a, b) { a.iso < b.iso })
  hydraulicEnergy.sort(function (a, b) { a.iso < b.iso })
  solarEnergy.sort(function (a, b) { a.iso < b.iso })

  var provinces = eolicEnergy.map(province => province.iso)

  // Chart

  var options = {
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
          type: 'column',// Energy server
          name: 'Eolic',
          color: 'Grey',
          data: []
      },
      {
          type: 'column',
          name: 'Hydraulic',
          color: 'Blue',
          data: []
      },
      {
          type: 'column',
          name: 'Solar',
          color: 'Orange',
          data: []
      },
      {
          type: 'spline',
          name: 'Est. solar',
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
          name: 'Est. hydraulic',
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
          name: 'Est. eolic',
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

  var configChart = function () {
    
    // Provinces
    options.xAxis.categories = provinces

    // Produced eolic energy
    options.series[0].data = eolicEnergy.map(province => province.value)
    // Produced hydraulic energy
    options.series[1].data = hydraulicEnergy.map(province => province.value)
    // Produced solar energy
    options.series[2].data = solarEnergy.map(province => province.value)

    // Estimated eolic energy
    options.series[3].data = eolicEnergy.map(province => province.value * Math.random() * 2)
    // Estimated hydraulic energy
    options.series[4].data = hydraulicEnergy.map(province => province.value * Math.random() * 2)
    // Estimated solar energy
    options.series[5].data = solarEnergy.map(province => province.value * Math.random() * 2)
    
    $('#container').highcharts(options);
  }

  // Init
  
  configChart()
  
});
