/*
 * RM001
 *
 * v 00.001 - 10/28/2024
 *
 * PIC: emonteverde
 * 
 */
import { FC, useCallback, useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import apiConfig from '../common/apiAxiosConfig'
import {
  Button,
  FormControl,
  TextField,
  FormLabel
} from "@mui/material";
import { GridRowSelectionModel  } from "@mui/x-data-grid";
import { getMessage } from "../common/MessageUtil";
import { Role, PageRequest } from "../types/DataType"; 
import { RootState } from '../redux/store';
import { setRole, selectItems, deleteMultiItems } from "../redux/RoleReducer";
import { setItemId, setParams } from "../redux/ParamUtilReducer";
import PanelHeader from '../component/PanelHeader';
import DialogBox from "../component/DialogBox";
import DataGridTable from "../component/table/DataGridTable";
import { routes, ColumnHeaders } from '../common/appConstant';
import '../style/RM001.css';

export const RoleList: FC = () => {
  const dispatch = useDispatch();
  const [headerFields, setHeaderFields] = useState({
    txtRoleName: "",
  });

  const loggedInUsername = useSelector((state: RootState) => state.params.param01);
  const loggedInUserRole = useSelector((state: RootState) => state.params.param02);

  // Control loading state
  const [loading, setLoading] = useState(false);

  // Get news list from redux store
  const roleList = useSelector((state: RootState) => state.role.roleList);

  // Get selected items from the store
  const selectedItems = useSelector((state: RootState) => state.role.selectedItems);

  // Handle pagination state
  const [paginationModel, setPaginationModel] = useState<PageRequest>({
    page: 0,
    size: 10,
    totalItems: 0, 
  });

  //Search Button Start
  const handleSearchClick = async () => { 
    if (!headerFields.txtRoleName ) {
      setDialogMessage(getMessage('E_TSCL_01_0017'));
      setShow(true);
      return;
    }

    const formData = {
      roleName: headerFields.txtRoleName
    };

    const pageRequest = JSON.stringify({
      page: paginationModel.page,
      size: paginationModel.size,
    });

    try {
      const response = await apiConfig.post('/role/search', {}, {
        params: {
          roleName: formData.roleName,
          pageRequest: pageRequest,
        },
      });

      const data = response.data;

      if (data.rm001List.content.length !== 0) {
        handleRoleListWithId(data.rm001List.content, data.rm001List.totalElements);
        handleSetPaginationModel(data.rm001List.totalElements);

      } else {
        setDialogMessage(getMessage("E_TSCL_01_0014",  [formData.roleName]));
        setShow(true);

      }
    } catch (error) {
      console.error('Error during search:', error);
    }
  };
  //Search Button End

  // Handle pagination change event
  const handlePaginationChange = (model: { page: number; pageSize: number }) => {
    setPaginationModel((prev) => ({
      ...prev,
      page: model.page,
      size: model.pageSize,
    }));
  };

  // Handle selection of items in the DataGrid
  const handleSelectionModelChange = (roleSelection: GridRowSelectionModel) => {
    dispatch(selectItems(roleSelection as number[]));
  };

  // Map role content to include the roleId as 'id' for DataGrid table rows
  const handleRoleListWithId = useCallback((content: Role[], totalItems: number) => {
    const roleListWithId = content.map((role: Role) => ({
      id: role.roleId,
      ...role,
    }));
    dispatch(setRole({ roleList: roleListWithId, totalItems }));
  }, [dispatch]);

  // Set pagination model state with the total number of items
  const handleSetPaginationModel = useCallback((totalElements: number) => {
    setPaginationModel((prev) => ({
      ...prev,
      totalItems: totalElements,
    }));
  }, []);

  //ONLOAD Event - Fetch data when component change
  const fetchRole = useCallback(async () => {
    setLoading(true);
    const pageRequest = JSON.stringify({
      page: paginationModel.page,
      size: paginationModel.size,
    });
  
    try {
      const response = await apiConfig.post('/role/list', null, {
        params: {
          loggedInUsername: loggedInUsername,
          loggedInUserRole: loggedInUserRole,
          pageRequest,
        },
      });

      const data = response.data;

      handleRoleListWithId(data.rm001List.content, data.rm001List.totalElements);
      handleSetPaginationModel(data.rm001List.totalElements);

    } catch (error) {
      console.error('Error fetching role:', error);
    } finally {
      setLoading(false);
    }
  }, [paginationModel.page, paginationModel.size, loggedInUsername, loggedInUserRole, handleRoleListWithId, handleSetPaginationModel]);

  useEffect(() => {
    fetchRole();
  }, [fetchRole, dispatch]);


  //Clear Button Start
  const handleClearClick = () => {
    setHeaderFields({ txtRoleName: "" });
    fetchRole();
  };
  //Clear Button End

  //Edit Button Start
  const handleEditClick = (roleId: number) => {
    dispatch(setParams({ param01: loggedInUsername, param02: loggedInUserRole }));
    dispatch(setItemId(roleId));
    navigate(routes.RM003);
  };
  //Edit Button End

  //Delete Item Button Start
  const handleDeleteItemClick = (roleId: number) => {
    dispatch(setParams({ param01: loggedInUsername, param02: loggedInUserRole }));
    dispatch(setItemId(roleId));
    navigate(routes.RM004);
  };
  //Delete Item  Button End

  //Dialog Message Start
  const [show, setShow] = useState(false);
  const [dialogMessage, setDialogMessage] = useState("");
  const [confirmationMessage, setConfirmationMessage] = useState(false);
  const handleClose = () => {
    setShow(false);
  };

  //Dialog Message End

  const navigate = useNavigate();
  const handleNavigate = (path: string, params = {}) => {
    navigate(path, { state: { ...params } });
  };

  //Back Button Start
  const handleBackClick = () => {
    navigate(-1); 
  };
  //Back Button End

  //Column List
  const columnRoleList = [
    { field: "roleId", headerName: ColumnHeaders.roleId, width: 200 },
    { field: "roleName", headerName: ColumnHeaders.roleName, width: 200 },
    { field: "roleShName", headerName: ColumnHeaders.roleShName, width: 200 },
    { field: "createId", headerName: ColumnHeaders.createId, width: 200 },
    {
        field: "createDate",
        headerName: ColumnHeaders.createDate,
        width: 200,
    },
    { field: "updateId", headerName: ColumnHeaders.updateId, width: 200 },
    {
        field: "updateDate",
        headerName: ColumnHeaders.updateDate,
        width: 200,
        valueFormatter: (params: { value: string | null } | null) => {
            if (!params || !params.value) return ''; // Handle null or empty values
            const date = new Date(params.value);
            return isNaN(date.getTime()) ? '' : new Intl.DateTimeFormat('en-US', {
              year: 'numeric',
              month: 'long',
              day: 'numeric',
            }).format(date);
          },
    },
    ];

  //Delete Multiple Button Start
  const handleDeleteClick = async () => {
    setDialogMessage(getMessage("E_TSCL_01_0012", [selectedItems.join(', ')]));
    setConfirmationMessage(true); 
    setShow(true); 
  };
  //Delete Multiple Button End

  //Delete Multiple Yes Button Start
  const handleYes = async () => {
    setLoading(true);

    const pageRequest = JSON.stringify({
      page: paginationModel.page,
      size: paginationModel.size,
    });

    try {
      const selectedItemsString = selectedItems.join(',');

      const response = await apiConfig.post('/role/deleteMulti', null, {
        params: {
          SelectedItems: selectedItemsString,
          pageRequest,
        },
      });

      const data = response.data;

      handleRoleListWithId(data.rm001List.content, data.rm001List.totalElements);
      handleSetPaginationModel(data.rm001List.totalElements);

      dispatch(deleteMultiItems());
      setShow(false);
    } catch (error) {
      console.error('Error during multiple deletion:', error);
    } finally {
      setLoading(false);
    }
  };
  //Delete Multiple Yes Button End

  //Delete Multiple No Button Start
  const handleNo = () => {
    setShow(false);
  };
  //Delete Multiple No Button End

  return (
    <div>
      <PanelHeader 
        showBackButton={true} 
        onBackClick={handleBackClick} 
        title="Role List"
      />
      <div className="webportal_screen">
        <div>
          <div className="add_button">
                <Button variant="contained" onClick={() => handleNavigate(routes.RM002)}>
                  Add Role
                </Button>
              </div>
          <FormControl fullWidth component="form">
            <div className="form_container">
              {/* Search Fields */}
              <div>
                <FormLabel className="form_label">Role Name</FormLabel>
                <TextField
                  id="txtRoleId"
                  variant="outlined"
                  fullWidth
                  size="small"
                  value={headerFields.txtRoleName}
                  onChange={(e) =>
                    setHeaderFields({
                      ...headerFields,
                      txtRoleName: e.target.value,
                    })
                  }
                />
              </div>
              <div className="search_button">
                <Button variant="contained" onClick={handleSearchClick}>
                  Search
                </Button>
              </div>
              <div className="search_button">
                <Button variant="contained" onClick={handleClearClick}>
                  Clear
                </Button>
              </div>
            </div>
            <div>
              <div className="del_button">
                <Button variant="contained" 
                onClick={handleDeleteClick}
                disabled={selectedItems.length === 0}
                >
                  Deletion
                </Button>
              </div>
            </div>
          </FormControl>
          <DataGridTable 
            rows={(Array.isArray(roleList) ? roleList : []).map((role: Role) => ({
              ...role,
              id: role.roleId,
            }))} 
            columns={columnRoleList}
            paginationModel={{
              page: paginationModel.page,
              pageSize: paginationModel.size,
            }}
            onPaginationModelChange={handlePaginationChange}
            totalItems={paginationModel.totalItems}
            onEdit={handleEditClick}
            onDelete={handleDeleteItemClick}
            loading={loading}
            selectedItems={selectedItems} 
            onSelectionModelChange={handleSelectionModelChange}  
            param02={loggedInUserRole}
          />
        </div>
      </div>
      <DialogBox
        show={show}
        dialogMessage={dialogMessage}
        handleClose={handleClose}
        handleYes={handleYes}
        handleNo={handleNo}
        confirmation={confirmationMessage}
      />
    </div>
  );
}
