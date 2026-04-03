import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { News } from '../types/DataType';

interface NewsState {
  newsList: News[];
  totalItems: number;
  selectedItems: number[];
}

const initialState: NewsState = {
  newsList: [],
  totalItems: 0,
  selectedItems: [],
};

const newsSlice = createSlice({
  name: 'news',
  initialState,
  reducers: {
    setNews(state, action: PayloadAction<{ newsList: News[], totalItems: number }>) {
      state.newsList = action.payload.newsList;
      state.totalItems = action.payload.totalItems;
    },
    resetNews(state) {
      state.newsList = [];
      state.totalItems = 0;
      state.selectedItems = [];
    },
    selectItems(state, action: PayloadAction<number[]>) { // Change to number[]
      state.selectedItems = action.payload;
    },
    deleteMultiItems(state) {
      // Filter out the selected items using the newsId property
      state.newsList = state.newsList.filter((item) => 
        !state.selectedItems.includes(item.newsId) // Use newsId here
      );
      state.selectedItems = []; // Clear selected items after deletion
      state.totalItems = state.newsList.length; // Update totalItems
    },
  },
});

export const { setNews, resetNews, selectItems, deleteMultiItems } = newsSlice.actions;

export default newsSlice.reducer;
