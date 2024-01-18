// initialize Leaflet
var map = L.map('map').setView({ lon: 0, lat: 0 }, 2);

// add the OpenStreetMap tiles
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
  maxZoom: 19,
  attribution: '&copy; <a href="https://openstreetmap.org/copyright">OpenStreetMap contributors</a>'
}).addTo(map);

let imageOverlay;



// show the scale bar on the lower left corner
L.control.scale().addTo(map);

// show a marker on the map
L.marker({ lon: 59.934280, lat: 30.335098 }).bindPopup('The center of the world').addTo(map);
function send() {
  if (imageOverlay != null) {
    map.removeLayer(imageOverlay)
  }
  let min = document.getElementById('minPoint').value.trim()
  let max = document.getElementById('maxPoint').value.trim()
  let width = document.getElementById('width').value.trim()
  let height = document.getElementById('height').value.trim()
  let maxX = max.split(" ")[0].trim()
  let maxY = max.split(" ")[1].trim()
  let minX = min.split(" ")[0].trim()
  let minY = min.split(" ")[1].trim()
  let imageUrl = `http://localhost:8070/render/create?width=${width}&height=${height}&minX=${minX}&minY=${minY}&maxX=${maxX}&maxY=${maxY}`;
  fetch(imageUrl, { mode: 'no-cors' })
    .then((response) => {
      let blob = new Blob(
        [response.data],
        { type: response.headers['content-type'] }
      )
      let image = window.webkitURL.createObjectURL(blob)
      return image
    })
    .then(blob => {
      document.getElementById("img").setAttribute('src', blob);
      console.log(imageUrl)
      let latLngBounds = L.latLngBounds([[0, 0], [width, height]]);
      imageOverlay = L.imageOverlay(imageUrl, latLngBounds, {
        opacity: 0.8,
        interactive: true
      }).addTo(map);

      console.log("created")
    });

}