import axios from 'axios';

const apiAxiosConfig = axios.create({
    baseURL: '/', 
    headers: {
        'Content-Type': 'application/json', 
    },
});

export default apiAxiosConfig;