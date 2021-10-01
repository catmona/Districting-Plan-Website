
// Importing combination
import React, { Component } from 'react';
import { Container } from 'react-bootstrap';
import { Col } from 'react-bootstrap';
import { Row, Nav } from 'react-bootstrap';
import Map from './Map'
import Topbar from './Topbar'
import kosta from './kosta.css'
import Chart from 'react-google-charts'
import { withThemeCreator } from '@material-ui/styles';
// If you are using ES6, then
import EnhancedTable from 'EnhancedTable.js';
import PropTypes from 'prop-types';
import VerticalTabs from './VerticalTab';
import CustomizedTables from './StateTable';
class Statistics extends Component {
    constructor(props) {
        super(props);
        this.state = {
            districts: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
        };
    }




    render() {
        return <div >

            <CustomizedTables/>
            <VerticalTabs/>
            
            <EnhancedTable/>

            
        </div>
    }
}

export default Statistics;