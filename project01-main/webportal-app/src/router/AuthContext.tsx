import React, { FC, createContext, useContext, useState, useEffect, ReactNode } from "react";
import apiAxiosConfig from '../common/apiAxiosConfig';
import { routes } from '../common/appConstant';

interface AuthContextType {
  isAuthenticated: boolean;
  login: () => void;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

interface AuthProviderProps {
  children: ReactNode;
}

export const AuthProvider: FC<AuthProviderProps> = ({ children }) => {
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(() => {
    const savedAuth = localStorage.getItem("isAuthenticated");
    return savedAuth ? JSON.parse(savedAuth) : false;
  });

  useEffect(() => {
    const checkAuthentication = async () => {
      try {
        const response = await apiAxiosConfig.get('/public/check-auth');

        if (response.status === 200) {
          setIsAuthenticated(true);
          localStorage.setItem("isAuthenticated", "true");
        } else {
          setIsAuthenticated(false);
          localStorage.removeItem("isAuthenticated");
        }
      } catch (error) {
        console.error("Error checking authentication:", error);
        setIsAuthenticated(false);
        localStorage.removeItem("isAuthenticated");
      }
    };

    const pathsToCheck = [routes.LOGIN, routes.LGN001, routes.PW001, routes.PW002];

    if (!pathsToCheck.includes(window.location.pathname)) {
      checkAuthentication();
    }
  }, []);

  useEffect(() => {
    localStorage.setItem("isAuthenticated", JSON.stringify(isAuthenticated));
  }, [isAuthenticated]);

  const login = () => setIsAuthenticated(true);
  const logout = () => setIsAuthenticated(false);

  return (
    <AuthContext.Provider value={{ isAuthenticated, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = (): AuthContextType => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
};
