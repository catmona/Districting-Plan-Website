import React, { useRef, useEffect, useState } from 'react';
import mapboxgl from '!mapbox-gl'; // eslint-disable-line import/no-webpack-loader-syntax

mapboxgl.accessToken = 'pk.eyJ1IjoiZ29sZHlmbGFrZXMiLCJhIjoiY2t0ZGtrNHhiMDB5MjJxcWN6bWZ5ZGx3byJ9.IMzQecUSVBFlT4rUycdG3Q';

function Map(props) {
    const mapContainer = useRef(null);
    const map = useRef(null);
    const [lng, setLng] = useState(-70.9);
    const [lat, setLat] = useState(42.35);
    const [zoom, setZoom] = useState(5);
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
        }
    });
  
    return (
        <div className = "map-wrapper">
            <div className = "map-fade" />
            <div ref={mapContainer} className = "map-container" />
        </div>
    );
}

export default Map;