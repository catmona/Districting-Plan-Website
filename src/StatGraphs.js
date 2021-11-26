import * as React from 'react';
import { Dropdown, DropdownButton, Row, Col, Form, Button } from 'react-bootstrap';
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import Chart from 'react-google-charts'
import BoxWhiskerModal from './BoxWhiskerModal';

function TabPanel(props) {
    const { children, value, index, stateData, ...other } = props;

    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`vertical-tabpanel-${index}`}
            aria-labelledby={`vertical-tab-${index}`}
            {...other}
        >
            {value === index && (
                <Box sx={{ p: 3 }}>
                    <Typography component={'span'}>{children}</Typography>
                </Box> 
            )}
        </div>
    );
}

TabPanel.propTypes = {
    children: PropTypes.node,
    index: PropTypes.number.isRequired,
    value: PropTypes.number.isRequired,
};

function a11yProps(index) {
    return {
        id: `vertical-tab-${index}`,
        'aria-controls': `vertical-tabpanel-${index}`,
    };
}

function formatResponseToBoxWhisker(result, popType, basis) {
    var boxData = []
    var pointData = { enacted: [], selected: [], equalized: [] }
    var yAxisLabel = ""

    //format boxes
    for(let i = 0; i < result.lowerExtreme.length; i++) {
        var l = "District " + (i+1);
        var box = [result.lowerExtreme[i], result.lowerQuartile[i], result.upperQuartile[i], result.upperExtreme[i], result.median[i]];
        var col = { label: l, y: box }
        boxData.push(col);
    }

    //format optional points
    //TODO

    //format y-axis label
    switch(popType) {
        case "TOTAL":
            yAxisLabel += "Total"
            break;
        case "VAP":
            yAxisLabel += "VAP"
            break;
        case "CVAP":
            yAxisLabel += "CVAP"
            break;
    }

    switch(basis) {
        case "african_american":
            yAxisLabel += " African American"
            break;
        case "hispanic":
            yAxisLabel += " Hispanic"
            break;
        case "asian":
            yAxisLabel += " Asian"
            break;
        case "white":
            yAxisLabel += " White"
            break;
        case "republican":
            yAxisLabel += " Republican"
            break;
        case "democrat":
            yAxisLabel += " Democrat"
            break;
    }

    yAxisLabel += " Population"

    return {boxes: boxData, points: pointData, label: yAxisLabel}
}

