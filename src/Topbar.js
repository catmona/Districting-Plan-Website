import React, { useRef, useEffect, useState } from 'react';
import { DropdownButton } from 'react-bootstrap';
import { Dropdown } from 'react-bootstrap';
import { Col } from 'react-bootstrap';
import { Row } from 'react-bootstrap';
import { Container } from 'react-bootstrap';

function Topbar(props) {
    //const [stateName, setStateName] = useState("");
    let stateName = props.stateName!==''?props.stateName:'Select State';
    let setStateName = props.setStateName;

    useEffect(() =>{
        //document.getElementById("state-name").textContent = stateName;
    });

    return (
        <Container fluid id="topbar">
            <Row>
                <Col md={6}>
                    <h2 className="topbar-text">I want to </h2> <h1 className="topbar-text"><em className="rainbow-text">redistrict</em></h1> <h2 className="topbar-text">...</h2>
                </Col>

                <Col md={5} lg={3}>
                    <DropdownButton menuVariant="dark" title={stateName} id="state-dropdown">
                        <Dropdown.Item onClick={() => setStateName("Washington")} className='state-dropdown-option'>Washington</Dropdown.Item>
                        <Dropdown.Item onClick={() => setStateName("Nevada")} className='state-dropdown-option'>Nevada</Dropdown.Item>
                        <Dropdown.Item onClick={() => setStateName("Arkansas")} className='state-dropdown-option'>Arkansas</Dropdown.Item>
                    </DropdownButton>
                </Col>
            </Row>
          
        </Container>
    )
}

/*
<Col>
    <h3 className="topbar-text rainbow-text" id="state-name"></h3>
</Col>
*/

export default Topbar;