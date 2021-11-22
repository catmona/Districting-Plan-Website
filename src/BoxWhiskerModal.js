import React, { useRef, useEffect, useState } from 'react';
import { Modal, Button } from 'react-bootstrap';
import { CanvasJSChart } from 'canvasjs-react-charts'

function BoxWhiskerModal(props) {
    const chart = useRef(null);
    let points = props.points;
    let boxes = props.boxes;
    let label = props.label;

    const options = {
        theme: "dark1",
        animationEnabled: true,
        axisY: { title: label }, //TODO make this whatever the selected pop measure was
        dataPointWidth: 20,
        data: [{
            type: "boxAndWhisker",
            //yValueFormatString: "#,##0.# \"kcal/100g\"",
            color: "#4285F4",
            stemThickness: 2,
            whickerThickness: 8,
            dataPoints: boxes
        },
        {
            type: "scatter",
            name: "Enacted Plan",
            toolTipContent: "<span style=\"color:{color}\">{name}</span>: {y} people",
            showInLegend: true,
            color: "#00ff00",
            dataPoints: points.enacted
        },
        {
            type: "scatter",
            name: "Selected Redistricting: ",
            toolTipContent: "<span style=\"color:{color}\">{name}</span>: {y} people",
            showInLegend: true,
            color: "#ff0000",
            dataPoints: points.selected
        },
        {
            type: "scatter",
            name: "Equalized Plan",
            toolTipContent: "<span style=\"color:{color}\">{name}</span>: {y} people",
            showInLegend: true,
            color: "#0000ff",
            dataPoints: points.equalized
        }]
    }

    useEffect(() => {
        if(!chart.current) return;
        chart.current.chart.render() //for resetting width/height after modal appears
    });

    return (
        <Modal {...props} size="lg" centered dialogClassName="modal-sizing" className="dark-modal">
            <Modal.Header closeButton>
                <Modal.Title id="boxWhiskerModalTitle">
                    Box & Whisker Chart
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <div id="box-whisker-container">
                    <CanvasJSChart options = {options} id="box-whisker" ref={chart} height="900px" />   
                </div>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="danger" onClick = {props.onHide}>Close</Button>
            </Modal.Footer>

        </Modal>
    )
}

export default BoxWhiskerModal;