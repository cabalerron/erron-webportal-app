
/*
 * HM001
 *
 * v 00.001 - 10/18/2024
 *
 * PIC: emonteverde
 * 
 * This is the home page used to display all the links to other screens, the calendar, and a list of news title. 
*/

import { FC, useCallback, useEffect, useState } from "react";
import { Typography, Paper, Box, CardActionArea, Card } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import '../style/HM001.css';
import PanelHeader from '../component/PanelHeader';

import { Calendar } from "../component/Calendar";
import CircleIcon from '@mui/icons-material/Circle';
import { routes } from "../common/appConstant";
import { useSelector, useDispatch } from "react-redux";
import { RootState } from "../redux/store";
import { News } from "../types/DataType";
import { setNews } from "../redux/NewsReducer";
import apiConfig from '../common/apiAxiosConfig';
import FeedIcon from '@mui/icons-material/Feed';
import AssignmentIndIcon from '@mui/icons-material/AssignmentInd';
import GroupIcon from '@mui/icons-material/Group';
import { getMessage } from "../common/MessageUtil";
export const Home: FC = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const param01 = useSelector((state: RootState) => state.params.param01);
  const param02 = useSelector((state: RootState) => state.params.param02);

  // Get news list from redux store
  const newsList = useSelector((state: RootState) => state.news.newsList);

  const [paginationModel, setPaginationModel] = useState({
    page: 0,
    size: 10,
    totalItems: 0,
  });


  const handleUserListWithId = useCallback((content: News[], totalItems: number) => {
    const newsListWithId = content.map((news: News) => ({
      id: news.newsId,
      ...news,
    }));
    dispatch(setNews({ newsList: newsListWithId, totalItems }));
  }, [dispatch]);

  const handleSetPaginationModel = useCallback((totalElements: number) => {
    setPaginationModel((prev) => ({
      ...prev,
      totalItems: totalElements,
    }));
  }, []);

  useEffect(() => {
    const fetchUsers = async () => {
      const pageRequest = JSON.stringify({
        page: paginationModel.page,
        size: paginationModel.size,
      });

      try {
        const response = await apiConfig.get('/articleTitle', {
          params: {
            Param01: param01,
            Param02: param02,
            pageRequest,
          },
        });

        const data = response.data;
        // console.log("API Fetch:", response.data);

        handleUserListWithId(data, data.length);
        handleSetPaginationModel(data.length);

      } catch (error) {
        console.error('Error fetching users:', error);
      }
    };

    fetchUsers();
  }, [dispatch, paginationModel.page, paginationModel.size, param01, param02, handleUserListWithId, handleSetPaginationModel]);

//handle fetch screen title from db
	const [screenTitle, setScreenTitle] = useState<string>('');

useEffect(() => {
		const fetctFuncName = async () => {
			try {
				const response = await apiConfig.get('/hm001FunctionName', {
					params: {
						Param01: param01,
            Param02: param02,
					},
				});
				const res = response.data;
        // console.log(res);
				setScreenTitle(res);
			} catch (error: any) {
				getMessage('E_TSCL_01_0020');
			}
		};
		fetctFuncName();
	}, [param01, param02]);

  const handleNavigate = (path: string, params = {}) => {
    navigate(path, { state: { ...params } });
  };

  const handleBackClick = () => {
    navigate(routes.HM001);
  };

  return (
    <>
      <div className="main_container">
        <PanelHeader showBackButton={true} onBackClick={handleBackClick} title={screenTitle} />

        <div className="link_card">
          <div className="card" onClick={() => handleNavigate(routes.NM001)}>
            <Card sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', width: '100px', height: '100px' }}>
              <CardActionArea sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', width: '100%', height: '100%' }}>
                <FeedIcon sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', width: '60%', height: '60%', color: "#01579b", }} />
              </CardActionArea>
            </Card>
            <div className="lnkNews">
              News
            </div>
          </div>

          <div className="card" onClick={() => handleNavigate(routes.UM001)}>
            <Card sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', width: '100px', height: '100px' }}>
              <CardActionArea sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', width: '100%', height: '100%' }}>
                <GroupIcon sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', width: '60%', height: '60%', color: "#01579b", }} />
              </CardActionArea>
            </Card>
            <div className="lnkUsers">
              Users
            </div>
          </div>

          <div className="card" onClick={() => handleNavigate(routes.RM001)}>
            <Card sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', width: '100px', height: '100px' }}>
              <CardActionArea sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', width: '100%', height: '100%' }}>
                <AssignmentIndIcon sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', width: '60%', height: '60%', color: "#01579b", }} />
              </CardActionArea>
            </Card>
            <div className="lnkRoles">
              Role
            </div>
          </div>
        </div>

        <Box sx={{ display: 'flex', gap: 10, margin: '20px' }}>
          <Box sx={{ flex: 1 }}>
            <Paper style={{ height: '500px' }}>
              <Typography variant="h4" style={{ padding: '3px', backgroundColor: '#01579b', boxShadow: '0px 2px 4px rgba(0,0,0,0.2)', color: 'white' }}>Calendar</Typography>
              <Box sx={{ display: "flex", height: '100%' }}>
                <Calendar />
              </Box>
            </Paper>
          </Box>

           <Box sx={{ flex: 1 }}>
            <Paper style={{ height: '500px' }}>
              <Typography variant="h4" style={{backgroundColor: '#01579b', boxShadow: '0px 2px 4px rgba(0,0,0,0.2)', color: 'white' }}>News</Typography>
              <div className="lnkArticle">
                 {newsList.length > 0 ? (
                  newsList.slice(0, paginationModel.size).map((newsList) => (
                    <div
                      key={newsList.title}
                      style={{
                        display: 'flex',
                        alignItems: 'center',
                        marginBottom: '5px'
                    }}
                    >
                      <Typography
                        variant="h6"
                        style={{ display: 'flex', alignItems: 'center', marginLeft: '5px' }}
                      >
                        <CircleIcon fontSize="small" />
                        {newsList.title}
                     </Typography>
                    </div>
                  ))
               ) : (
                 <Typography variant="h6" style={{ color: 'gray', textAlign: 'center' }}>
                    { getMessage('I_TSCL_01_0029',['news'])}
                 </Typography>
               )}
              </div>
            </Paper>
          </Box>
        </Box>
      </div >
    </>
  );
};