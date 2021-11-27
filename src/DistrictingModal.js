import React, { useRef, useEffect, useState } from 'react';
import { Modal, Button, Row, Col, DropdownButton, Dropdown, ProgressBar } from 'react-bootstrap';

function DistrictingModal(props) {
    const [selectedDistrict, setSelectedDistrict] = useState(-1);
    const {setPlanType, ...rest} = props;
    const [districtData, setDistrictData] = useState([{
        popAll: 100, popAfricanAmerican: 25, popAsian: 25, 
        popWhite: 50, election: 2014, demPercent: 60, repPercent: 40
    }]);

    let dropdownTitle = selectedDistrict != -1 ? "District " + selectedDistrict : 'Select District ';

    useEffect(() => {
        if(!props.data) return;
        
        let list = []
        let summary = props.data.data.summary;
        let pop = summary.districtPopulations;
        let elections = summary.districtElections;

        for(let i = 0; i < pop.length; i++) {
            let repVotes = elections[i].republicanVotes;
            let demVotes = elections[i].democraticVotes;
            let total = repVotes + demVotes;
            let repPercent = (repVotes/total) * 100;
            let demPercent = (demVotes/total) * 100;
            repPercent = parseFloat(repPercent.toFixed(2));
            demPercent = parseFloat(demPercent.toFixed(2));

            let data = {
                popAll: pop[i][3], popAfricanAmerican: pop[i][1], popAsian: pop[i][0], 
                popWhite: pop[i][2], election: 2014, demPercent: demPercent, repPercent: repPercent 
            }

            list.push(data);
        }


        setSelectedDistrict(-1);
        setDistrictData(list);
    }, [props.data]);

    useEffect(() => {
        let e = document.getElementById("districting-modal-stats");
        if(!e) return;

        if(selectedDistrict == -1)
            e.style.display = "none";
        else 
            e.style.display = "flex";
    }, [selectedDistrict]);

    return (
        <Modal {...rest} size="lg" centered className="dark-modal">
            <Modal.Header closeButton>
                <Modal.Title>
                    {props.stateName + " Redistricting Plan " + props.data.districtingNum}
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Row>
                    <DropdownButton id="districting-modal-dropdown" menuVariant="dark" title={dropdownTitle}>
                        {districtData.map((item, i) => {
                            return <Dropdown.Item onClick={() => {setSelectedDistrict(i+1)}}>{"District " + (i+1)}</Dropdown.Item>
                        })}
                    </DropdownButton>
                </Row>
                <Row id="districting-modal-stats">
                    <Col className="districting-modal-table">
                        <Row>
                            <p>Population: </p>
                            <div className="districting-modal-value">
                                <p>8600</p>
                            </div>
                        </Row>
                        <Row>
                            <p>African American: </p>
                            <div className="districting-modal-value">
                                <p>8600</p>
                            </div>
                        </Row>
                        <Row>
                            <p>Asian: </p>
                            <div className="districting-modal-value">
                                <p>8600</p>
                            </div>
                        </Row>
                        <Row>
                            <p>White: </p>
                            <div className="districting-modal-value">
                                <p>8600</p>
                            </div>
                        </Row>
                    </Col>
                    <Col id="districting-modal-congressional">
                        <Row className="districting-modal-congressional-block" id="districting-modal-election">
                            <h4>Election</h4>
                            <p>2014</p>
                        </Row>
                        <Row className="districting-modal-congressional-block districting-modal-table">
                            <Row>
                                <p>Democrat: </p>
                                <div className="districting-modal-value">
                                    <p id="districting-modal-dem">50%</p>
                                </div>
                            </Row>
                            <Row>
                                <p>Republican: </p>
                                <div className="districting-modal-value">
                                    <p id="districting-modal-rep">50%</p>
                                </div>
                            </Row>
                            <div>
                                <ProgressBar id="districting-modal-progress" now={50} variant="primary"/>
                            </div>
                        </Row>
                    </Col>
                </Row>
            </Modal.Body>
            <Modal.Footer>
                <Button 
                    variant="primary" 
                    onClick = {() => {
                        props.setPlanType("Districting " + props.data.districtingNum);
                        props.setSelectedPlanId(props.data.summary.planId);
                    }}
                >
                    Select
                </Button>
                <Button variant="danger" onClick = {props.onHide}>Close</Button>
            </Modal.Footer>

        </Modal>
    )
}

export default DistrictingModal;