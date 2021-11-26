import { CanvasJSChart } from 'canvasjs-react-charts';
import React, { useRef, useEffect, useState } from 'react';
import { Modal, Button, Row, Col, ProgressBar} from 'react-bootstrap';

function AlgProgressModal(props) {
    const [numIterations, setNumIterations] = useState(0);
    const [popEquality, setPopEquality] = useState(0); //TODO should grab this from stats
    const [compactness, setCompactness] = useState(0); //this too
    const [timeRunning, setTimeRunning] = useState(0);
    const [estTimeRemaining, setEstTimeRemaining] = useState(0);
    const [progressPercent, setProgressPrecent] = useState(50);
    const [isAlgDone, setIsAlgDone] = useState(false);
    const {setAlgResults, ...rest} = props;
    const chart = useRef(null);

    var dataLength = 20;
    var popEqualityDps = [];
    var compactnessDps = [];

    const options = {
        theme: "dark1",
        animationEnabled: true,
        axisX: { title: "Iteration" },
        dataPointWidth: 20,
        data: [{
            type: "line",
            color: "#ffc107",
            showInLegend: true,
            name: "Population Equality Score",
            dataPoints: popEqualityDps
        },
        {
            type: "line",
            color: "#17a2b8",
            showInLegend: true,
            name: "Compactness Score",
            dataPoints: compactnessDps
        }]
    }

    const finishAlg = () => {
        props.setAlgResults("h");
        props.onHide();
    }

    const stopAlg = () => {
        setIsAlgDone(true);
    }

    useEffect(() => {
        //do other stuff

        if(progressPercent >= 100) setIsAlgDone(true);

        //update chart
        if(!chart.current) return;

        popEqualityDps.push({
            x: numIterations,
            y: popEquality
        });
        compactnessDps.push({
            x: numIterations,
            y: compactness
        });

        if(popEqualityDps.length > dataLength) {
            popEqualityDps.shift();
        }
        if(compactnessDps.length > dataLength) {
            compactnessDps.shift();
        }

        chart.current.chart.render()
    });

    //TODO can the user pause? finish early? how do we resume?

    return(
        <Modal {...rest} size="lg" centered className="dark-modal">
            <Modal.Header>
                <Modal.Title>
                    Algorithm progress
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Row>
                    <div className="progress-block">
                        <h4 className="progress-label">Num Iterations: </h4>
                        <h4 id="progress-iterations" className="progress-value">{numIterations}</h4>
                    </div>
                    <Col className="progress-block">
                        <h4 className="progress-label">Current Population Equality: </h4>
                        <h4 id="progress-equality" className="progress-value">{popEquality}</h4>
                    </Col>
                    <Col className="progress-block">
                        <h4 className="progress-label">Current Compactness: </h4>
                        <h4 id="progress-compactness" className="progress-value">{compactness}</h4>
                    </Col>
                </Row>
                <Row>
                    <Col className="progress-graph"> {/* Pop Equality Graph */}
                    <CanvasJSChart options = {options} id="progress-chart" ref={chart} height="900px" />   
                    </Col>
                </Row>
                <Row>
                    <Col className="progress-block">
                        <h4 className="progress-label">Time Spent Equalizing: </h4>
                        <h4 id="progress-time" className="progress-value">{timeRunning}</h4>
                    </Col>
                    <Col className="progress-block">
                        <h4 className="progress-label">Est. Time Remaining: </h4>
                        <h4 id="progress-time-remaining" className="progress-value">{estTimeRemaining}</h4>
                    </Col>
                </Row>
                <Row>
                    <div className="progress-bar">
                        <ProgressBar animated now={progressPercent} label={`${progressPercent}%`} variant="success" />
                    </div>
                </Row>
            </Modal.Body>
            <Modal.Footer className="modal-spaced-footer">    
                <Button variant="success" disabled={!isAlgDone} onClick = {finishAlg}>See Results</Button>
                <Button variant="danger" disabled={isAlgDone} onClick = {stopAlg}>Stop</Button>
            </Modal.Footer>
        </Modal>
    );
}

export default AlgProgressModal;