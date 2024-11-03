import axios from 'axios';

/**
 * axios function encapsulation
 *
 * @author Yingtong Zhou
 *
 */

const service = axios.create({
    timeout: 5000,
    baseURL: 'http://8.221.118.240:8080',
    withCredentials: true
});

// request interceptor
service.interceptors.request.use(
    (config) => {
        return config;
    },
    (error) => {
        console.log(error);
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
        console.log(error);
        return Promise.reject();
    }
);

export default service;
