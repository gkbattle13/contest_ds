<template>
  <vx-card title="车辆里程">
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
  </vx-card>
</template>

<script>
import flatPickr from 'vue-flatpickr-component';
import 'flatpickr/dist/flatpickr.css';
import {Mandarin as China} from 'flatpickr/dist/l10n/zh.js';
// import * as moment from 'moment';

export default {
  components: {
    flatPickr
  },
  name: "VehicleMileage",
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
      }
    }
  }
}
</script>

<style scoped>

</style>
