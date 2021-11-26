import React, { useRef, useEffect, useState } from 'react';
import { Container, Row, Col, Popover, OverlayTrigger } from 'react-bootstrap';
import DistrictingModal from './DistrictingModal';

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
       <OverlayTrigger trigger={"hover", "focus"} placement="right" overlay={pop}>
           <img alt="alt" className = "img-thumbnail mx-auto thumbnail districting-img"></img>
       </OverlayTrigger>
    );
}

function districtings(props) {
    const [showModal, setShowModal] = useState(false);
    const [districtingSummary, setDistrictingSummary] = useState({distictingNum: 0, summary: []});
    const NUM_DISTRICTINGS = 20; //changeable to an array later, or fetched from a json or ini file
    let stateName = props.stateName;
    let previews = props.districtingPreviews;
    let loading = {'polsbyPopper':0,"popEquality":0};

    useEffect(() => {
        //right now this doesnt account for a different number of districtings per state
        //it wont delete excess images if there are any
        if(stateName || stateName != "") { 
            for(let i = 0; i < NUM_DISTRICTINGS; i++) {
                var col = document.getElementById("districting-img-" + (i+1));
                var img = col.firstChild;
                if(img) {
                    img.onclick = () => {
                        setShowModal(true);
                        setDistrictingSummary({districtingNum: i+1, summary: previews[i]})
                    };
                    img.src = require("/public/assets/thumbnails/" + stateName + "/districting-img-" + (i+1) + ".png").default;
                }
            }
        }
    });

    return(
        <>
            <Container id="districtings" className="scrollbar scrollbar-primary fluid">
                <Row>
                    <Col xs={3} id="districting-img-1"><DistrictingPopover num={1} summary={previews ? previews[0] : loading}/></Col>
                    <Col xs={3} id="districting-img-2"><DistrictingPopover num={2} summary={previews ? previews[1] : loading}/></Col>
                    <Col xs={3} id="districting-img-3"><DistrictingPopover num={3} summary={previews ? previews[2] : loading}/></Col>
                    <Col xs={3} id="districting-img-4"><DistrictingPopover num={4} summary={previews ? previews[3] : loading}/></Col>
                </Row>
                <Row>
                    <Col xs={3} id="districting-img-5"><DistrictingPopover num={5} summary={previews ? previews[4] : loading}/></Col>
                    <Col xs={3} id="districting-img-6"><DistrictingPopover num={6} summary={previews ? previews[5] : loading}/></Col>
                    <Col xs={3} id="districting-img-7"><DistrictingPopover num={7} summary={previews ? previews[6] : loading}/></Col>
                    <Col xs={3} id="districting-img-8"><DistrictingPopover num={8} summary={previews ? previews[7] : loading}/></Col>
                </Row>
                <Row>
                    <Col xs={3} id="districting-img-9"><DistrictingPopover num={9} summary={previews ? previews[8] : loading}/></Col>
                    <Col xs={3} id="districting-img-10"><DistrictingPopover num={10} summary={previews ? previews[9] : loading}/></Col>
                    <Col xs={3} id="districting-img-11"><DistrictingPopover num={11} summary={previews ? previews[10] : loading}/></Col>
                    <Col xs={3} id="districting-img-12"><DistrictingPopover num={12} summary={previews ? previews[11] : loading}/></Col>
                </Row>
                <Row>
                    <Col xs={3} id="districting-img-13"><DistrictingPopover num={13} summary={previews ? previews[12] : loading}/></Col>
                    <Col xs={3} id="districting-img-14"><DistrictingPopover num={14} summary={previews ? previews[13] : loading}/></Col>
                    <Col xs={3} id="districting-img-15"><DistrictingPopover num={15} summary={previews ? previews[14] : loading}/></Col>
                    <Col xs={3} id="districting-img-16"><DistrictingPopover num={16} summary={previews ? previews[15] : loading}/></Col>
                </Row>
                <Row>
                    <Col xs={3} id="districting-img-17"><DistrictingPopover num={17} summary={previews ? previews[16] : loading}/></Col>
                    <Col xs={3} id="districting-img-18"><DistrictingPopover num={18} summary={previews ? previews[17] : loading}/></Col>
                    <Col xs={3} id="districting-img-19"><DistrictingPopover num={19} summary={previews ? previews[18] : loading}/></Col>
                    <Col xs={3} id="districting-img-20"><DistrictingPopover num={20} summary={previews ? previews[19] : loading}/></Col>
                </Row>
            </Container>
            <>
                <DistrictingModal 
                    stateName = {stateName} 
                    data = {districtingSummary} 
                    show = {showModal} 
                    onHide = {() => setShowModal(false)} 
                    setPlanType = {props.setPlanType}
                />
            </>
        </>
    )
}

export default districtings;