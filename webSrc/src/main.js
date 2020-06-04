import Vue from "vue";
import App from "./App";
import store from "./store";
import router from "./router";
import "./plugins";
import "leaflet/dist/leaflet.css";

import axios from 'axios';

Vue.prototype.$axios = axios;

if (process.env.NODE_ENV === "test") {
  const { mockXHR } = require("../mock/static");
  mockXHR();
}

Vue.config.productionTip = false;

new Vue({
  el: "#vue-admin-beautiful",
  router,
  store,
  render: (h) => h(App),
});
