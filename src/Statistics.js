
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
            washington: null
        };
    }
    componentDidMount() {
        fetch("http://localhost:8080/api/stateinfo")
          .then(res => res.json())
          .then(
            (result) => {
                console.log(result.washington[0])
              this.setState({
                isLoaded: true,
                washington: result.washington
              });
            },
            // Note: it's important to handle errors here
            // instead of a catch() block so that we don't swallow
            // exceptions from actual bugs in components.
            (error) => {
                console.log(error)
            //   this.setState({
            //     isLoaded: true,
            //     error
            //   });
            }
          )
      }

    render() {
        return <div >
            <CustomizedTables/>
            <VerticalTabs/>
          
            {this.state.washington?<EnhancedTable stateData = {this.state.washington} />: <Box><CircularProgress/></Box> }
            {/* <button onClick = {() => {console.log(this.state.washington)}}>press me</button> */}
        </div>
    }
}

export default Statistics;