import React, {useEffect, useState } from 'react';
import { styled } from '@mui/material/styles';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';

const StyledTableCell = styled(TableCell)(({ theme }) => ({
    [`&.${tableCellClasses.head}`]: {
        backgroundColor: "#161616",
        color: theme.palette.common.white,
    },
    [`&.${tableCellClasses.body}`]: {
        backgroundColor: "#ffffff",
        fontSize: 14,
    },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
    '&:nth-of-type(odd)': {
        backgroundColor: "theme.palette.action.hover",
    },
    // hide last border
    '&:last-child td, &:last-child th': {
        border: 0,
    },
}));

//TODO get data from server
export default function CustomizedTables(props) {

    useEffect(() => {
        let popEquality = typeof props.measures.populationEquality != "undefined" ? props.measures.populationEquality.toFixed(3) : "-";
        let polsbyPopper = typeof props.measures.polsbyPopper != "undefined" ? props.measures.polsbyPopper.toFixed(3) : "-";
        let effGap = typeof props.measures.efficiencyGap != "undefined" ? props.measures.efficiencyGap.toFixed(3) : "-";
        let majMin = typeof props.measures.majorityMinority != "undefined" ? props.measures.majorityMinority : "-";
        let split = typeof props.measures.splitCounty != "undefined" ? props.measures.splitCounty : "-";
        
        if(props.planType.includes("Enacted")) {
            popEquality = "-";
            polsbyPopper = "-";
            effGap = "-";
            majMin = "-";
            split = "-";
        }
        
        document.getElementById("cellPopEquality").textContent = popEquality;
        document.getElementById("cellPolsbyPopper").textContent = polsbyPopper;
        document.getElementById("cellEfficiencyGap").textContent = effGap;
        document.getElementById("cellMajorityMinority").textContent = majMin;
        document.getElementById("SplitCounty").textContent = split;
        
    });

    //TODO insert formulas 
    return (
        <TableContainer component={Paper} className="measure-table">
            <Table aria-label="customized table">
                <TableHead>
                    <TableRow>
                        <StyledTableCell align='center'>Population Equality
                        </StyledTableCell>
                        <StyledTableCell align="center">Polsby Popper                         
                        </StyledTableCell>
                        <StyledTableCell align="center">Efficiency Gap
                        </StyledTableCell>
                        <StyledTableCell align="center">Majority-Minority
                        </StyledTableCell>
                        <StyledTableCell align="center">Split County                 
                        </StyledTableCell>
                    </TableRow>
                </TableHead>
                <TableBody id="district-tablebody">
                        <StyledTableRow>
                            <StyledTableCell id="cellPopEquality" align='center' component="th" scope="row">
                                {props.measures.populationEquality}
                            </StyledTableCell>
                            <StyledTableCell id="cellPolsbyPopper" align="center">{props.measures.populationEquality}</StyledTableCell>
                            <StyledTableCell id="cellEfficiencyGap" align="center">{10}</StyledTableCell>
                            <StyledTableCell id="cellMajorityMinority" align="center">{10}</StyledTableCell>
                            <StyledTableCell id="SplitCounty" align="center">{10}</StyledTableCell>
                        </StyledTableRow>
                    </TableBody>
            </Table>
        </TableContainer>
    );
}