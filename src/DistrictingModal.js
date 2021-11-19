import React, { useRef, useEffect, useState } from 'react';
import { Modal, Button } from 'react-bootstrap';

function DistrictingModal(props) {

    useEffect(() => {
        //document.getElementById("districtingModalTitle").innerText = "Districting" 
        //console.log(props.data)
    })

    return (
        <Modal {...props} size="lg" centered className="dark-modal">
            <Modal.Header closeButton>
                <Modal.Title id="districtingModalTitle">
                    {"Districting Plan " + props.data.districtingNum + " Summary"}
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <h4 id="districtingModalSubheader">Modal Subheader</h4>
                <p id="districtingModalText">Modal Text</p>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="primary">Select</Button>
                <Button variant="danger" onClick = {props.onHide}>Close</Button>
            </Modal.Footer>

        </Modal>
    )
}

export default DistrictingModal;