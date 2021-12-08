import React, { useRef, useEffect, useState } from 'react';
import { Container, Row, Col, Popover, OverlayTrigger } from 'react-bootstrap';
import DistrictingModal from './DistrictingModal';
import CircularProgress from '@mui/material/CircularProgress';
import Box from '@mui/material/Box';

function DistrictingPopover(props) {
    const planId = props.summary.districtingId;
    const polsby = props.summary.polsbyPopper;
    const popEquality = props.summary.populationEquality;

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
        <OverlayTrigger trigger={["hover", "focus"]} placement="right" overlay={pop}>
            <img alt="Loading..." className = "img-thumbnail mx-auto thumbnail districting-img" planId={planId}></img>
        </OverlayTrigger>
    );
}

function districtings(props) {
    const [showModal, setShowModal] = useState(false);
    const [districtingSummary, setDistrictingSummary] = useState({
        districtingNum: -1, data: {
            planId: 0, summary: {
                districtPopulations: [],
                districtElections: []
            }
        }
    });
    const {stateName} = props;
    const previews = props.districtingPreviews;
    const NUM_DISTRICTINGS = previews.length;
    const loading = {'polsbyPopper':0, "populationEquality":0};

    useEffect(() => {
        if(stateName || stateName !== "") { 
            for(let i = 0; i < NUM_DISTRICTINGS; i++) {
                const col = document.getElementById("districting-img-" + (i+1));
                if(!col) return;
                const img = col.firstChild;
                if(img) {
                    img.onclick = (e) => {
                        const planId = e.target.getAttribute("planId");
                        fetch("http://localhost:8080/api2/districtingSummary?districtingId=" + planId, { credentials: 'include' })
                            .then(res => res.json())
                            .then(
                                (result) => {
                                    console.log("District Summary result = %o", result);
                                    setShowModal(true);
                                    setDistrictingSummary({districtingNum: i+1, data: { planId, 'summary': result}});
                                },
                                (error) => {
                                    // showErrorModal("Failed to get districting plan data", error);
                                    console.log(error)
                                }
                            );
                    };
                    try {
                        img.src = require("/public/assets/thumbnails/" + stateName + "/districting-img-" + (i+1) + ".png").default;
                    } catch {
                        img.src = require("/public/assets/icons/usa.png").default;
                    }
                }
            }
        }
    });

    return(
        <>
            <Container id="districtings" className="scrollbar scrollbar-primary fluid">
                {previews ? previews.map((preview, i) => {
                        return <div xs={3} className="districting-container" id={"districting-img-" + (i+1)}><DistrictingPopover num={i+1} summary={previews ? preview : loading}/></div>
                    }) : 
                        <>
                            <Box className = 'loading-container'><CircularProgress className = 'loading-icon'/></Box>
                        </>}
            </Container>
            <>
                <DistrictingModal 
                    stateName = {stateName} 
                    data = {districtingSummary} 
                    show = {showModal} 
                    onHide = {() => setShowModal(false)} 
                    setPlanType = {props.setPlanType}
                    getDistrictingSummary={props.getDistrictingSummary}
                />
            </>
        </>
    )
}

export default districtings;