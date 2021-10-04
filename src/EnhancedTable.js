import React from 'react';
import { DataGrid } from '@mui/x-data-grid';
import Box from '@mui/material/Box';
import Fab from '@mui/material/Fab';
import NavigationIcon from '@mui/icons-material/Navigation';
import KeyboardBackspaceIcon from '@mui/icons-material/KeyboardBackspace';
import SaveAltIcon from '@mui/icons-material/SaveAlt';

const columns = [
  { id: 'district', headerName: 'District', width: 150 },
  {
    field: 'population',
    headerName: 'Population',
    width: 200,
    editable: false,
    type: 'number',
  },
  {
    field: 'democrat',
    headerName: 'Democratic Party',
    width: 200,
    editable: false,
    type: 'number',
  },
  {
    field: 'republican',
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
  },  {
    field: 'hispanic',
    headerName: 'Hispanic or Latino',
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
// ]};

const rows = [
  { id: 1, population: 791545, democrat: 249944, republican: 176407, africanamerican:12178, white:594230,asianamerican:102923,hispanic:76815 },
  { id: 2, population: 760064, democrat: 255252, republican: 148384 , africanamerican:25095, white:578055,asianamerican:72330,hispanic:84560 },
  { id: 3, population: 756675, democrat: 181347, republican: 235579 , africanamerican:12360, white:650254,asianamerican:24586,hispanic:77109 },
  { id: 4, population: 735797, democrat: 102667, republican: 202108 , africanamerican:10104, white:553564,asianamerican:10032,hispanic:291924 },
  { id: 5, population: 734322, democrat: 155737, republican: 247815 , africanamerican:12783, white:641200,asianamerican:18104,hispanic:50208 },
  { id: 6, population: 726540, democrat: 247429, republican: 168783, africanamerican:29054, white:587323,asianamerican:27394 ,hispanic:58095 },
  { id: 7, population: 817787, democrat: 387109, republican: 78240, africanamerican:41038, white:569986,asianamerican:119852,hispanic:66032  },
  { id: 8, population: 770177, democrat: 213123, republican: 198423, africanamerican:41038, white:557905,asianamerican:73610,hispanic:92771  },
  { id: 9, population: 751595, democrat: 258771, republican: 89697, africanamerican:41038, white:369451,asianamerican:179828,hispanic:97137  },
  { id: 10, population: 770391, democrat: 288977, republican: 51430, africanamerican:49594, white:549123,asianamerican:56308 ,hispanic:97072},
];



export default function EnhancedTable() {
  return (
    <div style={{ height: 400, width: '100%'}} className={'datagrid'}>
      <DataGrid
        //https://mui.com/api/data-grid/data-grid/
        rows={rows}
        columns={columns}
        pageSize={10}
        rowsPerPageOptions={[10]}
        rowHeight={32}
        //autoHeight={true}
        checkboxSelection
        disableSelectionOnClick={true}
        // disableColumnMenu
        hideFooter
        className={'datagrid'}
        
      />
     
      <Box sx={{ '& > :not(style)': { m: 1 } }} className='button-submit'>
      <Fab variant="extended" size="medium" color="primary" aria-label="add" className='submit'>
        <NavigationIcon sx={{ mr: 1 }} />
        <span className='submit'>Equalize Population</span>
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
