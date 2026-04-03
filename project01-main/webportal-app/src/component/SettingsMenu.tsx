// SettingsMenu.tsx
import * as React from 'react';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import { useNavigate } from 'react-router-dom';
import LogoutButton from './LogoutButton';

interface SettingsMenuProps {
  menuSettings: null | HTMLElement;
  handleClose: () => void;
}

const SettingsMenu: React.FC<SettingsMenuProps> = ({ menuSettings, handleClose }) => {
  const open = Boolean(menuSettings);
  const navigate = useNavigate();

  const handleNavigate = (path: string, params = {}) => {
    navigate(path, { state: { ...params } });
  };

  const handleEditUserInfo = () => {
    handleNavigate("/UM003");
    console.log("Edit User Info clicked");
    handleClose();
  };

  return (
    <Menu
      id="settings-menu"
      anchorEl={menuSettings}
      open={open}
      onClose={handleClose}
      MenuListProps={{
        'aria-labelledby': 'settings-button',
      }}
    >
      <MenuItem onClick={handleEditUserInfo}>Edit User Info</MenuItem>
      <LogoutButton handleClose={handleClose} />
    </Menu>
  );
};

export default SettingsMenu;
