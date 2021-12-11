import { CanvasJSChart } from 'canvasjs-react-charts';
import React, { useRef, useEffect, useState } from 'react';
import { Modal, Button, Row, Col, ProgressBar } from 'react-bootstrap';

function AlgProgressModal(props) {
    const [numIterations, setNumIterations] = useState(0);
    const [popEquality, setPopEquality] = useState(0); //TODO should grab this from stats
    const [compactness, setCompactness] = useState(0); //this too
    const [timeRunning, setTimeRunning] = useState("00:00");
    const [progressPercent, setProgressPercent] = useState(50);
    const [precinctsChanged, setPrecinctsChanged] = useState(0);
    const {setAlgResults, setIsAlgDone, isAlgDone, ...rest} = props;
    const chart = useRef(null);

    const dataLength = 20;
    let popEqualityDps = [];
    let compactnessDps = [];

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
        props.setAlgResults("h"); //TODO lol
        props.setPlanType("Equalized " + props.planType);
        props.onHide();
    }

    const stopAlg = () => {
        setIsAlgDone(true);
    }

    useEffect(() => {
        //do other stuff
        //console.log(precinctsChanged)
        
        //TODO format time
        const totalSeconds = 0; //get from server or something
        const minutes = totalSeconds / 60;
        const seconds = totalSeconds % 60;
        setTimeRunning(minutes.toString().padStart(2, '0') + ":" + seconds.toString().padStart(2, '0'));

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
        <Modal {...rest} size="lg" centered backdrop="static" className="dark-modal">
            <Modal.Header>
                <Modal.Title>
                    Current Algorithm Progress
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Row>
                    <div className="progress-block">
                        <h4 className="progress-label">Iteration #: </h4>
                        <h4 id="progress-iterations" className="progress-value">{numIterations}</h4>
                    </div>
                    <Col className="progress-block">
                        <h4 className="progress-label">Pop. Equality: </h4>
                        <h4 id="progress-equality" className="progress-value">{popEquality}</h4>
                    </Col>
                    <Col className="progress-block">
                        <h4 className="progress-label">Compactness: </h4>
                        <h4 id="progress-compactness" className="progress-value">{compactness}</h4>
                    </Col>
                </Row>
                <Row>
                    <Col className="progress-graph"> {/* Pop Equality Graph */}
                    <CanvasJSChart options = {options} id="progress-chart" ref={chart} height="900px" />   
                    </Col>
                </Row>
                <Row>
                    <Row id="progress-bottom-row">
                        <div>
                            <div id="progress-time-block">
                                <h4 id="progress-time" className="progress-value">{timeRunning}</h4>
                            </div>
                        </div>
                        <div id="progress-precincts-block">
                            <p>Precincts Changed: </p>
                            <h4>{precinctsChanged.toLocaleString()}</h4>
                        </div>
                    </Row>
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
