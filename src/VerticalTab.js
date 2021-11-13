import * as React from 'react';
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import Demo from 'DemographicsChart.js'
import Chart from 'react-google-charts'

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
                    <Typography>{children}</Typography>
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

export default function VerticalTabs(props) {
    const [value, setValue] = React.useState(0);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    const bgcolor = "#1f1f1f";
    const bgcolor2 = "#161616";
    let partyData = [['District', 'Democratic Party', 'Republican Party']]
    partyData.push(...props.stateData.map((x) => { return [x['id'] + "", x['democrat'], x['republican']] }));
    let demographicData = [['District', 'Hispanic or Latino', 'African American', 'Asian']];
    demographicData.push(...props.stateData.map((x) => { return [x['id'] + "", x['hispanic'], x['africanamerican'], x['asianamerican']] }));
    return (
        <Box
            sx={{ flexGrow: 1, bgcolor: bgcolor2, display: 'flex', height: 325 }}
        >
            {/* <button onClick = {() => {console.log(partyData)}}>Press me</button> */}
            <Tabs
                orientation="vertical"
                variant="scrollable"
                value={value}
                onChange={handleChange}
                aria-label="Vertical tabs example"
                sx={{ borderRight: 1, borderColor: 'divider', color: 'white' }}
                className="kosta-test"
            >
                <Tab label="Population" {...a11yProps(0)} width='200px' />
                <Tab label="Demographics" {...a11yProps(1)} />
                <Tab label="Comparison with Enacted " {...a11yProps(2)} />
            </Tabs>
            <TabPanel value={value} index={0} width={'100%'} className="dark-tabpanel">

                <Chart
                    className="dark-chart"
                    width={'600px'}
                    height={'300px'}
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
                    width={'600px'}
                    height={'300px'}
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
                <Chart
                    className="dark-chart"
                    width={600}
                    height={350}
                    chartType="CandlestickChart"
                    loader={<div>Loading Chart</div>}
                    data={[
                        ['day', 'a', 'b', 'c', 'd'],
                        ['1', 20, 28, 38, 45],
                        ['2', 31, 38, 55, 66],
                        ['3', 50, 55, 77, 80],
                        ['4', 77, 77, 66, 50],
                        ['5', 68, 66, 22, 15],
                        ['6', 20, 28, 38, 45],
                        ['7', 31, 38, 55, 66],
                        ['8', 50, 55, 77, 80],
                        ['9', 77, 77, 66, 50],
                        ['10', 68, 66, 22, 15],
                    ]}
                    options={{
                        legend: 'none',
                        title: "Box and Whiskers Plot Placeholder",

                        titleTextStyle: {
                            color: 'white',
                            fontSize: 20
                        },
                        backgroundColor: bgcolor,
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
                    }}
                    rootProps={{ 'data-testid': '2' }}
                />
            </TabPanel>
        </Box>
    );
}