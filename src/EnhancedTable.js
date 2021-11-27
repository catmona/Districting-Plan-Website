import React, { useState } from "react";
import { DataGrid } from "@mui/x-data-grid";
import Box from "@mui/material/Box";
import Fab from "@mui/material/Fab";
import NavigationIcon from "@mui/icons-material/Navigation";
import KeyboardBackspaceIcon from "@mui/icons-material/KeyboardBackspace";
import SaveAltIcon from "@mui/icons-material/SaveAlt";
import AlgLimitsModal from "./AlgLimitsModal";
import AlgProgressModal from "./AlgProgressModal";

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
  },
];

export default function EnhancedTable(props) {
    const [showAlgLimits, setShowAlgLimits] = useState(false);
    const [showAlgProgress, setShowAlgProgress] = useState(false);

    return (
        <>
            <div style={{ height: 400, width: "100%" }} className={"datagrid"}>
                <DataGrid
                    //https://mui.com/api/data-grid/data-grid/
                    rows={props.stateData}
                    columns={columns}
                    pageSize={10}
                    rowsPerPageOptions={[10]}
                    rowHeight={32}
                    //checkboxSelection
                    disableSelectionOnClick={true}
                    hideFooter
                    className={"datagrid"}
                />
                <Box sx={{ "& > :not(style)": { m: 1 } }} className="button-submit">
                    <Fab
                        variant="extended"
                        size="medium"
                        color="primary"
                        aria-label="add"
                        className="submit"
                    >
                        <NavigationIcon sx={{ mr: 1 }} />
                        <span className="submit" onClick={() => setShowAlgLimits(true)}>
                            Equalize Population
                        </span>
                    </Fab>
                    <Fab
                        variant="extended"
                        size="medium"
                        color="primary"
                        aria-label="add"
                        className="submit"
                    >
                        <KeyboardBackspaceIcon sx={{ mr: 1 }} />
                        <span className="submit">Go Back</span>
                    </Fab>
                    <Fab
                        variant="extended"
                        size="medium"
                        color="primary"
                        aria-label="add"
                    >
                        <SaveAltIcon sx={{ mr: 1 }} />
                        <span className="submit">Save</span>
                    </Fab>
                </Box>
            </div>
            <>
                <AlgLimitsModal
                    show={showAlgLimits}
                    onHide={() => setShowAlgLimits(false)}
                    showProgress={setShowAlgProgress}
                />
                <AlgProgressModal
                    show={showAlgProgress}
                    onHide={() => setShowAlgProgress(false)}
                    setAlgResults={props.setAlgResults}
                />
            </>
        </>
    );
}
