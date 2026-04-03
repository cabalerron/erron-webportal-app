// Router.js
import { FC } from "react";
import { Route, Routes } from "react-router-dom";
import { Login } from "../pages/LGN001";
import { Home } from "../pages/HM001";
import { NewsList } from "../pages/NM001";
import { NewsAdd } from "../pages/NM002";
import { NewsEdit } from "../pages/NM003";
import { NewsDelete } from "../pages/NM004";
import { NewsScreen } from "../pages/NM005";
import { UserList } from "../pages/UM001";
import { UserAdd } from "../pages/UM002";
import { UserEdit } from "../pages/UM003";
import { UserDelete } from "../pages/UM004";
import { UserPassChange } from "../pages/UM005";
import { RoleList } from "../pages/RM001";
import { RoleAdd } from "../pages/RM002";
import { RoleEdit } from "../pages/RM003";
import { RoleDelete } from "../pages/RM004";
import { EmailInput } from "../pages/PW001";
import { ForgotPass } from "../pages/PW002";
import PrivateRoute from "./AuthCheck";
import { routes } from '../common/appConstant'; // Ensure this path is correct

export const Router: FC = () => {
  return (
    <Routes>
      {/* Landing Page */}
      <Route path={routes.LOGIN} element={<Login />} />
      <Route path={routes.LGN001} element={<Login />} />

      {/* Home Pages */}
      <Route path={routes.HM001} element={<PrivateRoute element={<Home />} />} />

      {/* News Pages */}
      <Route path={routes.NM001} element={<PrivateRoute element={<NewsList />} />} />
      <Route path={routes.NM002} element={<PrivateRoute element={<NewsAdd />} />} />
      <Route path={routes.NM003} element={<PrivateRoute element={<NewsEdit />} />} />
      <Route path={routes.NM004} element={<PrivateRoute element={<NewsDelete />} />} />
      <Route path={routes.NM005} element={<PrivateRoute element={<NewsScreen />} />} />

      {/* User Pages */}
      <Route path={routes.UM001} element={<PrivateRoute element={<UserList />} />} />
      <Route path={routes.UM002} element={<PrivateRoute element={<UserAdd />} />} />
      <Route path={routes.UM003} element={<PrivateRoute element={<UserEdit />} />} />
      <Route path={routes.UM004} element={<PrivateRoute element={<UserDelete />} />} />
      <Route path={routes.UM005} element={<PrivateRoute element={<UserPassChange />} />} />

      {/* Role Pages */}
      <Route path={routes.RM001} element={<PrivateRoute element={<RoleList />} />} />
      <Route path={routes.RM002} element={<PrivateRoute element={<RoleAdd />} />} />
      <Route path={routes.RM003} element={<PrivateRoute element={<RoleEdit />} />} />
      <Route path={routes.RM004} element={<PrivateRoute element={<RoleDelete />} />} />

      {/* Password Recovery Pages */}
      <Route path={routes.PW001} element={<EmailInput />} />
      <Route path={routes.PW002} element={<ForgotPass />} />
    </Routes>
  );
};
