import React from 'react';
import { DataGrid } from '@mui/x-data-grid';
import { withTheme } from '@material-ui/core';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import Fab from '@mui/material/Fab';
import NavigationIcon from '@mui/icons-material/Navigation';
import KeyboardBackspaceIcon from '@mui/icons-material/KeyboardBackspace';
import SaveAltIcon from '@mui/icons-material/SaveAlt';
import 'kosta.css'
const columns = [
  { field: 'id', headerName: 'District', width: 150 },
  {
    field: 'lastName',
    headerName: 'Active Voters',
    width: 200,
    editable: true,
    type: 'number',
  },
  {
    field: 'firstName',
    headerName: 'Democratic Party',
    width: 200,
    editable: true,
    type: 'number',
  },
  {
    field: 'age',
    headerName: 'Republican Party',
    type: 'number',
    width: 200,
    editable: true,
  },
  {
    field: 'africanamerican',
    headerName: 'African American',
    type: 'number',
    width: 200,
    editable: true,
  },
  {
    field: 'white',
    headerName: 'White',
    type: 'number',
    width: 200,
    editable: true,
  },
  {
    field: 'asianamerican',
    headerName: 'Asian American',
    type: 'number',
    width: 200,
    editable: true,
  },
];

// data={[
//   ['District', 'Democratic Party', 'Republican Party'],
//   ['1', 81750, 75080],
//   ['2', 83750, 70080],
//   ['3', 87750, 76080],
//   ['4', 80750, 71080],
//   ['5', 82750, 74080],
//   ['6', 85750, 70080],
//   ['7', 89750, 71080],
//   ['8', 84750, 75080],
//   ['9', 81750, 73080],
//   ['10', 83750, 80080],
// ]}

const rows = [
  { id: 1, lastName: 426862, firstName: 249944, age: 176407, africanamerican:81750, white:81750,asianamerican:81750 },
  { id: 2, lastName: 404598, firstName: 255252, age: 148384 , africanamerican:81750, white:81750,asianamerican:81750 },
  { id: 3, lastName: 417903, firstName: 181347, age: 235579 , africanamerican:81750, white:81750,asianamerican:81750 },
  { id: 4, lastName: 305263, firstName: 102667, age: 202108 , africanamerican:81750, white:81750,asianamerican:81750 },
  { id: 5, lastName: 404360, firstName: 155737, age: 247815 , africanamerican:81750, white:81750,asianamerican:81750 },
  { id: 6, lastName: 417216, firstName: 247429, age: 168783, africanamerican:81750, white:81750,asianamerican:81750  },
  { id: 7, lastName: 466462, firstName: 387109, age: 78240, africanamerican:81750, white:81750,asianamerican:81750  },
  { id: 8, lastName: 412112, firstName: 213123, age: 198423, africanamerican:81750, white:81750,asianamerican:81750  },
  { id: 9, lastName: 349050, firstName: 258771, age: 89697, africanamerican:81750, white:81750,asianamerican:81750  },
  { id: 10, lastName: 340407, firstName: 288977, age: 51430, africanamerican:81750, white:81750,asianamerican:81750  },
];

export default function EnhancedTable() {
  return (
    <div style={{ height: 400, width: '100%'}} className={'kosta-test'}>
      <DataGrid
        rows={rows}
        columns={columns}
        pageSize={10}
        rowsPerPageOptions={[5]}
        checkboxSelection
        disableSelectionOnClick
        className={'datagrid'}
      />
     
      <Box sx={{ '& > :not(style)': { m: 1 } }} className='button-submit'>
      <Fab variant="extended" size="medium" color="primary" aria-label="add" className='submit'>
        <NavigationIcon sx={{ mr: 1 }} />
        <span className='submit'>Redistrict</span>
      </Fab>
      <Fab variant="extended" size="medium" color="primary" aria-label="add" className='submit'>
        <KeyboardBackspaceIcon sx={{ mr: 1 }} />
        <span className='submit'>Go Back</span>
      </Fab>
      <Fab variant="extended" size="medium" color="primary" aria-label="add">
        <SaveAltIcon sx={{ mr: 1 }} />
        <span className='submit'>Save</span>
      </Fab>
    
    </Box>
     
    </div>
  );
}
