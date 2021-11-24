import React, { useRef, useEffect, useState } from 'react';
import { Modal, Button } from 'react-bootstrap';

function DistrictingModal(props) {
    const {setPlanType, ...rest} = props;

    useEffect(() => {

    })

    return (
        <Modal {...rest} size="lg" centered className="dark-modal">
            <Modal.Header closeButton>
                <Modal.Title id="districtingModalTitle">
                    {props.stateName + " Redistricting Plan " + props.data.districtingNum}
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <h4 id="districtingModalSubheader">Modal Subheader</h4>
                <p id="districtingModalText">Modal Text</p>
            </Modal.Body>
            <Modal.Footer>
                <Button 
                    variant="primary" 
                    onClick = {() => props.setPlanType("Districting " + props.data.districtingNum)}
                >
                    Select
                </Button>
                <Button variant="danger" onClick = {props.onHide}>Close</Button>
            </Modal.Footer>

        </Modal>
    )
}

export default DistrictingModal;