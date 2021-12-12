import React, { useRef, useEffect, useState } from 'react';
import { Container, Popover, OverlayTrigger, DropdownButton, Dropdown, Button } from 'react-bootstrap';
import { QuestionCircle } from 'react-bootstrap-icons';
import DistrictingModal from './DistrictingModal';
import CircularProgress from '@mui/material/CircularProgress';
import Box from '@mui/material/Box';
import fallback from '/public/assets/icons/usa.png'

function DistrictingPopover(props) {
    const {score, polsbyPopper, populationEquality, efficiencyGap, majorityMinority, splitCounty} = props.summary;
    const {num, stateName, setDistrictingSummary, setShowModal} = props;
    const planId = props.summary.districtingId;

    const pop = (
        <Popover id="popover-basic" className="custom-popover">
            <Popover.Header as="h3">Districting Plan {num}</Popover.Header>
            <Popover.Body>
                <div className='pop-label'><b>Obj. Function Score: </b>{score.toFixed(3)}<br /></div>
                <div className='pop-label'><b>Population Equality: </b>{populationEquality.toFixed(3)}<br /></div>
                <div className='pop-label'><b>Polsby Popper: </b>{polsbyPopper.toFixed(3)}<br /></div>
                <div className='pop-label'><b>Efficiency Gap: </b>{efficiencyGap.toFixed(3)}<br /></div>
                <div className='pop-label'><b>Split County Count: </b>{splitCounty}<br /></div>
                <div className='pop-label'><b>Majority-Minority Count: </b>{majorityMinority}<br /></div>
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
            src = require("/public/assets/preview_images/" + stateName + "/" + planId + ".jpg").default;
        } catch(error) {
            //console.log("/public/assets/preview_images/" + stateName + "/" + planId + ".jpg")
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
    const [sortDescending, setSortDescending] = useState(false);
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
    
    function comparePopEquality(a, b, descending) {
        if(descending) {
            return b.preview.populationEquality - a.preview.populationEquality;
        }
        return a.preview.populationEquality - b.preview.populationEquality;
    }
    
    function compareCompactness(a, b, descending) {
        if(descending) {
            return b.preview.polsbyPopper - a.preview.polsbyPopper;
        }
        return a.preview.polsbyPopper - b.preview.polsbyPopper;
    }
    
    function comparePlanNumber(a, b, descending) {
        if(descending) {
            return b.num - a.num;
        }
        return a.num - b.num;
    }
    
    function compareMajorityMinority(a, b, descending) {
        if(descending) {
            return b.preview.majorityMinority - a.preview.majorityMinority;
        }
        return a.preview.majorityMinority - b.preview.majorityMinority;
    }
    
    function compareSplitCounty(a, b, descending) {
        if(descending) {
            return b.preview.splitCounty - a.preview.splitCounty;
        }
        return a.preview.splitCounty - b.preview.splitCounty;
    }
    
    function compareScore(a, b, descending) {
        if(descending) {
            return b.preview.score - a.preview.score;
        }
        return a.preview.score - b.preview.score;
    }
    
    function compareEffGap(a, b, descending) {
        if(descending) {
            return b.preview.efficiencyGap - a.preview.efficiencyGap;
        }
        return a.preview.efficiencyGap - b.preview.efficiencyGap;
    }
    
    function sortSelector() {
        switch(sortFunction) {
            case "Population Equality":
                return comparePopEquality;
            case "Polsby Popper":
                return compareCompactness;
            case "Majority Minority":
                return compareMajorityMinority;
            case "Split Counties":
                return compareSplitCounty;
            case "Objective Function Score":
                return compareScore;
            case "Efficiency Gap":
                return compareEffGap;
            case "Plan Number":
                return comparePlanNumber;
            default:
                return comparePlanNumber;
        }
    }
    
    function renderPlans() {
        const sortingFunction = sortSelector();
        
        const data = [].concat(plans)
        .sort((a, b) => sortingFunction(a, b, sortDescending))
        .map(plan => {
            return (
                <div xs={3} className="districting-container" id={"districting-img-" + plan.num} key={plan.num}>
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
    
    useEffect(() => {
        const e = document.getElementById("districtings-sort-button");
        if(!e) return;
        
        sortDescending ? e.innerHTML = '↑' : e.innerHTML = '↓';
    }, [sortDescending])
    
    const infoPopover = (
        <Popover className="custom-popover">
            <Popover.Header as="h3">Objective Function Weights</Popover.Header>
            <Popover.Body>
                <em style={{fontSize: 13}}>The districting plans were chosen based on the following normalized weights.</em><br /><br />
                <div className='pop-label'><b>Population Equality: </b>{9}<br /></div>
                <div className='pop-label'><b>Majority Minority: </b>{3}<br /></div>
                <div className='pop-label'><b>Polsby Popper: </b>{3}<br /></div>
                <div className='pop-label'><b>Deviation from Enacted: </b>{1}<br /></div>
                <div className='pop-label'><b>Deviation from Average: </b>{1}<br /></div>
            </Popover.Body>
        </Popover>
    );

    return(
        <>
            <div id="districtings-sort">
                <div id="districtings-sort-left">
                    <h4>Districting Plans</h4>
                    <OverlayTrigger trigger={["hover", "focus"]} placement='right' overlay={infoPopover}>
                        <QuestionCircle />
                    </OverlayTrigger>
                </div>
                <p>Sort By: </p>
                <DropdownButton id="districtings-sort-dropdown" menuVariant="dark" title={sortFunction} disabled={props.waitData}>
                    <Dropdown.Item onClick={() => setSortFunction("Plan Number")}>Plan Number</Dropdown.Item>
                    <Dropdown.Item onClick={() => setSortFunction("Objective Function Score")}>Objective Function Score</Dropdown.Item>
                    <Dropdown.Item onClick={() => setSortFunction("Population Equality")}>Population Equality</Dropdown.Item>
                    <Dropdown.Item onClick={() => setSortFunction("Polsby Popper")}>Polsby Popper</Dropdown.Item>
                    <Dropdown.Item onClick={() => setSortFunction("Efficiency Gap")}>Efficiency Gap</Dropdown.Item>
                    <Dropdown.Item onClick={() => setSortFunction("Split Counties")}>Split Counties</Dropdown.Item>
                    <Dropdown.Item onClick={() => setSortFunction("Majority Minority")}>Majority Minority Districts</Dropdown.Item>
                </DropdownButton>
                <Button 
                    variant='info' 
                    size='sm' 
                    onClick={() => setSortDescending(!sortDescending)} 
                    disabled={props.waitData} 
                    id='districtings-sort-button'
                />
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