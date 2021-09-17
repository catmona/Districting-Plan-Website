import React, { useRef, useEffect, useState } from 'react';
import { Container } from 'react-bootstrap';
import { Col } from 'react-bootstrap';
import { Row } from 'react-bootstrap';
import Map from './Map'
import 'mapbox-gl/dist/mapbox-gl.css';
import './index.css'

function App() {

    return(
        <Container fluid>
            <Row>
                <Col>
                    <h1>left side</h1>
                </Col>

                <Col>
                    <Map />
                </Col>
            </Row>
        </Container>
        
    );
}

export default App;