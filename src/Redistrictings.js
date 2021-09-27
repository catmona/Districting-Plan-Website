import React, { useRef, useEffect, useState } from 'react';
import { Container } from 'react-bootstrap';
import { Row } from 'react-bootstrap';
import { Col } from 'react-bootstrap';

function Redistrictings(props) {
    let stateName = props.stateName;
    let rPlan = props.rPlan;
    let setRPlan = props.setRPlan;
    const NUM_REDISTRICTINGS = 4; //changeable to an array later, or fetched from a json or ini file

    useEffect(() => {
        //right now this doesnt account for a different number of districtings per state
        //it wont delete excess images if there are any
        if(stateName || stateName != "") { 
            for(let i = 0; i < NUM_REDISTRICTINGS; i++) {
                var col = document.getElementById("redistricting-img-" + (i+1));
                if(col) {
                    var img = (col.firstChild);
                    if(typeof(img) == 'undefined' || img == null) { //dont recreate elements that already exist
                        img = document.createElement("img");
                        img.className += "img-thumbnail mx-auto thumbnail"
                        img.onclick = setRPlan(stateName + "-" + (i+1));
                        col.appendChild(img);
                    }

                    img.src = require("/public/assets/thumbnails/" + stateName + "/redistricting-img-" + (i+1) + ".png").default; //webpack bs that I hate
                }
            }
        }
    });

    return(
        <Container id="redistrictings" className="scrollbar scrollbar-primary">
            <Row>
                <Col xs={3} id="redistricting-img-1"></Col>
                <Col xs={3} id="redistricting-img-2"></Col>
                <Col xs={3} id="redistricting-img-3"></Col>
                <Col xs={3} id="redistricting-img-4"></Col>
            </Row>
            <Row>
                <Col xs={3} id="redistricting-img-5"></Col>
                <Col xs={3} id="redistricting-img-6"></Col>
                <Col xs={3} id="redistricting-img-7"></Col>
                <Col xs={3} id="redistricting-img-8"></Col>
            </Row>
            <Row>
                <Col xs={3} id="redistricting-img-9"></Col>
                <Col xs={3} id="redistricting-img-10"></Col>
                <Col xs={3} id="redistricting-img-11"></Col>
                <Col xs={3} id="redistricting-img-12"></Col>
            </Row>
            <Row>
                <Col xs={3} id="redistricting-img-13"></Col>
                <Col xs={3} id="redistricting-img-14"></Col>
                <Col xs={3} id="redistricting-img-15"></Col>
                <Col xs={3} id="redistricting-img-16"></Col>
            </Row>
            <Row>
                <Col xs={3} id="redistricting-img-17"></Col>
                <Col xs={3} id="redistricting-img-18"></Col>
                <Col xs={3} id="redistricting-img-19"></Col>
                <Col xs={3} id="redistricting-img-20"></Col>
            </Row>
        </Container>
    )
}

export default Redistrictings;