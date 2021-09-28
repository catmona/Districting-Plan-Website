import * as React from 'react';
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import Demo from 'DemographicsChart.js'
import Chart from 'react-google-charts'

function TabPanel(props) {
    const { children, value, index, ...other } = props;

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

export default function VerticalTabs() {
    const [value, setValue] = React.useState(0);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    const bgcolor = "#1f1f1f";
    const bgcolor2 = "#161616";

    return (
        <Box
            sx={{ flexGrow: 1, bgcolor: bgcolor2, display: 'flex', height: 325 }}
        >
            <Tabs
                orientation="vertical"
                variant="scrollable"
                value={value}
                onChange={handleChange}
                aria-label="Vertical tabs example"
                sx={{ borderRight: 1, borderColor: 'divider', color: 'white'}}
                className="kosta-test"
            >
                <Tab label="Population" {...a11yProps(0)} width='200px' />
                <Tab label="Ethnicity" {...a11yProps(1)} />
                <Tab label="Comparison vs Enacted" {...a11yProps(2)} />
            </Tabs>
            <TabPanel value={value} index={0} width={'100%'} className="dark-tabpanel">

                <Chart
                    className="dark-chart"
                    width={'600px'}
                    height={'300px'}
                    chartType="ColumnChart"
                    loader={<div>Loading Chart</div>}
                    data={[
                        ['District', 'Democratic Party', 'Republican Party'],
                        ['1', 249944, 176407],
                        ['2', 255252, 148384],
                        ['3', 181347, 235579],
                        ['4', 102667, 202108],
                        ['5', 155737, 247815],
                        ['6', 247429, 168783],
                        ['7', 387109, 78240],
                        ['8', 213123, 198423],
                        ['9', 258771, 89697],
                        ['10', 288977, 51430],
                    ]}

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
                    data={[
                        ['District', 'Hispanic or Latino', 'African American','Asian'],
                        ['1', 76815, 12178, 102923],
                        ['2', 84560, 25095, 72330],
                        ['3', 77109, 12360, 24586],
                        ['4', 291924, 10104, 10032],
                        ['5', 50208, 12783, 18104],
                        ['6', 58095, 29054, 27394],
                        ['7', 66032, 78240, 119852],
                        ['8', 92771, 41038, 73610],
                        ['9', 97137, 41038, 179828],
                        ['10', 97072, 49594, 56308],
                    ]}

                    options={{
                        // Material design options
                        title: "Ethnical Demographics per District",

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