
import React, { Component } from 'react';
import EnhancedTable from 'EnhancedTable.js';
import VerticalTabs from './VerticalTab';
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
        fetch("http://localhost:8080/api/stateinfo")
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
<<<<<<< HEAD
        return <div>
            {this.state.isLoaded ? (this.state.stateData ? <div>
                <CustomizedTables /> 
=======
        return <div >
            <CustomizedTables />
            
            {this.state.isLoaded? ( this.state.stateData?<div>
>>>>>>> 1c66328d9a3e01fa4201de07ccc9bdade5af5d86
                <VerticalTabs stateData={this.state.stateData} />
                <EnhancedTable stateData={this.state.stateData} />
            </div> : "") : <Box className = 'loading-container'><CircularProgress className = 'loading-icon'/></Box>}
        </div>
    }
}

export default Statistics;