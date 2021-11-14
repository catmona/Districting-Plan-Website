import React, { useRef, useEffect, useState } from 'react';
import mapboxgl from '!mapbox-gl'; // eslint-disable-line import/no-webpack-loader-syntax
import randomColor from 'randomcolor';
import USADistricts from './data/us_state_outlines.geojson';

mapboxgl.accessToken = 'pk.eyJ1IjoiZ29sZHlmbGFrZXMiLCJhIjoiY2t0ZGtrNHhiMDB5MjJxcWN6bWZ5ZGx3byJ9.IMzQecUSVBFlT4rUycdG3Q';

// constants for styling districts on map
const districtColors = randomColor({count: 10, luminosity: 'bright', seed: 'mavericks'});
//const districtColors = ['#d3a6e0', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff' ];
//console.log(districtColors);
const white = '#FFFFFF'; // white
const zoomThreshold = 4;

function Map(props) {
    const mapContainer = useRef(null);
    const map = useRef(null);
    const [lng, setLng] = useState(-98.9);
    const [lat, setLat] = useState(40.35);
    const [zoom, setZoom] = useState(3.6);
    const [bounds, setBounds] = useState([[-138.42, 12.22], [-56.80, 57.27]])
    let stateName = props.stateName;

    useEffect(() => {
        if (map.current) {
            /* Get geoJson on demand */
            getGeojson(stateName);

            //focus map on selected state
            flyToState(stateName);

        } // initialize map only once
        else {
            map.current = new mapboxgl.Map({
                container: mapContainer.current,
                style: 'mapbox://styles/goldyflakes/cktdkm1j51fmq18qj54fippsz',
                center: [lng, lat],
                zoom: zoom,
                maxBounds: bounds,
                });

            map.current.on("load", function() {
                setLng(map.current.getCenter().lng.toFixed(4));
                setLat(map.current.getCenter().lat.toFixed(4));
                // await style loading before loading district layers
                // removed for now since individual state enacted districts are now loaded asynchronously.
                
                // load the geojson for the state outlines
                map.current.addSource("USA-source", {
                    "type": "geojson",
                    "data": USADistricts
                });
                map.current.addLayer({
                    "id": "USA-layer",
                    "type": "fill",
                    "source": "USA-source",
                    "maxzoom": zoomThreshold,
                    "layout": {},
                    "paint": {
                        "fill-color": districtColors[1],
                        "fill-opacity": 0.5,
                    }
                });
            });
        }
    });

    /** this breaks everything. https://docs.mapbox.com/help/tutorials/use-mapbox-gl-js-with-react/ claims
    *   that it is necessary in order to store location data after the map has been interacted with. If
    *   this doesn't work then we can't have dynamic bounds, however it conflicts with flyToState() by 
    *   constantly setting the lat and lng in both flyToState() and in this function, crashing the app.
    */
    // useEffect(() => {
    //     if(!map.current) return; //wait for map to initialize

    //     map.current.on('move', ({ originalEvent }) => {
    //         if (originalEvent) {
    //           map.current.fire('usermove');
    //         } else {
    //           map.current.fire('flymove');
    //         }
    //       });

    //     map.current.on('usermove', () => {
    //         console.log(map.current.getCenter().lng.toFixed(4));
    //         console.log(map.current.getCenter().lat.toFixed(4));
    //         console.log(map.current.getZoom().toFixed(2));
    //     })
    //     map.current.on('flymove', () => console.log("flying"))
    // });

    function getGeojson(stateName) {
        if (stateName) { // user selected a state
            let layer = map.current.getSource(stateName + "-district-source");
            if (!layer) { // map does not have the district boundaries
                let url = "http://localhost:8080/api" + "/districts?state=" + stateName + "&file=State&year=2012";
                fetch(url)
                    .then(res => res.json())
                    .then(
                        (result) => {
                            addDistrictGeoJSON(map.current, stateName, result);
                        },
                        (error) => {
                            console.log(error); // failed to fetch geojson
                        }
                    );
            }
        }
    }

    function flyToState(stateName) {
        switch(stateName) {
            case "Washington": 
                setLat(47.75);
                setLng(-120.74);
                setZoom(6);
                map.current.flyTo({
                    center: [lng, lat],
                    zoom: zoom
                });
                break;
    
            case "Nevada": 
                setLat(38.80);
                setLng(-116.42);
                setZoom(6);
                map.current.flyTo({
                    center: [lng, lat],
                    zoom: zoom
                });
                break;
    
            case "Arkansas": 
                setLat(35.20);
                setLng(-91.83);
                //setBounds([-96.83, 30.20], [-86.83, 40.20])
                setZoom(6);
                map.current.flyTo({
                    center: [lng, lat],
                    zoom: zoom,
                });
                //console.log(map.current.getMaxBounds())
                break;
        }
    
    }

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
        "minzoom": zoomThreshold,
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
            "fill-opacity": 0.5,
        }
    });

    // adds polygon outlines with adjustable width
    map.addLayer({
        'id': layerName + '-outline',
        'type': 'line',
        "minzoom": zoomThreshold,
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
        "minzoom": zoomThreshold,
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