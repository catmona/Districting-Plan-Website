import React, { useRef, useEffect, useState } from 'react';
import mapboxgl from '!mapbox-gl'; // eslint-disable-line import/no-webpack-loader-syntax
import randomColor from 'randomcolor';
import nvDistricts from './data/NV/2012/nv-0-2012.geojson';
import arDistricts from './data/AR/2012/ar-0-2012.geojson';
import waDistricts from './data/WA/2012/wa-0-2012.geojson';

mapboxgl.accessToken = 'pk.eyJ1IjoiZ29sZHlmbGFrZXMiLCJhIjoiY2t0ZGtrNHhiMDB5MjJxcWN6bWZ5ZGx3byJ9.IMzQecUSVBFlT4rUycdG3Q';

// constants for styling districts on map
const districtColors = randomColor({count: 10, luminosity: 'bright', seed: 'mavericks'});
//const districtColors = ['#d3a6e0', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff' ];
console.log(districtColors);
const white = '#FFFFFF'; // white

function Map(props) {
    const mapContainer = useRef(null);
    const map = useRef(null);
    const [lng, setLng] = useState(-95.9);
    const [lat, setLat] = useState(40.35);
    const [zoom, setZoom] = useState(3);
    let stateName = props.stateName;

    useEffect(() => {
        if (map.current) {
            /* To minimize network calls in the future */
            // if (stateName) { // user selected a state
            //     let stateGeoJSON = sessionStorage.getItem(stateName + "-State-2012"); // hardcoded because all we have are 2012 geojsons
            //     if (!stateGeoJSON) {
            //         // user did not load the requested state map before
            //         let url = "http://localhost:8080/api" + "/districts?state=" + stateName + "&file=State&year=2012";
            //         fetch(url)
            //             .then(res => res.json())
            //             .then(
            //                 (result) => {
            //                     let mapGeoJson = JSON.stringify(result);
            //                     sessionStorage.setItem(stateName + "-State-2012", mapGeoJson);
            //                     addDistrictGeoJSON(map.current, stateName, result);
            //                 },
            //                 (error) => {
            //                     console.log(error); // failed to fetch geojson
            //                 }
            //             )
            //     } else {
            //         // state map is saved in session storage
            //         let layer = map.current.getSource(stateName + "-district-source");
            //         console.log("[Mapbox] Looking for %s", stateName + "-district-source");
            //         if (!layer) {
            //             console.log("[Mapbox] %s source layer is not on the map", stateName);
            //             addDistrictGeoJSON(map.current, stateName, JSON.parse(stateGeoJSON));
            //         }
            //     }
            //     // if user already loaded the requested state map, the map layer should already be on the map so no need to do anything else.
            // }

            switch(stateName) {
                case "Washington": 
                    console.log("w")
                    setLat(47.75);
                    setLng(-120.74);
                    setZoom(6);
                    map.current.flyTo({
                        center: [lng, lat],
                        zoom: zoom
                    });
                    break;

                case "Nevada": 
                    console.log("n")
                    setLat(38.80);
                    setLng(-116.42);
                    setZoom(6);
                    map.current.flyTo({
                        center: [lng, lat],
                        zoom: zoom
                    });
                    break;

                case "Arkansas": 
                    console.log("a")
                    setLat(35.20);
                    setLng(-91.83);
                    setZoom(6);
                    map.current.flyTo({
                        center: [lng, lat],
                        zoom: zoom
                    });
                    break;
            }
            console.log("flying to " + stateName)
        } // initialize map only once
        else {
            map.current = new mapboxgl.Map({
                container: mapContainer.current,
                style: 'mapbox://styles/goldyflakes/cktdkm1j51fmq18qj54fippsz',
                center: [lng, lat],
                zoom: zoom,
                interactive: false
                });

            map.current.on("load", function() {
                // await style loading before loading district layers
                // removed for now since individual state enacted districts are now loaded asynchronously.
                // We should load the geojson for the state outlines here in the future, so the starting map is not blank. 
                // user did not load the requested state map before
                var states = ["Washington", "Nevada", "Arkansas"];
                states.forEach(s => {
                    let url = "http://localhost:8080/api" + "/districts?state=" + s + "&file=State&year=2012";
                    fetch(url)
                    .then(res => res.json())
                    .then(
                        (result) => {
                            let mapGeoJson = JSON.stringify(result);
                            sessionStorage.setItem(s + "-State-2012", mapGeoJson);
                            addDistrictGeoJSON(map.current, s, result);
                        },
                        (error) => {
                            console.log(error); // failed to fetch geojson
                        }
                    );
                });
                // addDistrictGeoJSON(map.current, "NV", nvDistricts);
                // addDistrictGeoJSON(map.current, "AR", arDistricts);
                // addDistrictGeoJSON(map.current, "WA", waDistricts);
            });
        }
    });
  
    return (
        <div className = "map-wrapper">
            <div className = "map-fade" />
            <div ref={mapContainer} className = "map-container" />
        </div>
    );
}

