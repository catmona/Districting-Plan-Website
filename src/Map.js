import React, { useRef, useEffect, useState } from 'react';
import mapboxgl from '!mapbox-gl';
import StateOverlaySource from './data/us_state_overlay.geojson';
import StateBoundarySource from './data/us_state_boundaries.geojson';
import WACountyBoundarySource from './data/WA/washington_county_boundaries.geojson';
import NVCountyBoundarySource from './data/NV/nevada_county_boundaries.geojson';
import ARCountyBoundarySource from './data/AR/arkansas_county_boundaries.geojson';
import { Form } from 'react-bootstrap';

mapboxgl.accessToken = 'pk.eyJ1IjoiZ29sZHlmbGFrZXMiLCJhIjoiY2t0ZGtrNHhiMDB5MjJxcWN6bWZ5ZGx3byJ9.IMzQecUSVBFlT4rUycdG3Q';

// constants for styling districts on map
const districtColors = ["#00ff73", "#00ffed", "#0024ff", "#ac00ff", "#ff0056", "#d0ff00", "#ff9700", "#f3ccf2", "#90f1c9", "#d1f190"]
const white = '#FFFFFF';
const stateBoundaryColor = '#c5e0e2';
const countyBoundaryColor = '#9e9e9e';
const zoomThreshold = 4;       
let hoveredStateId = null;

