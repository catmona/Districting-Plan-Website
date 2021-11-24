import React, { useRef, useEffect, useState } from 'react';
import { Modal, Button, Form} from 'react-bootstrap';

function ErrorModal(props) {
    const {error, ...rest} = props;

    return(
        <Modal {...rest} size="lg" centered className="dark-modal">
            <Modal.Header>
                <Modal.Title>
                    An Error Has Occurred :(
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <h3>{props.error.header}</h3>
                <p>{props.error.error}</p>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="primary" onClick={() => window.location.reload(false)}>
                    Click to Reload!
                </Button>
            </Modal.Footer >
        </Modal>
    );
}

export default ErrorModal;