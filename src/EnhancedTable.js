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
  { field: 'id', headerName: 'District', width: 5 },
  {
    field: 'firstName',
    headerName: 'Total Population',
    width: 150,
    editable: false,
    type: 'number',
  },
  {
    field: 'lastName',
    headerName: 'Democratic Party',
    width: 200,
    editable: false,
    type: 'number',
  },
  {
    field: 'age',
    headerName: 'Republican Party',
    type: 'number',
    width: 200,
    editable: false,
  },
  {
    field: 'africanamerican',
    headerName: 'African American',
    type: 'number',
    width: 200,
    editable: false,
  },
  {
    field: 'white',
    headerName: 'White',
    type: 'number',
    width: 200,
    editable: false,
  },
  {
    field: 'asianamerican',
    headerName: 'Asian American',
    type: 'number',
    width: 200,
    editable: false,
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
  { id: 1, lastName: 81750, firstName: 81750, age: 81750, africanamerican:81750, white:81750,asianamerican:81750 },
  { id: 2, lastName: 83750, firstName: 81750, age: 81750 , africanamerican:81750, white:81750,asianamerican:81750 },
  { id: 3, lastName: 87750, firstName: 81750, age: 81750 , africanamerican:81750, white:81750,asianamerican:81750 },
  { id: 4, lastName: 80750, firstName: 81750, age: 81750 , africanamerican:81750, white:81750,asianamerican:81750 },
  { id: 5, lastName: 82750, firstName: 81750, age: 81750 , africanamerican:81750, white:81750,asianamerican:81750 },
  { id: 6, lastName: 85750, firstName: 81750, age: 81750, africanamerican:81750, white:81750,asianamerican:81750  },
  { id: 7, lastName: 84750, firstName: 81750, age: 81750, africanamerican:81750, white:81750,asianamerican:81750  },
  { id: 8, lastName: 81750, firstName: 81750, age: 81750, africanamerican:81750, white:81750,asianamerican:81750  },
  { id: 9, lastName: 83750, firstName: 81750, age: 81750, africanamerican:81750, white:81750,asianamerican:81750  },
  { id: 10, lastName: 83750, firstName: 81750, age: 81750, africanamerican:81750, white:81750,asianamerican:81750  },
];

export default function EnhancedTable() {
  return (
    <div style={{ height: 300, width: '100%'}} className={'kosta-test'}>
      <DataGrid
        rows={rows}
        columns={columns}
        pageSize={10}
        rowsPerPageOptions={[5]}
        checkboxSelection
        disableSelectionOnClick
        disableColumnMenu
        getRowClassName = {'custom-column'}
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
