/*
 * DataGridTable
 *
 * v 00.001 - 10/25/2024
 *
 * PIC: emonteverde
 * 
 * 
 */

import React from "react";
import { DataGrid, GridRenderCellParams, GridRowSelectionModel  } from "@mui/x-data-grid";
import { IconButton, Paper, CircularProgress } from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import '../../style/main.css';

interface DataGridTableProps {
  rows: any[];
  columns: any[];
  paginationModel: { page: number; pageSize: number };
  onPaginationModelChange: (model: { page: number; pageSize: number }) => void;
  totalItems: number;
  loading?: boolean;
  onEdit: (id: number) => void;
  onDelete: (id: number) => void;
  selectedItems: GridRowSelectionModel; 
  onSelectionModelChange: (selection: number[]) => void; 
  param02: string;
}

const DataGridTable: React.FC<DataGridTableProps> = ({
  rows,
  columns,
  paginationModel,
  onPaginationModelChange,
  totalItems,
  loading = false,
  onEdit,
  onDelete,
  selectedItems, 
  onSelectionModelChange, 
  param02,
}) => {
  const actionColumn = {
    field: "action",
    headerName: "Action",
    width: 200,
    align: "center", 
    headerAlign: "center",
    renderCell: (params: GridRenderCellParams) => (
      <>
        <IconButton
          color="secondary"
          size="small"
          style={{ marginLeft: 8 }}
          onClick={() => onDelete(params.row.id)}
        >
          <DeleteIcon />
        </IconButton>
        <IconButton
          color="primary"
          size="small"
          onClick={() => onEdit(params.row.id)}
        >
          <EditIcon />
        </IconButton>
      </>
    ),
  };

  const columnsWithActions = param02 === '1' ? [...columns, actionColumn] : columns;

  return (
    <div className="data_grid_container">
      <Paper className="data_grid_wrapper">
        {loading ? (
          <div
            style={{
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
              height: "100%",
            }}
          >
            <CircularProgress />
          </div>
        ) : (
          <DataGrid
            rows={rows}
            columns={columnsWithActions}
            pagination
            paginationModel={paginationModel}
            onPaginationModelChange={onPaginationModelChange}
            pageSizeOptions={[10]}
            rowCount={totalItems}
            checkboxSelection
            paginationMode="server"
            loading={loading}
            sx={{ border: 0 }}
            rowSelectionModel={selectedItems} 
            onRowSelectionModelChange={(newSelection) => {
              onSelectionModelChange(newSelection as number[]);
            }}
          />
        )}
      </Paper>
    </div>
  );
};

export default DataGridTable;
