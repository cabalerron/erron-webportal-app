import React from "react";
import { Dialog, DialogTitle, DialogContent, DialogContentText, DialogActions, Button } from "@mui/material";

interface DialogComponentProps {
  show: boolean;
  dialogMessage: string;
  handleClose: () => void;
  confirmation?: boolean; 
  handleYes?: () => void;
  handleNo?: () => void;
}

const DialogComponent: React.FC<DialogComponentProps> = ({
  show,
  dialogMessage,
  handleClose,
  confirmation = false,
  handleYes,
  handleNo,
}) => {
  return (
    <Dialog
      open={show}
      onClose={handleClose}
      aria-labelledby="alert-dialog-title"
      aria-describedby="alert-dialog-description"
    >
      <DialogTitle id="alert-dialog-title">
        {confirmation ? "Confirmation" : "Dialog Message"}
      </DialogTitle>
      <DialogContent>
        <DialogContentText id="alert-dialog-description">
          {dialogMessage}
        </DialogContentText>
      </DialogContent>
      <DialogActions>
        {confirmation ? (
          // Render Yes/No buttons if it's a confirmation dialog
          <>
            <Button onClick={handleYes} color="primary" autoFocus>
              Yes
            </Button>
            <Button onClick={handleNo} color="primary">
              No
            </Button>
          </>
        ) : (
          <Button onClick={handleClose} autoFocus>
            OK
          </Button>
        )}
      </DialogActions>
    </Dialog>
  );
};

export default DialogComponent;
