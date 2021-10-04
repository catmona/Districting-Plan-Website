
// Importing combination
import React, { Component } from 'react';
// If you are using ES6, then
import EnhancedTable from 'EnhancedTable.js';
import VerticalTabs from './VerticalTab';
import CustomizedTables from './StateTable';
import CircularProgress from '@mui/material/CircularProgress';
import Box from '@mui/material/Box';
class Statistics extends Component {
    constructor(props) {
        super(props);
        this.state = {
            stateData: null
        };
    }
    componentDidMount() {
        fetch("http://localhost:8080/api/stateinfo")
            .then(res => res.json())
            .then(
                (result) => {
                    this.setState({
                        stateData: result.stateData
                    });
                },
                (error) => {
                    console.log(error)
                }
            )
    }

    render() {
        return <div >
            <CustomizedTables />
            {this.state.stateData ? <div><VerticalTabs stateData={this.state.stateData} /><EnhancedTable stateData={this.state.stateData} /></div> : <Box><CircularProgress /></Box>}
        </div>
    }
}

export default Statistics;