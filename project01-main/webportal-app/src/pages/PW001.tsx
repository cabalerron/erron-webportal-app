/*
 * PW001
 *
 * v 00.001 - 10/22/2024
 *
 * PIC: emonteverde
 */
import React, { FC, useState, FormEvent } from 'react';
import { TextField, Button, Paper, CircularProgress } from '@mui/material';
import { Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from '@mui/material';
import PageTitle from '../component/Page'; 
import '../style/PW001.css';
import apiAxiosConfig from '../common/apiAxiosConfig'; 
import { getMessage } from "../common/MessageUtil";
import { routes, ColumnHeaders } from '../common/appConstant';

export const EmailInput: FC = () => {
  const [email, setEmail] = useState<string>('');
  const [loading, setLoading] = useState<boolean>(false);
  const [show, setShow] = useState(false);
  const [dialogMessage, setDialogMessage] = useState("");

  const PAGE_TITLE = process.env.REACT_APP_FORGOT_PASSWORD_PAGE_TITLE;

  const handleEmail = async (e: FormEvent) => {
    e.preventDefault();
    setLoading(true);

    try {
      const response = await apiAxiosConfig.post('/public/send-link', null, {
        params: {
          email,
        },
        withCredentials: true,
      });
      const messageCode = response.data.messageCode;
      setDialogMessage(getMessage(messageCode));
      setShow(true);

    } catch (error: any) {
      const messageCode = error.response.data.messageCode;
      const fieldName = error.response.data.fieldName;
      setDialogMessage(getMessage(messageCode, [fieldName]));
      setShow(true);
    } finally {
      setLoading(false);
    }
  };

  const handleClose = () => {
    setShow(false); 
  };

  return (
    <div className="email-container">
      <Paper elevation={3} className="email-paper">
        <PageTitle title={PAGE_TITLE!} />

        <form onSubmit={handleEmail}>
          <div className="form-margin">
            <TextField
              label={ColumnHeaders.mailAddress}
              variant="outlined"
              fullWidth
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>

          <div className="button-container">
            <Button 
              variant="contained" 
              color="primary" 
              type="submit" 
              fullWidth
              disabled={loading}
            >
              {loading ? <CircularProgress size={24} color="inherit" /> : ColumnHeaders.sendRestLink} 
            </Button>
          </div>
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
