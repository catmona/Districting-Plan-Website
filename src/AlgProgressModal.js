import React, { useRef, useEffect, useState } from 'react';
import { Modal, Button, Form, ModalBody, ModalFooter} from 'react-bootstrap';
import ModalHeader from 'react-bootstrap/esm/ModalHeader';

function AlgProgressModal(props) {
   
    //TODO Change in measures
    //TODO num iterations
    //TODO time has been running
    //TODO est. time complete
    //TODO can the user pause? finish early? how do we resume?
    
    return(
        <Modal {...props} size="lg" centered className="dark-modal">
            <ModalHeader>
                Algorithm progress
            </ModalHeader>
            <ModalBody>
                
            </ModalBody>
            <ModalFooter>
               
                <Button variant="danger" onClick = {props.onHide}>Cancel</Button>
            </ModalFooter>
        </Modal>
    );
}

export default AlgProgressModal;