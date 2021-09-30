import * as React from 'react';
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

function createData(name, calories, fat, carbs, protein) {
    return { name, calories, fat, carbs, protein };
}

const rows = [
    createData('150,000', 159, 6.0, 24, 4.0),
];

export default function CustomizedTables() {
    return (
        <TableContainer component={Paper} className="kosta-test">
            <Table aria-label="customized table">
                <TableHead>
                    <TableRow>
                        <StyledTableCell align='center'>Population Equality
                            <Tooltip title="The population equality measure was computed using Mattingly's formula: [FORMULA].">
                                <IconButton>
                                    <ContactSupportIcon className='white-icon'/>
                                </IconButton>
                            </Tooltip>
                        </StyledTableCell>
                        <StyledTableCell align="center">Deviation From Average                          
                        <Tooltip title="The deviation from average districting is computed by taking the sum of squared differences between the average and the computed districting: [FORMULA]. ">
                            <IconButton>
                                <ContactSupportIcon className='white-icon' />
                            </IconButton>
                        </Tooltip></StyledTableCell>
                        <StyledTableCell align="center">Deviation From Enacted
                            <Tooltip title="The deviation from enacted districting is computed by taking the sum of squared differences between the enacted and the computed districting: [FORMULA].">
                                <IconButton>
                                    <ContactSupportIcon className='white-icon' />
                                </IconButton>
                            </Tooltip></StyledTableCell>
                        <StyledTableCell align="center">Compactness
                            <Tooltip title="The compactness was computed using the Polsby-Popper measure: [FORMULA].">
                                <IconButton>
                                    <ContactSupportIcon className='white-icon' />
                                </IconButton>
                            </Tooltip></StyledTableCell>
                        <StyledTableCell align="center">Political Fairness                          <Tooltip title="Political fairness was computed with the Efficiency Gap measure by Stephanopoulos & McGhee: [FORMULA].">
                            <IconButton>
                                <ContactSupportIcon className='white-icon' />
                            </IconButton>
                        </Tooltip></StyledTableCell>
                    </TableRow>
                </TableHead>
                <TableBody id="district-tablebody">
                    {rows.map((row) => (
                        <StyledTableRow key={row.name}>
                            <StyledTableCell align='center' component="th" scope="row">
                                {0.2}
                            </StyledTableCell>
                            <StyledTableCell align="center">{0.05}</StyledTableCell>
                            <StyledTableCell align="center">{0.1}</StyledTableCell>
                            <StyledTableCell align="center">{0.8}</StyledTableCell>
                            <StyledTableCell align="center">{0.5}</StyledTableCell>
                        </StyledTableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}