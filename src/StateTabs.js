import React, { useEffect, useState } from 'react';
import { Container, Tab, Tabs } from 'react-bootstrap';
import Districtings from './Districtings';
import Statistics from 'Statistics.js';

function StateTabs(props) {
    const [stateSelected, setStateSelected] = useState(false);
    const [tab, setTab] = useState("none");
    let onSelectTab = props.onSelectTab

    function selectTab(key) {
        if (key === "random-districts") {
            onSelectTab();
        }
        setTab(key)
    }

    useEffect(() => {
        if (!props.stateName || props.stateName === "") {
            setTab("none");
            setStateSelected(false);
        } else {
            setTab("data-table");
            setStateSelected(true);
        }
    }, [props.stateName]);

    return (
        <Container fluid id="state-tabs">
            <Tabs activeKey={tab} onSelect={(k) => selectTab(k)}>
                <Tab disabled={!stateSelected} eventKey="data-table" title="District Data">
                    {(props.stateName)?<Statistics stateName={props.stateName} districtingData={props.districtingData}/>:""}
                </Tab>
                <Tab disabled={!stateSelected} eventKey="random-districts" title="Districtings">
                        <Districtings stateName={props.stateName} districtingPlan={props.districtingPlan} setDistrictingPlan={props.setDistrictingPlan} seaWulfSummaries={props.seaWulfSummaries}/>
                </Tab>
            </Tabs>
            <div hidden={stateSelected} className="instructions-container">
                <img src={require("/public/assets/icons/usa.png").default} className="website-icon"/>
                <h3 className="text-center"> Select a state from the dropdown or by clicking on the map to the right!</h3>
            </div>
        </Container>
    );
}

export default StateTabs;