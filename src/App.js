import React, { useRef, useEffect, useState } from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import Map from './Map';
import Topbar from './Topbar';
import StateTabs from './StateTabs';
import ErrorModal from './ErrorModal';

window.onbeforeunload = function() {
    return "Data will be lost if you leave the page, are you sure?";
  };

function App() {
    const [stateName, setStateName] = useState("");
    const [districtingData, setDistrictingData] = useState(""); //statistics table data, population per district of the plan
    const [districtingPreviews, setDistrictingPreviews] = useState(""); //tooltip summaries for SeaWulf districtings 
    const [planType, setPlanType] = useState("");
    const [selectedPlanId, setSelectedPlanId] = useState("");
    const [showError, setShowError] = useState(false);
    const [errorText, setErrorText] = useState({header: "", error: ""});
    const [isAlgDone, setIsAlgDone] = useState(false);
    const [waitData, setWaitData] = useState(false);

    function showErrorModal(h, e) {
        setErrorText({header: h, error: e});
        setShowError(true);
    }

    function getStateSummary(stateAbbr) {
        if(waitData) return; //for user clicking on map while loading case
        
        setWaitData(true);
        fetch("http://localhost:8080/api2/getStateSummary?state=" + stateAbbr, { credentials: 'include' })
        .then(res => res.json())
        .then(
            (result) => {
                setDistrictingData(result);
                setSelectedPlanId(result.enactedId);
                setIsAlgDone(false);
                setWaitData(false);
            },
            (error) => {
                setDistrictingData(null);
                showErrorModal("Failed to get state data", error);
                console.log(error);
            }
        );
        
        switch (stateAbbr) {
            case "WA":
                setStateName("Washington")
            break;
            
            case "NV":
                setStateName("Nevada")
            break;

            case "AR":
                setStateName("Arkansas")
            break;
        }

        setPlanType("Enacted");
    }

    function getDistrictingSummary(planId = selectedPlanId) {
        setWaitData(true);
        fetch("http://localhost:8080/api2/districtingSummary?districtingId=" + planId, { credentials: 'include' })
        .then(res => res.json())
        .then(
            (result) => {
                setSelectedPlanId(planId);
                setIsAlgDone(false);
                setDistrictingData(result);
                setWaitData(false);
                if(planType.includes("Equalized ")) {
                    setPlanType(planType.replace("Equalized ", ""));
                }
            },
            (error) => {
                setDistrictingData(null)
                showErrorModal("Failed to get districting data", error);
                console.log(error);
            }
        );
    }

    function getDistrictingPreviews() {
        setWaitData(true);
        fetch("http://localhost:8080/api2/districtings", { credentials: 'include' })
        .then(res => res.json())
        .then(
            (result) => {
                setDistrictingPreviews(result);
                setWaitData(false);
            },
            (error) => {
                showErrorModal("Failed to get districting previews", error)
                console.log(error)
            }
        );
    }

    return (
        <>  
            <Container fluid style={{height: "100%"}}>
                <Row style={{height: "100%"}}>
                    <Col id="left-app">
                        <Row style={{height: "100%"}}>
                            <Topbar 
                                stateName={stateName} 
                                setState={getStateSummary} 
                                planType={planType} 
                                waitData={waitData}
                            />
                            <StateTabs  
                                showError={showErrorModal}
                                stateName={stateName} 
                                districtingData={districtingData} 
                                setDistrictingData={setDistrictingData}
                                getDistrictingPreviews={getDistrictingPreviews}
                                getDistrictingSummary={getDistrictingSummary}
                                districtingPreviews={districtingPreviews}
                                planType={planType}
                                setPlanType={setPlanType}
                                selectedPlanId={selectedPlanId}
                                setSelectedPlanId={setSelectedPlanId}
                                isAlgDone={isAlgDone}
                                setIsAlgDone={setIsAlgDone}
                                waitData={waitData}
                            />
                        </Row>
                    </Col>
                    <Col id="right-app">
                        <Map 
                            showError={showErrorModal}
                            stateName={stateName} 
                            districtingData={districtingData} 
                            setState={getStateSummary} 
                            selectedPlanId={selectedPlanId}
                        />
                    </Col>
                </Row>
            </Container>
            <ErrorModal 
                error={errorText} 
                show={showError} 
                backdrop="static"    
            />
        </>
    );
}

export default App;