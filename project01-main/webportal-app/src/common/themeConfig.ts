import { createTheme } from '@mui/material/styles';

// Define your theme configuration
const theme = createTheme({
  palette: {
    primary: {
      main: '#01579b', 
    },

    background: {
      default: '#f5f5f5',
    },
  },
  typography: {
    fontFamily: 'Roboto, Arial, sans-serif',
    h4: {
      fontWeight: 600,
      color: '#fff', 
    },
    body1: {
      color: '#333',
    },
  },
  components: {
    MuiAppBar: {
      styleOverrides: {
        root: {
          '&.transparent': {
            backgroundColor: 'transparent',
            color: '#01579b',
            boxShadow: 'none',
          },
          '&.primary-header': {
            backgroundColor: '#01579b',
            boxShadow: '0px 2px 4px rgba(0, 0, 0, 0.2)',
          }
        },
      },
    },
    MuiToolbar: {
      styleOverrides: {
        root: {
          display: 'flex',
          justifyContent: 'space-between',
          padding: '0px 5px',
          minHeight: '30px',
          '&.primary-toolbar': {
            padding: '10px 24px',
          },
        },
      },
    },
    MuiButton: {
      styleOverrides: {
        root: {
          textTransform: 'none',
          fontSize: '15px',
          // backgroundColor: '#f50057',
        },
      },
    },
    MuiTypography: {
      styleOverrides: {
        h4: {
          padding: '3px',
          backgroundColor: '#01579b',
        },
      },
    },
  },
});

export default theme;