function Map(props) {
    const mapContainer = useRef(null);
    const map = useRef(null);
    const [lng, setLng] = useState(-98.9);
    const [lat, setLat] = useState(40.35);
    const [zoom, setZoom] = useState(3.6);
    const [bounds, setBounds] = useState([[-138.42, 12.22], [-56.80, 57.27]])
    const [showDistrictBoundaries, setShowDistrictBoundaries] = useState(true)
    const [showStateBoundaries, setShowStateBoundaries] = useState(true)
    const [showCountyBoundaries, setShowCountyBoundaries] = useState(true)
    const {stateName, districtingData, setState} = props;

    useEffect(() => {
        if (map.current) return; 
            try {
                map.current = new mapboxgl.Map({
                    container: mapContainer.current,
                    style: 'mapbox://styles/goldyflakes/cktdkm1j51fmq18qj54fippsz',
                    center: [lng, lat],
                    zoom,
                    maxBounds: bounds,
                    });

                map.current.on("load", () => {
                    addStateOverlay()
                    addStateBoundaries()
                });
            } catch (e) {
                props.showError("Failed to start map", e)
                console.log(e)
            }
        }
    );
    
    useEffect(() => {
        if(!map.current || !stateName) return;
        
        if(districtingData) { getStateGeoJSON(); }
        focusState(stateName);
        toggleBoundaries(stateName); //yes this needs to be in both useEffects
    })
    
    useEffect(() => {
        if(!map.current) return;
        
        toggleBoundaries(stateName);
    }, [showCountyBoundaries, showDistrictBoundaries, showStateBoundaries])

    function toggleBoundaries() {
        const stateBoundaryLayer = "State-Boundary-Layer";
        const districtBoundaryLayer = stateName + "-district-source-layer-outline";
        const countyBoundaryLayer = stateName + "-county-boundary-layer";
        const sLayer = map.current.getLayer(stateBoundaryLayer);
        const dLayer = map.current.getLayer(districtBoundaryLayer);
        const cLayer = map.current.getLayer(countyBoundaryLayer);
        let v = "visible";
        
        //state boundaries
        showStateBoundaries ? v = "visible" : v = "none";
        if(typeof sLayer !== 'undefined') {
            map.current.setLayoutProperty(stateBoundaryLayer, 'visibility', v)
        }

        //district boundaries
        showDistrictBoundaries ? v = "visible" : v = "none";
        if(typeof dLayer !== 'undefined') {
            map.current.setLayoutProperty(districtBoundaryLayer, 'visibility', v)
        }
        
        //county boundaries
        showCountyBoundaries ? v = "visible" : v = "none";
        if(typeof cLayer !== 'undefined') {
            map.current.setLayoutProperty(countyBoundaryLayer, 'visibility', v)
        }
    }
    
    function getStateGeoJSON() {
        if (districtingData && districtingData.featureCollection) { // user selected a state
            const layer = map.current.getSource(stateName + "-district-source");

            if (!layer) { // map does not have the district boundaries
                const geojson = districtingData.featureCollection;
                addDistrictGeoJSON(map.current, stateName, geojson);
                addCountyGeoJSON(map.current, stateName);
            }
        }
    }

    function focusState(stateName) {
        switch(stateName) {
            case "Washington": 
                setLat(47.75);
                setLng(-120.74);
                setZoom(6);
                map.current.flyTo({
                    center: [lng, lat],
                    zoom
                });
                break;
    
            case "Nevada": 
                setLat(38.80);
                setLng(-116.42);
                setZoom(6);
                map.current.flyTo({
                    center: [lng, lat],
                    zoom
                });
                break;
    
            case "Arkansas": 
                setLat(35.20);
                setLng(-91.83);
                setZoom(6);
                map.current.flyTo({
                    center: [lng, lat],
                    zoom,
                });
                break;
        }
    
    }
    
    function addStateBoundaries() {
        map.current.addSource("State-Boundary-Source", {
            "type": "geojson",
            "data": StateBoundarySource
        });
        map.current.addLayer({
            "id": "State-Boundary-Layer", 
            'type': 'line',
            'source': "State-Boundary-Source",
            'paint': {
                'line-color': stateBoundaryColor,
                'line-width': 1
            }
        });
    }

    function addStateOverlay() {
        map.current.addSource("State-Overlay-Source", {
            "type": "geojson",
            "data": StateOverlaySource
        });
        map.current.addLayer({
            "id": "State-Overlay-Layer",
            "type": "fill",
            "source": "State-Overlay-Source",
            "maxzoom": zoomThreshold,
            "layout": {},
            "paint": {
                "fill-color": '#4285F4',
                "fill-opacity": [
                    'case',
                    ['boolean', ['feature-state', 'hover'], false],
                    1,
                    0.5
                ]
            }
        });

        //set state by clicking on its overlay
        map.current.on('click', 'State-Overlay-Layer', (e) => {
            setState(e.features[0].properties.ABBR);
        });

        //change cursor to pointer when hovering a state outline and change color of overlay
        map.current.on('mousemove', 'State-Overlay-Layer', (e) => {
            map.current.getCanvas().style.cursor = 'pointer';
            if (e.features.length > 0) {
                if (hoveredStateId !== null) {
                    map.current.setFeatureState( //TODO fix this
                        { source: 'State-Overlay-Source', id: hoveredStateId },
                        { hover: false }
                    );
                }
                hoveredStateId = e.features[0].id;
                map.current.setFeatureState(
                    { source: 'State-Overlay-Source', id: hoveredStateId },
                    { hover: true }
                );
            }

        });
        
        //change cursor back when not hovering a state outline and change color of overlay
        map.current.on('mouseleave', 'State-Overlay-Layer', () => {
            map.current.getCanvas().style.cursor = '';
            if (hoveredStateId !== null) {
                map.current.setFeatureState(
                    { source: 'State-Overlay-Source', id: hoveredStateId },
                    { hover: false }
                );
            }
            hoveredStateId = null;
        });
    }

    return (
        <div className = "map-wrapper">
            <div className = "map-fade" />
            <div id="map-filter">
                <p id="map-filter-label"><b>Filter</b></p>
                <Form>
                <Form.Check //state
                        type="checkbox" 
                        className="dark-checkbox" 
                        id="map-filter-state" 
                        onChange={(e) => setShowStateBoundaries(e.target.checked)}
                        checked= {showStateBoundaries}
                        label="State"
                    />
                    <Form.Check //districts
                        type="checkbox" 
                        className="dark-checkbox" 
                        id="map-filter-districts" 
                        onChange={(e) => setShowDistrictBoundaries(e.target.checked)}
                        checked= {showDistrictBoundaries}
                        label="Districts"
                    />
                    <Form.Check //counties
                        type="checkbox" 
                        className="dark-checkbox" 
                        id="map-filter-counties" 
                        onChange={(e) => setShowCountyBoundaries(e.target.checked)}
                        checked= {showCountyBoundaries}
                        label="Counties"
                    />
                    <Form.Check //precincts
                        type="checkbox" 
                        className="dark-checkbox" 
                        id="map-filter-precincts" 
                        checked={false}
                        label="Precincts"
                        disabled
                    />
                </Form>
            </div>
            <div ref={mapContainer} className = "map-container" />
        </div>
    );
}

