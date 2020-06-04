<template>
  <div id="cesiumContainer" class="test-container"></div>
</template>
<script>
// 局部组件引用
import {
  Viewer,
  WebMapTileServiceImageryProvider,
  createWorldTerrain,
  GeoJsonDataSource,
  Cartesian3,
  Color,
  ScreenSpaceEventType,
  GridImageryProvider,
  GeographicTilingScheme,
  TileCoordinatesImageryProvider,
  UrlTemplateImageryProvider,
  CesiumTerrainProvider,
  ColorGeometryInstanceAttribute,
  PerInstanceColorAppearance,
  GeometryInstance,
  Primitive,
  GroundPrimitive,
  CircleGeometry,
  RectangleGeometry,
  Rectangle,
  LabelCollection,
  PolylineColorAppearance,
  PolylineGeometry,
  Math,
  Ion,
} from "cesium/Cesium";
import "cesium/Widgets/widgets.css";

let viewer;

export default {
  name: "ColorMap",
  data() {
    return { show: true };
  },
  created() {},
  mounted() {
    this.initMap();
  },
  methods: {
    initMap() {
      Ion.defaultAccessToken =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiJjZTRhODJiMC1iYWE0LTQ5OWUtOWQ1OS02MDgyYzBhZWE5M2QiLCJpZCI6Mjg1MjUsInNjb3BlcyI6WyJhc3IiLCJnYyJdLCJpYXQiOjE1OTExNzQ0Mzh9.haT8fq-Q-F3PtfUBFNlblRRZe0CmSc5n17qeDvgnOJg";

      let guge = new WebMapTileServiceImageryProvider({
        url:
          "http://t0.tianditu.com/img_w/wmts?service=wmts&request=GetTile&version=1.0.0&LAYER=img&tileMatrixSet=w&TileMatrix={TileMatrix}&TileRow={TileRow}&TileCol={TileCol}&style=default&format=tiles&tk=7711a24780452f03bb7c02fba98183b9",
        layer: "tdtVecBasicLayer",
        style: "default",
        format: "image/jpeg",
        tileMatrixSetID: "GoogleMapsCompatible",
        show: true,
      });

      let terrain = new createWorldTerrain({
        // requestWaterMask: true,
        requestVertexNormals: true,
        terrainExaggeration: 5,
      });

      viewer = new Viewer("cesiumContainer", {
        imageryProvider: guge,
        terrainProvider: terrain,
        // 需要进行可视化的数据源的集合
        animation: false, // 是否显示动画控件
        shouldAnimate: true,
        homeButton: false, // 是否显示Home按钮
        fullscreenButton: false, // 是否显示全屏按钮
        baseLayerPicker: false, // 是否显示图层选择控件
        geocoder: false, // 是否显示地名查找控件
        timeline: false, // 是否显示时间线控件
        sceneModePicker: true, // 是否显示投影方式控件
        navigationHelpButton: false, // 是否显示帮助信息控件
        infoBox: false, // 是否显示点击要素之后显示的信息
        requestRenderMode: true, // 启用请求渲染模式
        scene3DOnly: false, // 每个几何实例将只能以3D渲染以节省GPU内存
        sceneMode: 3, // 初始场景模式 1 2D模式 2 2D循环模式 3 3D模式  Cesium.SceneMode
        fullscreenElement: document.body, // 全屏时渲染的HTML元素 暂时没发现用处,
        selectionIndicator: false,
      });
      viewer._cesiumWidget._creditContainer.style.display = "none";

      viewer.scene.camera.setView({
        destination: new Cartesian3.fromDegrees(
          108.845808,
          34.214282,
          20000000
        ),
      });
    },

    fillColor(val, valArray, colorArray) {
      var color = null;
      for (var i = 0; i < valArray.length; i++) {
        if (val < valArray[i]) {
          color = colorArray[i];
          break;
        }
      }
      if (color === null && val >= valArray[valArray.length - 1]) {
        color = colorArray[colorArray.length - 1];
      }
      return color;
    },
  },
};
</script>
