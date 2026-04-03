/*
 * LGN001
 *
 * v 00.001 - 10/21/2024
 *
 * PIC: emonteverde
 */
import React, { FC, useState, FormEvent, useEffect } from 'react';
import { TextField, Button, Paper, InputAdornment } from '@mui/material';
import { Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from '@mui/material';
import AccountCircle from '@mui/icons-material/AccountCircle';
import LockIcon from '@mui/icons-material/Lock';
import PageTitle from '../component/Page'; 
import { useNavigate } from 'react-router-dom';
import '../style/LGN001.css';
import { useAuth } from "../router/AuthContext";
import { getMessage } from "../common/MessageUtil";
import apiAxiosConfig from '../common/apiAxiosConfig'; 
import { useDispatch } from 'react-redux';
import { setParams } from '../redux/ParamUtilReducer';
import { routes, ColumnHeaders } from '../common/appConstant';

export const Login: FC = () => {
  const { login, isAuthenticated  } = useAuth();
  const [accountId, setAccountid] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const navigate = useNavigate();
  const dispatch = useDispatch();

  // Dialog sample Message State
  const [show, setShow] = useState(false);
  const [dialogMessage, setDialogMessage] = useState("");

  const PAGE_TITLE = process.env.REACT_APP_LOGIN_PAGE_TITLE;

  useEffect(() => {
    if (isAuthenticated) {
      navigate(routes.HM001, { replace: true });  // Replace ensures user cannot go back to the login page
    }
  }, [isAuthenticated, navigate]);

  const handleLogin = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const loginData = {
      accountId: accountId,
      password: password,
    };

    try {
      const response = await apiAxiosConfig.post('/public/login', null, {
        params: loginData,
        withCredentials: true,
      }); 

      const { accountId, roleId, errorCode, fieldName } = response.data;

      if (response.status === 200) {
        login();
        dispatch(setParams({ param01: accountId, param02: roleId }));
        navigate(routes.HM001);
      }else {
        setDialogMessage(getMessage(errorCode, [fieldName]));
        setShow(true);
      }
      } catch (error: any) {
          const { errorCode, fieldName } = error.response.data;
          setDialogMessage(getMessage(errorCode, [fieldName]));
          setShow(true);
      }
  };

  const handleClose = () => {
    setShow(false); 
  };

  return (
    <div className="login-container">
      <Paper elevation={3} className="login-paper">
      <PageTitle title={PAGE_TITLE!} />
        <form onSubmit={handleLogin}>
          <div className="form-margin">
            <TextField
               label={ColumnHeaders.accountId}
              variant="outlined"
              fullWidth
              value={accountId}
              onChange={(e) => setAccountid(e.target.value)}
              InputLabelProps={{ shrink: true }}
              InputProps={{
                endAdornment: (
                  <InputAdornment position="end">
                    <AccountCircle />
                  </InputAdornment>
                ),
              }}
            />
          </div>

          <div className="form-margin">
            <TextField
              label={ColumnHeaders.password}
              variant="outlined"
              type="password"
              fullWidth
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              InputLabelProps={{ shrink: true }}
              InputProps={{
                endAdornment: (
                  <InputAdornment position="end">
                    <LockIcon />
                  </InputAdornment>
                ),
              }}
            />
          </div>

          <div className="button-container">
            <Button 
              variant="contained" 
              color="primary" 
              type="submit" 
              fullWidth
            >
              Login
            </Button>
          </div>
          <a className='forgot-pass-link' href={routes.PW001}>{ColumnHeaders.forgotPassword}</a>
        </form>
      </Paper>
      {/* Material-UI Dialog for error messages */}
      <Dialog
        open={show}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
        PaperProps={{
          sx: { 
            width: { xs: "90%", sm: "500px" },
            maxWidth: "none", 
            height: { xs: "auto", sm: "200px" }
          }
        }}
      >
        <DialogTitle id="alert-dialog-title">{"Dialog Message"}</DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            {dialogMessage}
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} autoFocus>
            Cancel
          </Button>
          <Button onClick={handleClose} autoFocus>
            OK
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
};
