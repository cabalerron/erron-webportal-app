/*
 * UM005
 *
 * v 00.001 - 10/25/2024
 *
 * PIC: emonteverde
 */
import React, { FC, useState, FormEvent } from 'react';
import PanelHeader from '../component/PanelHeader';
import { TextField, Button } from '@mui/material';
import { Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import apiAxiosConfig from '../common/apiAxiosConfig';
import '../style/UM005.css';
import { getMessage } from "../common/MessageUtil";
import { routes, ColumnHeaders } from '../common/appConstant';
import { useSelector } from "react-redux";
import { RootState } from "../redux/store";
import LogoutButton from '../component/LogoutButton'; // Import the LogoutButton component

export const UserPassChange: FC = () => {
  const navigate = useNavigate();
  const [oldPassword, setOldPassword] = useState<string>('');
  const [newPassword, setNewPassword] = useState<string>('');
  const [confPassword, setConfPassword] = useState<string>('');
  const [show, setShow] = useState(false);
  const [dialogMessage, setDialogMessage] = useState("");
  const [isPasswordChangeSuccessful, setIsPasswordChangeSuccessful] = useState(false);
  const accountId = useSelector((state: RootState) => state.params.param01);
  
  const PAGE_TITLE = process.env.REACT_APP_CHANGE_PASSWORD_PAGE_TITLE;

  const handleBackClick = () => {
    navigate(routes.UM003); 
  };

  const handleChangePassword = async (e: FormEvent) => {
    e.preventDefault();
    try {
      const response = await apiAxiosConfig.put('/change-password', null, {
        params: { accountId, oldPassword, newPassword, confPassword },
      });
      if (response.status === 200) {
        const { errorCode, fieldName } = response.data;
        setDialogMessage(getMessage(errorCode, [fieldName]));
        setShow(true);
        setIsPasswordChangeSuccessful(true);
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
    if (isPasswordChangeSuccessful) {
      handleLogout();
    }
  };

  const handleLogout = async () => {
    try {
      const response = await apiAxiosConfig.post('/public/logout');
      console.log(response.data);
      localStorage.removeItem("isAuthenticated");
      window.location.href = "/";
    } catch (error) {
      console.error('Logout failed', error);
    }
  };

  return (
    <div>
      <PanelHeader showBackButton={true} onBackClick={handleBackClick} title={PAGE_TITLE!} />
      <div className='changepass-container'>
        <form onSubmit={handleChangePassword}>
          <div className="form-margin">
            <TextField
              label={ColumnHeaders.oldPassword}
              variant="outlined"
              fullWidth
              type="password"
              value={oldPassword}
              onChange={(e) => setOldPassword(e.target.value)}
            />
          </div>

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
      </div>
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
