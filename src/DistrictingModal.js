import React, { useRef, useEffect, useState } from 'react';
import { Modal, Button, Row, Col, DropdownButton, Dropdown, ProgressBar } from 'react-bootstrap';

function DistrictingModal(props) {
    const [selectedDistrict, setSelectedDistrict] = useState(-1);
    const {setPlanType, ...rest} = props;
    const [districtData, setDistrictData] = useState([{
        popAll: 100, popAfricanAmerican: 25, popAsian: 25, 
        popWhite: 50, election: 2014, demPercent: 60, repPercent: 40
    }]);

    const dropdownTitle = selectedDistrict !== -1 ? "District " + (selectedDistrict+1) : 'Select District ';

    useEffect(() => {
        if(!props.data) return;
        
        const list = []
        const {summary} = props.data.data;
        const pop = summary.districtPopulations;
        const elections = summary.districtElections;

        for(let i = 0; i < pop.length; i++) {
            const repVotes = elections[i].republicanVotes;
            const demVotes = elections[i].democraticVotes;
            const total = repVotes + demVotes;
            let repPercent = (repVotes/total) * 100;
            let demPercent = (demVotes/total) * 100;
            repPercent = parseFloat(repPercent.toFixed(2));
            demPercent = parseFloat(demPercent.toFixed(2));

            const data = {
                popAll: Number(pop[i][3]).toLocaleString(), 
                popAfricanAmerican: Number(pop[i][1]).toLocaleString(), 
                popAsian: Number(pop[i][2]).toLocaleString(), 
                popWhite: Number(pop[i][0]).toLocaleString(), 
                election: elections[i].info.year,
                demPercent, repPercent 
            }

            list.push(data);
        }


        setSelectedDistrict(-1);
        setDistrictData(list);
    }, [props.data]);

    useEffect(() => {
        const e = document.getElementById("districting-modal-stats");
        if(!e) return;

        if(selectedDistrict === -1)
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
                            return <Dropdown.Item key={i} onClick={() => {setSelectedDistrict(i)}}>{"District " + (i+1)}</Dropdown.Item>
                        })}
                    </DropdownButton>
                </Row>
                <Row id="districting-modal-stats">
                    <Col className="districting-modal-table">
                        <Row id="districting-modal-pop-title">
                            <h4>Population</h4>
                        </Row>
                        <Row>
                            <p>Total: </p>
                            <div className="districting-modal-value">
                                <p>{districtData[selectedDistrict] ? districtData[selectedDistrict].popAll : ""}</p>
                            </div>
                        </Row>
                        <Row>
                            <p>Asian: </p>
                            <div className="districting-modal-value">
                                <p>{districtData[selectedDistrict] ? districtData[selectedDistrict].popAsian : ""}</p>
                            </div>
                        </Row>
                        <Row>
                            <p>White: </p>
                            <div className="districting-modal-value">
                                <p>{districtData[selectedDistrict] ? districtData[selectedDistrict].popWhite : ""}</p>
                            </div>
                        </Row>
                        <Row>
                            <p>African American: </p>
                            <div className="districting-modal-value">
                                <p>{districtData[selectedDistrict] ? districtData[selectedDistrict].popAfricanAmerican : ""}</p>
                            </div>
                        </Row>
                    </Col>
                    <Col id="districting-modal-congressional">
                        <Row className="districting-modal-congressional-block" id="districting-modal-election">
                            <h4>Election</h4>
                            <p>{districtData[selectedDistrict] ? districtData[selectedDistrict].election : ""}</p>
                        </Row>
                        <Row className="districting-modal-congressional-block districting-modal-table">
                            <Row>
                                <p>Democrat: </p>
                                <div className="districting-modal-value">
                                    <p id="districting-modal-dem">{districtData[selectedDistrict] ? districtData[selectedDistrict].demPercent + "%" : ""}</p>
                                </div>
                            </Row>
                            <Row>
                                <p>Republican: </p>
                                <div className="districting-modal-value">
                                    <p id="districting-modal-rep">{districtData[selectedDistrict] ? districtData[selectedDistrict].repPercent + "%" : ""}</p>
                                </div>
                            </Row>
                            <div>
                                <ProgressBar id="districting-modal-progress" now={districtData[selectedDistrict] ? districtData[selectedDistrict].demPercent : ""} variant="primary"/>
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
                        props.getDistrictingSummary(props.data.data.planId);
                        props.onHide();
                    }}
                >
                    Select
                </Button>
                <Button variant="danger" onClick={props.onHide}>Close</Button>
            </Modal.Footer>

        </Modal>
    )
}

export default DistrictingModal;