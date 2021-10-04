import React, { useEffect, useState } from 'react';
import { Container, Tab, Tabs } from 'react-bootstrap';
import Redistrictings from './Redistrictings';
import Statistics from 'Statistics.js';

function StateTabs(props) {
    const [stateSelected, setStateSelected] = useState(false);
    const [tab, setTab] = useState("none");

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
            <Tabs activeKey={tab} onSelect={(k) => setTab(k)}>
                        <Tab disabled={!stateSelected} eventKey="data-table" title="District Data">
                            {(props.stateName ==='Washington')?<Statistics/>:""}
                            </Tab>
                        <Tab disabled={!stateSelected} eventKey="random-districts" title="Districtings">
                                <Redistrictings stateName={props.stateName} rPlan={props.rPlan} setRPlan={props.setRPlan} />
                        </Tab>
                    </Tabs>
        </Container>
    );
}

export default StateTabs;