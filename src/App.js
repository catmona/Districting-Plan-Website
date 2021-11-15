import React, { useRef, useEffect, useState } from 'react';
import { Container, Tab, Tabs } from 'react-bootstrap';
import { Col } from 'react-bootstrap';
import { Row, Nav, Sonnet } from 'react-bootstrap';
import Map from './Map';
import Topbar from './Topbar';
import StateTabs from './StateTabs';
import { propTypes } from 'react-bootstrap/esm/Image';

function App() {
    const [stateName, setStateName] = useState("");
    const [districtingPlan, setDistrictingPlan] = useState(""); //districting plan
    const [districtingData, setDistrictingData] = useState(""); //statisctics table data, population per district of the plan
    const [seaWulfSummaries, setSeaWulfSummaries] = useState(""); //tooltip summaries for SeaWulf districtings 

    function getStateSummary(stateAbbr) {
        fetch("http://localhost:8080/api2/getStateSummary?state=" + stateAbbr, { credentials: 'include' })
                .then(res => res.json())
                .then(
                    (result) => {
                        setDistrictingData(result);
                    },
                    (error) => {
                        setDistrictingData(null)
                        console.log(error)
                    }
                );
    }

    function getSeaWulfSummaries() {
        fetch("http://localhost:8080/api2/districtings", { credentials: 'include' })
                    .then(res => res.json())
                    .then(
                        (result) => {
                            console.log("Summaries: %o", result);
                            setSeaWulfSummaries(result);
                        },
                        (error) => {
                            console.log("Summaries: %o", result);
                            console.log(error)
                        }
                    );
    }

    return (
        <Container fluid>
            <Row>
                <Col id="left-app">
                    <Row>
                        <Topbar stateName={stateName} setStateName={setStateName} onSelect={getStateSummary} />

                        <StateTabs stateName={stateName} districtingPlan={districtingPlan} districtingData={districtingData} setDistrictingPlan={setDistrictingPlan} onSelectTab={getSeaWulfSummaries} seaWulfSummaries={seaWulfSummaries}></StateTabs>
                    </Row>
                </Col>
                <Col id="right-app">
                    <Map stateName={stateName} setStateName={setStateName} districtingData={districtingData} onSelect={getStateSummary} />
                </Col>
            </Row>
        </Container>
    );
}

export default App;