import { Box } from '@mui/material';
import * as React from 'react';

export const Time: React.FC = () => {
    const [time, setTime] = React.useState(new Date().toLocaleTimeString());

    React.useEffect(() => {
        // Set the interval to update the time every second
        const intervalId = setInterval(() => {
            setTime(new Date().toLocaleTimeString());
        }, 1000);

        // Cleanup interval on component unmount
        return () => clearInterval(intervalId);
    }, []);

    return (
        <>
            <Box>
                {time}
            </Box>
        </>
    );
};