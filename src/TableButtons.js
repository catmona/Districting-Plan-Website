import React, { useRef, useEffect, useState } from 'react';
import Box from "@mui/material/Box";
import Fab from "@mui/material/Fab";
import NavigationIcon from "@mui/icons-material/Navigation";
import KeyboardBackspaceIcon from "@mui/icons-material/KeyboardBackspace";
import SaveAltIcon from "@mui/icons-material/SaveAlt";

function TableButtons(props) {
    const {districtingData} = props;
    
    function goBack() {
        props.setIsAlgDone(false);
        props.getDistrictingSummary();
        
    }
    
    function saveGeoJSON() {
        const element = document.createElement('a');
        const dataStr = 'data:text/json;charset=utf-8,' + encodeURIComponent((districtingData.featureCollection));
        element.setAttribute('href', dataStr);
        element.setAttribute('download', 'districting.geojson');
        document.body.appendChild(element);
        element.click();
        element.remove();
    }
    
    return (
        <Box sx={{ "& > :not(style)": { m: 1 } }} className="button-submit">
            <Fab
                variant="extended"
                size="medium"
                color="primary"
                aria-label="add"
                className="submit"
                disabled={!props.isAlgDone}
                onClick={() => goBack()}
            >
                <KeyboardBackspaceIcon sx={{ mr: 1 }} />
                <span className="submit">Undo Algorithm</span>
            </Fab>
            <Fab
                variant="extended"
                size="medium"
                color="primary"
                aria-label="add"
                disabled={props.isAlgDone}
                className="submit"
            >
                <NavigationIcon sx={{ mr: 1 }} />
                <span className="submit" onClick={() => props.setShowAlgLimits(true)}>
                    Equalize Population
                </span>
            </Fab>
            <Fab
                variant="extended"
                size="medium"
                color="primary"
                aria-label="add"
                onClick={() => saveGeoJSON()}
            >
                <SaveAltIcon sx={{ mr: 1 }} />
                <span className="submit">Save Geo Data</span>
            </Fab>
        </Box>  
    );
}

export default TableButtons;