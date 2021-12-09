import React, { useRef, useEffect, useState } from 'react';
import { Container, Row, Col, Popover, OverlayTrigger } from 'react-bootstrap';
import DistrictingModal from './DistrictingModal';
import CircularProgress from '@mui/material/CircularProgress';
import Box from '@mui/material/Box';
import fallback from '/public/assets/icons/usa.png'

function DistrictingPopover(props) {
    const planId = props.summary.districtingId;
    const polsby = props.summary.polsbyPopper;
    const popEquality = props.summary.populationEquality;
    const {num, stateName, setDistrictingSummary, setShowModal} = props;

    const pop = (
        <Popover id="popover-basic" className="custom-popover">
            <Popover.Header as="h3">Districting Plan {num}</Popover.Header>
            <Popover.Body>
                <em style={{fontSize: 13}}>This districting was chosen for it's high political fairness.</em><br /><br />
                <div className='districting-labels'><b>Population Equality: </b>{popEquality}<br /></div>
                <div className='districting-labels'><b>Compactness: </b>{polsby}<br /></div>
            </Popover.Body>
        </Popover>
    );
    
    function getPreview() {
        fetch("http://localhost:8080/api2/districtingSummary?districtingId=" + planId, { credentials: 'include' })
            .then(res => res.json())
            .then(
                (result) => {
                    //console.log("District Summary result = %o", result);
                    setShowModal(true);
                    setDistrictingSummary({districtingNum: num, data: { planId, 'summary': result}});
                },
                (error) => {
                    //showErrorModal("Failed to get districting plan data", error);
                    console.log(error)
                }
            );
    }
    
    function getThumbnail() {
        let src = "";
        try {
            src = require("/public/assets/thumbnails/" + stateName + "/districting-img-" + (num) + ".png").default;
        } catch(error) {
            src = fallback;
        }
        
        return (
            <img 
                alt="Loading..."
                src={src}
                className = "img-thumbnail mx-auto thumbnail districting-img" 
                planId={planId}
                onClick={() => getPreview()}
            />
        )
    }

    return (
        <OverlayTrigger trigger={["hover", "focus"]} placement="right" overlay={pop}>
            {getThumbnail()}
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
    const loading = {'polsbyPopper': 0, "populationEquality": 50};

    return(
        <>
            <Container id="districtings" className="scrollbar scrollbar-primary fluid">
                {previews ? previews.map((preview, i) => {
                        return (
                            <div xs={3} className="districting-container" id={"districting-img-" + (i+1)}>
                                <DistrictingPopover 
                                    num={i+1} 
                                    summary={previews ? preview : loading} 
                                    setShowModal = {setShowModal}
                                    setDistrictingSummary = {setDistrictingSummary}
                                    stateName = {stateName || ""}
                                />
                            </div>
                        )
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