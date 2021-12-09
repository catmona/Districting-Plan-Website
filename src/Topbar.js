import React, { useRef, useEffect, useState } from 'react';
import { Dropdown, DropdownButton, Col, Row } from 'react-bootstrap';
import { Refresh } from '@mui/icons-material'

function Topbar(props) {
    const stateName = props.stateName!=='' ? props.stateName : 'Select State';
    const {setState} = props;

    useEffect(() => {
        if(props.planType === "")
            document.getElementById("topbar-status").firstChild.style.display="none"
        else
        document.getElementById("topbar-status").firstChild.style.display="inline-block"
    }, [props.planType])

    return (
        <Row id="topbar">
            <Col className="topbar-item">
                <h2 className="topbar-text">I want to </h2> 
                <h1 className="topbar-text">
                    <em className="rainbow-text">redistrict</em>
                </h1> 
                <h2 className="topbar-text">...</h2>
            </Col>
            <Col className="topbar-item" id="topbar-status" xd={"auto"}>
                <div onClick={() => window.location.reload(false)}>
                    <Refresh />
                    <h3 className="topbar-text">{props.planType}</h3>
                </div>
            </Col>
            <Col className="topbar-item" xs={"auto"}>
                <DropdownButton id="state-dropdown" menuVariant="dark" title={stateName}>
                    <Dropdown.Item onClick={() => setState("WA")} className='state-dropdown-option'>Washington</Dropdown.Item>
                    <Dropdown.Item onClick={() => setState("NV")} className='state-dropdown-option'>Nevada</Dropdown.Item>
                    <Dropdown.Item onClick={() => setState("AR")} className='state-dropdown-option'>Arkansas</Dropdown.Item>
                </DropdownButton>
            </Col>
        </Row>
    )
}

export default Topbar;