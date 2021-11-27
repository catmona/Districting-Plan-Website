import React, { Component, useEffect, useState } from 'react';
import EnhancedTable from 'EnhancedTable.js';
import StatGraphs from './StatGraphs';
import CustomizedTables from './StateTable';
import CircularProgress from '@mui/material/CircularProgress';
import Box from '@mui/material/Box';

// enums that correlate to server enums
const PopMeasure = {
    TOTAL: 0,
    CVAP: 1,
    VAP: 2
}
const Demographic = {
    WHITE: 0,
    AFRICAN_AMERICAN: 1,
    ASIAN: 2,
    ALL: 3
}

function formatResponseToStatisticData(response) {
    // console.log("DATA FROM SERVER: %o", response);
    var formattedDataList = [];
    for(let i = 0; i < response.districtPopulations.length; i++) {
        let districtPop = response.districtPopulations[i];
        let election = response.districtElections[i];
        var formattedData = {
            'id': i+1,
            'popMeasure': PopMeasure.TOTAL,
            'africanamerican': districtPop[Demographic.AFRICAN_AMERICAN],
            'white': districtPop[Demographic.WHITE],
            'asianamerican': districtPop[Demographic.ASIAN],
            'republican': election.republicanVotes,
            'democrat': election.democraticVotes,
            'population': districtPop[Demographic.ALL]
        };
        formattedDataList.push(formattedData);
        // console.log("Formatted data: %o", formattedDataList);
    }
    return formattedDataList;
}

function Statistics(props) {
    const [state, setState] = useState({
        isLoaded: false,
        stateName: props.stateName ? props.stateName.toLowerCase() : "",
        stateData: null
    });
    const [popType, setPopType] = useState("TOTAL");

    function getPopType(p) {
        setPopType(p)
        //fetch("http://localhost:8080/api2/setPopulationType?populationType=" + popType);
        //TODO use this
    }

    useEffect(() => {
        if (props.districtingData) {
            var formattedData = formatResponseToStatisticData(props.districtingData);

            setState({
                isLoaded: true,
                stateName: props.stateName ? props.stateName.toLowerCase() : "",
                stateData: formattedData
            });
        }
    }, [props.districtingData]);
    
    //TODO popType shouldnt need to be passed, accessible in stateData
    return (
        <div>
            {state.isLoaded ? (state.stateData ? <div>
                <CustomizedTables /> 
                <StatGraphs 
                    showError={props.showError}
                    stateData={state.stateData} 
                    onSelectPopType={getPopType} 
                    popType={popType}
                /> 
                <EnhancedTable 
                    stateData={state.stateData} 
                    algResults={props.algResults} 
                    setAlgResults={props.setAlgResults} 
                />
            </div> : "") : <Box className = 'loading-container'><CircularProgress className = 'loading-icon'/></Box>}
        </div>
    );
}

export default Statistics;