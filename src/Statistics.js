
// Importing combination
import React, { Component } from 'react';
// If you are using ES6, then
import EnhancedTable from 'EnhancedTable.js';
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