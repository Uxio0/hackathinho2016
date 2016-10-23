(function () {
  // Create app ns
  var App = App || {}
  App.fn = App.fn || {}
  window.App = App


  function init(options){
    //console.log('Init app')

    App.fn.loadMap({
      mapId: options.mapId,
      onClickProvince: function(province, event) {
        var provinceIsoCode = App.fn.getProvinceIsoCode(province)
        if (provinceIsoCode){
          alert("Has seleccionado " + provinceIsoCode);
          App.fn.selectProvinceInGraphics(provinceIsoCode)
        } else {
           alert("TODO: Falta mapear la provincia " + province.name + ' ('+province.number+')');
        }

      }
    })
    App.fn.loadGraphics({
      graphicsId: options.graphicsId,
      onClickProvince: function(){
        // ¿Dejaremos clickar en las provincias del grafico?
      }
    })
  }

  App.fn.init = init
})();
