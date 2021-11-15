import React, { useRef, useEffect, useState } from 'react';
import { Container } from 'react-bootstrap';
import { Row } from 'react-bootstrap';
import { Col } from 'react-bootstrap';
import { Popover } from 'react-bootstrap';
import { OverlayTrigger } from 'react-bootstrap';

function DistrictingPopover(props) {
    let polsby = props.summary.polsbyPopper;
    let popEquality = props.summary.populationEquality;

    const pop = (
        <Popover id="popover-basic" className="custom-popover">
            <Popover.Header as="h3">Districting Plan {props.num}</Popover.Header>
                <Popover.Body>
                    <em style={{fontSize: 13}}>This districting was chosen for it's high political fairness.</em><br /><br />
                    <div className='districting-labels'><b>Population Equality: </b>{popEquality}<br /></div>
                    <div className='districting-labels'><b>Compactness: </b>{polsby}<br /></div>
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
    let summaries = props.seaWulfSummaries;
    const NUM_districtingS = 20; //changeable to an array later, or fetched from a json or ini file
    let loading = {'polsbyPopper':0,"popEquality":0};

    useEffect(() => {
        //right now this doesnt account for a different number of districtings per state
        //it wont delete excess images if there are any
        if(stateName || stateName != "") { 
            for(let i = 0; i < NUM_districtingS; i++) {
                var col = document.getElementById("districting-img-" + (i+1));
                var img = col.firstChild;
                if(img) {
                    img.onclick = setDistrictingPlan(stateName + "-" + (i+1));
                    img.src = require("/public/assets/thumbnails/" + stateName + "/districting-img-" + (i+1) + ".png").default;
                }
            }
        }
    });

    // summaries.map((s) => (<Col xs={3} id="districting-img-"+""><DistrictingPopover num={1} data={props.}/></Col>))
    // TODO figure this out later
    return(
        <Container id="districtings" className="scrollbar scrollbar-primary fluid">
            <Row>
                <Col xs={3} id="districting-img-1"><DistrictingPopover num={1} summary={summaries ? summaries[0] : loading}/></Col>
                <Col xs={3} id="districting-img-2"><DistrictingPopover num={2} summary={summaries ? summaries[1] : loading}/></Col>
                <Col xs={3} id="districting-img-3"><DistrictingPopover num={3} summary={summaries ? summaries[2] : loading}/></Col>
                <Col xs={3} id="districting-img-4"><DistrictingPopover num={4} summary={summaries ? summaries[3] : loading}/></Col>
            </Row>
            <Row>
                <Col xs={3} id="districting-img-5"><DistrictingPopover num={5} summary={summaries ? summaries[4] : loading}/></Col>
                <Col xs={3} id="districting-img-6"><DistrictingPopover num={6} summary={summaries ? summaries[5] : loading}/></Col>
                <Col xs={3} id="districting-img-7"><DistrictingPopover num={7} summary={summaries ? summaries[6] : loading}/></Col>
                <Col xs={3} id="districting-img-8"><DistrictingPopover num={8} summary={summaries ? summaries[7] : loading}/></Col>
            </Row>
            <Row>
                <Col xs={3} id="districting-img-9"><DistrictingPopover num={9} summary={summaries ? summaries[8] : loading}/></Col>
                <Col xs={3} id="districting-img-10"><DistrictingPopover num={10} summary={summaries ? summaries[9] : loading}/></Col>
                <Col xs={3} id="districting-img-11"><DistrictingPopover num={11} summary={summaries ? summaries[10] : loading}/></Col>
                <Col xs={3} id="districting-img-12"><DistrictingPopover num={12} summary={summaries ? summaries[11] : loading}/></Col>
            </Row>
            <Row>
                <Col xs={3} id="districting-img-13"><DistrictingPopover num={13} summary={summaries ? summaries[12] : loading}/></Col>
                <Col xs={3} id="districting-img-14"><DistrictingPopover num={14} summary={summaries ? summaries[13] : loading}/></Col>
                <Col xs={3} id="districting-img-15"><DistrictingPopover num={15} summary={summaries ? summaries[14] : loading}/></Col>
                <Col xs={3} id="districting-img-16"><DistrictingPopover num={16} summary={summaries ? summaries[15] : loading}/></Col>
            </Row>
            <Row>
                <Col xs={3} id="districting-img-17"><DistrictingPopover num={17} summary={summaries ? summaries[16] : loading}/></Col>
                <Col xs={3} id="districting-img-18"><DistrictingPopover num={18} summary={summaries ? summaries[17] : loading}/></Col>
                <Col xs={3} id="districting-img-19"><DistrictingPopover num={19} summary={summaries ? summaries[18] : loading}/></Col>
                <Col xs={3} id="districting-img-20"><DistrictingPopover num={20} summary={summaries ? summaries[19] : loading}/></Col>
            </Row>
        </Container>
    )
}

export default districtings;