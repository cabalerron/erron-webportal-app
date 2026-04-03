/*
 * NM002
 *
 * v 00.001 - 10/25/2024
 *
 * PIC: emonteverde
 * 
 * Description: Used to display a form to add news.
 * 
 */
import { Button, Container, Paper, Stack, TextField, Typography } from "@mui/material";
import { LocalizationProvider, DatePicker } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { Dayjs } from "dayjs";
import { FC, useEffect, useState } from "react";
import DialogComponent from "../component/DialogBox";
import DragDropFileUpload from "../component/DragDropFileUpload";
import PanelHeader from "../component/PanelHeader";
import { useNavigate } from "react-router-dom";
import { getMessage } from "../common/MessageUtil";
import { useSelector } from "react-redux";
import { RootState } from "../redux/store";
import apiAxiosConfig from "../common/apiAxiosConfig";
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import { ColumnHeaders, newsMaxImageSize, labelText, richTextModules, routes } from "../common/appConstant";


export const NewsAdd: FC = () => {
  const navigate = useNavigate();
  const [title, setTitle] = useState<string>('');
  const [content, setContent] = useState('');
  const [selectedStartDate, setSelectedStartDate] = useState<Dayjs | null>(null);
  const [selectedEndDate, setSelectedEndDate] = useState<Dayjs | null>(null);
  const [imageFile, setImageFile] = useState<File | null>(null);
  const [selectedDefaultImage, setSelectedDefaultImage] = useState<string | null>(null);
  const [dialogMessage, setDialogMessage] = useState<string>("");
  const [showDialog, setShowDialog] = useState(false);
  const createId = useSelector((state: RootState) => state.params.param01);
  const [defaultImages, setDefaultImages] = useState<string[]>([]);
  const allowedImageTypes = process.env.REACT_APP_ALLOWED_IMAGE_TYPES?.split(',') || [];
  const allowedExtensions = (process.env.REACT_APP_ALLOWED_NEWS_IMAGE_EXTENSIONS || '').split(',').map(ext => ext.trim().toLowerCase());
  const titleFormatRegex = new RegExp(process.env.REACT_APP_TITLE_FORMAT_REGEX || '');
  const [closeDialogSuccessAdd, setCloseDialogSuccessAdd] = useState(false);

  const handleBackClick = () => {
    navigate(-1);
  };

  const handleDialogClose = () => {
    setShowDialog(false);
    setDialogMessage("");
    if (closeDialogSuccessAdd) {
      navigate(routes.NM001)
    }
  };
  const handleFileUpload = (file: File | null, imageSrc?: string) => {
    if (file) {
      setImageFile(file);
      setSelectedDefaultImage(null);
    } else if (imageSrc) {
      setSelectedDefaultImage(imageSrc);
      setImageFile(null);
    }
  };
  const handlePostClick = async () => {
    // Check for required fields
    if (!title) {
      setDialogMessage(getMessage('E_TSCL_01_0006', [ColumnHeaders.title]));
      setShowDialog(true);
      return;
    }

    if (!selectedStartDate) {
      setDialogMessage(getMessage('E_TSCL_01_0006', [ColumnHeaders.startDate]));
      setShowDialog(true);
      return;
    }

    if (!selectedEndDate) {
      setDialogMessage(getMessage('E_TSCL_01_0006', [ColumnHeaders.endDate]));
      setShowDialog(true);
      return;
    }

    if (!imageFile && !selectedDefaultImage) {
      setDialogMessage(getMessage('E_TSCL_01_0006', [ColumnHeaders.image]));
      setShowDialog(true);
      return;
    }

    // Check for title length
    if (title.length > 200) {
      setDialogMessage(getMessage('E_TSCL_01_0028', [ColumnHeaders.title]));
      setShowDialog(true);
      return;
    }

    // Check for title format using regex from .env
    if (!titleFormatRegex.test(title)) {
      setDialogMessage(getMessage('E_TSCL_01_0019'));
      setShowDialog(true);
      return;
    }
    // Check image file size (greater than 1MB) if an image file is provided
    if (imageFile && imageFile.size > newsMaxImageSize * newsMaxImageSize) {
      setDialogMessage(getMessage('E_TSCL_01_0007'));
      setShowDialog(true);
      return;
    }

    // Check file type if an image file is provided
    if (imageFile) {
      const fileExtension = imageFile.name.split('.').pop()?.toLowerCase();
      if (!allowedExtensions.includes(fileExtension || '')) {
        setDialogMessage(getMessage('E_TSCL_01_0008'));
        setShowDialog(true);
        return;
      }
    }

    // Format dates to MM-dd-yyyy
    const startDateFormatted = selectedStartDate?.format(process.env.REACT_APP_START_END_DATE_FORMAT);
    const endDateFormatted = selectedEndDate?.format(process.env.REACT_APP_START_END_DATE_FORMAT);

    // Create FormData object
    const formData = new FormData();
    formData.append('createId', createId);
    formData.append('title', title);
    formData.append('content', content);
    if (imageFile) {
      formData.append('image', imageFile);
    } else if (selectedDefaultImage) {
      formData.append('imagePath', selectedDefaultImage);
    }

    formData.append('startDate', startDateFormatted);
    formData.append('endDate', endDateFormatted);

    try {
      // API call to post the news
      await apiAxiosConfig.post('/news/add', formData, {
        headers: { 'Content-Type': 'multipart/form-data' },
      });

      // Show success message if API call is successful
      setDialogMessage(getMessage('E_TSCL_01_0023'));
      setShowDialog(true);
      setCloseDialogSuccessAdd(true);
    } catch (error) {
      // Show error message if API call fails
      setDialogMessage(getMessage('E_TSCL_01_0021'));
      setShowDialog(true);
    }
  };
  useEffect(() => {
    const fetchDefaultImages = async () => {
      try {
        const response = await apiAxiosConfig.get('/news/default-news-images');
        setDefaultImages(response.data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchDefaultImages();
  }, []);

  return (
    <div>
      <Container maxWidth={false} className="nm002-main-container" sx={{ display: 'flex', flexDirection: 'column' }}>
        <PanelHeader showBackButton={true} onBackClick={handleBackClick} title="News" />
        <Stack flexGrow={1} paddingBottom={2}>
          <Stack
            spacing={2}
            position={'relative'}
            direction={'row'}
            flexGrow={1}
            sx={{
              margin: '12px 0',
            }}
            className="main-form"
          >
            <Stack
              className=""
              padding={'24px'}
              width={'50%'}
              spacing={'5%'}
            >
              <Stack direction={'row'} spacing={'10%'}>
                <TextField
                  disabled
                  id="outlined-disabled"
                  label={ColumnHeaders.newsId}
                  defaultValue=""
                  fullWidth
                />
                <TextField
                  disabled
                  id="outlined-disabled"
                  label={ColumnHeaders.createId}
                  defaultValue=""
                  fullWidth
                />
              </Stack>
              <Stack>
                <TextField
                  required
                  id="outlined"
                  label={ColumnHeaders.title}
                  value={title}
                  onChange={(e) => setTitle(e.target.value)}
                />
              </Stack>
              <Paper sx={{ padding: '10px', minHeight: '200px' }}>
                <Typography >{ColumnHeaders.content}</Typography>
                <ReactQuill theme="snow" value={content} onChange={setContent} modules={richTextModules} />
              </Paper>
            </Stack>
            <Stack
              className=""
              width={'50%'}
              padding={'24px'}
            >
              <Stack spacing={'5%'} height={'100%'}>
                <LocalizationProvider dateAdapter={AdapterDayjs}>
                  <Stack direction={'row'} spacing={'10%'}>
                    <DatePicker
                      label={ColumnHeaders.startDate}
                      value={selectedStartDate}
                      onChange={(newSelectedStartDate: Dayjs | null) => {
                        setSelectedStartDate(newSelectedStartDate);
                      }}
                      sx={{ flexGrow: 1 }}
                    />
                    <DatePicker
                      label={ColumnHeaders.endDate}
                      value={selectedEndDate}
                      onChange={(newSelectedEndDate: Dayjs | null) => {
                        setSelectedEndDate(newSelectedEndDate);
                      }}
                      sx={{ flexGrow: 1 }}
                    />
                  </Stack>
                </LocalizationProvider>
                <Paper className="" sx={{ padding: '3%', height: '100%' }}>
                  <DragDropFileUpload
                    onFileUpload={handleFileUpload}
                    defaultImages={defaultImages}
                  />
                </Paper>
              </Stack>
            </Stack>
          </Stack>
          <Stack direction={'row'} justifyContent={'right'} spacing={3}>
            <Button variant="contained" onClick={handlePostClick}>{labelText.post}</Button>
            <Button variant="outlined" onClick={handleBackClick}>{labelText.cancel}</Button>
          </Stack>
        </Stack>
        {/* Dialog Component for showing error or success messages */}
        <DialogComponent
          show={showDialog}
          dialogMessage={dialogMessage}
          handleClose={handleDialogClose}
        />
      </Container>
    </div>
  );
};