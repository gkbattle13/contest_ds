<template>
  <vx-card title="车辆里程">
    <div class="vx-row">
      <div class="vx-col mb-2 default-input">
        <vs-input class="inputx" placeholder="vin" v-model="vin" name="vin" v-validate="'required'"/>
        <span class="text-danger text-sm" v-show="errors.has('vin')">{{ errors.first('vin') }}</span>
      </div>
      <div class="vx-col mb-2">
        <flat-pickr :config="configs" v-model="sdatetime" placeholder="Start Time" name="sdatetime" v-validate="'required'"/>
        <div>
        <span class="text-danger text-sm" v-show="errors.has('sdatetime')">{{ errors.first('sdatetime') }}</span></div>
      </div>
      <vs-button radius color="primary" type="border" icon="search" @click="mileageCount()"/>
    </div>
    <div class="vx-row">
      <div class="vx-col w-full sm:w-1/2 md:w-1/2 lg:w-1/4 xl:w-1/4 mb-base">
        <statistics-card-line icon="TruckIcon" statisticTitle="单车行驶总里程" :statistic="mileage"
                              type='area'/>
      </div>
      <div class="vx-col w-full sm:w-1/2 md:w-1/2 lg:w-1/4 xl:w-1/4 mb-base">
        <statistics-card-line icon="TruckIcon" statisticTitle="所有车辆行驶总里程" :statistic="totalMileage"
                              type='area'/>
      </div>
      <div class="vx-col w-full sm:w-1/2 md:w-1/2 lg:w-1/4 xl:w-1/4 mb-base">
        <statistics-card-line icon="DatabaseIcon" statisticTitle="数据总行数" :statistic="dataTotal"
                              type='area'/>

      </div>
    </div>


  </vx-card>


</template>

<script>
import flatPickr from 'vue-flatpickr-component';
import 'flatpickr/dist/flatpickr.css';
import {Mandarin as China} from 'flatpickr/dist/l10n/zh.js';
import StatisticsCardLine from '@/components/statistics-cards/StatisticsCardLine.vue'
import * as moment from "moment";
// import * as moment from 'moment';

export default {
  created() {
    //  data total
    this.$http.get("/api/contest/sample/getCount")
      .then((response) => {
        this.dataTotal = response.data
      })
      .catch((error) => {
        console.log(error);

      })

    //  all data total
    this.$http.get("/api/contest/sample/totalMileage")
      .then((response) => {
        this.totalMileage = response.data
      })
      .catch((error) => {
        console.log(error)
      })
  },
  components: {
    flatPickr,
    StatisticsCardLine
  },
  name: "VehicleMileage",
  data: function () {
    return {
      vin: null,
      sdatetime: new Date(),
      mileage: 0,
      dataTotal: 0,
      totalMileage: 0,
      configs: {
        enableTime: true,
        enableSeconds: true,
        dateFormat: 'Y-m-d H:i:S',
        locale: China, // locale for this instance only
      }
    }
  },
  methods: {
    // 查询指定车辆指定日期的行驶总里程
    mileageCount: function () {
      const me = this;
      me.$validator.validateAll().then(result => {
        if (result) {
          let param = {
            deviceCode: me.vin,
            specified: moment(me.sdatetime).format("YYYY-MM-DD HH:mm:ss")
          }
          me.$http.get("/api/contest/sample/getTotalMileage", {params: param})
            .then((response) => {
              this.mileage = response.data.mileage;
            })
            .catch((error) => {
              this.$vs.notify({
                title: "",
                text: error,
                color: 'danger',
                position: 'top-right'
              });
            })
        } else {
          // form have errors
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
