(function (ns) {
    function getGraphicOptions(){
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
        type: 'column',
        name: 'Eolic (MW)',
        color: 'Grey',
        data: []
      },
      {
        type: 'column',
        name: 'Hydraulic (MW)',
        color: '#95ceff',
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
        color: '#95ceff',
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

      // Peticion de produccion real de energias
      // Peticion de produccion estimada de energias

      // Ordenar de mayor producion real a menor

      // Cuando se tienen los datos anteriores, se incorporan al objeto de la siguiente manera:

      // Provincias
      var provinces = ['A Coruña', 'Vitoria', 'Albacete', 'Alicante', 'Almeria',
                'Asturias', 'Avila', 'Badajoz',
                'Palma de Mallorca', 'Barcelona', 'Bilbao', 'Burgos', 'Caceres', 'Cadiz',
                'Cantabria', 'Castellon', 'Ciudad Real', 'Cordoba', 'Cuenca', 'San Sebastian',
                'Girona', 'Granada', 'Guadalajara', 'Huelva', 'Huesca', 'Jaen', 'La Rioja',
                'Las Palmas', 'Leon', 'Lleida', 'Lugo', 'Madrid', 'Malaga',
                'Murcia', 'Navarra', 'Ourense', 'Palencia',
                'Pontevedra', 'Salamanca', 'Santa Cruz de Tenerife', 'Segovia', 'Sevilla',
                'Soria', 'Tarragona', 'Teruel', 'Toledo', 'Valencia', 'Valladolid', 'Zamora', 'Zaragoza']
      options.xAxis.categories = provinces

      // Real data (eolic, hydraulic, solar)
      options.series[0].data = []
      options.series[1].data = []
      options.series[2].data = []
      
      for (var i = 0; i < provinces.length; i++) {
        options.series[0].data.push(Math.random() * 200)
        options.series[1].data.push(Math.random() * 400)
        options.series[2].data.push(Math.random() * 100)
      }

      // Estimated data (eolic, hydraulic, solar)
      options.series[3].data = options.series[0].data.map(value => value * (1 + Math.random()))
      options.series[4].data = options.series[1].data.map(value => value * (1 + Math.random()))
      options.series[5].data = options.series[2].data.map(value => value * (1 + Math.random()))

      return options
    }



    function loadGraphics(settings){
      var graphicOptions = getGraphicOptions()
      $('#'+settings.graphicsId).highcharts(graphicOptions);
    }

    function selectProvinceInGraphics(isoCode){
      console.warn('TODO: Select in graphics.js - '+isoCode)
      // TODO: Impleemntar un filtro. Habria que relacionar el isoCode con el numero de provincia
    }


    ns.fn = ns.fn || {}
    ns.fn.loadGraphics = loadGraphics
    ns.fn.selectProvinceInGraphics = selectProvinceInGraphics

})(App);
