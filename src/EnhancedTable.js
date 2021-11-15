import React from 'react';
import { DataGrid } from '@mui/x-data-grid';
import Box from '@mui/material/Box';
import Fab from '@mui/material/Fab';
import NavigationIcon from '@mui/icons-material/Navigation';
import KeyboardBackspaceIcon from '@mui/icons-material/KeyboardBackspace';
import SaveAltIcon from '@mui/icons-material/SaveAlt';

const columns = [
  { field: 'id', headerName: 'District', width: 150 },
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

export default function EnhancedTable(props) {
  return (
    <div style={{ height: 400, width: '100%'}} className={'datagrid'}>
      {/* <button onClick = {() => {console.log(props.stateData)}}>press me</button>  */}
      <DataGrid
        //https://mui.com/api/data-grid/data-grid/
        rows={props.stateData}
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
        <span className='submit'>Equalize Population</span> //TODO note to cat: use a Modal
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
