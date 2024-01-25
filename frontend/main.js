import ImageWMS from 'ol/source/ImageWMS.js';
import Map from 'ol/Map.js';
import OSM from 'ol/source/OSM.js';
import View from 'ol/View.js';
import {Image as ImageLayer, Tile as TileLayer} from 'ol/layer.js';

const layers = [
  new TileLayer({
    source: new OSM(),
  }),
  new ImageLayer({
    extent: [-20037508.342789, -20037508.342789, 19426991, 20037508],
    source: new ImageWMS({
      // url: 'https://ahocevar.com/geoserver/wms',
      url: 'http://localhost:8070/render/create',
      params: {LAYERS: 'topp:states'},
      ratio: 1,
      serverType: 'geoserver',
    }),
  }),
];
const map = new Map({
  layers: layers,
  target: 'map',
  view: new View({
    center: [-10997148, 4569099],
    zoom: 4,
  }),
});
