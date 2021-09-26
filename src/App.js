import React, { useRef, useEffect, useState } from 'react';
import { Container, Tab, Tabs } from 'react-bootstrap';
import { Col } from 'react-bootstrap';
import { Row } from 'react-bootstrap';
import Map from './Map'
import Topbar from './Topbar'
import StateTabs from './StateTabs'

function App() {
    const [stateName, setStateName] = useState("");

    return(
        <Container fluid>
            <Row>
                <Col>
                    <Topbar stateName={stateName} setStateName={setStateName} />
                    <StateTabs stateName={stateName}></StateTabs>
                </Col>
                <Col>
                    <Map stateName={stateName} />
                </Col>
            </Row>
        </Container>
    );
}

export default App;