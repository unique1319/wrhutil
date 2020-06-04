<template>
  <div id="map"></div>
</template>
<script>
import * as L from "leaflet";

let map;

export default {
  name: "Map",
  data() {
    return { show: false };
  },
  created() {},
  mounted() {
    map = L.map("map", {
      center: [31.87, 120.55],
      zoom: 13,
    });
    L.tileLayer(
      "http://webrd0{s}.is.autonavi.com/appmaptile?lang=zh_cn&size=1&scale=1&style=8&x={x}&y={y}&z={z}",
      {
        subdomains: ["1", "2", "3", "4"],
        attribution: "高德",
      }
    ).addTo(map);
  },
  methods: {},
};
</script>

<style scoped>
#map {
}
</style>
