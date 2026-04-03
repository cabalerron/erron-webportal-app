import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { Users } from '../types/DataType';

interface UserState {
  userList: Users[];
  totalItems: number;
  selectedItems: string[];
}

const initialState: UserState = {
  userList: [],
  totalItems: 0,
  selectedItems: [],
};

const userSlice = createSlice({
  name: 'users',
  initialState,
  reducers: {
    setUser(state, action: PayloadAction<{ userList: Users[], totalItems: number }>) {
      state.userList = action.payload.userList;
      state.totalItems = action.payload.totalItems;
    },
    resetUser(state) {
      state.userList = [];
      state.totalItems = 0;
      state.selectedItems = [];
    },
    selectItems(state, action: PayloadAction<string[]>) {
      state.selectedItems = action.payload;
    },
    deleteMultiItems(state) {
      state.userList = state.userList.filter(item => !state.selectedItems.includes(item.accountId));
      state.selectedItems = [];
      state.totalItems = state.userList.length;
    },
  },
});

export const { setUser, resetUser, selectItems, deleteMultiItems } = userSlice.actions;
export default userSlice.reducer;