/**
 * Adds a district's GeoJSON to the map and styles it. 
 * @param {*} map mapboxgl.Map object
 * @param {*} sourceId id of the source (pass in the abbr. e.g.: NV, AR, WA)
 * @param {*} source the GeoJSON file
 * @returns id of the source added. Sources are named: [State abbv]-district-source
 */
function addDistrictGeoJSON(map, sourceId, source) {
    let sourceName = sourceId + "-district-source";
    map.addSource(sourceName, {
        "type": "geojson",
        "data": source
    });
    console.log("[Mapbox] Added %s", sourceName);
    
    addDistrictStyleLayer(map, sourceName);
    return sourceName;
}

/**
 * YOU PROBABLY MEAN TO CALL addDistrictGeoJSON().
 * Adds a district style layer to the given map source.
 * The districts should have a property called "District_Name"
 * denoting which # district it is in the state. This colors the
 * district the designated color.
 * @param {*} map mapboxgl.Map object
 * @param {*} sourceId id of the source
 * @returns id of the layer added. Layers are named: [State abbv]-district-source-layer
 */
function addDistrictStyleLayer(map, sourceId) {
    // this is really just to stop anyone from mistakenly using this method. I meant for it to be used w/ addDistrictGeoJSON.
    if (!map.getSource(sourceId)) {
        console.log("Must add source before applying style layer.");
        return;
    }

    let layerName = sourceId + "-layer";
    map.addLayer({
        "id": layerName,
        "type": "fill",
        "source": sourceId,
        "layout": {},
        "paint": {
            "fill-color": [
                'match',
                ['get', 'District'],
                1,
                districtColors[0],
                2,
                districtColors[1],
                3,
                districtColors[2],
                4,
                districtColors[3],
                5,
                districtColors[4],
                6,
                districtColors[5],
                7,
                districtColors[6],
                8,
                districtColors[7],
                9,
                districtColors[8],
                10,
                districtColors[9],
                /* other */ white
            ],
            "fill-opacity": [
                "case",
                ["boolean", ["feature-state", "hover"], false],
                0.8, 0.5
            ],
        }
    });

    // adds polygon outlines with adjustable width
    map.addLayer({
        'id': layerName + '-outline',
        'type': 'line',
        'source': sourceId,
        'paint': {
          'line-color': white,
          'line-width': 1
        }
      });

    // Add a symbol layer
    map.addLayer({
        'id': layerName + '-label',
        'type': 'symbol',
        'source': sourceId,
        'layout': {
        // get the title name from the source's "title" property
        'text-field': ['get', 'District_Name'],
        'text-font': [
        'Open Sans Semibold',
        'Arial Unicode MS Bold'
        ],
        'text-anchor': 'center'
        }
        });

    return layerName;
}

export default Map;