import axios from 'axios';

import {BASE_API_URL} from '../data/constants';
import {isExpiredToken, updateAccessToken} from '../utils/token';

const api = axios.create({
    baseURL: BASE_API_URL,
    withCredentials: true,
});

api.interceptors.request.use(function(config) {
    if (isExpiredToken('access')) {
        updateAccessToken();
    }

    return config;
});

export default api;
