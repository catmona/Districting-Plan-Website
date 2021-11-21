import React, { useRef, useEffect, useState } from 'react';
import Chart from 'react-google-charts'
import { Modal, Button } from 'react-bootstrap';
import { CanvasJSChart } from 'canvasjs-react-charts'

function BoxWhiskerModal(props) {
    const chart = useRef(null)
    const options = {
        theme: "dark1",
        animationEnabled: true,
        axisY: { title: "test" }, //TODO make this whatever the selected pop measure was
        dataPointWidth: 15,
        data: [{
            type: "boxAndWhisker",
            //yValueFormatString: "#,##0.# \"kcal/100g\"",
            color: "#4285F4",
            stemThickness: 2,
            whickerThickness: 8,
            dataPoints: props.boxes
        },
        {
            type: "scatter",
            name: "Enacted Plan",
            toolTipContent: "<span style=\"color:{color}\">{name}</span>: {y} people",
            showInLegend: true,
            color: "#00ff00",
            dataPoints: []
        },
        {
            type: "scatter",
            name: "Selected Redistricting: ",
            toolTipContent: "<span style=\"color:{color}\">{name}</span>: {y} people",
            showInLegend: true,
            color: "#ff0000",
            dataPoints: []
        },
        {
            type: "scatter",
            name: "Equalized Plan",
            toolTipContent: "<span style=\"color:{color}\">{name}</span>: {y} people",
            showInLegend: true,
            color: "#0000ff",
            dataPoints: []
        }]
    }

    useEffect(() => {
        if(!chart.current) return;
        chart.current.chart.render() //for resetting width/height after modal appears
    });

    return (
        <Modal {...props} size="lg" centered className="dark-modal">
            <Modal.Header closeButton>
                <Modal.Title id="boxWhiskerModalTitle">
                    Box & Whisker Chart
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <div id="box-whisker-container">
                    <CanvasJSChart options = {options} id="box-whisker" 
                        ref={chart} />   
                </div>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="danger" onClick = {props.onHide}>Close</Button>
            </Modal.Footer>

        </Modal>
    )
}

export default BoxWhiskerModal;

{/* <Chart
                        className="box-whisker-chart"
                        chartType="CandlestickChart"
                        loader={<div>Loading Chart</div>}
                        data={props.boxes}
                        options={{
                            legend: {
                                position: 'bottom',
                                textStyle: { color: 'white' }
                            },
                            title: "",

                            titleTextStyle: {
                                color: 'white',
                                fontSize: 20
                            },
                            backgroundColor: props.bgcolor,
                            hAxis: {
                                textStyle: {
                                    color: 'white'
                                },
                                titleTextStyle: {
                                    color: 'white'
                                }
                            },
                            vAxis: {
                                textStyle: {
                                    color: 'white'
                                },
                                titleTextStyle: {
                                    color: 'white'
                                }
                            },
                        }}
                        rootProps={{ 'data-testid': '2' }}
                    /> */}