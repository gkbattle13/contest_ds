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
        <vs-input class="inputx" placeholder="Vin" v-model="vin"/>
      </div>
      <div class="vx-col mb-2">
        <flat-pickr :config="configs" v-model="sdatetime" placeholder="Start Time"/>
      </div>
      <div class="vx-col mb-2">
        <flat-pickr :config="configs" v-model="edatetime" placeholder="End Time"/>
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
        <button @click="add()">add marker</button>
        <button @click="track()">track</button>
        <button @click="start()">start</button>
        <button @click="stop()">stop</button>
        <button @click="destroy()">destroy</button>
        <button @click="render()">render</button>
        <button @click="hide()">hide</button>
        <button @click="show()">show</button>
        <button @click="getData()">getData</button>
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
      configs: {
        enableTime: true,
        enableSeconds: true,
        dateFormat: 'Y-m-d H:i:s',
        locale: China, // locale for this instance only
      },
      zoom: 12,
      center: [121.59996, 31.197646],
      amapManager: amapManager,
      nav: null,
      path: null,
      events: {
        // eslint-disable-next-line no-unused-vars
        init(map) {
          AMapUI.loadUI(['overlay/SimpleMarker'], function (SimpleMarker) {
            // eslint-disable-next-line no-unused-vars
            const marker = new SimpleMarker({
              iconLabel: 'A',
              iconStyle: 'red',
              map: map,
              position: map.getCenter()
            });
          });
        }
      }
    };
  },

  methods: {
    async getData() {
      console.log("getData......")
      const me = this;
      await me.$http.get("https://a.amap.com/amap-ui/static/data/big-routes.json").then(res => {
        console.log(res);
      }).catch(err => {
        console.log(err);
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
    add() {
      let o = amapManager.getMap();
      let marker = new AMap.Marker({
        position: [121.59996, 31.177646]
      });
      marker.setMap(o);
    },
    track() {
      const me = this;
      console.log(me.vin);

      console.log(moment(me.sdatetime).format("YYYY-MM-DD HH:mm:ss"));
      console.log(moment(me.edatetime).format("YYYY-MM-DD HH:mm:ss"));

      // eslint-disable-next-line no-unused-vars
      AMapUI.load(['ui/misc/PathSimplifier', 'lib/$'], function (PathSimplifier, $) {

        if (!PathSimplifier.supportCanvas) {
          alert('当前环境不支持 Canvas！');
          return;
        }

        let map = amapManager.getMap();

        // eslint-disable-next-line no-unused-vars
        var emptyLineStyle = {
          lineWidth: 0,
          fillStyle: null,
          strokeStyle: null,
          borderStyle: null
        };

        //just some colors
        var colors = [
          "#3366cc", "#dc3912", "#ff9900", "#109618", "#990099", "#0099c6", "#dd4477", "#66aa00",
          "#b82e2e", "#316395", "#994499", "#22aa99", "#aaaa11", "#6633cc", "#e67300", "#8b0707",
          "#651067", "#329262", "#5574a6", "#3b3eac"
        ];


        var pathSimplifierIns = new PathSimplifier({
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
            // renderAllPointsIfNumberBelow: 100 //绘制路线节点，如不需要可设置为-1
            //将点、线相关的style全部置emptyLineStyle
            // pathLineStyle: emptyLineStyle,
            // pathLineSelectedStyle: emptyLineStyle,
            // pathLineHoverStyle: emptyLineStyle,
            // keyPointStyle: emptyLineStyle,
            // startPointStyle: emptyLineStyle,
            // endPointStyle: emptyLineStyle,
            // keyPointHoverStyle: emptyLineStyle,
            // keyPointOnSelectedPathLineStyle: emptyLineStyle
            pathLineStyle: {
              dirArrowStyle: true
            },
            getPathStyle: function (pathItem, zoom) {

              var color = colors[pathItem.pathIndex % colors.length],
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

        window.pathSimplifierIns = pathSimplifierIns;

        me.$http.get("https://a.amap.com/amap-ui/static/data/big-routes.json").then(res => {
          console.log(res);
          //设置数据
          // pathSimplifierIns.setData([{
          //   name: '路线0',
          //   path: [
          //     [116.405289, 39.904987],
          //     [113.964458, 40.54664],
          //     [111.47836, 41.135964],
          //     [108.949297, 41.670904],
          //     [106.380111, 42.149509],
          //     [103.774185, 42.56996],
          //     [101.135432, 42.930601],
          //     [98.46826, 43.229964],
          //     [95.777529, 43.466798],
          //     [93.068486, 43.64009],
          //     [90.34669, 43.749086],
          //     [87.61792, 43.793308]
          //   ]
          // }]);
          let path = res.data[7].path;
          pathSimplifierIns.setData([{
            name: '路线0',
            path: path
          }]);

          me.path = pathSimplifierIns;

          //对第一条线路（即索引 0）创建一个巡航器
          var navg1 = pathSimplifierIns.createPathNavigator(0, {
            loop: true, // 循环播放
            speed: 1000000, // 巡航速度，单位千米/小时
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
          me.nav = navg1;

          navg1.start();
        }).catch(err => {
          console.log(err);
        })


      });
    }
  }
};
</script>
