import React, { useRef, useEffect, useState } from 'react';
import { Modal, Button, Form} from 'react-bootstrap';

function AlgLimitsModal(props) {
    const [popEqualityLim, setPopEqualityLim] = useState(props.initialPopEquality.toFixed(3));
    const [algReady, setAlgReady] = useState(false);
    const {showProgress, ...rest} = props;

    const setAlgLimits = (event) => {
        event.preventDefault();

        fetch("http://localhost:8080/api2/algorithmlimits?" 
        + "minPopulationEquality=" + popEqualityLim, { method: 'POST', credentials: 'include' })
        .then(res => {
            if (!res.ok) {
                console.log("Error calling setLimits: %o", res);
                return;
            }
            setAlgReady(true);
        });
    };

    const startAlg = () => {
        console.log("alg started")
        fetch("http://localhost:8080/api2/algorithm?districingNum=" + props.selectedPlanId, { credentials: 'include' })
        .then(res => res.json())
        .then(
            (result) => {
                console.log("Start algorithm res: %o", result);
                props.setStartTime(Date.now());
                props.showProgress(true);
                props.onHide();
            },
            (error) => {
                console.log(error);
            }
        );
    }
    
    useEffect(() => {
        setPopEqualityLim(props.initialPopEquality.toFixed(3));
    }, [props.initialPopEquality])

    return (
        <Modal {...rest} size="lg" centered className="dark-modal">
            <Modal.Header closeButton>
                <Modal.Title>
                    Algorithm Constraints
                </Modal.Title>
            </Modal.Header>
            <Form onSubmit={setAlgLimits}>
                <Modal.Body>
                    <Form.Group className="constraint-group">
                        <div className="constraint-label">
                            <Form.Label>Population Equality Goal: </Form.Label>
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
                            max={props.initialPopEquality.toFixed(3)}
                            min={0} 
                            step={0.001} 
                        />
                    </Form.Group>
                </Modal.Body>
                <Modal.Footer className="modal-spaced-footer">
                    <Button variant="primary" type="submit">Set Limits</Button>
                    <Button variant="success" onClick = {startAlg} disabled={!algReady}>Start Algorithm</Button>
                    <Button variant="danger" onClick = {props.onHide}>Cancel</Button>
                </Modal.Footer>
            </Form>
        </Modal>
    )
}

export default AlgLimitsModal;