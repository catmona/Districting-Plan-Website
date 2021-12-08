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
export default function CustomizedTables() {

    useEffect(() => {
        document.getElementById("cellPopEquality").textContent = 0.05;
        document.getElementById("cellDevAverage").textContent = 0.5;
        document.getElementById("cellDevEnacted").textContent = 0.5;
        document.getElementById("cellCompactness").textContent = 0.5;
        document.getElementById("cellFairness").textContent = 0.5;
    });

    //TODO insert formulas 
    return (
        <TableContainer component={Paper} className="measure-table">
            <Table aria-label="customized table">
                <TableHead>
                    <TableRow>
                        <StyledTableCell align='center'>Population Equality
                            <Tooltip title={
                                <React.Fragment>
                                    <p className="formula-text">The population equality measure was computed using Mattingly's formula: </p>
                                    <img src={require("/public/assets/formulas/PopEquality.svg").default} className="formula-svg" />
                                </React.Fragment>}
                                >
                                <IconButton>
                                    <ContactSupportIcon className='white-icon'/>
                                </IconButton>
                            </Tooltip>
                        </StyledTableCell>
                        <StyledTableCell align="center">Deviation From Average                          
                            <Tooltip title={
                                <React.Fragment>
                                    <p className="formula-text">The deviation from average districting is computed by taking the sum of squared differences between the average and the computed districting: </p>
                                    <img src={require("/public/assets/formulas/PopEquality.svg").default} className="formula-svg" />
                                </React.Fragment>}
                                >
                                <IconButton>
                                    <ContactSupportIcon className='white-icon' />
                                </IconButton>
                            </Tooltip>
                        </StyledTableCell>
                        <StyledTableCell align="center">Deviation From Enacted
                            <Tooltip title={
                                <React.Fragment>
                                    <p className="formula-text">The deviation from enacted districting is computed by taking the sum of squared differences between the enacted and the computed districting: </p>
                                    <img src={require("/public/assets/formulas/PopEquality.svg").default} className="formula-svg" />
                                </React.Fragment>}
                                >
                                <IconButton>
                                    <ContactSupportIcon className='white-icon' />
                                </IconButton>
                            </Tooltip>
                        </StyledTableCell>
                        <StyledTableCell align="center">Compactness
                            <Tooltip title={
                                <React.Fragment>
                                    <p className="formula-text">The compactness was computed using the Polsby-Popper measure: </p>
                                    <img src={require("/public/assets/formulas/PolsbyPopper.svg").default } className="formula-svg" />
                                </React.Fragment>}
                                >
                                <IconButton>
                                    <ContactSupportIcon className='white-icon' />
                                </IconButton>
                            </Tooltip>
                        </StyledTableCell>
                        <StyledTableCell align="center">Political Fairness                          
                            <Tooltip title={
                                <React.Fragment>
                                    <p className="formula-text">Political fairness was computed with the Efficiency Gap measure by Stephanopoulos & McGhee: </p>
                                    <img src={require("/public/assets/formulas/PolFairness.svg").default} className="formula-svg" />
                                </React.Fragment>}
                                >
                                <IconButton>
                                    <ContactSupportIcon className='white-icon' />
                                </IconButton>
                            </Tooltip>
                        </StyledTableCell>
                    </TableRow>
                </TableHead>
                <TableBody id="district-tablebody">
                        <StyledTableRow>
                            <StyledTableCell id="cellPopEquality" align='center' component="th" scope="row">
                                {0.2}
                            </StyledTableCell>
                            <StyledTableCell id="cellDevAverage" align="center">{0.05}</StyledTableCell>
                            <StyledTableCell id="cellDevEnacted" align="center">{0.1}</StyledTableCell>
                            <StyledTableCell id="cellCompactness" align="center">{0.8}</StyledTableCell>
                            <StyledTableCell id="cellFairness" align="center">{0.5}</StyledTableCell>
                        </StyledTableRow>
                    </TableBody>
            </Table>
        </TableContainer>
    );
}