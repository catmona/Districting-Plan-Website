import * as React from 'react';
import { useState, useEffect } from 'react';
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
    const boxData = []
    const pointData = { enacted: [], selected: [], equalized: [] }
    let yAxisLabel = ""

    //format boxes
    for(let i = 0; i < result.boxes.length; i++) {
        const l = "District " + (i+1); 
        const data = result.boxes[i];
        const box = [data.lowerExtreme, data.lowerQuartile, data.upperQuartile, data.upperExtreme, data.median];
        if (basis == 'republican' || basis == 'democrat') {
            // change to percent
            box = box.map(x => x * 100);
        }
        const col = { label: l, y: box }
        boxData.push(col);
    }

    // order boxes in ascending height
    boxData.sort((a,b) => a.y[0] - b.y[0]);

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
    const [value, setValue] = useState(0);
    const [boxWhiskerBasis, setBoxWhiskerBasis] = useState("african_american")
    const [boxWhiskerEnacted, setBoxWhiskerEnacted] = useState(false)
    const [boxWhiskerCurrent, setBoxWhiskerCurrent] = useState(false)
    const [boxWhiskerEqualized, setBoxWhiskerEqualized] = useState(false)
    const [showModal, setShowModal] = useState(false)
    const [boxes, setBoxes] = useState(null)
    const [points, setPoints] = useState({ enacted: [], selected: [], equalized: [] })
    const [label, setLabel] = useState("Total Population")
    const [currentAvailable, setCurrentAvailable] = useState(false);
    const [algAvailable, setAlgAvailable] = useState(false);
    const {planType} = props;
    const bgcolor = "#1f1f1f";
    const bgcolor2 = "#161616";

    useEffect(() => {
        if(planType.includes("Districting")) {
            setCurrentAvailable(true);
        }
        if(planType.includes("Equalized")) {
            setAlgAvailable(true);
        }
        if(planType.includes("Enacted")) {
            setCurrentAvailable(false);
            setAlgAvailable(false);
        }
        
    }, [planType])
    
    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    const chartEvents = [{
        eventName: "select",
        callback({ chartWrapper }) {
            console.log("Selected ", chartWrapper.getChart().getSelection());
            }
        }
    ];

    const getBoxWhiskerData = (event) => {
        event.preventDefault();
        fetch("http://localhost:8080/api2/boxwhiskers?districtingId=" + ((props.selectedPlanId || props.selectedPlanId !== "") ? props.selectedPlanId : '-1')
        + "&basis=" + boxWhiskerBasis 
        + "&enacted=" + boxWhiskerEnacted
        + "&postAlg=" + boxWhiskerEqualized,
        { credentials: 'include' })
        .then(res => res.json())
        .then(
            (result) => {
                const formattedData = formatResponseToBoxWhisker(result.boxWhisker, props.popType, boxWhiskerBasis);
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

    const partyData = [['District', 'Democratic Party', 'Republican Party']]
    partyData.push(...props.stateData.map((x) => { return [x.id + "", x.democrat, x.republican] }));

    const demographicData = [['District', 'White', 'African American', 'Asian']];
    demographicData.push(...props.stateData.map((x) => { return [x.id + "", x.white, x.africanamerican, x.asianamerican] }));
    
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
                        chartEvents={chartEvents}
                        sx={{ borderRight: 1, borderColor: 'divider', color: 'white' }}
                    >
                        <Tab disabled={props.waitData} className="graph-label" label="Party Population By District" {...a11yProps(0)} />
                        <Tab disabled={props.waitData} className="graph-label" label="Demographic Population By District" {...a11yProps(1)} />
                        <Tab disabled={props.waitData} className="graph-label" label="Compare to Average" {...a11yProps(2)} />
                    </Tabs>
                    <hr />
                    <DropdownButton disabled={props.waitData} menuVariant="dark" size="md" title={"Pop. Type: " + props.popType} id="poptype-dropdown">
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
                            title: "Party Population By District",

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
                            isStacked: 'false',
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
                            title: "Demographic Population By District",

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
                                            disabled={!currentAvailable}
                                        />
                                        <Form.Check 
                                            type="checkbox" 
                                            classname="dark-checkbox" 
                                            id="boxwhisker-equalized" 
                                            onChange={ (e) => setBoxWhiskerEqualized(e.target.checked) }
                                            label="Show equalized plan?" 
                                            disabled={!algAvailable}
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
                                            checked = {boxWhiskerBasis === "african_american"}
                                            onChange={ () => setBoxWhiskerBasis("african_american") }
                                            label="Compare African American Population" 
                                        />
                                        <Form.Check 
                                            type="radio" 
                                            classname="dark-checkbox" 
                                            id="boxwhisker-basis-white" 
                                            name="boxwhisker-basis"
                                            checked = {boxWhiskerBasis === "asian"}
                                            onChange={ () => setBoxWhiskerBasis("asian") }
                                            label="Compare Asian Population" 
                                        />
                                        <Form.Check 
                                            type="radio" 
                                            classname="dark-checkbox" 
                                            id="boxwhisker-basis-white" 
                                            name="boxwhisker-basis"
                                            checked = {boxWhiskerBasis === "white"}
                                            onChange={ () => setBoxWhiskerBasis("white") }
                                            label="Compare White Population" 
                                        />
                                        <Form.Check 
                                            type="radio" 
                                            classname="dark-checkbox" 
                                            id="boxwhisker-basis-republican" 
                                            name="boxwhisker-basis"
                                            checked = {boxWhiskerBasis === "republican"}
                                            onChange={ () => setBoxWhiskerBasis("republican") }
                                            label="Compare Republican Population" 
                                        />
                                        <Form.Check 
                                            type="radio" 
                                            classname="dark-checkbox" 
                                            id="boxwhisker-basis-democratic" 
                                            name="boxwhisker-basis"
                                            checked = {boxWhiskerBasis === "democrat"}
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
                <BoxWhiskerModal 
                    boxes = {boxes} 
                    points = {points} 
                    label = {label} 
                    bgcolor = {bgcolor} 
                    show = {showModal} 
                    onHide = {() => setShowModal(false)} 
                />
            </>
        </>
    );
}

export default StatGraphs;