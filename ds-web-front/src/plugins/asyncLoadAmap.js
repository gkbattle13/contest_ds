// 异步加载高德地图API
export function loadMP() {
  const mp = new Promise(function (resolve, reject) {
    let hasLoaded1 = document.getElementById("amap");
    if(hasLoaded1) { // 只加载一次
      return
    }
    window.init = function () {
      resolve(AMap)
    };
    let script = document.createElement("script");
    script.type = "text/javascript";
    script.src = "//webapi.amap.com/maps?v=1.4.4&key=a571b7296a612ede8a843c6b0ce52c93&plugin=AMap.Driving,AMap.Geocoder,AMap.ToolBar&callback=init";
    script.id = "amap";
    script.onerror = reject;
    document.head.appendChild(script);
  });
  const mpUI = new Promise(function (resolve,reject) {
    let hasLoaded2 = document.getElementById("amapUI");
    if(hasLoaded2) { // 只加载一次
      return
    }
    let script2 = document.createElement("script");
    script2.type = "text/javascript";
    script2.src = "//webapi.amap.com/ui/1.0/main.js";
    script2.id = 'amapUI';
    script2.onerror = reject;
    script2.onload = function(su){
      resolve(AMapUI)
    };
    document.head.appendChild(script2);
  });
  return Promise.all([mp,mpUI])
    .then(function (result) {
      return result
    }).catch(e=>{
      console.log(e);})
}
