$(function (ns) {
  var mapProvincesToIsoCode = {
    36: 'ES-PO',
    15: 'ES-C',
    32: 'ES-OR',
    27: 'ES-LU'
    //TODO: completar usando spain-map.js + procinces.json
  }

  function draw(id){
    new SpainMap({
      id: 'map',
      width: 700,
      height: 500,
      fillColor: "#eeeeee",
      strokeColor: "#cccccc",
      strokeWidth: 0.7,
      selectedColor: "#66bbdd",
      animationDuration: 200,
      onClick: function(province, event) {
        alert("Has seleccionado " + mapProvincesToIsoCode[province.number]);
      }
      //onMouseOver: function(province, event) {
      //  console.log('Navigating through ' + province.name);
      //},
      //onMouseOut: function(province, event) {
      //  console.log('Leaving ' + province.name);
      //}
    });
  }

  function selectProvince(isoCode){
    alert('TODO: Highlight province '+isoCode)
  }

  ns.provinces = {
    draw: draw,
    selectProvince: selectProvince
  }
})(App);
