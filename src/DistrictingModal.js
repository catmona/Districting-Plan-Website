import React, { useRef, useEffect, useState } from 'react';
import { Modal, Button, Row, Col, DropdownButton, Dropdown, ProgressBar } from 'react-bootstrap';

function DistrictingModal(props) {
    const [selectedDistrict, setSelectedDistrict] = useState(-1);
    const {setPlanType, ...rest} = props;

    console.log(selectedDistrict)
    let dropdownTitle = selectedDistrict != -1 ? "District " + selectedDistrict : 'Select District ';

    useEffect(() => {
        console.log("data updated")
    }, [props.data]);

    useEffect(() => {
        if(selectedDistrict == -1)
            document.getElementById("districting-modal-stats").style.display = "none";
        else 
            document.getElementById("districting-modal-stats").style.display = "flex";
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
                        <Dropdown.Item onClick={() => {setSelectedDistrict(1)}}>District 1</Dropdown.Item> {/* TODO harcoded for now */}
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