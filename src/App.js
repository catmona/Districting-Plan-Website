import React, { useRef, useEffect, useState } from 'react';
import { Container } from 'react-bootstrap';
import { Col } from 'react-bootstrap';
import { Row, Tab, Nav, Sonnet } from 'react-bootstrap';
import Map from './Map'
import Topbar from './Topbar'
import kosta from './kosta.css'
import Statistics from './Statistics'

function App() {
    const [stateName, setStateName] = useState("");

    return (
        <Container fluid>
            <Row>
                <Col>
                <Row>
                    <Topbar stateName={stateName} setStateName={setStateName} />
                    HELL
                </Row>
                <Row>
                    <Statistics/>
                </Row>
                </Col>

                <Col>
                    <Map stateName={stateName} />
                </Col>
            </Row>
        </Container>

    );
}

export default App;