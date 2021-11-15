import React, { useRef, useEffect, useState } from 'react';
import { Container, Tab, Tabs } from 'react-bootstrap';
import { Col } from 'react-bootstrap';
import { Row, Nav, Sonnet } from 'react-bootstrap';
import Map from './Map';
import Topbar from './Topbar';
import StateTabs from './StateTabs';

function App() {
    const [stateName, setStateName] = useState("");
    const [districtingPlan, setDistrictingPlan] = useState(""); //districting plan
    const [districtingData, setDistrictingData] = useState(""); //statisctics table data, population per district of the plan

    function stateSummaryCallback(stateAbbr) {
        fetch("http://localhost:8080/api2/getStateSummary?state=" + stateAbbr)
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

    return (
        <Container fluid>
            <Row>
                <Col>
                <Row>
                    <Topbar stateName={stateName} setStateName={setStateName} onSelect={stateSummaryCallback}/>

                    <StateTabs stateName={stateName} districtingPlan={districtingPlan} districtingData={districtingData} setDistrictingPlan={setDistrictingPlan}></StateTabs>
                </Row>
                </Col>
                <Col>
                    <Map stateName={stateName} setStateName={setStateName} districtingData={districtingData} onSelect={stateSummaryCallback}/>
                </Col>
            </Row>
        </Container>
    );
}

export default App;