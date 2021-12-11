import React, { useRef, useEffect, useState } from 'react';
import { CanvasJSChart } from 'canvasjs-react-charts';
import { Modal, Button, Row, Col, ProgressBar } from 'react-bootstrap';

function AlgProgressModal(props) {
    const [numIterations, setNumIterations] = useState(0);
    const [popEquality, setPopEquality] = useState(0); //TODO should grab this from stats
    const [compactness, setCompactness] = useState(0); //this too
    const [timeRunning, setTimeRunning] = useState("00:00");
    const [progressPercent, setProgressPercent] = useState(50);
    const [precinctsChanged, setPrecinctsChanged] = useState(0);
    const [timerIntervalId, setTimerIntervalId] = useState(null);
    const [progressIntervalId, setProgressIntervalId] = useState(null);
    const {setAlgResults, setIsAlgDone, isAlgDone, startTime, ...rest} = props;
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
        if(timerIntervalId) clearInterval(timerIntervalId);
        if(progressIntervalId) clearInterval(progressIntervalId);
        props.setAlgResults("h"); //TODO lol
        props.setPlanType("Equalized " + props.planType);
        props.onHide();
    }

    const stopAlg = () => {
        if(timerIntervalId) clearInterval(timerIntervalId);
        if(progressIntervalId) clearInterval(progressIntervalId);
    }
    
    function updateTimer() {
        const elapsedTime = Date.now() - startTime;
        const totalSeconds = Math.trunc(elapsedTime/1000);
        const minutes = Math.trunc(totalSeconds / 60);
        const seconds = totalSeconds % 60;
        
        setTimeRunning(minutes.toString().padStart(2, '0') + ":" + seconds.toString().padStart(2, '0'));
    }
    
    function updateProgress() {
        fetch("http://localhost:8080/api2/algorithmProgress", { credentials: 'include' })
        .then(res => res.json())
        .then((result) => {
            console.log(result);
            setPopEquality((result.measures.populationEqualityScore).toFixed(6));
            setNumIterations(result.iterations);
            
            if(!result.running) {
                setIsAlgDone(true);
            }
        }, (error) => {
            console.log(error);
        })
    }
    
    useEffect(() => {
        if(!isAlgDone) return;
        
        stopAlg();
        
    }, [isAlgDone]);
    
    useEffect(() => {
        if(startTime === 0) return;
        
        //reset
        setPopEquality(0);
        setNumIterations(0);
        setTimeRunning("00:00");
        
        //start timed stuff
        setTimerIntervalId(setInterval(updateTimer, 1000));
        setProgressIntervalId(setInterval(updateProgress, 2000));
        
    }, [startTime]);

    useEffect(() => {        
        //update chart
        if(!chart.current) return;

        popEqualityDps.push({
            x: numIterations,
            y: popEquality
        });
        // compactnessDps.push({
        //     x: numIterations,
        //     y: compactness
        // });

        if(popEqualityDps.length > dataLength) {
            popEqualityDps.shift();
        }
        if(compactnessDps.length > dataLength) {
            compactnessDps.shift();
        }

        chart.current.chart.render()
    }, [numIterations]);

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
                <Button variant="danger" disabled={isAlgDone} onClick = {() => setIsAlgDone(true)}>Stop</Button>
            </Modal.Footer>
        </Modal>
    );
}

export default AlgProgressModal;
