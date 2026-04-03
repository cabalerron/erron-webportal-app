/*
 * NM001
 *
 * v 00.001 - 10/25/2024
 *
 * PIC: emonteverde
 * 
 * Description: Used to display a form to news list.
 * 
 */


import { FC, useCallback, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
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
import { News, PageRequest } from "../types/DataType";
import { setNews, selectItems, deleteMultiItems } from "../redux/NewsReducer";
import { RootState } from '../redux/store';
import PanelHeader from "../component/PanelHeader";
import '../style/main.css';
import apiConfig from '../common/apiAxiosConfig';
import { setItemId, setParams } from "../redux/ParamUtilReducer";
import DataGridTable from "../component/table/DataGridTable";
import { GridRowSelectionModel  } from "@mui/x-data-grid";
import { getMessage } from "../common/MessageUtil";
import { routes, ColumnHeaders } from '../common/appConstant';
import { paginationDefaults } from '../common/appConstant';
import DialogBox from "../component/DialogBox";
import DialogComponent from "../component/DialogBox";

export const NewsList: FC = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const [headerFields, setHeaderFields] = useState({
    txtNewsId: "",
    txtTitle: "",
    txtCreatedBy: "",
  });

  // Set dialog message
  const [showDialog, setShowDialog] = useState(false);                    
  const [dialogMessage, setDialogMessage] = useState("");                 

  const param01 = useSelector((state: RootState) => state.params.param01);
  const param02 = useSelector((state: RootState) => state.params.param02);
  const [screenTitle, setScreenTitle] = useState<string>('');

  // Handle pagination state
  const [paginationModel, setPaginationModel] = useState<PageRequest>({
    ...paginationDefaults,
  })

  // Control loading state
  const [loading, setLoading] = useState(false);

  // Get news list from redux store
  const newsList = useSelector((state: RootState) => state.news.newsList);

  // Map news content to include the newsId as 'id' for DataGrid table rows
  const handleNewsListWithId = useCallback((content: News[], totalItems: number) => {
    const newsListWithId = content.map((news: News) => ({
      id: news.newsId,
      ...news,
    }));
    dispatch(setNews({ newsList: newsListWithId, totalItems }));
  }, [dispatch]);

  // Set pagination model state with the total number of items
  const handleSetPaginationModel = useCallback((totalElements: number) => {
    setPaginationModel((prev) => ({
      ...prev,
      totalItems: totalElements,
    }));
  }, []);

  const [reset, setReset] = useState(false);

  //ONLOAD Event - Fetch data when component change
  useEffect(() => {
    const fetchNews = async () => {
      setLoading(true);
      const pageRequest = JSON.stringify({
        page: paginationModel.page,
        size: paginationModel.size,
      });
  
      try {
        const response = await apiConfig.post('/initNM001', null, {
          params: {
            loggedInUsername: param01,
            loggedInUserRole: param02,
            pageRequest,
          },
        });
        
        console.log("API Fetch:", response.data);

        if(response.status === 200){
          const data = response.data;
          setScreenTitle(data.screenTitle);
          handleNewsListWithId(data.nm001List.content, data.nm001List.totalElements);
          handleSetPaginationModel(data.nm001List.totalElements);
        }

      } catch (error: any) {
        if(error.response){
          const statusCode = error.response.status;
         
          console.error(`Error ${statusCode}: ${error.response.data?.message || 'Internal Server Error'}`);

          if (statusCode === 500) {
            console.error("500 ERROR: Internal Server Error occurred while loading NM001");
          }
        }
      } finally {
        setLoading(false);
      }
    };
    fetchNews();
  }, [reset, 
    dispatch, 
    paginationModel.page, 
    paginationModel.size, 
    param01, 
    param02, 
    handleNewsListWithId, 
    handleSetPaginationModel]);

  // Get selected items from the store
  const selectedItems = useSelector((state: RootState) => state.news.selectedItems);

  // Handle selection of items in the DataGrid
  const handleSelectionModelChange = (newSelection: GridRowSelectionModel) => {
    dispatch(selectItems(newSelection as number[]));
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

  // ADD EVENT ( Go to the News Registration Screen (NM002))
  const btnAddNews = () => {
    navigate(routes.NM002);
  };

  // Edit EVENT ( Go to the News Edit Screen (NM003))
  const btnEdit = (newsId: number) => {
    dispatch(setParams({ param01, param02 }));
    dispatch(setItemId(newsId));
    navigate(routes.NM003);
  };

  // Delete EVENT ( Go to the News Delete Screen (NM004))
  const btnDelete = (newsId: number) => {
    dispatch(setParams({ param01, param02 }));
    dispatch(setItemId(newsId));
    navigate(routes.NM004);
  };

  // Search EVENT - Handle search functionality based on input fields
  const btnSearch = async () => {
    if (!headerFields.txtNewsId && !headerFields.txtTitle && !headerFields.txtCreatedBy) {
      setDialogMessage(getMessage('E_TSCL_01_0017'));
      setShowDialog(true);
      return;
    }

    const formData = {
      newsid: headerFields.txtNewsId,
      title: headerFields.txtTitle,
      createdby: headerFields.txtCreatedBy,
    };

    const pageRequest = JSON.stringify({
      page: paginationModel.page,
      size: paginationModel.size,
    });

    try {
      const response = await apiConfig.post('/searchNM001', {}, {
        params: {
          Param01: param01,
          Param02: param02,
          newsid: formData.newsid,
          title: formData.title,
          createdby: formData.createdby,
          pageRequest: pageRequest,
        },
      });

      
      console.log("API Search:", response.data);

      if(response.status === 200){
        const data = response.data;
        if (data.nm001List.content.length !== 0) {
          handleNewsListWithId(data.nm001List.content, data.nm001List.totalElements);
          handleSetPaginationModel(data.nm001List.totalElements);
        } else {
          setDialogMessage("No relevant data exist");
          setShowDialog(true);
        }
      }
    } catch (error: any) {
      const statusCode = error.response.status;

      console.error(`Error ${statusCode}: ${error.response.data?.message || 'Internal Server Error'}`);

      if (statusCode === 401) {
        console.error("401 ERROR: Unauthorized!");
      }

    }
  };

  // Close the alert dialog
  const closeDialog = () => {
    setShowDialog(false);
  };

  const handleClose = () => {
    setShow(false);
  };

  const handleNo = () => {
    setShow(false);
  };

  // Column configuration for DataGrid table
  const columnNewsList = [
    { field: "newsId", headerName: ColumnHeaders.newsId, width: 150, align: "center", headerAlign: "center"},
    { field: "title", headerName: ColumnHeaders.title, width: 200, align: "center", headerAlign: "center"},
    { field: "createId", headerName: ColumnHeaders.createId, width: 200, align: "center", headerAlign: "center" },
    { field: "createDate", headerName: ColumnHeaders.createDate, width: 200, align: "center", headerAlign: "center" },
    { field: "updateDate", headerName: ColumnHeaders.updateDate, width: 200, align: "center", headerAlign: "center" },
  ];

  
  const setNewsListHeader = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { id, value } = e.target;
  
    // Update the header fields state
    setHeaderFields(prevFields => ({
      ...prevFields,
      [id]: value,
    }));
  
    // If the input field is cleared, reset to the original news list
    if (value === '') {
      setReset(prev => !prev);
    }
  };
  

  // Delete Multi EVENT - Handle deletion of multiple selected items
  const btnDeleteMulti = async () => {
    
    setLoading(true);
    const pageRequest = JSON.stringify({
      page: paginationModel.page,
      size: paginationModel.size,
    });
    try {

      const selectedItemsString = selectedItems.join(',');

      const response = await apiConfig.post('/deleteMultiNM001', null, {
        params: {
          Param01: param01,
          Param02: param02,
          SelectedItems: selectedItemsString,
          pageRequest,
        },
      });

      
      console.log("API Delete Fetch:", response.data);

      if(response.status === 200){
        const data = response.data;
        handleNewsListWithId(data.nm001List.content, data.nm001List.totalElements);
        handleSetPaginationModel(data.nm001List.totalElements);
        dispatch(deleteMultiItems());
        // console.log("Deleting items:", selectedItems);
        setShow(false);


      }
    } catch (error: any) {
      const statusCode = error.response.status;
      console.error(`Error ${statusCode}: ${error.response.data?.message || 'Internal Server Error'}`);

      if (statusCode === 401) {
        console.error("401 ERROR: Unauthorized!");
      }

    } finally {
      setLoading(false);
    }
    
  };

  const [show, setShow] = useState(false);
  const [confirmationMessage, setConfirmationMessage] = useState(false);
  
  //Delete Multiple Button Start
  const handleDeleteClick = async () => {
    setDialogMessage(getMessage("E_TSCL_01_0012", [selectedItems.join(', ')]));
    setConfirmationMessage(true); 
    setShow(true); 
  };


  return (
    <div>
      <PanelHeader
        showBackButton={true}
        onBackClick={btnBack}
        title={screenTitle}
      />
      <div className="webportal_screen">
      {param02 === '1' && (
        <Button
          className="add_news_button"
          variant="contained"
          size="large"
          onClick={btnAddNews}
          sx={{ marginBottom: 2 }}
        >
          Add News
        </Button>
      )}

        <FormControl fullWidth component="form">
          <div className="form_container">
            <div>
              <FormLabel className="form_label">News ID</FormLabel>
              <TextField
                id="txtNewsId"
                variant="outlined"
                fullWidth
                size="small"
                value={headerFields.txtNewsId}
                onChange={setNewsListHeader}
              />
            </div>
            <div>
              <FormLabel className="form_label">Title</FormLabel>
              <TextField
                id="txtTitle"
                variant="outlined"
                fullWidth
                size="small"
                value={headerFields.txtTitle}
                onChange={setNewsListHeader}
              />
            </div>

            <div>
              <FormLabel className="form_label">Created By</FormLabel>
              <TextField
                id="txtCreatedBy"
                variant="outlined"
                fullWidth
                size="small"
                value={headerFields.txtCreatedBy}
                onChange={setNewsListHeader}
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
                  <Button variant="contained" 
                  onClick={handleDeleteClick}
                  disabled={selectedItems.length === 0}
                  >
                    Deletion
                  </Button>
              )}
              </div>
          </div>
        </FormControl>
      </div>
      <DataGridTable
          rows={(Array.isArray(newsList) ? newsList : []).map((news: News) => ({
            ...news,
            id: news.newsId, // Ensure each row has a unique id
          }))}
        columns={columnNewsList}
        paginationModel={{
          page: paginationModel.page,
          pageSize: paginationModel.size,
        }}
        onPaginationModelChange={handlePaginationChange}
        totalItems={paginationModel.totalItems}
        onEdit={btnEdit}
        onDelete={btnDelete}
        loading={loading}
        selectedItems={selectedItems} 
        onSelectionModelChange={handleSelectionModelChange} 
        param02={param02}
      />

      <DialogComponent
				show={showDialog}
				dialogMessage={dialogMessage}
				handleClose={() => 
					closeDialog()
				}
			/>
      <DialogBox
        show={show}
        dialogMessage={dialogMessage}
        handleClose={handleClose}
        handleYes={btnDeleteMulti}
        handleNo={handleNo}
        confirmation={confirmationMessage}
      />
    </div>
  );
};