function StatGraphs(props) {
    const [value, setValue] = React.useState(0);
    const [boxWhiskerBasis, setBoxWhiskerBasis] = React.useState("african_american")
    const [boxWhiskerEnacted, setBoxWhiskerEnacted] = React.useState(false)
    const [boxWhiskerCurrent, setBoxWhiskerCurrent] = React.useState(false)
    const [boxWhiskerEqualized, setBoxWhiskerEqualized] = React.useState(false)
    const [showModal, setShowModal] = React.useState(false)
    const [boxes, setBoxes] = React.useState(null)
    const [points, setPoints] = React.useState({ enacted: [], selected: [], equalized: [] })
    const [label, setLabel] = React.useState("Total Population")

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    const getBoxWhiskerData = (event) => {
        event.preventDefault();
        fetch("http://localhost:8080/api2/boxwhiskers?districtingId=0" //TODO get districting ID from StateInfo
        + "&basis=" + boxWhiskerBasis 
        + "&enacted=" + boxWhiskerEnacted 
        + "&current=" + boxWhiskerCurrent 
        + "&postAlg=" + boxWhiskerEqualized,
        { credentials: 'include' })
        .then(res => res.json())
        .then(
            (result) => {
                var formattedData = formatResponseToBoxWhisker(result, props.popType, boxWhiskerBasis);
                setBoxes(formattedData.boxes);
                setPoints(formattedData.points);
                setLabel(formattedData.label);
                setShowModal(true);
                console.log(result)
            },
            (error) => {
                props.showError("Failed to get box and whisker data", error)
                console.log(error);
            }
        );
    };

    const bgcolor = "#1f1f1f";
    const bgcolor2 = "#161616";

    let partyData = [['District', 'Democratic Party', 'Republican Party']]
    partyData.push(...props.stateData.map((x) => { return [x['id'] + "", x['democrat'], x['republican']] }));

    let demographicData = [['District', 'Hispanic or Latino', 'African American', 'Asian']];
    demographicData.push(...props.stateData.map((x) => { return [x['id'] + "", x['hispanic'], x['africanamerican'], x['asianamerican']] }));
    
    return (
        <>
            <Box sx={{ flexGrow: 1, bgcolor: bgcolor2, display: 'flex' }} >
                {/* <button onClick = {() => {console.log(partyData)}}>Press me</button> */}
                <div id="tab-section">
                    <Tabs
                        className="vertical-tab-bar"
                        orientation="vertical"
                        variant="scrollable"
                        value={value}
                        onChange={handleChange}
                        aria-label="Vertical tabs example"
                        sx={{ borderRight: 1, borderColor: 'divider', color: 'white' }}
                    >
                        <Tab label="Population" {...a11yProps(0)} width='200px' />
                        <Tab label="Demographics" {...a11yProps(1)} />
                        <Tab label="Compare to Average" {...a11yProps(2)} />
                    </Tabs>
                    <hr />
                    <DropdownButton menuVariant="dark" size="md" title={"Population Type: " + props.popType} id="poptype-dropdown">
                        <Dropdown.Item onClick={() => {props.onSelectPopType("TOTAL")}} className='poptype-dropdown-option'>Total</Dropdown.Item>
                        <Dropdown.Item disabled onClick={() => {props.onSelectPopType("CVAP")}} className='poptype-dropdown-option'>CVAP</Dropdown.Item>
                        <Dropdown.Item disabled onClick={() => {props.onSelectPopType("VAP")}} className='poptype-dropdown-option'>VAP</Dropdown.Item>
                    </DropdownButton>
                </div>
                <TabPanel value={value} index={0} width={'100%'} className="dark-tabpanel">
                    <Chart
                        className="dark-chart"
                        chartType="ColumnChart"
                        loader={<div>Loading Chart</div>}
                        data={partyData}

                        options={{
                            // Material design options
                            title: "District Populations",

                            titleTextStyle: {
                                color: 'white',
                                fontSize: 20
                            },
                            hAxis: {
                                textStyle: {
                                    color: 'white'
                                },
                                titleTextStyle: {
                                    color: 'white'
                                }
                            },
                            vAxis: {
                                textStyle: {
                                    color: 'white'
                                },
                                titleTextStyle: {
                                    color: 'white'
                                }
                            },
                            isStacked: 'true',
                            backgroundColor: bgcolor,
                            legend: {
                                position: 'bottom',
                                textStyle: { color: 'white' }
                            },
                            opacity: 0,

                        }}
                        // For tests
                        rootProps={{ 'data-testid': '2' }}
                        legendToggle
                    />
                </TabPanel>
                <TabPanel value={value} index={1} width={'100%'} className="dark-tabpanel">
                    <Chart
                        className="dark-chart"
                        chartType="ColumnChart"
                        loader={<div>Loading Chart</div>}
                        data={demographicData}

                        options={{
                            // Material design options
                            title: "Racial Demographics per District",

                            titleTextStyle: {
                                color: 'white',
                                fontSize: 20
                            },
                            hAxis: {
                                textStyle: {
                                    color: 'white'
                                },
                                titleTextStyle: {
                                    color: 'white'
                                }
                            },
                            vAxis: {
                                textStyle: {
                                    color: 'white'
                                },
                                titleTextStyle: {
                                    color: 'white'
                                }
                            },
                            backgroundColor: bgcolor,
                            legend: {
                                position: 'bottom',
                                textStyle: { color: 'white' }
                            },
                            opacity: 0,

                        }}
                        // For tests
                        rootProps={{ 'data-testid': '2' }}
                        legendToggle
                    />
                </TabPanel>
                <TabPanel value={value} index={2} width={'100%'} className="dark-tabpanel">
                        <Form onSubmit={getBoxWhiskerData}> 
                            <Row id="box-whisker-form">
                                <Col id="box-whisker-form-left">
                                    <Form.Group>
                                        <Form.Check 
                                            type="checkbox" 
                                            classname="dark-checkbox" 
                                            id="boxwhisker-enacted" 
                                            onChange={ (e) => setBoxWhiskerEnacted(e.target.checked) }
                                            label="Show enacted plan?" 
                                        />
                                        <Form.Check 
                                            type="checkbox" 
                                            classname="dark-checkbox" 
                                            id="boxwhisker-districting" 
                                            onChange={ (e) => setBoxWhiskerCurrent(e.target.checked) }
                                            label="Show selected redistricting plan?" 
                                            disabled //Enabled when a plan other than the enacted plan is selected
                                        />
                                        <Form.Check 
                                            type="checkbox" 
                                            classname="dark-checkbox" 
                                            id="boxwhisker-equalized" 
                                            onChange={ (e) => setBoxWhiskerEqualized(e.target.checked) }
                                            label="Show equalized plan?" 
                                            disabled //Enabled when the user has run the equalize algorithm on current districting plan
                                        />
                                    </Form.Group>
                                </Col>
                                <Col id="box-whisker-form-right">
                                    <Form.Group className="scrollbar-primary">
                                        <Form.Check 
                                            type="radio" 
                                            classname="dark-checkbox" 
                                            id="boxwhisker-basis-african" 
                                            name="boxwhisker-basis"
                                            checked = {boxWhiskerBasis == "african_american" ? true : false}
                                            onChange={ () => setBoxWhiskerBasis("african_american") }
                                            label="Compare African American Population" 
                                        />
                                        <Form.Check 
                                            type="radio" 
                                            classname="dark-checkbox" 
                                            id="boxwhisker-basis-hispanic" 
                                            name="boxwhisker-basis"
                                            checked = {boxWhiskerBasis == "hispanic" ? true : false}
                                            onChange={ () => setBoxWhiskerBasis("hispanic") }
                                            label="Compare Hispanic Population" 
                                        />
                                        <Form.Check 
                                            type="radio" 
                                            classname="dark-checkbox" 
                                            id="boxwhisker-basis-white" 
                                            name="boxwhisker-basis"
                                            checked = {boxWhiskerBasis == "asian" ? true : false}
                                            onChange={ () => setBoxWhiskerBasis("asian") }
                                            label="Compare Asian Population" 
                                        />
                                        <Form.Check 
                                            type="radio" 
                                            classname="dark-checkbox" 
                                            id="boxwhisker-basis-white" 
                                            name="boxwhisker-basis"
                                            checked = {boxWhiskerBasis == "white" ? true : false}
                                            onChange={ () => setBoxWhiskerBasis("white") }
                                            label="Compare White Population" 
                                        />
                                        <Form.Check 
                                            type="radio" 
                                            classname="dark-checkbox" 
                                            id="boxwhisker-basis-republican" 
                                            name="boxwhisker-basis"
                                            checked = {boxWhiskerBasis == "republican" ? true : false}
                                            onChange={ () => setBoxWhiskerBasis("republican") }
                                            label="Compare Republican Population" 
                                        />
                                        <Form.Check 
                                            type="radio" 
                                            classname="dark-checkbox" 
                                            id="boxwhisker-basis-democratic" 
                                            name="boxwhisker-basis"
                                            checked = {boxWhiskerBasis == "democrat" ? true : false}
                                            onChange={ () => setBoxWhiskerBasis("democrat") }
                                            label="Compare Democratic Population" 
                                        />
                                    </Form.Group>
                                </Col>
                            </Row>
                            <Row id="box-whisker-button-container">
                                <Button id="box-whisker-submit" size="md" variant="primary" type="submit">
                                    Show Chart
                                </Button>
                            </Row>
                        </Form>
                </TabPanel>
            </Box>
            <>
                <BoxWhiskerModal boxes = {boxes} points = {points} label = {label} bgcolor = {bgcolor} show = {showModal} onHide = {() => setShowModal(false)} />
            </>
        </>
    );
}

export default StatGraphs;