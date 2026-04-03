/*
 * NM005
 *
 * v 00.001 - 10/23/2024
 *
 * PIC: emonteverde
 * 
 * Description: Used to display all active news in a carousel format.
 * 
 */

import { FC, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Box, Container, Grow, Paper, Stack, Typography } from "@mui/material";

import { NewsListItem } from "../types/DataType";

import { getMessage } from "../common/MessageUtil";
import apiAxiosConfig from "../common/apiAxiosConfig";
import { ColumnHeaders } from '../common/appConstant';
import RichTextDisplay from "../component/RichTextDisplay";
import PanelHeader from "../component/PanelHeader";
import DialogComponent from "../component/DialogBox";

import '../style/main.css';

// Configuration Constants from .env
const NEWS_INTERVAL = parseInt(process.env.REACT_APP_NEWS_INTERVAL as string, 10);
const SHORT_DELAY = parseInt(process.env.REACT_APP_SHORT_DELAY as string, 10);
const MIN_ITEMS = parseInt(process.env.REACT_APP_MIN_ITEMS as string, 10);
const DOUBLE_ITEM_COUNT = parseInt(process.env.REACT_APP_DOUBLE_ITEM_COUNT as string, 10);
const DEFAULT_ITEM_COUNT = parseInt(process.env.REACT_APP_DEFAULT_ITEM_COUNT as string, 10);

