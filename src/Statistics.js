
import React, { Component } from 'react';
import EnhancedTable from 'EnhancedTable.js';
import StatGraphs from './StatGraphs';
import CustomizedTables from './StateTable';
import CircularProgress from '@mui/material/CircularProgress';
import Box from '@mui/material/Box';

class Statistics extends Component {

    constructor(props) {
        super(props);
        this.state = {
            isLoaded: false,
            stateName: props.stateName.toLowerCase(),
            stateData: null
        };
    }
    
    componentDidMount() {
        if (!this.props.districtingData) return;
        this.setState({
            isLoaded: true,
            stateData: this.props.districtingData
        });
        fetch("http://localhost:8080/api2/stateinfo")
            .then(res => res.json())
            .then(
                (result) => {
                    this.setState({
                        isLoaded: true,
                        stateData: result[this.state.stateName]
                    });
                },
                (error) => {
                    this.setState({
                        isLoaded: true,
                        stateData: null
                    })
                    console.log(error)
                }
            )
    }

    render() {
        return <div>
            {this.state.isLoaded ? (this.state.stateData ? <div>
                <CustomizedTables /> 
                <StatGraphs stateData={this.state.stateData} />
                <EnhancedTable stateData={this.state.stateData} />
            </div> : "") : <Box className = 'loading-container'><CircularProgress className = 'loading-icon'/></Box>}
        </div>
    }
}

export default Statistics;