/*
 * PW002
 *
 * v 00.001 - 10/23/2024
 *
 * PIC: emonteverde
 */
import React, { FC, useState, useEffect, FormEvent } from 'react';
import { TextField, Button, Paper, Typography } from '@mui/material';
import { Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from '@mui/material';
import PageTitle from '../component/Page';
import { useNavigate } from 'react-router-dom';
import apiAxiosConfig from '../common/apiAxiosConfig';
import '../style/PW002.css';
import { getMessage } from "../common/MessageUtil";
import { routes, ColumnHeaders } from '../common/appConstant';

export const ForgotPass: FC = () => {
  const [newPassword, setNewPassword] = useState<string>('');
  const [confPassword, setConfPassword] = useState<string>('');
  const navigate = useNavigate();
  const [show, setShow] = useState(false);
  const [dialogMessage, setDialogMessage] = useState("");
  const [isTokenValid, setIsTokenValid] = useState<boolean | null>(null);
  const [isPasswordResetSuccessful, setIsPasswordResetSuccessful] = useState(false);

  const query = new URLSearchParams(window.location.search);
  const token = query.get('token');

  useEffect(() => {
    const checkToken = async () => {
      try {
        if (token) {
          const response = await apiAxiosConfig.get('/public/check-token', {
            params: { token }
          });
          if (response.status === 200) {
            setIsTokenValid(true);
          } else {
            setIsTokenValid(false);
            setDialogMessage("An error occurred");
            setShow(true);
          }
        } else {
          setIsTokenValid(false);
          setDialogMessage("An error occurred");
          setShow(true);
        }
      } catch (error: any) {
        setIsTokenValid(false);
        setDialogMessage(getMessage(error.response.data));
        setShow(true);
      }
    };
  
    checkToken();
  }, [token]);

  const handleResetPassword = async (e: FormEvent) => {
    e.preventDefault();
    try {
      const response = await apiAxiosConfig.post('/public/reset-pass', null, {
        params: { token, newPassword, confPassword },
      });
      if (response.status === 200) {
        const { errorCode, fieldName } = response.data;
        setDialogMessage(getMessage(errorCode, [fieldName]));
        setIsPasswordResetSuccessful(true);
        setShow(true);
      } else {
        setDialogMessage("An error occurred");
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
    if (isPasswordResetSuccessful) {
      navigate(routes.LOGIN);
    }
  };

  const redirectToLogin = () => {
    navigate(routes.LOGIN);
  };

  return (
    <div className="forgot-container">
      <Paper elevation={3} className="forgot-paper">
        {isTokenValid === null ? (
          null
        ) : isTokenValid ? (
          <>
            <PageTitle title="Reset Password" />
            <form onSubmit={handleResetPassword}>
              <div className="form-margin">
                <TextField
                  label={ColumnHeaders.newPassword}
                  variant="outlined"
                  fullWidth
                  type="password"
                  value={newPassword}
                  onChange={(e) => setNewPassword(e.target.value)}
                />
              </div>

              <div className="form-margin">
                <TextField
                  label={ColumnHeaders.confirmPassword}
                  variant="outlined"
                  fullWidth
                  type="password"
                  value={confPassword}
                  onChange={(e) => setConfPassword(e.target.value)}
                />
              </div>

              <div className="button-container">
                <Button
                  variant="contained"
                  color="primary"
                  type="submit"
                  fullWidth
                >
                  Change Password
                </Button>
              </div>
            </form>
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
                  OK
                </Button>
              </DialogActions>
            </Dialog>
          </>
        ) : (
          <div className="error-message">
            <Typography variant="h6" color="error" gutterBottom align="center">
              {dialogMessage}
            </Typography>
            <Button
              variant="contained"
              color="primary"
              onClick={redirectToLogin}
              fullWidth
            >
              Go to Login
            </Button>
          </div>
        )}
      </Paper>
    </div>
  );
};
