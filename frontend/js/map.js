(function (ns) {
  function SpainMap(config) {
    var R, attr, load, mapBBox, province, _i, _len;
    var spainMap = ns.data.spainMap;
    R = Raphael(config.id, config.width, config.height);

    attr = {
      "fill": config.fillColor || "#d3d3d3",
      "stroke": config.strokeColor || "#fff",
      "stroke-width": config.strokeWidth || "0.75"
    };

    load = function(province) {
      var path;

      path = R.path(province.path).attr(attr);
      delete province.path;
      path[0].style.cursor = "pointer";
      path[0].onmouseover = function(e) {
        path.attr({
          fill: config.selectedColor
        });
        R.safari();
        if (config.onMouseOver) {
          return config.onMouseOver(province, e, path);
        }
      };
      path[0].onmouseout = function(e) {
        path.animate({
          fill: config.fillColor
        }, config.animationDuration || 500);
        R.safari();
        if (config.onMouseOut) {
          return config.onMouseOut(province, e, path);
        }
      };
      if (config.onClick) {
        return path[0].onclick = function(e) {
          return config.onClick(province, e, path);
        };
      }
    };

    for (_i = 0, _len = spainMap.length; _i < _len; _i++) {
      province = spainMap[_i];
      load(province);
    }
    mapBBox = {
      x: 150.522,
      y: 11.305,
      width: 416.74600000000004,
      height: 348.17
    };

    R.setViewBox(mapBBox.x, mapBBox.y, mapBBox.width, mapBBox.height, true);
    return spainMap = null;
  };

  var mapProvincesToIsoCode = {
    36: 'ES-PO',
    15: 'ES-C',
    32: 'ES-OR',
    27: 'ES-LU'
    //TODO: completar usando spain-map.js + procinces.json
  }

  function loadMap(options){
    //console.log('loadMap: '+options.mapId)
    new SpainMap({
      id: options.mapId,
      width: 700,
      height: 500,
      fillColor: "#eeeeee",
      strokeColor: "#cccccc",
      strokeWidth: 0.7,
      selectedColor: "#66bbdd",
      animationDuration: 200,
      onClick: options.onClickProvince
      //onMouseOver: function(province, event) {
      //  console.log('Navigating through ' + province.name);
      //},
      //onMouseOut: function(province, event) {
      //  console.log('Leaving ' + province.name);
      //}
    });
  }

  function getProvinceIsoCode(province){
    return mapProvincesToIsoCode[province.number]
  }

  ns.fn = ns.fn || {}
  ns.fn.loadMap = loadMap
  ns.fn.getProvinceIsoCode = getProvinceIsoCode
})(App);
