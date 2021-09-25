import React, { useRef, useEffect, useState } from 'react';
import mapboxgl from '!mapbox-gl'; // eslint-disable-line import/no-webpack-loader-syntax
import randomColor from 'randomcolor';
import nvDistricts from './data/NV/2012/nv-0-2012.geojson';
import arDistricts from './data/AR/2012/ar-0-2012.geojson';
import waDistricts from './data/WA/2012/wa-0-2012.geojson';

mapboxgl.accessToken = 'pk.eyJ1IjoiZ29sZHlmbGFrZXMiLCJhIjoiY2t0ZGtrNHhiMDB5MjJxcWN6bWZ5ZGx3byJ9.IMzQecUSVBFlT4rUycdG3Q';

// constants for styling districts on map
const districtColors = randomColor({count: 10, luminosity: 'bright', seed: 'mavericks'});
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
                style: 'mapbox://styles/goldyflakes/cktox1jbl1omg17nt35gafjid',
                center: [lng, lat],
                zoom: zoom,
                interactive: false
                });

            map.current.on("load", function() {
                // await style loading before loading district layers
                addDistrictGeoJSON(map.current, "NV", nvDistricts);
                addDistrictGeoJSON(map.current, "AR", arDistricts);
                addDistrictGeoJSON(map.current, "WA", waDistricts);
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
                ['get', 'District_Name'],
                '1',
                districtColors[0],
                '2',
                districtColors[1],
                '3',
                districtColors[2],
                '4',
                districtColors[3],
                '5',
                districtColors[4],
                '6',
                districtColors[5],
                '7',
                districtColors[6],
                '8',
                districtColors[7],
                '9',
                districtColors[8],
                '10',
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

    return layerName;
}

export default Map;