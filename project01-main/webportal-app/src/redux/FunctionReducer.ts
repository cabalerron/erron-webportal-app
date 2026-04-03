import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { Function } from '../types/DataType';

interface FunctionState {
  functionList: Function[];
  selectedItems: number[];
}

const initialState: FunctionState = {
  functionList: [],
  selectedItems: [],
};

const functionSlice = createSlice({
  name: 'function',
  initialState,
  reducers: {
    setFunction(state, action: PayloadAction<{ functionList: Function[]}>) {
      state.functionList = action.payload.functionList;
    },
    resetFunction(state) {
      state.functionList = [];
      state.selectedItems = [];
    },
    selectItems(state, action: PayloadAction<number[]>) { 
      state.selectedItems = action.payload;
    },
    }
});

export const { setFunction, resetFunction, selectItems } = 
      functionSlice.actions;
export default functionSlice.reducer;
