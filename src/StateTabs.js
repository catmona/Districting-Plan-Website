import React, { useEffect, useState } from 'react';
import { Container, Tab, Tabs } from 'react-bootstrap';
import Districtings from './Districtings';
import Statistics from 'Statistics.js';

function StateTabs(props) {
    const [stateSelected, setStateSelected] = useState(false);
    const [tab, setTab] = useState("none");
    let getDistrictingPreviews = props.getDistrictingPreviews;
    let stateName = props.stateName;

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
        } else {
            setTab("data-table");
            setStateSelected(true);
        }
    }, [stateName]);

    return (
        <Container fluid id="state-tabs">
            <Tabs activeKey={tab} onSelect={(k) => selectTab(k)}>
                <Tab disabled={!stateSelected} eventKey="data-table" title="District Data">
                    {(stateName)?<Statistics stateName={stateName} districtingData={props.districtingData}/>:""}
                </Tab>
                <Tab disabled={!stateSelected} eventKey="random-districts" title="Districtings">
                        <Districtings stateName={stateName} districtingPreviews={props.districtingPreviews}/>
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