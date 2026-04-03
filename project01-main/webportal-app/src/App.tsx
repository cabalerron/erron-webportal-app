import { Router } from './router/Router';
import './App.css';
import { ThemeProvider } from '@emotion/react';
import theme from './common/themeConfig';


function App() {
  return (
    <>

      <ThemeProvider theme={theme}>
        <Router />
      </ThemeProvider>

    </>
  );
}

export default App;
