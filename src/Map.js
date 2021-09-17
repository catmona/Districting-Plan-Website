import React, { useRef, useEffect, useState } from 'react';
import mapboxgl from '!mapbox-gl'; // eslint-disable-line import/no-webpack-loader-syntax

mapboxgl.accessToken = 'pk.eyJ1IjoiZ29sZHlmbGFrZXMiLCJhIjoiY2t0ZGtrNHhiMDB5MjJxcWN6bWZ5ZGx3byJ9.IMzQecUSVBFlT4rUycdG3Q';

function Map() {
    const mapContainer = useRef(null);
    const map = useRef(null);
    const [lng, setLng] = useState(-70.9);
    const [lat, setLat] = useState(42.35);
    const [zoom, setZoom] = useState(9);

    useEffect(() => {
        if (map.current) return; // initialize map only once
        map.current = new mapboxgl.Map({
            container: mapContainer.current,
            style: 'mapbox://styles/goldyflakes/cktox1jbl1omg17nt35gafjid',
            center: [lng, lat],
            zoom: zoom,
            interactive: false
            });
        });
    
    if(map.current) { //doesnt work, workin on it
        map.current.on('render', function() {
            map.resize();
        });    
    }
  
    return (
        <div className = "map-wrapper">
            <div className = "map-fade" />
            <div ref={mapContainer} className = "map-container" />
        </div>
    );
}

export default Map;