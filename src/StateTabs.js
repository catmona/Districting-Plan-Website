import React, { useEffect, useState } from 'react';
import { Container, Tab, Tabs } from 'react-bootstrap';
import Districtings from './Districtings';
import Statistics from 'Statistics.js';

function StateTabs(props) {
    const [stateSelected, setStateSelected] = useState(false);
    const [tab, setTab] = useState("none");
    const {getDistrictingPreviews, stateName} = props;

    function selectTab(key) {
        if (key === "random-districts") {
            getDistrictingPreviews();
        }
        setTab(key)
    }

    useEffect(() => {
        if (!stateName || stateName === "") {
            setTab("none");
            setStateSelected(false);
            document.getElementById("state-tabs").getElementsByClassName("tab-content")[0].style.display = "none";
            
        } else {
            setTab("data-table");
            props.setAlgResults(null);
            setStateSelected(true);
            document.getElementById("state-tabs").getElementsByClassName("tab-content")[0].style.display = "flex";
        }
    }, [props.planType]);

    return (
        <Container fluid id="state-tabs">
            <Tabs activeKey={tab} onSelect={(k) => selectTab(k)}>
                <Tab disabled={!stateSelected} eventKey="data-table" title="Statistics">
                    {(stateName) ? 
                        <Statistics 
                            showError={props.showError}
                            stateName={stateName} 
                            districtingData={props.districtingData} 
                            algResults={props.algResults} 
                            setAlgResults={props.setAlgResults} 
                            selectedPlanId={props.selectedPlanId}
                            planType={props.planType}
                            setPlanType={props.setPlanType}
                            getDistrictingSummary={props.getDistrictingSummary}
                        /> 
                    : ""}
                </Tab>
                <Tab disabled={!stateSelected} eventKey="random-districts" title="Districtings">
                    <Districtings 
                        showError={props.showError}
                        stateName={stateName} 
                        districtingPreviews={props.districtingPreviews} 
                        setPlanType={props.setPlanType}
                        getDistrictingSummary={props.getDistrictingSummary}
                    />
                </Tab>
            </Tabs>
            
            <div hidden={stateSelected} className="instructions-container">
                <img src={require("/public/assets/icons/usa.png").default} className="website-icon" />
                <h3 className="text-center"> Select a state from the dropdown or by clicking on the map to the right!</h3>
            </div>
        </Container>
    );
}

export default StateTabs;