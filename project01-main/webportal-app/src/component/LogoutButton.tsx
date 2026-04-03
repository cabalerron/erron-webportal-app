import * as React from 'react';
import MenuItem from '@mui/material/MenuItem';
import apiAxiosConfig from '../common/apiAxiosConfig';

interface LogoutButtonProps {
  handleClose: () => void;
}

const LogoutButton: React.FC<LogoutButtonProps> = ({ handleClose }) => {
  const handleLogout = async () => {
    try {
      const response = await apiAxiosConfig.post('/public/logout');
      console.log(response.data);
      localStorage.removeItem("isAuthenticated");
      window.location.href = "/";
    } catch (error) {
      console.error('Logout failed', error);
    } finally {
      handleClose();
    }
  };

  return (
    <MenuItem onClick={handleLogout}>Logout</MenuItem>
  );
};

export default LogoutButton;
