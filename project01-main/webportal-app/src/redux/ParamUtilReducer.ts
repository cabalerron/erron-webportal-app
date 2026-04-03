import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface ParamUtilState {
    param01: string;
    param02: string;
    itemId: number | null; // Change to number | null
}


const initialState: ParamUtilState = {
    param01: '', // Sample user ID
    param02: '',     // Sample Role ID
    itemId: null,     // Initialize to null or 0
};

const paramUtilSlice = createSlice({
    name: 'params',
    initialState,
    reducers: {
        setParams: (state, action: PayloadAction<{ param01: string; param02: string }>) => {
            state.param01 = action.payload.param01;
            state.param02 = action.payload.param02;
        },
        setItemId: (state, action: PayloadAction<number | null>) => { 
            state.itemId = action.payload;
        },
    },
});

export const { setParams, setItemId } = paramUtilSlice.actions;
export default paramUtilSlice.reducer;
