import React, {useEffect, useState } from 'react';
import { styled } from '@mui/material/styles';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import IconButton from '@mui/material/IconButton';
import ContactSupportIcon from '@mui/icons-material/ContactSupport';
import Tooltip from '@mui/material/Tooltip';

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
        const popEquality = props.measures.populationEquality ? props.measures.populationEquality.toFixed(3) : "-";
        const polsbyPopper = props.measures.polsbyPopper ? props.measures.polsbyPopper.toFixed(3) : "-";
        const effGap = props.measures.efficiencyGap ? props.measures.efficiencyGap.toFixed(3) : "-";
        const majMin = props.measures.majorityMinority ? props.measures.majorityMinority : "-";
        const split = props.measures.splitCounty ? props.measures.splitCounty : "-";
        
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