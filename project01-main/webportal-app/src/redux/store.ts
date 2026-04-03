import { configureStore } from '@reduxjs/toolkit';
import { persistStore, persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage';
import newsReducer from './NewsReducer';
import paramUtilReducer from './ParamUtilReducer';
import calendarReducer from "./CalendarReducer";
import roleReducer from './RoleReducer';
import userReducer from './UserReducer';
import functionReducer from './FunctionReducer';

const paramPersistConfig = {
  key: 'params',
  storage,
};

const persistedParamUtilReducer = persistReducer(paramPersistConfig, paramUtilReducer);

const store = configureStore({
  reducer: {
    params: persistedParamUtilReducer,
    news: newsReducer,
    calendar: calendarReducer,
    role: roleReducer,
    user: userReducer,
    function: functionReducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
        serializableCheck: {
            ignoredActions: ['persist/PERSIST', 'persist/REHYDRATE'],
            ignoredPaths: ['params.register'],
        },
    }),
});

export const persistor = persistStore(store);
export type RootState = ReturnType<typeof store.getState>;
export default store;