export const NewsScreen: FC = () => {
  const [newsList, setNewsList] = useState<NewsListItem[]>([]);
  const [currentNewsIndex, setCurrentNewsIndex] = useState<number>(0);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [growIn, setGrowIn] = useState<boolean>(true);
  const [slideClass, setSlideClass] = useState<string>('reset');
  const [dialogMessage, setDialogMessage] = useState<string>("");
  const [showDialog, setShowDialog] = useState(false);
  const navigate = useNavigate();
  const baseURL = `${window.location.origin}`; // used for background images

  useEffect(() => {
    const onLoad = async () => {
      try {
        const response = await apiAxiosConfig.get('/news/bulletin');
        if (response.data) {
          setNewsList(response.data);
        }
      } catch (err) {
        setDialogMessage(getMessage('E_TSCL_01_0039'));
      } finally {
        setLoading(false);
      }
    };
    onLoad();
  }, []);

  useEffect(() => {
    const onDisplay = () => {
      if (!error) {
        setCurrentNewsIndex(0);
      }
    };
    if (!loading) {
      onDisplay();
    }
  }, [newsList, loading, error]);

  const handleDialogClose = () => {
    setShowDialog(false);
    setDialogMessage("");
  };

  useEffect(() => {
    const intervalId = setInterval(() => {
      if (newsList.length > MIN_ITEMS) {
        setGrowIn(false);
        setSlideClass('slide');
        setTimeout(() => {
          setCurrentNewsIndex((prevIndex) =>
            prevIndex === newsList.length - 1 ? 0 : prevIndex + 1
          );
          setGrowIn(true);
          setSlideClass('reset');
        }, SHORT_DELAY);
      }
    }, NEWS_INTERVAL);

    return () => clearInterval(intervalId);
  }, [newsList.length]);

  const handleBackClick = () => {
    navigate(-1);
  };

  const currentNews = newsList[currentNewsIndex] || {};

  const nextNews = () => {
    if (newsList.length <= MIN_ITEMS) return [];

    const nextItems = [];
    const itemsToShow = newsList.length === DOUBLE_ITEM_COUNT ? 1 : DEFAULT_ITEM_COUNT;
    for (let i = 1; i <= itemsToShow; i++) {
      const index = (currentNewsIndex + i) % newsList.length;
      nextItems.push(newsList[index]);
    }
    return nextItems;
  };

  const nextNewsItems = nextNews();
  const backgroundImageUrl = currentNews.image_path ? `${baseURL}${currentNews.image_path}` : '';


  return (
    <div className="nm005-wrapper">
      <Container maxWidth={false} className="nm005-main-container" sx={{ display: 'flex', flexDirection: 'column' }}>
        <PanelHeader showBackButton={true} onBackClick={handleBackClick} title="News" />

        <Stack spacing={2} position={'relative'} className="" sx={{ marginTop: '12px', flexGrow: 1 }}>
          <Box className="main-news-container" position={'relative'} height={'100%'}>
            {newsList.length === 0 ? (
              <p>{getMessage('I_TSCL_01_0029', ['news'])}</p>
            ) : (
              <Stack spacing={2} position={'relative'} display={'flex'} height={'95%'} className="">
                <Box className="news-id-box ">
                  <Grow in={growIn}>
                    <Typography id="txtNewsId" variant="h3">
                      {ColumnHeaders.newsId}: {currentNews.news_id}
                    </Typography>
                  </Grow>
                </Box>
                <Box sx={{ display: 'flex', justifyContent: 'right' }} className="news-info-box ">
                  <Grow in={growIn}>
                    <Stack spacing={10} direction={'row'} className="news-info-box">
                      <Typography id="txtCreateId" variant="h6">{ColumnHeaders.createId}: {currentNews.create_id}</Typography>
                      <Typography id="txtCreatedDate" variant="h6"> {ColumnHeaders.createDate}: {currentNews.create_date}</Typography>
                      <Typography id="txtUpdateDate" variant="h6">{ColumnHeaders.updateDate}: {currentNews.update_date}</Typography>
                    </Stack>
                  </Grow>
                </Box>
                <Paper
                  id="txtImagePath"
                  className="current-news-paper"
                  sx={{
                    flexGrow: '1',
                    backgroundImage: `url(${backgroundImageUrl})`,
                    backgroundRepeat: 'no-repeat',
                    backgroundSize: 'cover',
                    backgroundPosition: 'right',
                    position: 'relative',
                    overflow: 'hidden',
                    display: 'flex',
                    alignItems: 'center'
                  }}
                >
                  <Box marginLeft={'3%'} height={'80%'} width={'50%'} maxWidth={'50%'} sx={{ display: 'flex', flexDirection: 'column'}}>
                    <Box maxHeight={'50%'} sx={{overflow:'hidden'}}>
                      <Grow in={growIn}>
                        <Typography id="txtTitle" sx={{ background: 'none', fontWeight: 'bold', fontSize:'2.5rem' }}>{currentNews.title}</Typography>
                      </Grow>
                    </Box>
                    <Box pl={'10%'} sx={{overflow:'hidden'}}>
                      <Grow in={growIn}>
                        <span><RichTextDisplay content={currentNews.content} /></span>
                      </Grow>
                    </Box>
                  </Box>

                  <Box id="newsSlider" className="news-preview-container"
                    sx={{
                      display: 'flex',
                      justifyContent: 'center',
                      alignItems: 'center',
                      gap: '5%',
                      background: 'transparent',
                      position: 'absolute',
                      width: '50%',
                      height: '30%',
                      bottom: 0,
                      right: '-8%',
                    }}>
                    <Stack className="news-preview-stack"
                      spacing={4}
                      direction={'row'}
                      sx={{
                        overflow: 'hidden',
                        height: '100%',
                        width: '100%',
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'space-around'
                      }}>
                      {nextNewsItems.map((news, index) => (
                        <Box key={index}
                          sx={{
                            height: '100%',
                            width: '80%',
                            display: 'flex',
                            alignItems: 'center',
                            justifyContent: 'center'
                          }}
                        >
                          <Paper className={`preview-item ${slideClass}`}
                            sx={{
                              height: '70%',
                              width: nextNewsItems.length > 1 ? '70%' : '30%',
                            }}>
                            <Box className="next-news-paper"
                              sx={{
                                height: '100%',
                                width: '100%',
                                // backgroundImage: `url(http://localhost:8080${news.image_path})`,
                                backgroundImage: `url(${baseURL}${news.image_path})`,
                                backgroundRepeat: 'no-repeat',
                                backgroundSize: 'cover',
                                backgroundPosition: 'center',
                              }}>
                              <Typography variant="h6">{ColumnHeaders.newsId}: {news.news_id}</Typography>
                            </Box>
                          </Paper>
                        </Box>
                      ))}
                    </Stack>
                  </Box>
                </Paper>
              </Stack>
            )}
          </Box>
        </Stack>
        <DialogComponent
          show={showDialog}
          dialogMessage={dialogMessage}
          handleClose={handleDialogClose}
        />
      </Container>
    </div>
  );
};
