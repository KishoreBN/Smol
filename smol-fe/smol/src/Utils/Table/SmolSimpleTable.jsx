import "./SmolSimpleTable.scss";
import { DataGrid } from "@mui/x-data-grid";

const paginationModel = { page: 0, pageSize: 5 };

const SmolSimpleTable = (props) => {
  const { rows, columns, rowSelection = false, ...rest } = props;
  return (
    <DataGrid
      rows={rows}
      columns={columns}
      initialState={{ pagination: { paginationModel } }}
      pageSizeOptions={[5, 10]}
      checkboxSelection={rowSelection}
      disableRowSelectionOnClick={!rowSelection}
      sx={{
        color: "white",
        backgroundColor: "black",
        border: "2px solid #343232",
        "& .MuiDataGrid-columnHeader": {
          backgroundColor: "black !important",
          borderBottom: "none !important"
        },
        "& .MuiDataGrid-filler": {
          backgroundColor: "black !important",
        },
        "& .MuiDataGrid-iconButtonContainer": {
          backgroundColor: "black !important",
        },
        "& .MuiDataGrid-menuIconButton": {
          backgroundColor: "black !important",
          color: "white !important",
        },
        "& .MuiDataGrid-sortIcon": {
          color: "#ffffff",
          opacity: 1,
          visibility: "visible",
        },
        "& .MuiDataGrid-row:hover": {
          backgroundColor: "transparent !important",
        },
        "& .MuiDataGrid-footerContainer": {
          color: "#ffffff",
          borderTop: "2px solid #343232",
          "& .MuiTablePagination-root": {
            color: "#ffffff",
          },
          "& .MuiTablePagination-selectIcon": {
            color: "#ffffff",
          },
          "& .MuiSelect-icon": {
            color: "#ffffff",
          },
        },
        "& .MuiDataGrid-columnHeaders": {
          borderBottom: "2px solid #343232",
        },
        "& .MuiDataGrid-columnSeparator": {
          color: "#343232",
        },
        "& .MuiDataGrid-row" : {
            "--rowBorderColor" : "none"
        }
      }}
    />
  );
};

//To-Do
const defaultProps = {
    color : "white",
    backgroundColor : "black",
    border: "2px solid #343232",
};

export default SmolSimpleTable;
