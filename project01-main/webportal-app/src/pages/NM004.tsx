/*
 * NM004
 *
 * v 00.001 - 11/07/2024
 *
 * PIC: emonteverde
 * 
 * Description: Used to display a form to news delete.
 * 
 */

import React, { FC, useState, useEffect } from "react";
import { useSelector } from 'react-redux';
import { RootState } from '../redux/store';
import PanelHeader from "../component/PanelHeader";
import { useNavigate } from "react-router-dom";
import apiConfig from '../common/apiAxiosConfig';
import dayjs, { Dayjs } from "dayjs";
import { SubmitHandler, useForm, Controller } from "react-hook-form";
import { News } from "../types/DataType";
import { DATE_FORMATS, labelText, richTextModules, routes } from "../common/appConstant";
import { Box, Button, FormControl, FormLabel, Paper, Stack, TextField, Typography } from "@mui/material";
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css'; 
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { getMessage } from "../common/MessageUtil";
import DialogComponent from "../component/DialogBox";

export const NewsDelete: FC = () => {

  const loggedInUsername = useSelector((state: RootState) => state.params.param01);
  const loggedInUserRole = useSelector((state: RootState) => state.params.param02);
  const newsId = useSelector((state: RootState) => state.params.itemId);
  const navigate = useNavigate();

  const [screenTitle, setScreenTitle] = useState<string>('');
  const [selectedStartDate, setSelectedStartDate] = useState<Dayjs | null>(null);
  const [selectedEndDate, setSelectedEndDate] = useState<Dayjs | null>(null); 

  const [imagePreview, setImagePreview] = useState<string | null>(null); 

  const [isLoading, setIsLoading] = useState<Boolean | null>(null);
  const [showDeleteDialog, setShowDeleteDialog] = useState(false);
	const [showCancelDialog, setShowCancelDialog] = useState(false);
  const [dialogMessage, setDialogMessage] = useState('');
  const [showDialog, setShowDialog] = useState(false);

  const { getValues, reset, control, handleSubmit, register, setValue } = useForm<News>();

    //ONLOAD Function
    useEffect(() => {
      const fetchNews = async () => {
        try{
          const response = await apiConfig.post('/initNM004', null, {
            params: {
              loggedInUsername: loggedInUsername,
              loggedInUserRole: loggedInUserRole,
              NewsId: newsId,
            },
          });
          console.log("API Fetch:", response.data);
  
          if(response.status === 200){
  
          const res = response.data;
  
          setScreenTitle(res.screenTitle);
  
          const startDate = res.startDate ? dayjs(res.startDate) : null;
          const endDate = res.endDate ? dayjs(res.endDate) : null;
  
          setSelectedStartDate(startDate);
          setSelectedEndDate(endDate);
          setImagePreview(res.imgPath);
         
          setValue("newsId", res.newsId);
          setValue("title", res.title);
          setValue("createId", res.createId);
          setValue("content", res.content);
          setValue("imgPath", res.imgPath);
          setValue("startDate", startDate ? startDate.format(DATE_FORMATS.defaultFormat) : DATE_FORMATS.emptyFormat);
          setValue("endDate", endDate ? endDate.format(DATE_FORMATS.defaultFormat) : DATE_FORMATS.emptyFormat);
  
          }
          
  
        } catch(error: any){
          if(error.response){
            const statusCode = error.response.status;
           
            console.error(
              `Error ${statusCode}: ${error.response.data?.message 
                || 'Internal Server Error'}`);
  
            if (statusCode === 401) {
              console.error("401 ERROR: Unauthorized!");
              setIsLoading(false);
            }
      
            if (statusCode === 500) {
              console.error("500 ERROR: Internal Server Error occurred while loading NM003");
              setIsLoading(false);
            }
  
          }
        }
      };
      fetchNews();
  },[])

  const handleBackClick = () => {
		reset();
		navigate(routes.NM001);
	};

  const handleDelete: SubmitHandler<News> = async (data) => {

    console.log("Deleted: ", data);
    setIsLoading(true);

    const formData = new FormData();
    formData.append('loggedInUsername', loggedInUsername);
		formData.append('loggedInUserRole', loggedInUserRole);
    formData.append('NewsId', data.newsId.toString());

    // const formDataObject = Object.fromEntries(formData.entries());
    // console.log(formDataObject);

    try {
      const response = await apiConfig.post('/deleteNM004', formData, {
          headers: {
              'Content-Type': 'multipart/form-data',
          },
      });

      // console.log('Response from server:', response.data);

      if(response.status === 200){
        setDialogMessage(getMessage('I_TSCL_01_0037'));
        setShowDialog(true);

      }

    } catch (error: any) {
      const statusCode = error.response.status;

      console.error(`Error ${statusCode}: ${error.response.data?.message || 'Internal Server Error'}`);

      if (statusCode === 401) {
        console.error("401 ERROR: Unauthorized!");
        setIsLoading(false);
      }

      if (statusCode === 500) {
        console.error("500 ERROR: Internal Server Error occurred while loading NM003");
        setIsLoading(false);
      }
    }
  }


  return (
    <div>
      <PanelHeader showBackButton={true} 
      onBackClick={handleBackClick} title={screenTitle} />
      <div className="webportal_screen">
        <FormControl fullWidth component="form" 
            onSubmit={handleSubmit(handleDelete)}>
              
          <div className="news-edit_container">
            <div className="news-edit_column">
              <FormLabel className="news-edit_label">{labelText.newsId}</FormLabel>
              <TextField 
              id="txtNewsId" 
              variant="outlined"
              disabled={true} 
              sx={{backgroundColor: 'lightgray'}}
              size="small"
              {...register('newsId')} 
               />
              
            </div>

            <div className="news-edit_column">
              <FormLabel className="news-edit_label">{labelText.createdBy}</FormLabel>
              <TextField 
              id="txtCreatedBy" 
              variant="outlined" 
              size="small" 
              disabled={true}
              sx={{backgroundColor: 'lightgray'}}
              {...register('createId')} 
              />
            </div>

            <div>
              <FormLabel className="news-edit_label">{labelText.display}</FormLabel>
            </div>

            <div className="news-edit_column">
            <FormLabel className="news-edit_label">{labelText.startDate}</FormLabel>
                <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <Controller
                        name="startDate"
                        control={control}
                        render={({ field }) => (
                            <DatePicker
                                disabled
                                {...field}
                                value={selectedStartDate}
                                onChange={(newSelectedStartDate: Dayjs | null) => {
                                  setSelectedStartDate(newSelectedStartDate);
                                  setValue("startDate", newSelectedStartDate ? newSelectedStartDate.format('MM-DD-YYYY') : '');
                              }}
                                sx={{flexGrow: 1, backgroundColor: 'lightgray'}}
                            />
                        )}
                    />
                </LocalizationProvider>
            </div>

            <div className="news-edit_column">
            <FormLabel className="news-edit_label">{labelText.endDate}</FormLabel>
                <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <Controller
                        name="endDate"
                        control={control}
                        render={({ field }) => (
                            <DatePicker
                                disabled
                                {...field}
                                value={selectedEndDate}
                                onChange={(newSelectedEndDate: Dayjs | null) => {
                                    setSelectedEndDate(newSelectedEndDate);
                                    setValue("endDate", newSelectedEndDate ? newSelectedEndDate.format('MM-DD-YYYY') : '');
                                }}
                                sx={{flexGrow: 1, backgroundColor: 'lightgray'}}
                            />
                        )}
                    />
                </LocalizationProvider>
            </div>
          </div>

          <div className="news-edit_container">
            <div className="news-edit_column">
              <div className="news-edit_row">
                <FormLabel className="news-edit_label">{labelText.title}</FormLabel>
                <TextField 
                id="txtTitle" 
                variant="outlined" 
                size="small" fullWidth 
                {...register('title')}
                disabled={true}
                sx={{backgroundColor: 'lightgray'}}
                />
                
              </div>

              <div className="news-edit_row">
                <Typography>{labelText.contents}</Typography>
                <Paper sx={{ padding: '10px', minHeight: '320px', backgroundColor: 'lightgray' }}>
                  <Controller
                      name="content"
                      control={control}
                      defaultValue=""
                      
                      render={({ field }) => (
                          <ReactQuill
                              theme="snow"
                              value={field.value}
                              readOnly={true}  
                              modules={richTextModules}
                              onChange={(value) => {
                                  field.onChange(value);
                                  setValue("content", value);
                              }}
                          />
                      )}
                  />
                </Paper>
              </div>

            </div>

            <div className="news-edit_column_images">
              
              <Paper sx={{ padding: '10px', minHeight: '350px' }}>

                {/* Attachments Section */}
                <div className="news-edit-attachment">
                  <Typography sx={{ color: 'black', marginBottom: '8px' }}>
                    {labelText.attachment}
                  </Typography>
                </div>
                <div className="news-edit_images_attachment">
                  <Box
                    className="upload-box"
                    sx={{
                      border: '1px dashed black',
                      padding: '20px',
                      display: 'flex',
                      flexDirection: 'column',
                      alignItems: 'center',
                      justifyContent: 'center',
                      height: '200px',
                      width: '400px',
                      margin: 'auto',
                      borderRadius: '8px',
                      backgroundColor: 'lightgray'
                    }}
                  >
                    {imagePreview ? (
                      <img
                        src={imagePreview}
                        alt="Fetched Image"
                        style={{
                          width: '100%',
                          height: '100%',
                          objectFit: 'contain',
                          marginTop: '8px',
                          
                        }}
                      />
                    ) : (
                      <Typography variant="body2" sx={{ marginTop: '16px', color: 'black' }}>
                        {labelText.noImageAvailable}
                      </Typography>
                    )}
                  </Box>
                </div>

              </Paper>
              <div>
                <Stack direction={'row'} justifyContent={'right'} spacing={3} marginTop={2}>
                  <Button variant="contained" 
                  onClick={() => setShowDeleteDialog(true)}>
                    {labelText.delete}
                  </Button>
                  <Button variant="outlined" onClick={() => setShowCancelDialog(true)}>{labelText.cancel}</Button>
                </Stack>
              </div>
            </div>
          </div>
        </FormControl>
      </div>

      {/* Confirm Modal For Update Button*/}
      <DialogComponent
        show={showDeleteDialog}
        dialogMessage={getMessage('E_TSCL_01_0012', [String(getValues('newsId'))])}
        handleClose={() => setShowDeleteDialog(false)}
        confirmation={true}
        handleYes={() => {
          handleSubmit(handleDelete)();
          setShowDeleteDialog(false);
          }}
        handleNo={() => setShowDeleteDialog(false)}
      />

      {/* Message Modal */}
			<DialogComponent
				show={showDialog}
				dialogMessage={dialogMessage}
				handleClose={() => {
					setShowDialog(false);
					navigate(routes.NM001);
				}}
			/>
			{isLoading && (
				<div className="loading_page">
					{/* 3 dots animation */}
					<div className="loading_animation"></div>
				</div>
			)}
    </div>
  );
}