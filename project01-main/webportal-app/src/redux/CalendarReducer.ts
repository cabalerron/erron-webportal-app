import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface calendarState {
  currentDate: number; 
}

const initialState: calendarState = {
  currentDate: Date.now(), 
};

const calendarSlice = createSlice({
  name: "calendar",
  initialState,
  reducers: {
    setDate: (state, action: PayloadAction<number>) => {
      state.currentDate = action.payload; 
    },
  },
});

export const { setDate } = calendarSlice.actions;
export default calendarSlice.reducer;
