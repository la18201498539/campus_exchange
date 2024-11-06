import axios from 'axios';

/**
 * axios function encapsulation
 *
 * @author Yingtong Zhou
 *
 * Date: 2024/11/3
 */

const service = axios.create({
    timeout: 5000,
    baseURL: 'http://47.252.36.46:8080',
    withCredentials: true,
    timeoutErrorMessage: 'The request took too long, please try again later.'
});

// request interceptor
service.interceptors.request.use(
    (config) => {
        console.log('Request URL:', config.baseURL + config.url);
        console.log('Request Data:', config.data);
        return config;
    },
    (error) => {
        console.log('Request Error:', error);
        return Promise.reject();
    }
);

// response interceptor
service.interceptors.response.use(
    (response) => {
        if (response.status === 200) {
            return response.data;
        } else {
            Promise.reject();
        }
    },
    (error) => {
        console.log('Response Error:', error);
        return Promise.reject();
    }
);

export default service;
