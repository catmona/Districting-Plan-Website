import React, { useRef, useEffect, useState } from 'react';
import { Modal, Button } from 'react-bootstrap';
import { CanvasJSChart } from 'canvasjs-react-charts'

function BoxWhiskerModal(props) {
    const chart = useRef(null);
    const {points} = props;
    const {boxes} = props;
    const {label} = props;

    function isPercent(label) {
        return label.includes('Republican') || label.includes('Democrat');
    }

    const suffix = isPercent(label) ? '%' : '';
    const tooltip = isPercent(label) ? '%' : ' people';

    const options = {
        theme: "dark1",
        animationEnabled: true,
        axisY: { title: label, suffix },
        dataPointWidth: 20,
        data: [{
            type: "boxAndWhisker",
            //yValueFormatString: "#,##0.# \"kcal/100g\"",
            stemThickness: 2,
            whickerThickness: 8,
            dataPoints: boxes,
            toolTipContent: "<div class=\"bw-label\"><span>Maximum:</span> {y[3]}</div><br>" +
                            "<div class=\"bw-label\"><span>Q3:</span>      {y[2]}</div><br>" +
                            "<div class=\"bw-label\"><span>Median:</span>  {y[4]}</div><br>" + 
                            "<div class=\"bw-label\"><span>Q1:</span>      {y[1]}</div><br>" +
                            "<div class=\"bw-label\"><span>Minimum:</span> {y[0]}</div>",
            color: "#4285F4",
        },
        {
            type: "scatter",
            name: "Enacted Plan",
            toolTipContent: "<span style=\"color:#00ff00\">{name}</span>: {y}" + tooltip,
            showInLegend: true,
            color: "#00ff00",
            dataPoints: points.enacted
        },
        {
            type: "scatter",
            name: "Selected Redistricting",
            toolTipContent: "<span style=\"color:#ff0000\">{name}</span>: {y}" + tooltip,
            showInLegend: true,
            color: "#ff0000",
            dataPoints: points.selected
        },
        {
            type: "scatter",
            name: "Equalized Plan",
            toolTipContent: "<span style=\"color:#0000ff\">{name}</span>: {y}" + tooltip,
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