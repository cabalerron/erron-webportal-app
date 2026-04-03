import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter as Router } from "react-router-dom";
import { Provider } from 'react-redux';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { AuthProvider } from "./router/AuthContext";
import store from './redux/store';  // Import the Redux store

// Add store to window object for debugging purposes (if needed)
(window as any).store = store;

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);

root.render(
  <Provider store={store}>  {/* Wrap App with the Provider to make the store available throughout the app */}
    <React.StrictMode>
      <AuthProvider>
        <Router>
          <App />
        </Router>
      </AuthProvider>
    </React.StrictMode>
  </Provider>
);

// Optional: Measure performance in your app
reportWebVitals();