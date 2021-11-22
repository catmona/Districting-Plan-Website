import React, { useRef, useEffect, useState } from 'react';
import { DropdownButton } from 'react-bootstrap';
import { Dropdown } from 'react-bootstrap';
import { Col } from 'react-bootstrap';
import { Row } from 'react-bootstrap';
import { Container } from 'react-bootstrap';

function Topbar(props) {
    //const [stateName, setStateName] = useState("");
    let stateName = props.stateName!==''?props.stateName:'Select State';
    let setState = props.setState;

    useEffect(() =>{
        //document.getElementById("state-name").textContent = stateName;
    });

    return (
        <Row id="topbar">
            <Col className="topbar-item">
                <h2 className="topbar-text">I want to </h2> <h1 className="topbar-text"><em className="rainbow-text">redistrict</em></h1> <h2 className="topbar-text">...</h2>
            </Col>
            <Col className="topbar-item" xs={"auto"}>
                <DropdownButton id="state-dropdown" menuVariant="dark" title={stateName}>
                    <Dropdown.Item onClick={() => {setState("WA");}} className='state-dropdown-option'>Washington</Dropdown.Item>
                    <Dropdown.Item onClick={() => {setState("NV");}} className='state-dropdown-option'>Nevada</Dropdown.Item>
                    <Dropdown.Item onClick={() => {setState("AR");}} className='state-dropdown-option'>Arkansas</Dropdown.Item>
                </DropdownButton>
            </Col>
        </Row>
    )
}

/*
<Col>
    <h3 className="topbar-text rainbow-text" id="state-name"></h3>
</Col>
*/

export default Topbar;