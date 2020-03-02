<!-- =========================================================================================
    File Name: GMapBasic.vue
    Description: G map Basic
    ----------------------------------------------------------------------------------------
    Item Name: Vuexy - Vuejs, HTML & Laravel Admin Dashboard Template
      Author: Pixinvent
    Author URL: http://www.themeforest.net/user/pixinvent
========================================================================================== -->


<template>
  <vx-card title="车辆轨迹">
    <!--    <p>Creating basic Gmap</p>-->
    <div class="vx-row">
      <!--      <vs-col vs-type="flex" vs-justify="center" vs-align="center" vs-w="6">-->
      <!--        <vs-input class="inputx" placeholder="Vin" v-model="value1"/>-->
      <!--      </vs-col>-->

      <div class="vx-col mb-2 default-input">
        <vs-input class="inputx" placeholder="vin" v-model="vin" name="vin" v-validate="'required'"/>
        <span class="text-danger text-sm" v-show="errors.has('vin')">{{ errors.first('vin') }}</span>
      </div>
      <div class="vx-col mb-2">
        <flat-pickr :config="configs" v-model="sdatetime" placeholder="Start Time" name="sdatetime"
                    v-validate="'required'"/>
        <div>
          <span class="text-danger text-sm" v-show="errors.has('sdatetime')">{{ errors.first('sdatetime') }}</span>
        </div>
      </div>
      <div class="vx-col mb-2">
        <flat-pickr :config="configs" v-model="edatetime" placeholder="End Time" name="edatetime"
                    v-validate="'required'"/>
        <div>
          <span class="text-danger text-sm" v-show="errors.has('edatetime')">{{ errors.first('edatetime') }}</span>
        </div>
      </div>
      <vs-button radius color="primary" type="border" icon="search" @click="track()"/>
    </div>


    <div class="mt-5">
      <div>
        <el-amap vid="amapDemo" :center="center" :amap-manager="amapManager" :zoom="zoom"
                 style="width: 100%; height: 600px" :events="events">
        </el-amap>
      </div>

      <div>
        <button @click="clear()">clear</button>
      </div>
    </div>
  </vx-card>
</template>

<script>
import VueAMap from "vue-amap";
import CarUrl from "../../../../assets/images/elements/car.png";
import flatPickr from 'vue-flatpickr-component';
import 'flatpickr/dist/flatpickr.css';
// import 'flatpickr/dist/themes/material_blue.css';
import {Mandarin as China} from 'flatpickr/dist/l10n/zh.js';
import * as moment from 'moment';
// import {loadMP} from '../../../../plugins/asyncLoadAmap.js'

let amapManager = new VueAMap.AMapManager();

