import * as React from 'react';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { DateCalendar } from '@mui/x-date-pickers/DateCalendar';
import { useSelector, useDispatch } from 'react-redux';
import { RootState } from '../redux/store';
import { setDate } from '../redux/CalendarReducer';
import dayjs, { Dayjs } from 'dayjs';
import { Box } from '@mui/material';
import { Time } from './Time';

export const Calendar: React.FC = () => {
    const currentDate = useSelector((state: RootState) => state.calendar.currentDate);
    const dispatch = useDispatch();

    const handleDateChange = (newValue: Dayjs | null) => {
        if (newValue) {
            dispatch(setDate(newValue.toDate().getTime())); // Dispatch as timestamp
        }
    };

    return (
        <LocalizationProvider dateAdapter={AdapterDayjs}>
            <Box sx={{ maxWidth: '600px', margin: '0 auto' }}>
                <h1 style={{ textAlign: 'center' }}>
                    <Time />
                </h1>
                <DateCalendar
                    value={dayjs(currentDate)} // Convert currentDate to Dayjs
                    onChange={handleDateChange}
                    sx={{
                        '& .MuiPickersDay-root': {
                            width: '40px',  // Adjust day cell width
                            height: '40px', // Adjust day cell height
                            display: 'flex',
                            alignItems: 'center',
                            justifyContent: 'center',
                        },
                        '& .MuiPickersDay-day': {
                            flexGrow: 1,
                        },
                    }}
                />
            </Box>
        </LocalizationProvider>
    );
};
