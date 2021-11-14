import React, { useRef, useEffect, useState } from 'react';
import { Container } from 'react-bootstrap';
import { Row } from 'react-bootstrap';
import { Col } from 'react-bootstrap';
import { Popover } from 'react-bootstrap';
import { OverlayTrigger } from 'react-bootstrap';

function districtingPopover(props) {
    const pop = (
        <Popover id="popover-basic" className="custom-popover">
            <Popover.Header as="h3">Redisticting Plan {props.num}</Popover.Header>
                <Popover.Body>
                    <em style={{fontSize: 13}}>This districting was chosen for it's high political fairness.</em><br /><br />
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
           <img alt="alt" className = "img-thumbnail mx-auto thumbnail districting-img"></img>
       </OverlayTrigger>
    );
}

function districtings(props) {
    let stateName = props.stateName;
    let districtingPlan = props.districtingPlan;
    let setDistrictingPlan = props.setDistrictingPlan;
    const NUM_districtingS = 20; //changeable to an array later, or fetched from a json or ini file

    useEffect(() => {
        //right now this doesnt account for a different number of districtings per state
        //it wont delete excess images if there are any
        if(stateName || stateName != "") { 
            for(let i = 0; i < NUM_districtingS; i++) {
                var col = document.getElementById("districting-img-" + (i+1));
                var img = col.firstChild;
                if(img) {
                    img.onclick = setDistrictingPlan(stateName + "-" + (i+1));
                    img.src = require("/public/assets/thumbnails/" + stateName + "/districting-img-" + (i+1) + ".png").default; //webpack bs that I hate
                }
            }
        }
    });

    return(
        <Container id="districtings" className="scrollbar scrollbar-primary fluid">
            <Row>
                <Col xs={3} id="districting-img-1"><districtingPopover num={1}/></Col>
                <Col xs={3} id="districting-img-2"><districtingPopover num={2}/></Col>
                <Col xs={3} id="districting-img-3"><districtingPopover num={3}/></Col>
                <Col xs={3} id="districting-img-4"><districtingPopover num={4}/></Col>
            </Row>
            <Row>
                <Col xs={3} id="districting-img-5"><districtingPopover num={5}/></Col>
                <Col xs={3} id="districting-img-6"><districtingPopover num={6}/></Col>
                <Col xs={3} id="districting-img-7"><districtingPopover num={7}/></Col>
                <Col xs={3} id="districting-img-8"><districtingPopover num={8}/></Col>
            </Row>
            <Row>
                <Col xs={3} id="districting-img-9"><districtingPopover num={9}/></Col>
                <Col xs={3} id="districting-img-10"><districtingPopover num={10}/></Col>
                <Col xs={3} id="districting-img-11"><districtingPopover num={11}/></Col>
                <Col xs={3} id="districting-img-12"><districtingPopover num={12}/></Col>
            </Row>
            <Row>
                <Col xs={3} id="districting-img-13"><districtingPopover num={13}/></Col>
                <Col xs={3} id="districting-img-14"><districtingPopover num={14}/></Col>
                <Col xs={3} id="districting-img-15"><districtingPopover num={15}/></Col>
                <Col xs={3} id="districting-img-16"><districtingPopover num={16}/></Col>
            </Row>
            <Row>
                <Col xs={3} id="districting-img-17"><districtingPopover num={17}/></Col>
                <Col xs={3} id="districting-img-18"><districtingPopover num={18}/></Col>
                <Col xs={3} id="districting-img-19"><districtingPopover num={19}/></Col>
                <Col xs={3} id="districting-img-20"><districtingPopover num={20}/></Col>
            </Row>
        </Container>
    )
}

export default districtings;