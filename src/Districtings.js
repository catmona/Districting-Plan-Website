import React, { useRef, useEffect, useState } from 'react';
import { Container, Row, Col, Popover, OverlayTrigger, DropdownButton, Dropdown } from 'react-bootstrap';
import DistrictingModal from './DistrictingModal';
import CircularProgress from '@mui/material/CircularProgress';
import Box from '@mui/material/Box';
import fallback from '/public/assets/icons/usa.png'

function DistrictingPopover(props) {
    const {score, polsbyPopper, populationEquality, majorityMinority, splitCounty} = props.summary;
    const {num, stateName, setDistrictingSummary, setShowModal} = props;
    const planId = props.summary.districtingId;

    const pop = (
        <Popover id="popover-basic" className="custom-popover">
            <Popover.Header as="h3">Districting Plan {num}</Popover.Header>
            <Popover.Body>
                <em style={{fontSize: 13}}>This districting was chosen for it's high political fairness.</em><br /><br />
                <div className='districting-labels'><b>Obj. Function Score: </b>{score.toFixed(5)}<br /></div>
                <div className='districting-labels'><b>Population Equality: </b>{populationEquality.toFixed(5)}<br /></div>
                <div className='districting-labels'><b>Compactness: </b>{polsbyPopper.toFixed(5)}<br /></div>
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

    let plans = [];
    if(previews) {
        plans = previews.map((preview, i) => {
            return {preview, num: (i+1)}
        })
    }
    
    function comparePopEquality(a, b) {
        return a.preview.populationEquality - b.preview.populationEquality;
    }
    
    function compareCompactness(a, b) {
        return a.preview.polsbyPopper - b.preview.polsbyPopper;
    }
    
    function comparePlanNumber(a, b) {
        return a.preview.num - b.preview.num;
    }
    
    function compareMajorityMinority(a, b) {
        return a.preview.majorityMinority - b.preview.majorityMinority;
    }
    
    function compareSplitCounty(a, b) {
        return a.preview.splitCounty - b.preview.splitCounty;
    }
    
    function compareScore(a, b) {
        return a.preview.score - b.preview.score;
    }
    
    function sortSelector() {
        switch(sortFunction) {
            case "Population Equality":
                return comparePopEquality;
            case "Population Compactness":
                return compareCompactness;
            case "Majority Minority":
                return compareMajorityMinority;
            case "Split Counties":
                return compareSplitCounty;
            case "Objective Function Score":
                return compareScore;
            default:
                return comparePlanNumber;
        }
    }
    
    function renderPlans() {
        const sortingFunction = sortSelector();
        
        const data = [].concat(plans)
        .sort((a, b) => sortingFunction(a, b))
        .map(plan => {
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
        });
        
        return data;
    }

    return(
        <>
            <div id="districtings-sort">
                <h4>Districting Plans</h4>
                <p>Sort By: </p>
                <DropdownButton id="districtings-sort-dropdown" menuVariant="dark" title={sortFunction}>
                    <Dropdown.Item onClick={() => setSortFunction("Plan Number")}>Plan Number</Dropdown.Item>
                    <Dropdown.Item onClick={() => setSortFunction("Objective Function Score")}>Objective Function Score</Dropdown.Item>
                    <Dropdown.Item onClick={() => setSortFunction("Population Equality")}>Population Equality</Dropdown.Item>
                    <Dropdown.Item onClick={() => setSortFunction("Population Compactness")}>Population Compactness</Dropdown.Item>
                    <Dropdown.Item onClick={() => setSortFunction("Majority Minority")}>Majority Minority Districts</Dropdown.Item>
                    <Dropdown.Item onClick={() => setSortFunction("Split Counties")}>Split Counties</Dropdown.Item>
                </DropdownButton>
            </div>
            <Container id="districtings" className="scrollbar scrollbar-primary fluid">
                {plans.length > 0 ? renderPlans() : 
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