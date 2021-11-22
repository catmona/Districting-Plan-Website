import React, { useRef, useEffect, useState } from 'react';
import { Modal, Button, Form} from 'react-bootstrap';

function AlgorithmModal(props) {
    const [popEqualityLim, setPopEqualityLim] = useState(1)
    const [compactnessLim, setCompactnessLim] = useState(1)
    const [algReady, setAlgReady] = useState(false)

    useEffect(() => {

    });

    const setAlgLimits = (event) => {
        event.preventDefault();

        //TODO fetch

        setAlgReady(true);
    };

    const startAlg = () => {
        console.log("alg started")
    }

    return (
        <Modal {...props} size="lg" centered className="dark-modal">
            <Modal.Header closeButton>
                <Modal.Title>
                    Set Equalization Constraints
                </Modal.Title>
            </Modal.Header>
            <Form onSubmit={setAlgLimits}>
                <Modal.Body>
                    <Form.Group className="constraint-group">
                        <div className="constraint-label">
                            <Form.Label>Minimum Population Equality Score: </Form.Label>
                            <h3>{popEqualityLim}</h3>
                        </div>
                        <Form.Range 
                            className="constraint-slider"
                            value={popEqualityLim} 
                            onChange={ (e) => {
                                setPopEqualityLim(e.target.value);
                                setAlgReady(false);
                            } } 
                            size="lg"
                            max={1} 
                            min={0} 
                            step={0.01} 
                        />
                    </Form.Group>
                    <Form.Group className="constraint-group">
                        <div className="constraint-label">
                            <Form.Label>Minimum Compactness Score: </Form.Label>
                            <h3>{compactnessLim}</h3>
                        </div>
                        <Form.Range 
                            className="constraint-slider"
                            value={compactnessLim} 
                            onChange={ (e) => {
                                setCompactnessLim(e.target.value);
                                setAlgReady(false)
                            } } 
                            size="lg"
                            max={1} 
                            min={0} 
                            step={0.01} 
                        />
                    </Form.Group>
                </Modal.Body>
                <Modal.Footer class="modal-spaced-footer">
                    <Button variant="primary" type="submit">Set Limits</Button>
                    <Button variant="success" onClick = {startAlg} disabled={!algReady}>Start Algorithm</Button>
                    <Button variant="danger" onClick = {props.onHide}>Cancel</Button>
                </Modal.Footer>
            </Form>
        </Modal>
    )
}

export default AlgorithmModal