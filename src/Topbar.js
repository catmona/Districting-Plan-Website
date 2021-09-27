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
        document.getElementById("state-name").textContent = stateName;
    });

    return(

        <Container fluid id="topbar">
            <Row>
            <Col>
                    <h3 className="topbar-text" id="state-name"></h3>
                </Col>
                <Col md={6}>
                    <h3 className="topbar-text">I want to de-racist-ify...</h3>
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

export default Topbar;