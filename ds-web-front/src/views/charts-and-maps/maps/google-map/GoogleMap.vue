<!-- =========================================================================================
    File Name: GoogleMao.vue
    Description: Google Map (third-party) - Imports page portions
    ----------------------------------------------------------------------------------------
    Item Name: Vuexy - Vuejs, HTML & Laravel Admin Dashboard Template
      Author: Pixinvent
    Author URL: http://www.themeforest.net/user/pixinvent
========================================================================================== -->

<template>
  <div id="extra-component-google-map-demo">

    <p class="mb-4"><a href="https://xkjyeah.github.io/vue-google-maps/" target="_blank" rel="nofollow"></a>
    </p>

    <vehicle-mileage/>
    <g-map-basic/>

    <!--    <google-map-basic></google-map-basic>-->
    <!--    <google-map-info-window></google-map-info-window>-->
    <!--    <google-map-street-view></google-map-street-view>-->

    <!--    <div class="content-area__content">-->
    <!--    </div>-->
  </div>
</template>

<script>
import GoogleMapBasic from "./GoogleMapBasic.vue"
import GMapBasic from "./GMapBasic.vue"
import GoogleMapInfoWindow from "./GoogleMapInfoWindow.vue"
import GoogleMapStreetView from "./GoogleMapStreetView.vue"
import VehicleMileage from "./VehicleMileage.vue"

export default {
  components: {
    VehicleMileage,
    GMapBasic,
    GoogleMapBasic,
    GoogleMapInfoWindow,
    GoogleMapStreetView,
  },
}
</script>
