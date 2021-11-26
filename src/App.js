import React, { useRef, useEffect, useState } from 'react';
import { Container, Tab, Tabs } from 'react-bootstrap';
import { Col } from 'react-bootstrap';
import { Row, Nav, Sonnet } from 'react-bootstrap';
import Map from './Map';
import Topbar from './Topbar';
import StateTabs from './StateTabs';
import ErrorModal from './ErrorModal';

function App() {
    const [stateName, setStateName] = useState("");
    const [districtingData, setDistrictingData] = useState(""); //statistics table data, population per district of the plan
    const [districtingPreviews, setDistrictingPreviews] = useState(""); //tooltip summaries for SeaWulf districtings 
    const [algResults, setAlgResults] = useState(null);
    const [planType, setPlanType] = useState("");
    const [showError, setShowError] = useState(false);
    const [errorText, setErrorText] = useState({header: "", error: ""});

    function showErrorModal(h, e) {
        setErrorText({header: h, error: e});
        setShowError(true);
    }

    function getStateSummary(stateAbbr) {
        fetch("http://localhost:8080/api2/getStateSummary?state=" + stateAbbr, { credentials: 'include' })
        .then(res => res.json())
        .then(
            (result) => {
                setDistrictingData(result);
            },
            (error) => {
                setDistrictingData(null)
                showErrorModal("Failed to get stats data", error);
                console.log(e)
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

    function getDistrictingPreviews() {
        fetch("http://localhost:8080/api2/districtings", { credentials: 'include' })
        .then(res => res.json())
        .then(
            (result) => {
                setDistrictingPreviews(result);
            },
            (error) => {
                showErrorModal("Failed to get districting previews", error)
                console.log(e)
            }
        );
    }

    return (
        <>
            <Container fluid>
                <Row>
                    <Col id="left-app">
                        <Row>
                            <Topbar stateName={stateName} setState={getStateSummary} planType={planType} />
                            <StateTabs 
                                showError={showErrorModal}
                                stateName={stateName} 
                                districtingData={districtingData} 
                                algResults={algResults} 
                                setAlgResults={setAlgResults} 
                                getDistrictingPreviews={getDistrictingPreviews} 
                                districtingPreviews={districtingPreviews}
                                setPlanType={setPlanType}
                            />
                        </Row>
                    </Col>
                    <Col id="right-app">
                        <Map 
                            showError={showErrorModal}
                            stateName={stateName} 
                            districtingData={districtingData} 
                            setState={getStateSummary} 
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