import React, { useRef, useEffect, useState } from 'react';
import { DropdownButton } from 'react-bootstrap';
import { Dropdown } from 'react-bootstrap';
import { Col } from 'react-bootstrap';
import { Row } from 'react-bootstrap';
import { Container } from 'react-bootstrap';

function Topbar(props) {
    //const [stateName, setStateName] = useState("");
    let stateName = props.stateName;
    let setStateName = props.setStateName;

    useEffect(() =>{
        document.getElementById("state-name").textContent = stateName;
    });

    return(

        <Container fluid id="topbar">
            <Row>
                <Col md={5} lg={3}>
                    <DropdownButton menuVariant="dark" title="State Selection" id="state-dropdown">
                        <Dropdown.Item onClick={() => setStateName("Washington")}>Washington</Dropdown.Item>
                        <Dropdown.Item onClick={() => setStateName("Nevada")}>Nevada</Dropdown.Item>
                        <Dropdown.Item onClick={() => setStateName("Arkansas")}>Arkansas</Dropdown.Item>
                    </DropdownButton>
                </Col>
                <Col md={6}>
                    <h3 className="topbar-text">I want to de-racist-ify...</h3>
                </Col>
                <Col>
                    <h3 className="topbar-text" id="state-name"></h3>
                </Col>
            </Row>
        </Container>
    )
}

export default Topbar;