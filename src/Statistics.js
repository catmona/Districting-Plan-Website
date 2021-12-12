import React, { Component, useEffect, useState } from 'react';
import { Row } from 'react-bootstrap';
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
    console.log("DATA FROM SERVER: %o", response);
    const formattedDataList = [];
    for(let i = 0; i < response.districtPopulations.length; i++) {
        const districtPop = response.districtPopulations[i];
        const formattedData = {
            'id': i+1,
            'popMeasure': PopMeasure.TOTAL,
            'africanamerican': districtPop.black,
            'white': districtPop.white,
            'asianamerican': districtPop.asian,
            'republican': districtPop.republicanVotes,
            'democrat': districtPop.democraticVotes,
            'population': districtPop.populationTotal
        };
        formattedDataList.push(formattedData);
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
        fetch("http://localhost:8080/api2/setPopulationType?populationType=" + popType, { method: 'POST', credentials: 'include' })
        .then(res => res.json())
        .then(
            (result) => {
                setPopType(p)
            },
            (error) => {
                // showErrorModal("Failed to set population measure type", error);
                console.log(error)
            }
        );
    }

    useEffect(() => {
        if (props.districtingData) {
            const formattedData = formatResponseToStatisticData(props.districtingData);

            setState({
                isLoaded: true,
                stateName: props.stateName ? props.stateName.toLowerCase() : "",
                stateData: formattedData
            });
        }
    }, [props.districtingData]);
    
    return (
        <div id="stats-wrapper">
            {state.isLoaded ? (state.stateData ? <div id="stats-container">
                <Row id='stats-top'>
                    <CustomizedTables 
                        measures={props.districtingData}
                        planType={props.planType}
                    /> 
                    <StatGraphs 
                        showError={props.showError}
                        stateData={state.stateData} 
                        onSelectPopType={getPopType} 
                        popType={popType}
                        selectedPlanId={props.selectedPlanId}
                        planType={props.planType}
                        waitData={props.waitData}
                    /> 
                </Row>
                <Row id='stats-bottom'>
                    <EnhancedTable 
                        stateData={state.stateData} 
                        selectedPlanId={props.selectedPlanId}
                        setPlanType={props.setPlanType}
                        planType={props.planType}
                        getDistrictingSummary={props.getDistrictingSummary}
                        districtingData={props.districtingData} 
                        setDistrictingData={props.setDistrictingData}
                        isAlgDone={props.isAlgDone}
                        setIsAlgDone={props.setIsAlgDone}
                        waitData={props.waitData}
                    />
                </Row>
            </div> : "") : <Box className = 'loading-container'><CircularProgress className = 'loading-icon'/></Box>}
        </div>
    );
}

export default Statistics;