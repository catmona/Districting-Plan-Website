
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
    HISPANIC: 3,
    ALL: 4
}

function Statistics(props) {
    const [state, setState] = useState({
        isLoaded: false,
        stateName: props.stateName ? props.stateName.toLowerCase() : null,
        stateData: null
    });

    useEffect(() => {
        if (!state.isLoaded && props.districtingData) {

            console.log(props.districtingData);
            var formattedDataList = [];
            for(let i = 0; i < props.districtingData.districtPopulations.length; i++) {
                let total = props.districtingData.districtPopulations[i].populations[PopMeasure.TOTAL];
                console.log("Total: %o", total);
                var formattedData = {
                    'id': i+1,
                    'popMeasure':PopMeasure.TOTAL,
                    'africanamerican':total[Demographic.AFRICAN_AMERICAN],
                    'white':total[Demographic.WHITE],
                    'asianamerican':total[Demographic.ASIAN],
                    'hispanic':total[Demographic.HISPANIC],
                    'republican':0,
                    'democrat':0,
                    'population':total[Demographic.ALL]
                };
                formattedDataList.push(formattedData);
                console.log("Formatted data: %o", formattedDataList);
            }
            console.log("DONE Formatted data: %o", formattedDataList);

            setState({
                isLoaded: true,
                stateData: formattedDataList
            });
        }
    });
    
    return (
        <div>
            {state.isLoaded ? (state.stateData ? <div>
                <CustomizedTables /> 
                <StatGraphs stateData={state.stateData} />
                <EnhancedTable stateData={state.stateData} />
            </div> : "") : <Box className = 'loading-container'><CircularProgress className = 'loading-icon'/></Box>}
        </div>
    );
}

// class Statistics extends Component {

//     constructor(props) {
//         super(props);
//         this.state = {
//             isLoaded: false,
//             stateName: props.stateName.toLowerCase(),
//             stateData: null
//         };
//     }
    
//     componentDidMount() {
//         if (!this.props.districtingData) return;

//         this.setState({
//             isLoaded: true,
//             stateData: this.props.districtingData
//         });
//     }

//     render() {
//         return <div>
//             {this.state.isLoaded ? (this.state.stateData ? <div>
//                 <CustomizedTables /> 
//                 <StatGraphs stateData={this.state.stateData} />
//                 <EnhancedTable stateData={this.state.stateData} />
//             </div> : "") : <Box className = 'loading-container'><CircularProgress className = 'loading-icon'/></Box>}
//         </div>
//     }
// }

export default Statistics;