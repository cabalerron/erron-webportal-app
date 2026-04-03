import React, { useState } from 'react';
import { AppBar, Toolbar, Button, Typography } from '@mui/material';
import SettingsMenu from './SettingsMenu';
import HomeIcon from '@mui/icons-material/Home';


interface PanelHeaderProps {
  showBackButton: boolean; // Indicates whether to show the back button
  onBackClick: () => void; // Function to call when back button is clicked
  title: string; // The title to display in the header
}

const PanelHeader: React.FC<PanelHeaderProps> = ({ showBackButton, onBackClick, title }) => {
  const [menuSettings, setMenuSettings] = useState<null | HTMLElement>(null);

  const handleSettingsClick = (event: React.MouseEvent<HTMLElement>) => {
    setMenuSettings(event.currentTarget);
  };

  const handleClose = () => {
    setMenuSettings(null);
  };

  return (
    <>
      {/* First Header: Back Button and Settings */}
      <AppBar position="static" sx={{ backgroundColor: 'transparent', color: '#01579b', boxShadow: 'none' }}>
        <Toolbar sx={{ display: 'flex', justifyContent: 'space-between', padding: '0px 5px', minHeight: '30px' }}>
          <Typography variant="h6" sx={{ flexGrow: 1, color: '#01579b', fontWeight: 'bold' }}>
            {showBackButton && (
             <Button color="inherit" onClick={onBackClick} sx={{ textTransform: 'none', fontSize: '15px' }}>
                <HomeIcon sx={{ textTransform: 'none', fontSize: '50px' }}/>
              </Button>
            )}
          </Typography>
          <Button color="inherit" onClick={handleSettingsClick} sx={{ textTransform: 'none', fontSize: '15px' }}>
            Settings
          </Button>
        </Toolbar>
      </AppBar>

      {/* Second Header: Title */}
      <AppBar position="static" sx={{ backgroundColor: '#01579b', boxShadow: '0px 2px 4px rgba(0,0,0,0.2)' }}>
        <Toolbar sx={{ padding: '10px 24px' }}>
          <Typography variant="h4" sx={{ flexGrow: 1, color: 'white' }}>
            {title}
          </Typography>
        </Toolbar>
      </AppBar>

      <SettingsMenu menuSettings={menuSettings} handleClose={handleClose} />
    </>
  );
};

export default PanelHeader;
