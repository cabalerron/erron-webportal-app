/*
 * NM003
 *
 * v 00.001 - 11/06/2024
 *
 * PIC: emonteverde
 * 
 * Description: Used to display a form to news edit.
 * 
 */

import React, { FC, useState, useRef, useEffect } from "react";
import { useSelector } from 'react-redux';
import { RootState } from '../redux/store';
import PanelHeader from "../component/PanelHeader";
import { useNavigate } from "react-router-dom";
import { DATE_FORMATS, labelText, richTextModules, routes } from '../common/appConstant';
import { Box, Button, FormControl, FormLabel, Paper, Stack, TextField, Typography } from "@mui/material";
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import '../style/main.css';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css'; 
import ImageIcon from '@mui/icons-material/Image';
import CloudUploadIcon from '@mui/icons-material/CloudUpload';
import apiConfig from '../common/apiAxiosConfig';
import { SubmitHandler, useForm, Controller } from "react-hook-form";
import { News } from "../types/DataType";
import dayjs, { Dayjs } from "dayjs";
import { getMessage } from '../common/MessageUtil';
import DialogComponent from '../component/DialogBox';
import DefaultImagesDisplay from "../component/DefaultImagesDisplay";

export const NewsEdit: FC = () => {
  const loggedInUsername = useSelector((state: RootState) => state.params.param01);
  const loggedInUserRole = useSelector((state: RootState) => state.params.param02);
  const newsId = useSelector((state: RootState) => state.params.itemId);
  const navigate = useNavigate();

  const fileInputRef = useRef<HTMLInputElement | null>(null); 
  const [uploadedFile, setUploadedFile] = useState<File | null>(null); 
  const [imagePreview, setImagePreview] = useState<string | null>(null); // State to hold the image path from the API

  const handleUploadClick = () => {
    fileInputRef.current?.click(); 
  };

  const [isLoading, setIsLoading] = useState<Boolean | null>(null);
  const [showDialog, setShowDialog] = useState(false);
	const [showUpdateDialog, setShowUpdateDialog] = useState(false);
	const [showCancelDialog, setShowCancelDialog] = useState(false);
	const [dialogMessage, setDialogMessage] = useState('');
  const [screenTitle, setScreenTitle] = useState<string>('');

  // Validation
  const allowedTypes = ['image/jpeg', 'image/png']; 
  const maxSize = 1 * 1024 * 1024; 
  
  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const files = event.target.files;
    if (files && files.length > 0) {
        const file = files[0];

        // Validate file type
        if (!allowedTypes.includes(file.type)) {
            setDialogMessage(getMessage('E_TSCL_01_0008', ['Image']));
            setShowDialog(true);
            console.error('Invalid file type:', file.type); 
            resetFileInput();
            return;
        }

        //Validate file size
        if (file.size > maxSize) {
            setDialogMessage(getMessage('E_TSCL_01_0007', ['Image']));
            setShowDialog(true);
            console.error('File size exceeds limit:', file.size); 
            resetFileInput(); 
            return;
        }

        setUploadedFile(file);

        const reader = new FileReader();
        reader.onload = (e) => {
            const fileDataUrl = e.target?.result as string; 
            setImagePreview(fileDataUrl); 
            // console.log('File preview URL:', fileDataUrl);
        };
        reader.readAsDataURL(file); 
    } else {
        setDialogMessage(getMessage('E_TSCL_01_0006', ['Image']));
        setShowDialog(true);
        return;
    }
  };


  const resetFileInput = () => {
      if (fileInputRef.current) {
          fileInputRef.current.value = '';
      }
  };

  const { getValues, reset, clearErrors, control, handleSubmit, register, setValue, 
    formState: { errors } } = useForm<News>();
  

  //ONLOAD Function
  useEffect(() => {
    const fetchNews = async () => {
      try{
        const response = await apiConfig.post('/initNM003', null, {
          params: {
            loggedInUsername: loggedInUsername,
            loggedInUserRole: loggedInUserRole,
            NewsId: newsId,
          },
        });
        // console.log("API Fetch:", response.data);

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

  const handleUpdate: SubmitHandler<News> = async (data) => {
    setIsLoading(true);
    // console.log("data", data);
    const formData = new FormData();
    formData.append("loggedInUsername", loggedInUsername);
    formData.append("loggedInUserRole", loggedInUserRole);
    formData.append('NewsId', data.newsId.toString());
    formData.append('CreateBy', data.createId);
    formData.append('Title', data.title);
    formData.append('Content', data.content);

    formData.append(
      'StartDate', selectedStartDate ? 
      selectedStartDate.format(DATE_FORMATS.defaultFormat) : DATE_FORMATS.emptyFormat);

    formData.append(
      'EndDate', selectedEndDate ? 
      selectedEndDate.format(DATE_FORMATS.defaultFormat): DATE_FORMATS.emptyFormat);
    
    if (uploadedFile) {
      formData.append('ImgFile', uploadedFile); 
    } else if (imagePreview) {
      formData.append('ImgPath', imagePreview); 
    }

    // const formDataObject = Object.fromEntries(formData.entries());
    // console.log(formDataObject);

    try {
        const response = await apiConfig.post('/updateNM003', formData, {
            headers: {
                'Content-Type': 'multipart/form-data',
            },
        });

        // console.log('Response from server:', response.data);

        if(response.status === 200){
          const data = response.data;
          if(data.isdetailsUpdated === true){
            setDialogMessage(getMessage('I_TSCL_01_0040' , [String(getValues('newsId'))]));
            setShowDialog(true);
            setIsLoading(false);

          } else{
            setDialogMessage(getMessage('E_TSCL_01_0018'));
            setShowDialog(true);
            setIsLoading(false);
          }

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

  };
  
  const [selectedStartDate, setSelectedStartDate] = useState<Dayjs | null>(null);
  const [selectedEndDate, setSelectedEndDate] = useState<Dayjs | null>(null); 


  const handleBackClick = () => {
		reset();
		navigate(routes.NM001);
	};

  // Helper function to set preview from a URL directly
  const setImagePreviewFromUrl = (imageUrl: string) => {
    setImagePreview(imageUrl);
  };

  const handleImageClick = (imagePath: string) => {
    // console.log("Selected Images: ",imagePath);
    setImagePreviewFromUrl(imagePath); // Update selected image path when an image is clicked
  };
  

  return (
    <div>
      <PanelHeader showBackButton={true} 
        onBackClick={handleBackClick} title={screenTitle} />

      <div className="webportal_screen">
        <FormControl fullWidth component="form" 
            onSubmit={handleSubmit(handleUpdate)}>
              
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
                        rules={{
                            required: {
                                value: true,
                                message: getMessage('E_TSCL_01_0006', ['Start Date']), 
                            },
                            validate: {
                              validStart: (value) => {
                                
                                if (!selectedStartDate || !selectedStartDate.isValid()) {
                                    return getMessage('E_TSCL_01_0006', ['Start Date']); 
                                }
            
                                if (selectedEndDate && selectedStartDate.isAfter(selectedEndDate)) {
                                    return getMessage('E_TSCL_01_0041', ['Start Date']);
                                
                                  }
            
                                return true; 
                            },
                          },
                        }}
                        render={({ field }) => (
                            <DatePicker
                                {...field}
                                value={selectedStartDate}
                                onChange={(newSelectedStartDate: Dayjs | null) => {
                                  setSelectedStartDate(newSelectedStartDate);
          
                                 
                                  if (newSelectedStartDate && newSelectedStartDate.isValid()) {
                                    clearErrors("startDate");
                                }
          
                                  
                                  setValue("startDate", newSelectedStartDate ? newSelectedStartDate.format('MM-DD-YYYY') : '');
                              }}
                                sx={{ flexGrow: 1 }}
                            />
                        )}
                    />
                </LocalizationProvider>
                {errors.startDate && <p className="errorText">{errors.startDate.message}</p>}
            </div>

            <div className="news-edit_column">
            <FormLabel className="news-edit_label">{labelText.endDate}</FormLabel>
                <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <Controller
                        name="endDate"
                        control={control}
                        rules={{
                            required: {
                                value: true,
                                message: getMessage('E_TSCL_01_0006', ['End Date']), 
                            },
                            validate: {
                              validEnd: (value) => {
                                
                                if (!selectedEndDate || !selectedEndDate.isValid()) {
                                    return getMessage('E_TSCL_01_0006', ['End Date']); // Error message for invalid end date
                                }
        
                                
                                if (selectedStartDate && selectedEndDate.isBefore(selectedStartDate)) {
                                  return getMessage('E_TSCL_01_0042', ['End Date']);
                                }
        
                                return true; 
                            },
                          },
                        }}
                        render={({ field }) => (
                            <DatePicker
                                {...field}
                                value={selectedEndDate}
                                onChange={(newSelectedEndDate: Dayjs | null) => {
                                    setSelectedEndDate(newSelectedEndDate);

                                    
                                    if (newSelectedEndDate && newSelectedEndDate.isValid()) {
                                        clearErrors("endDate");
                                    }

                                   
                                    setValue("endDate", newSelectedEndDate ? newSelectedEndDate.format('MM-DD-YYYY') : '');
                                }}
                                sx={{ flexGrow: 1 }}
                            />
                        )}
                    />
                </LocalizationProvider>
                {errors.endDate && <p className="errorText">{errors.endDate.message}</p>}
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
                {...register('title',{
                  required: {
                    value: true,
                    message: getMessage('E_TSCL_01_0006', ['Title']),
                  }
                })}
                />
                {errors.title && <p className="errorText">{errors.title.message}</p>}
              </div>
              <div className="news-edit_row">
                <Paper sx={{ padding: '10px', minHeight: '400px' }}>
                  <Typography>{labelText.contents}</Typography>
                  <Controller
                      name="content"
                      control={control}
                      defaultValue=""
                      rules={{
                          required: {
                              value: true,
                              message: getMessage('E_TSCL_01_0006', ['Content']),
                          },
                          validate: {
                            nonEmpty: (value) => {
                                const strippedContent = value.replace(/<[^>]+>/g, '').trim(); // Remove HTML tags and trim whitespace
                                return strippedContent.length > 0 || getMessage('E_TSCL_01_0006', ['Contents']);
                            }
                         }
                          
                      }}
                      render={({ field }) => (
                          <ReactQuill
                              theme="snow"
                              value={field.value}
                              modules={richTextModules}
                              style={{ height: '320px', marginBottom: '20px' }}
                              onChange={(value) => {
                                  field.onChange(value);
                                  setValue("content", value);
                              }}
                          />
                      )}
                  />
                </Paper>
                {errors.content && <p className="errorText">{errors.content.message}</p>}
              </div>
            </div>

            <div className="news-edit_column_images">
              <Paper sx={{ padding: '10px', minHeight: '400px' }}>
              {/* Attachments Section */}
              <div className="news-edit-attachment">
                <Typography sx={{ color: 'black', marginBottom: '8px' }}>
                    {labelText.attachment}
                </Typography>
              </div>

              <div className="news-edit_images_attachment">
                <Box
                  className="upload-box"
                  onClick={handleUploadClick}
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
                  }}
                >
                  
                {uploadedFile ? (
                    <>
                        <Typography variant="body2" sx={{ marginTop: '16px', color: 'black' }}>
                            {labelText.uploadFile} {uploadedFile.name}
                        </Typography>
                        <img
                            src={URL.createObjectURL(uploadedFile)}
                            alt={uploadedFile.name}
                            style={{
                                width: '100%',
                                height: '100%',
                                objectFit: 'contain',
                                marginTop: '8px'
                            }}
                        />
                    </>
                ) : imagePreview ? (
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
                    <>
                        <ImageIcon sx={{ fontSize: 50 }} />
                        <CloudUploadIcon sx={{ fontSize: 30, position: 'relative', top: '-10px', right: '-15px' }} />
                        <Typography variant="body2" sx={{ marginTop: '16px', color: 'black' }}>
                            {labelText.dragAndDrop}
                        </Typography>
                    </>
                )}
                <input
                    type="file"
                    ref={fileInputRef}
                    onChange={handleFileChange}
                    style={{ display: 'none' }}
                />
            </Box>
          </div>
                <div className="news-edit-use-this-image">
                  <Typography sx={{ color: 'black', marginBottom: '8px' }}>
                    {labelText.useThisImage}
                  </Typography>
                </div>
                <DefaultImagesDisplay onImageClick={handleImageClick} />
              </Paper>
              <div>
                <Stack direction={'row'} justifyContent={'right'} spacing={3} marginTop={2}>
                  <Button variant="contained" 
                  onClick={() => setShowUpdateDialog(true)}>
                    {labelText.update}
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
        show={showUpdateDialog}
        dialogMessage={getMessage('I_TSCL_01_0043', [String(getValues('newsId'))])}
        handleClose={() => setShowUpdateDialog(false)}
        confirmation={true}
        handleYes={() => {
          handleSubmit(handleUpdate)();
          setShowUpdateDialog(false);
          }}
        handleNo={() => setShowUpdateDialog(false)}
      />

      {/* Confirm Modal For Cancel Button*/}
      <DialogComponent show={showCancelDialog} dialogMessage={getMessage('I_TSCL_01_0025')} handleClose={() => setShowCancelDialog(false)} confirmation={true} handleYes={handleBackClick} handleNo={() => setShowCancelDialog(false)} />

      {/* Message Modal */}
			<DialogComponent 
        show={showDialog} 
        dialogMessage={dialogMessage} 
        handleClose={() => {
          setShowDialog(false);
          navigate(routes.NM001);
        }} />
      {/* Loading Animation */}
      {isLoading && (
        <div className="loading_page">
          {/* 3 dots animation */}
          <div className="loading_animation"></div>
        </div>
      )}

    </div>
  );
}
