/*
 * UM001
 *
 * v 00.001 - 10/21/2024
 *
 *Description: Displays the User Management
 *
 * PIC: emonteverde
 */

import { FC, useCallback, useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import {
  Button,
  FormControl,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  TextField,
  FormLabel,
} from "@mui/material";
import { setUser, selectItems, deleteMultiItems } from "../redux/UserReducer";
import { RootState } from '../redux/store';
import PanelHeader from "../component/PanelHeader";
import "../style/UM001.css";
import apiConfig from '../common/apiAxiosConfig';
import { setItemId, setParams } from "../redux/ParamUtilReducer";
import DataGridTable from "../component/table/DataGridTable";
import { GridRenderCellParams, GridRowSelectionModel } from "@mui/x-data-grid";
import { Users } from "../types/DataType";
import { getMessage } from "../common/MessageUtil";
import { routes } from "../common/appConstant";

export const UserList: FC = () => {

  const dispatch = useDispatch();
  const navigate = useNavigate();

  const [headerFields, setHeaderFields] = useState({
    txtName: "",
    txtBusinessUnit: "",
    txtPosition: "",
    txtDepartment: "",
  });

  const [showDialog, setShowDialog] = useState(false);
  const [dialogMessage, setDialogMessage] = useState("");

	// const [screenTitle, setScreenTitle] = useState<string>("");
  const param01 = useSelector((state: RootState) => state.params.param01);
  const param02 = useSelector((state: RootState) => state.params.param02);

  const [paginationModel, setPaginationModel] = useState({
    page: 0,
    size: 10,
    totalItems: 0,
  });

  const [loading, setLoading] = useState(false);

  const userList = useSelector((state: RootState) => state.user.userList);

  const handleUserListWithId = useCallback((content: Users[], totalItems: number) => {
    const userListWithId = content.map((user: Users) => ({
      id: user.userId,
      ...user,
    }));
    dispatch(setUser({ userList: userListWithId, totalItems }));
  }, [dispatch]);

  const handleSetPaginationModel = useCallback((totalElements: number) => {
    setPaginationModel((prev) => ({
      ...prev,
      totalItems: totalElements,
    }));
  }, []);
  const [reset, setReset] = useState(false);

  useEffect(() => {
    const fetchUsers = async () => {
      setLoading(true);
      const pageRequest = JSON.stringify({
        page: paginationModel.page,
        size: paginationModel.size,
      });

      try {
        const response = await apiConfig.get('/userList', {
          params: {
            Param01: param01,
            Param02: param02,
            pageRequest,
          },
        });

        const data = response.data;
        handleUserListWithId(data, data.length);
        handleSetPaginationModel(data.length);

      } catch (error) {
        console.error('Error fetching users:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchUsers();
  }, [reset, paginationModel.page, paginationModel.size, param01, param02, handleUserListWithId, handleSetPaginationModel]);

//handle fetch screen title from db
	const [screenTitle, setScreenTitle] = useState<string>('');

useEffect(() => {
		const fetctFuncName = async () => {
			try {
				const response = await apiConfig.get('/functionName', {
					params: {
						Param01: param01,
            Param02: param02,
					},
				});
				const res = response.data;
				setScreenTitle(res);
			} catch (error: any) {
				setDialogMessage(getMessage('E_TSCL_01_0020'));
				setShowDialog(true);
			}
		};
		fetctFuncName();
	}, [param01, param02]);

  const selectedItems = useSelector((state: RootState) => state.user.selectedItems);

  // Handle selection of items in the DataGrid
  const handleSelectionModelChange = (newSelection: GridRowSelectionModel) => {
    dispatch(selectItems(newSelection as string[]));
    console.log("Selected items:", newSelection);
  };

  // Handle pagination change event
  const handlePaginationChange = (model: { page: number; pageSize: number }) => {
    setPaginationModel((prev) => ({
      ...prev,
      page: model.page,
      size: model.pageSize,
    }));
  };

  // Navigate back to the previous page ( initial )
  const btnBack = () => {
    navigate(routes.HM001);
  };

  // ADD EVENT ( Go to the User Registration Screen (NM002))
  const btnAddUser = () => {
    navigate(routes.UM002);
  };

  // Edit EVENT ( Go to the User Edit Screen (UM003))
  const btnEdit = (userId: number) => {
    dispatch(setParams({ param01, param02 }));
    dispatch(setItemId(userId));
    navigate(routes.RM001);
  };

  // Delete EVENT ( Go to the User Delete Screen (UM004))
  const btnDelete = (userId: number) => {
    dispatch(setParams({ param01, param02 }));
    dispatch(setItemId(userId));
    navigate(routes.UM004);
  };

  const btnSearch = async () => {
    if (!headerFields.txtName && !headerFields.txtPosition && !headerFields.txtBusinessUnit && !headerFields.txtDepartment) {
      setDialogMessage(getMessage("E_TSCL_01_0017"));
      setShowDialog(true);
      return;
    }

    const userData = {
      firstName: headerFields.txtName,
      lastName: headerFields.txtName,
      positionShName: headerFields.txtPosition,
      departmentShName: headerFields.txtDepartment,
      sectionShName: headerFields.txtBusinessUnit, // sectionShName should match the backend DTO
    };

    const pageRequest = JSON.stringify({
      page: paginationModel.page,
      size: paginationModel.size,
    });

    try {
      // POST request with search criteria in the body, not params
      const response = await apiConfig.post('/searchUM001', userData, {
        params: {
          Param01: param01,
          Param02: param02,
          pageRequest: pageRequest,
        },
      });

      const data = response.data;
      if (data.length !== 0) {
        handleUserListWithId(data, data.totalElements);
        handleSetPaginationModel(data.totalElements);
      } else {
        setDialogMessage(getMessage("E_TSCL_01_0014",["User"]));
        setShowDialog(true);
      }
    } catch (error) {
      console.error('Error during search:', error);
    }
  };

  const closeDialog = () => {
    setShowDialog(false);
  };

  const columnUserList = [
    {
      field: "accountId",
      headerName: "Username",
      width: 200,
      renderCell: (params: GridRenderCellParams) => (
        param02 === '1' ? (
          <Link to={"/UM003"}>
            {params.value}
          </Link>
        ) : (
          <span>{params.value}</span> // Render plain text for general users
        )
      )
    },
    { field: "firstName", headerName: "First Name", width: 200 },
    { field: "lastName", headerName: "Last Name", width: 200 },
    { field: "positionShName", headerName: "Position", width: 200 },
    { field: "mailaddress", headerName: "Email", width: 200 },
    { field: "sectionShName", headerName: "Business Unit", width: 200 },
    { field: "departmentShName", headerName: "Department", width: 200 },
    {
      field: "createDate",
      headerName: "Created",
      width: 200,
      valueFormatter: (params: Date) => {
        return new Intl.DateTimeFormat("en-US", {
          year: "numeric",
          month: "short",
          day: "2-digit",
          hour: "2-digit",
          minute: "2-digit",
          hour12: true,
        }).format(new Date(params));
      },
    },
  ];

  const setUserListHeader = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { id, value } = e.target;
    setHeaderFields(prevFields => ({
      ...prevFields,
      [id]: value,
    }));

    if (value === '') {
      setReset(prev => !prev);
    }
  };

   const btnDeleteMulti = async () => {
    
    setLoading(true);
    const pageRequest = JSON.stringify({
      page: paginationModel.page,
      size: paginationModel.size,
    });
    try {

      const selectedItemsString = selectedItems.join(',');

      const response = await apiConfig.post('/deleteMultiUM001', null, {
        params: {
          Param01: param01,
          Param02: param02,
          SelectedItems: selectedItemsString,
          pageRequest,
        },
      });

      
      // console.log("API Delete Fetch:", response.data);

      
        const data = response.data;
         if (data.length !== 0) {
        handleUserListWithId(data.um001List.content, data.um001List.totalElements);
        handleSetPaginationModel(data.um001List.totalElements);

        dispatch(deleteMultiItems());
        dispatch(selectItems([])); 
       
      }
    } catch (error) {
      console.error('Error during search:', error);
    
    } finally {
      setLoading(false);
    }
    
  };

  return (
    <div>
      <PanelHeader showBackButton={true} onBackClick={btnBack} title={screenTitle}/>
       
<div className="webportal_screen">
        {param02 === '1' && (
          <Button
            className="add_news_button"
            variant="contained"
            size="large"
            onClick={btnAddUser}
            sx={{ marginBottom: 2 }}
          >
            Add User
          </Button>
        )}

        <FormControl fullWidth component="form">
          <div className="form_container">
            <div>
              <FormLabel className="form_label">Name</FormLabel>
              <TextField
                id="txtName"
                variant="outlined"
                fullWidth
                size="small"
                value={headerFields.txtName}
                onChange={setUserListHeader}
              />
            </div>
            <div>
              <FormLabel className="form_label">Position</FormLabel>
              <TextField
                id="txtPosition"
                variant="outlined"
                fullWidth
                size="small"
                value={headerFields.txtPosition}
                onChange={setUserListHeader}
              />
            </div>

            <div>
              <FormLabel className="form_label">Business Unit</FormLabel>
              <TextField
                id="txtBusinessUnit"
                variant="outlined"
                fullWidth
                size="small"
                value={headerFields.txtBusinessUnit}
                onChange={setUserListHeader}
              />
            </div>
            <div>
              <FormLabel className="form_label">Department</FormLabel>
              <TextField
                id="txtDepartment"
                variant="outlined"
                fullWidth
                size="small"
                value={headerFields.txtDepartment}
                onChange={setUserListHeader}
              />
            </div>
            <div className="btn">
              <Button variant="contained" onClick={btnSearch}>
                Search
              </Button>
            </div>
          </div>
          <div className="form_container">
            <div className="btn">
               {param02 === '1' && (
                <Button
                  variant="contained"
                  onClick={btnDeleteMulti}
                  disabled={selectedItems.length === 0}
                >
                  Delete Selected Users
                </Button>
              )}
            </div>
          </div>
        </FormControl>

        {/* DataGrid Table */}
        <DataGridTable
          rows={(Array.isArray(userList) ? userList : []).map((users: Users) => ({
            ...users,
            id: users.userId, 
          }))}
          columns={columnUserList}
          paginationModel={{
            page: paginationModel.page,
            pageSize: paginationModel.size,
          }}
          onPaginationModelChange={handlePaginationChange}
          totalItems={paginationModel.totalItems}
          loading={loading}
          onSelectionModelChange={handleSelectionModelChange}
          onEdit={btnEdit}
          onDelete={btnDelete}
          selectedItems={selectedItems}
          param02={param02}
        />

        <Dialog
          open={showDialog}
          onClose={closeDialog}
          aria-labelledby="alert-dialog-title"
          aria-describedby="alert-dialog-description"
        >
        <DialogTitle>Alert</DialogTitle>
          <DialogContent>
            <DialogContentText id="alert-dialog-description">
              {dialogMessage}
            </DialogContentText>
          </DialogContent>
          <DialogActions>
            <Button onClick={closeDialog} autoFocus>
              OK
            </Button>
          </DialogActions>
        </Dialog>
      </div>
    </div>
  );
};