export default {
  components: {
    flatPickr
  },
  data: function () {
    return {
      vin: null,
      sdatetime: new Date(),
      edatetime: new Date(),
      pathSimplifier: null,
      configs: {
        enableTime: true,
        enableSeconds: true,
        dateFormat: 'Y-m-d H:i:S',
        locale: China, // locale for this instance only
      },
      zoom: 12,
      center: [121.59996, 31.197646],
      amapManager: amapManager,
      nav: null,
      path: null,
      pathData: [],
      events: {
        // eslint-disable-next-line no-unused-vars
        init(map) {
          // AMapUI.loadUI(['overlay/SimpleMarker'], function (SimpleMarker) {
          //   // eslint-disable-next-line no-unused-vars
          //   const marker = new SimpleMarker({
          //     iconLabel: 'A',
          //     iconStyle: 'red',
          //     map: map,
          //     position: map.getCenter()
          //   });
          // });

          AMap.plugin('AMap.Geolocation', function () {
            var geolocation = new AMap.Geolocation({
              enableHighAccuracy: true,//是否使用高精度定位，默认:true
              timeout: 10000,          //超过10秒后停止定位，默认：5s
              buttonPosition: 'RB',    //定位按钮的停靠位置
              buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
              zoomToAccuracy: true,   //定位成功后是否自动调整地图视野到定位点

            });
            map.addControl(geolocation);
            geolocation.getCurrentPosition(function (status, result) {
              if (status === 'complete') {
                console.log(result);
              } else {
                console.log(result);
              }
            });
          });
        }
      }
    };
  },

  methods: {
    async getData() {
      let auth = {
        "accountNonExpired": true,
        "accountNonLocked": true,
        "authorities": [
          {
            "authority": ""
          }
        ],
        "credentialsNonExpired": true,
        "enabled": true,
        "moduleType": "1",
        "password": "",
        "username": "",
        "u": "18607157691",
        "p": "123456",
        "ts": 123456,
        "v": "NA"
      };
      const me = this;
      await me.$http.post("/api/plat/auth/login", auth).then(res => {
        console.log(res);
      }).catch(err => {
        this.$vs.notify({
          title: "",
          text: err,
          color: 'danger',
          position: 'top-left'
        });
      })
    },
    stop() {
      const me = this;
      me.nav.pause();
    },
    start() {
      const me = this;
      me.nav.resume();
    },
    destroy() {
      const me = this;
      me.nav.destroy();
    },
    render() {
      const me = this;
      me.path.render();
    },
    hide() {
      const me = this;
      me.path.hide();
    },
    show() {
      const me = this;
      me.path.show();
    },
    clear() {
      const me = this;
      if (me.path != null) {
        me.path.setData([]);
      }
      if (me.nav != null) {
        me.nav.destroy();
      }
    },
    pathSet() {
      const me = this;
      me.clear();
      me.path.setData([{
        name: '路线1',
        path: me.pathData[5].path
      }]);

      let newNav = me.path.createPathNavigator(0, {
        loop: true, // 循环播放
        speed: 1000000, // 巡航速度，单位千米/小时
        pathNavigatorStyle: {
          width: 16,
          height: 32,
          //使用图片
          content: me.pathSimplifier.Render.Canvas.getImageContent(CarUrl, onload, onerror),
          strokeStyle: null,
          fillStyle: null,
          // //经过路径的样式
          pathLinePassedStyle: {
            lineWidth: 6,
            strokeStyle: 'black',
            dirArrowStyle: {
              stepSpace: 15,
              strokeStyle: 'red'
            }
          }
        }
      });
      newNav.start();
      me.nav = newNav;
    },
    add() {
      let o = amapManager.getMap();
      let marker = new AMap.Marker({
        position: [121.59996, 31.177646]
      });
      marker.setMap(o);
    },
    track() {
      const me = this;

      // 清除之前的轨迹
      me.clear();
      // eslint-disable-next-line no-unused-vars
      AMapUI.load(['ui/misc/PathSimplifier', 'lib/$'], function (PathSimplifier, $) {
        if (!PathSimplifier.supportCanvas) {
          me.$vs.notify({
            title: "",
            text: "当前环境不支持 Canvas!",
            color: 'danger',
            position: 'top-right'
          });
          return;
        }

        me.pathSimplifier = PathSimplifier;
        let map = amapManager.getMap();

        // just some colors
        let colors = [
          "#3366cc", "#dc3912", "#ff9900", "#109618", "#990099", "#0099c6", "#dd4477", "#66aa00",
          "#b82e2e", "#316395", "#994499", "#22aa99", "#aaaa11", "#6633cc", "#e67300", "#8b0707",
          "#651067", "#329262", "#5574a6", "#3b3eac"
        ];


        let pathSimplifierIns = new PathSimplifier({
          zIndex: 100,
          //autoSetFitView:false,
          map: map, //所属的地图实例
          // eslint-disable-next-line no-unused-vars
          getPath: function (pathData, pathIndex) {
            return pathData.path;
          },
          getHoverTitle: function (pathData, pathIndex, pointIndex) {
            if (pointIndex >= 0) {
              //point
              return pathData.name + '，点：' + pointIndex + '/' + pathData.path.length;
            }
            return pathData.name + '，点数量' + pathData.path.length;
          },

          renderOptions: {
            pathLineStyle: {
              dirArrowStyle: true
            },
            getPathStyle: function (pathItem, zoom) {

              let color = colors[pathItem.pathIndex % colors.length],
                lineWidth = Math.round(4 * Math.pow(1.1, zoom - 3));

              return {
                pathLineStyle: {
                  strokeStyle: color,
                  lineWidth: lineWidth
                },
                pathLineSelectedStyle: {
                  lineWidth: lineWidth + 2
                },
                pathNavigatorStyle: {
                  fillStyle: color
                }
              };
            }
          }
        });

        // window.pathSimplifierIns = pathSimplifierIns; "https://a.amap.com/amap-ui/static/data/big-routes.json"
        me.$validator.validateAll().then(result => {
          if (result) {
            me.$vs.loading();
            let dataUrl = "/api/contest/sample/queryTrack";
            let param = {
              "deviceCode": me.vin,
              "startTime": moment(me.sdatetime).format("YYYY-MM-DD HH:mm:ss"),
              "endTime": moment(me.edatetime).format("YYYY-MM-DD HH:mm:ss")
            };

            me.$http.get(dataUrl, {params: param}).then(res => {
              let path = res.data;
              let newPath;
              newPath = path.map(item => {
                return [item.longitude, item.latitude];
              });
              me.pathData = newPath;
              pathSimplifierIns.setData([{
                name: '路线0',
                path: newPath
              }]);

              me.path = pathSimplifierIns;

              // 创建巡航器
              var nav = pathSimplifierIns.createPathNavigator(0, {
                loop: true, // 循环播放
                speed: 4000, // 巡航速度，单位千米/小时
                pathNavigatorStyle: {
                  width: 16,
                  height: 32,
                  //使用图片
                  content: PathSimplifier.Render.Canvas.getImageContent(CarUrl, onload, onerror),
                  strokeStyle: null,
                  fillStyle: null,
                  // //经过路径的样式
                  pathLinePassedStyle: {
                    lineWidth: 6,
                    strokeStyle: 'black',
                    dirArrowStyle: {
                      stepSpace: 15,
                      strokeStyle: 'red'
                    }
                  }
                }
              });

              me.nav = nav;
              nav.start();
              me.$vs.loading.close();
            }).catch(err => {
              me.$vs.notify({
                title: "",
                text: err,
                color: 'danger',
                position: 'top-right'
              });
              me.$vs.loading.close();
            })
          } else {
            // form have errors
          }
        })
      });
    }
  }
};
</script>
