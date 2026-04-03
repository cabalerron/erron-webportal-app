/*
 * RM001
 *
 * v 00.001 - 10/28/2024
 *
 * PIC: emonteverde
 * 
 */
import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { Role } from '../types/DataType';

interface RoleState {
  roleList: Role[];
  totalItems: number;
  selectedItems: number[];
}

const initialState: RoleState = {
  roleList: [],
  totalItems: 0,
  selectedItems: [],
};

const roleSlice = createSlice({
  name: 'role',
  initialState,
  reducers: {
    setRole(state, action: PayloadAction<{ roleList: Role[], totalItems: number }>) {
      state.roleList = action.payload.roleList;
      state.totalItems = action.payload.totalItems;
    },
    resetRole(state) {
      state.roleList = [];
      state.totalItems = 0;
      state.selectedItems = [];
    },
    selectItems(state, action: PayloadAction<number[]>) { // Change to number[]
      state.selectedItems = action.payload;
    },
    deleteMultiItems(state) {
      // Filter out the selected items using the roleId property
      state.roleList = state.roleList.filter((item) => 
        !state.selectedItems.includes(item.roleId) // Use roleId here
      );
      state.selectedItems = []; // Clear selected items after deletion
      state.totalItems = state.roleList.length; // Update totalItems
    },

    }
});

export const { setRole, resetRole, selectItems, deleteMultiItems } = roleSlice.actions;
export default roleSlice.reducer;
