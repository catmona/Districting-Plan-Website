import React, { useRef, useEffect, useState } from 'react';
import { Container, Tab, Tabs } from 'react-bootstrap';
import { Col } from 'react-bootstrap';
import { Row, Nav, Sonnet } from 'react-bootstrap';
import Map from './Map';
import Topbar from './Topbar';
import StateTabs from './StateTabs';

function App() {
    const [stateName, setStateName] = useState("");
    const [districtingData, setDistrictingData] = useState(""); //statistics table data, population per district of the plan
    const [districtingPreviews, setDistrictingPreviews] = useState(""); //tooltip summaries for SeaWulf districtings 

    function getStateSummary(stateAbbr) {
        fetch("http://localhost:8080/api2/getStateSummary?state=" + stateAbbr, { credentials: 'include' })
                .then(res => res.json())
                .then(
                    (result) => {
                        setDistrictingData(result);
                    },
                    (error) => {
                        setDistrictingData(null)
                        console.log(error)
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

            default:
            break;
        }
    }

    function getDistrictingPreviews() {
        fetch("http://localhost:8080/api2/districtings", { credentials: 'include' })
                    .then(res => res.json())
                    .then(
                        (result) => {
                            setDistrictingPreviews(result);
                        },
                        (error) => {
                            console.log(error)
                        }
                    );
    }

    return (
        <Container fluid>
            <Row>
                <Col id="left-app">
                    <Row>
                        <Topbar stateName={stateName} setState={getStateSummary} />

                        <StateTabs stateName={stateName} districtingData={districtingData} getDistrictingPreviews={getDistrictingPreviews} districtingPreviews={districtingPreviews}></StateTabs>
                    </Row>
                </Col>
                <Col id="right-app">
                    <Map stateName={stateName} districtingData={districtingData} setState={getStateSummary} />
                </Col>
            </Row>
        </Container>
    );
}

export default App;