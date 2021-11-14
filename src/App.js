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

    return (
        <Container fluid>
            <Row>
                <Col>
                <Row>
                    <Topbar stateName={stateName} setStateName={setStateName} />

                    <StateTabs stateName={stateName} districtingPlan={districtingPlan} setDistrictingPlan={setDistrictingPlan}></StateTabs>
                </Row>
                </Col>
                <Col>
                    <Map stateName={stateName} setStateName={setStateName} />
                </Col>
            </Row>
        </Container>
    );
}

export default App;