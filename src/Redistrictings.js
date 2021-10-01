import React, { useRef, useEffect, useState } from 'react';
import { Container } from 'react-bootstrap';
import { Row } from 'react-bootstrap';
import { Col } from 'react-bootstrap';
import { Popover } from 'react-bootstrap';
import { OverlayTrigger } from 'react-bootstrap';
import 'kosta.css'

function RPlanPopover(props) {
    const pop = (
        <Popover id="popover-basic" className="custom-popover">
            <Popover.Header as="h3">Redisticting Plan {props.num}</Popover.Header>
                <Popover.Body>
                    <em style={{fontSize: 13}}>This redistricting was chosen for it's high political fairness.</em><br /><br />
                    <div className='districting-labels'><b>Population Equality: </b> 0.2<br /></div>
                    <div className='districting-labels'><b>Compactness: </b>0.8<br /></div>
                    <div className='districting-labels'><b>Deviation From Enacted: </b> 0.1 <br /></div>
                    <div className='districting-labels'><b>Deviation From Average: </b> 0.2<br /></div>
                    <div className='districting-labels'><b>Political Fairness: </b> 0.9<br/> </div>

                </Popover.Body>
        </Popover>
    );

    return(
       <OverlayTrigger trigger="hover" placement="right" overlay={pop}>
           <img alt="alt" className = "img-thumbnail mx-auto thumbnail redistricting-img"></img>
       </OverlayTrigger>
    );
}

function Redistrictings(props) {
    let stateName = props.stateName;
    let rPlan = props.rPlan;
    let setRPlan = props.setRPlan;
    const NUM_REDISTRICTINGS = 20; //changeable to an array later, or fetched from a json or ini file

    useEffect(() => {
        //right now this doesnt account for a different number of districtings per state
        //it wont delete excess images if there are any
        if(stateName || stateName != "") { 
            for(let i = 0; i < NUM_REDISTRICTINGS; i++) {
                var col = document.getElementById("redistricting-img-" + (i+1));
                var img = col.firstChild;
                if(img) {
                    img.onclick = setRPlan(stateName + "-" + (i+1));
                    img.src = require("/public/assets/thumbnails/" + stateName + "/redistricting-img-" + (i+1) + ".png").default; //webpack bs that I hate
                }
            }
        }
    });

    return(
        <Container id="redistrictings" className="scrollbar scrollbar-primary fluid">
            <Row>
                <Col xs={3} id="redistricting-img-1"><RPlanPopover num={1}/></Col>
                <Col xs={3} id="redistricting-img-2"><RPlanPopover num={2}/></Col>
                <Col xs={3} id="redistricting-img-3"><RPlanPopover num={3}/></Col>
                <Col xs={3} id="redistricting-img-4"><RPlanPopover num={4}/></Col>
            </Row>
            <Row>
                <Col xs={3} id="redistricting-img-5"><RPlanPopover num={5}/></Col>
                <Col xs={3} id="redistricting-img-6"><RPlanPopover num={6}/></Col>
                <Col xs={3} id="redistricting-img-7"><RPlanPopover num={7}/></Col>
                <Col xs={3} id="redistricting-img-8"><RPlanPopover num={8}/></Col>
            </Row>
            <Row>
                <Col xs={3} id="redistricting-img-9"><RPlanPopover num={9}/></Col>
                <Col xs={3} id="redistricting-img-10"><RPlanPopover num={10}/></Col>
                <Col xs={3} id="redistricting-img-11"><RPlanPopover num={11}/></Col>
                <Col xs={3} id="redistricting-img-12"><RPlanPopover num={12}/></Col>
            </Row>
            <Row>
                <Col xs={3} id="redistricting-img-13"><RPlanPopover num={13}/></Col>
                <Col xs={3} id="redistricting-img-14"><RPlanPopover num={14}/></Col>
                <Col xs={3} id="redistricting-img-15"><RPlanPopover num={15}/></Col>
                <Col xs={3} id="redistricting-img-16"><RPlanPopover num={16}/></Col>
            </Row>
            <Row>
                <Col xs={3} id="redistricting-img-17"><RPlanPopover num={17}/></Col>
                <Col xs={3} id="redistricting-img-18"><RPlanPopover num={18}/></Col>
                <Col xs={3} id="redistricting-img-19"><RPlanPopover num={19}/></Col>
                <Col xs={3} id="redistricting-img-20"><RPlanPopover num={20}/></Col>
            </Row>
            {/* <Overlay target={containerRef.current} container = {this} trigger="hover" placement="right" show={show}>
                {({placement, scheduleUpdate, ...p}) => (
                    <Popover id="popover-basic" {...p}>
                        <Popover.Header as="h3">Redisticting Plan info</Popover.Header>
                        <Popover.Body>
                            testestestetstettstettsttetstettste
                        </Popover.Body>
                    </Popover>
                )}
            </Overlay> */}
        </Container>
    )
}

export default Redistrictings;