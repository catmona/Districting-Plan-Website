import React, { useRef, useEffect, useState } from 'react';
import { Modal, Button } from 'react-bootstrap';

function AlgorithmModal(props) {

    useEffect(() => {

    });

    return (
        <Modal {...props} size="lg" centered className="dark-modal">
            <Modal.Header closeButton>
                <Modal.Title>
                    Algorithm Modal
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <h4>Modal Subheader</h4>
                <p>Modal Text</p>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="danger" onClick = {props.onHide}>Cancel</Button>
            </Modal.Footer>

        </Modal>
    )
}

export default AlgorithmModal