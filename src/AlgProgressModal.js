import React, { useRef, useEffect, useState } from 'react';
import { CanvasJSChart } from 'canvasjs-react-charts';
import { Modal, Button, Row, Col } from 'react-bootstrap';

function AlgProgressModal(props) {
    const [numIterations, setNumIterations] = useState(0);
    const [popEquality, setPopEquality] = useState(0);
    const [timeRunning, setTimeRunning] = useState("00:00");
    const [precinctsChanged, setPrecinctsChanged] = useState(0);
    const [timerIntervalId, setTimerIntervalId] = useState(null);
    const [progressIntervalId, setProgressIntervalId] = useState(null);
    const [popEqualityDps, setPopEqualityDps] = useState([])
    const {setIsAlgDone, setDistrictingData, planType, setPlanType, isAlgDone, startTime, initialPopEquality, ...rest} = props;
    const chart = useRef(null);
    const dataLength = 10;

    const options = {
        theme: "dark1",
        animationEnabled: true,
        axisX: { 
            title: "Iteration",
        },
        axisY: {
            minimum: 0,  
        },
        dataPointWidth: 20,
        data: [{
            type: "line",
            color: "#ffc107",
            showInLegend: true,
            name: "Population Equality Score",
            dataPoints: popEqualityDps
        }]
    }
    
    const finishAlg = () => {
        if(timerIntervalId) clearInterval(timerIntervalId);
        if(progressIntervalId) clearInterval(progressIntervalId);
        setPlanType("Equalized " + planType);
        props.onHide();
    }

    const stopAlg = () => {
        if(timerIntervalId) clearInterval(timerIntervalId);
        if(progressIntervalId) clearInterval(progressIntervalId);
        
        fetch("http://localhost:8080/api2/stopAlgorithm", { credentials: 'include' })
        .then(res => res.json())
        .then((result) => {
            fetch("http://localhost:8080/api2/algorithmResults", { credentials: 'include' })
            .then(res => res.json())
            .then((result) => {
                console.log(result)
                setPrecinctsChanged(result.precinctsChanged);
                setDistrictingData(result);
                
                const precinctElement = document.getElementById("progress-precincts-block");
                precinctElement.style.backgroundColor = "#161616";
                precinctElement.style.border = "1px solid #000000"
                precinctElement.children[0].style.display = "inline";
                precinctElement.children[1].style.display = "inline";
            }, (error) => {
                console.log(error);
            }); 
        }, (error) => {
            console.log(error);
        });
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
            setPopEquality((result.populationEquality).toFixed(3));
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
        const init = initialPopEquality.toFixed(3);
        setNumIterations(0);
        setPopEquality(init);
        setPopEqualityDps([{x: 0, y: Number(init)}])
        setTimeRunning("00:00");
        
        //start timed stuff
        setTimerIntervalId(setInterval(updateTimer, 1000));
        setProgressIntervalId(setInterval(updateProgress, 2000));
        
    }, [startTime]);

    useEffect(() => {        
        if(!chart.current) return;

        setPopEqualityDps(old => [...old, {x: numIterations, y: Number(popEquality)}])
        
        if(popEqualityDps.length > dataLength) {
            popEqualityDps.shift();
        }

        chart.current.render()
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
                        <h4 className="progress-label">Initial: </h4>
                        <h4 id="progress-compactness" className="progress-value">{initialPopEquality.toFixed(3)}</h4>
                    </Col>
                </Row>
                <Row>
                    <Col className="progress-graph"> {/* Pop Equality Graph */}
                    <CanvasJSChart 
                        options = {options} 
                        id="progress-chart" 
                        onRef={ref => (chart.current = ref)} 
                        height="900px" 
                    />   
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
