import React, { useRef, useEffect, useState } from 'react';
import { Container, Row, Col, Popover, OverlayTrigger, DropdownButton, Dropdown } from 'react-bootstrap';
import DistrictingModal from './DistrictingModal';
import CircularProgress from '@mui/material/CircularProgress';
import Box from '@mui/material/Box';
import fallback from '/public/assets/icons/usa.png'

function DistrictingPopover(props) {
    const planId = props.summary.districtingId;
    const score = props.summary.score;
    const polsby = props.summary.polsbyPopper;
    const popEquality = props.summary.populationEquality;
    const majorityMinority = props.summary.majorityMinority;
    const splitCounty = props.summary.splitCounty;
    // const devAvgPop = props.summary.devFromAveragePopulation;
    // const devEnactedPop = props.summary.devFromEnactedPopulation;
    const {num, stateName, setDistrictingSummary, setShowModal} = props;

    const pop = (
        <Popover id="popover-basic" className="custom-popover">
            <Popover.Header as="h3">Districting Plan {num}</Popover.Header>
            <Popover.Body>
                <em style={{fontSize: 13}}>This districting was chosen for it's high political fairness.</em><br /><br />
                <div className='districting-labels'><b>SeaWulf Score: </b>{score.toFixed(5)}<br /></div>
                <div className='districting-labels'><b>Population Equality: </b>{popEquality.toFixed(5)}<br /></div>
                <div className='districting-labels'><b>Compactness: </b>{polsby.toFixed(5)}<br /></div>
                <div className='districting-labels'><b>Majority-Minority Count: </b>{majorityMinority}<br /></div>
                <div className='districting-labels'><b>Split County Count: </b>{splitCounty}<br /></div>
                {/* <div className='districting-labels'><b>Deviation from Average Population: </b>{devAvgPop}<br /></div> */}
                {/* <div className='districting-labels'><b>Deviation from Enacted Population: </b>{devEnactedPop}<br /></div> */}
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
    const [sortFunction, setSortFunction] = useState("Plan Number");
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
    
    const plans = previews ? previews.map((preview, i) => {
        return {preview, num: (i+1)}
    }) : []
    
    function comparePopEquality(a, b) {
        const v1 = a.preview.populationEquality;
        const v2 = b.preview.populationEquality;
        
        if(v1 < v2) { return -1; }
        if(v1 > v2) { return 1; }
        if(v1 === v2) { return 0; }
    }
    
    function compareCompactness(a, b) {
        const v1 = a.preview.polsbyPopper;
        const v2 = b.preview.polsbyPopper;
        
        if(v1 < v2) { return -1; }
        if(v1 > v2) { return 1; }
        if(v1 === v2) { return 0; }
    }
    
    function comparePlanNumber(a, b) {
        const v1 = a.num;
        const v2 = b.num;
        
        if(v1 < v2) { return -1; }
        if(v1 > v2) { return 1; }
        if(v1 === v2) { return 0; }
    }
    
    useEffect(() => {
        switch(sortFunction) {
            case "Population Equality":
                plans.sort(comparePopEquality);
                break;
            
            case "Population Compactness":
                plans.sort(compareCompactness);
                break;
            default:
                plans.sort(comparePlanNumber);
                break;
        }
    }, [sortFunction, plans])

    return(
        <>
            <div id="districtings-sort">
                <h4>Districting Plans</h4>
                <p>Sort By: </p>
                <DropdownButton id="districtings-sort-dropdown" menuVariant="dark" title={sortFunction}>
                    <Dropdown.Item onClick={() => setSortFunction("Population Equality")}>Population Equality</Dropdown.Item>
                    <Dropdown.Item onClick={() => setSortFunction("Population Compactness")}>Population Compactness</Dropdown.Item>
                    <Dropdown.Item onClick={() => setSortFunction("Plan Number")}>Plan Number</Dropdown.Item>
                
                </DropdownButton>
            </div>
            <Container id="districtings" className="scrollbar scrollbar-primary fluid">
                {plans.length > 0 ?   
                    plans.map(plan => {
                        return (
                            <div xs={3} className="districting-container" id={"districting-img-" + plan.num}>
                                <DistrictingPopover 
                                    num={plan.num}
                                    summary={plan.preview} 
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