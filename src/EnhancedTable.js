import React, { useState } from "react";
import { DataGrid } from "@mui/x-data-grid";
import AlgLimitsModal from "./AlgLimitsModal";
import AlgProgressModal from "./AlgProgressModal";
import TableButtons from "./TableButtons";

const columns = [
  { 
      field: 'id', 
      headerName: 'District', 
      width: 20,
      editable: false,
      type: 'number',
  },
  {
    field: 'population',
    headerName: 'Population',
    width: 150,
    editable: false,
    type: 'number',
  },
  {
    field: 'democrat',
    headerName: 'Democrat',
    width: 140,
    editable: false,
    type: 'number',
    valueFormatter: (params) => {
        const valueFormatted = Number(params.value * 100).toFixed(2).toLocaleString();
        return valueFormatted + '%';
    }
  },
  {
    field: 'republican',
    headerName: 'Republican',
    type: 'number',
    width: 150,
    editable: false,
    valueFormatter: (params) => {
        const valueFormatted = Number(params.value * 100).toFixed(2).toLocaleString();
        return valueFormatted + '%';
    }
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
    width: 110,
    editable: false,
  },
  {
    field: 'asianamerican',
    headerName: 'Asian',
    type: 'number',
    width: 110,
    editable: false,
  },
];

export default function EnhancedTable(props) {
    const [showAlgLimits, setShowAlgLimits] = useState(false);
    const [showAlgProgress, setShowAlgProgress] = useState(false);
    const [startTime, setStartTime] = useState(0);

    return (
        <>
            <div className='datagrid-row'>
                <div className='datagrid-container'>
                    <DataGrid
                        //https://mui.com/api/data-grid/data-grid/
                        rows={props.stateData}
                        columns={columns}
                        pageSize={10}
                        rowsPerPageOptions={[10]}
                        rowHeight={32}
                        disableColumnMenu={true}
                        disableSelectionOnClick={true}
                        hideFooter
                        className={"datagrid"}
                    />
                </div>
            </div>
            <TableButtons 
                setShowAlgLimits={setShowAlgLimits}
                isAlgDone={props.isAlgDone}
                getDistrictingSummary={props.getDistrictingSummary}
                districtingData={props.districtingData} 
                planType={props.planType}
                waitData={props.waitData}
            />
            <>
                <AlgLimitsModal
                    show={showAlgLimits}
                    onHide={() => setShowAlgLimits(false)}
                    showProgress={setShowAlgProgress}
                    selectedPlanId={props.selectedPlanId}
                    setStartTime={setStartTime}
                    initialPopEquality={props.districtingData.populationEquality}
                />
                <AlgProgressModal
                    show={showAlgProgress}
                    onHide={() => setShowAlgProgress(false)}
                    setDistrictingData={props.setDistrictingData}
                    setPlanType={props.setPlanType}
                    planType={props.planType}
                    setIsAlgDone={props.setIsAlgDone}
                    isAlgDone={props.isAlgDone}
                    startTime={startTime}
                    initialPopEquality={props.districtingData.populationEquality}
                />
            </>
        </>
    );
}
