(function (ns) {
    function getGraphicOptions(){
      var options = {
          title: {
              text: 'Producción energética'
          },
          xAxis: {
              categories: [],
              min: 0,
              max:9
          },
          scrollbar: {
              enabled: true
          },
          series: [{
              type: 'column',
              name: 'Solar',
              color: 'Orange',
              data: []
          }, {
              type: 'column',
              name: 'Hidráulica',
              color: '#95ceff',
              data: []
          }, {
              type: 'column',
              name: 'Eólica',
              color: 'Grey',
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
                  fillColor: 'white'
              }
          },
          {
              type: 'spline',
              name: 'Est. Hidráulica',
              color: '#95ceff',
              data: [],
              marker: {
                  lineWidth: 2,
                  lineColor: Highcharts.getOptions().colors[3],
                  fillColor: 'white'
              }
          },
          {
              type: 'spline',
              name: 'Est. Eólica',
              color: 'Grey',
              data: [],
              marker: {
                  lineWidth: 2,
                  lineColor: Highcharts.getOptions().colors[3],
                  fillColor: 'white'
              }
          }]
      }

      // Peticion de produccion real de energias
      // Peticion de produccion estimada de energias

      // Ordenar de mayor producion real a menor

      // Cuando se tienen los datos anteriores, se incorporan al objeto de la siguiente manera:

      // Provincias
      options.xAxis.categories = ['Alava', 'Albacete', 'A Coruna', 'Sevilla', 'Badajoz', 'Alava', 'Albacete', 'A Coruna', 'Sevilla', 'Badajoz', 'Alava', 'Albacete', 'A Coruna', 'Sevilla', 'Badajoz', 'Alava', 'Albacete', 'A Coruna', 'Sevilla', 'Badajoz', 'Alava', 'Albacete', 'A Coruna', 'Sevilla', 'Badajoz', 'Alava', 'Albacete', 'A Coruna', 'Sevilla', 'Badajoz', 'Alava', 'Albacete', 'A Coruna', 'Sevilla', 'Badajoz', 'Alava', 'Albacete', 'A Coruna', 'Sevilla', 'Badajoz', 'Alava', 'Albacete', 'A Coruna', 'Sevilla', 'Badajoz']

      // Datos SOLARES reales
      options.series[0].data = [3, 2, 1, 3, 4, 3, 2, 1, 3, 4, 3, 2, 1, 3, 4, 3, 2, 1, 3, 4, 3, 2, 1, 3, 4, 3, 2, 1, 3, 4, 3, 2, 1, 3, 4, 3, 2, 1, 3, 4, 3, 2, 1, 3, 4]
      // Datos HIDRAULICOS reales
      options.series[1].data = [2, 3, 5, 7, 6, 2, 3, 5, 7, 6, 2, 3, 5, 7, 6, 2, 3, 5, 7, 6, 2, 3, 5, 7, 6, 2, 3, 5, 7, 6, 2, 3, 5, 7, 6, 2, 3, 5, 7, 6, 2, 3, 5, 7, 6]
      // Datos EOLICOS reales
      options.series[2].data = [4, 3, 3, 9, 2, 4, 3, 3, 9, 2, 4, 3, 3, 9, 2, 4, 3, 3, 9, 2, 4, 3, 3, 9, 2, 4, 3, 3, 9, 2, 4, 3, 3, 9, 2, 4, 3, 3, 9, 2, 4, 3, 3, 9, 2]

      // Datos SOLARES estimados
      options.series[3].data = [2, 1, 2,2, 2.67, 3, 6.33, 3.33, 2,2, 2.67, 3, 6.33, 3.33, 2,2, 2.67, 3, 6.33, 3.33, 2,2, 2.67, 3, 6.33, 3.33, 2,2, 2.67, 3, 6.33, 3.33, 2,2, 2.67, 3, 6.33, 3.33, 2,2, 2.67, 3, 6.33, 3.33, 2]
      // Datos HIDRAULICOS estimados
      options.series[4].data = [4, 3.67, 1, 2.33, 6.33, 4, 3.67, 1, 2.33, 6.33,4, 3.67, 1, 2.33, 6.33,4, 3.67, 1, 2.33, 6.33,4, 3.67, 1, 2.33, 6.33,4, 3.67, 1, 2.33, 6.33,4, 3.67, 1, 2.33, 6.33,4, 3.67, 1, 2.33, 6.33,4, 3.67, 1, 2.33, 6.33]
      // Datos EOLICOS estimados
      options.series[5].data = [1, 5.67, 4, 4.33, 3.33, 1, 5.67, 4, 4.33, 3.33, 1, 5.67, 4, 4.33, 3.33, 1, 5.67, 4, 4.33, 3.33, 1, 5.67, 4, 4.33, 3.33, 1, 5.67, 4, 4.33, 3.33, 1, 5.67, 4, 4.33, 3.33, 1, 5.67, 4, 4.33, 3.33, 1, 5.67, 4, 4.33, 3.33]

      return options
    }



    function loadGraphics(settings){
      var graphicOptions = getGraphicOptions()
      $("#"+settings.graphicsId).highcharts(graphicOptions);
    }

    function selectProvinceInGraphics(isoCode){
      console.warn('TODO: Select in graphics.js - '+isoCode)
      // TODO: Impleemntar un filtro. Habria que relacionar el isoCode con el numero de provincia
    }


    ns.fn = ns.fn || {}
    ns.fn.loadGraphics = loadGraphics
    ns.fn.selectProvinceInGraphics = selectProvinceInGraphics

})(App);