function addCountyGeoJSON(map, stateName) {
    let source = "";
    switch (stateName) {
        case "Washington": 
            source = WACountyBoundarySource;
            break;

        case "Nevada": 
            source = NVCountyBoundarySource;
            break;

        case "Arkansas": 
            source = ARCountyBoundarySource;
            break;
    }
    
    const sourceName = stateName + "-county-boundary-source";
    map.addSource(sourceName, {
        "type": "geojson",
        "data": source
    });
    
    map.addLayer({
        "id": stateName + "-county-boundary-layer", 
        'type': 'line',
        'source': stateName + "-county-boundary-source",
        'paint': {
            'line-color': countyBoundaryColor,
            'line-width': 0.8
        }
    });
}

function addDistrictGeoJSON(map, sourceId, source) {
    const sourceName = sourceId + "-district-source";
    map.addSource(sourceName, {
        "type": "geojson",
        "data": source
    });
    console.log("[Mapbox] Added %s", sourceName);
    
    addDistrictStyleLayer(map, sourceName); 
    return sourceName;
}

function addDistrictStyleLayer(map, sourceId) {
    if (!map.getSource(sourceId)) {
        console.log("Must add source before applying style layer.");
        return;
    }

    const layerName = sourceId + "-layer";
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
    
    //TODO add state/county/precinct layers
    // adds district outlines with adjustable width
    map.addLayer({
        'id': layerName + '-outline',
        'type': 'line',
        "minzoom": zoomThreshold,
        'source': sourceId,
        'paint': {
            'line-color': white,
            'line-width': 2.25
        }
    });

    // Add district names
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
        },
        'paint': {
            'text-color': white
        }
    });

    return layerName;
}

export default Map;

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


        /**
     * YOU PROBABLY MEAN TO CALL addDistrictGeoJSON().
     * Adds a district style layer to the given map source.
     * The districts should have a property called "District_Name"
     * denoting which # district it is in the state. This colors the--
     * district the designated color.
     * @param {*} map mapboxgl.Map object
     * @param {*} sourceId id of the source
     * @returns id of the layer added. Layers are named: [State abbv]-district-source-layer
     */

            //deprecated
    // function getGeojson(stateName) {
    //     if (stateName) { // user selected a state
    //         let layer = map.current.getSource(stateName + "-district-source");
    //         if (!layer) { // map does not have the district boundaries
    //             let url = "http://localhost:8080/api2/getStateSummary?state=" + stateName;
    //             fetch(url)
    //                 .then(res => res.json())
    //                 .then(
    //                     (result) => {
    //                         addDistrictGeoJSON(map.current, stateName, result.featureCollection);
    //                     },
    //                     (error) => {
    //                         console.log(error); // failed to fetch geojson
    //                     }
    //                 );
    //         }
    //     }
    // }

        /**
 * Adds a district's GeoJSON to the map and styles it. 
 * @param {*} map mapboxgl.Map object
 * @param {*} sourceId id of the source (pass in the abbr. e.g.: NV, AR, WA)
 * @param {*} source the GeoJSON file
 * @returns id of the source added. Sources are named: [State abbv]-district-source
 */