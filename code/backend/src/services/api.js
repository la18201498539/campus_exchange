import axios from 'axios';

const API_URL = 'http://localhost:8080/api/'; // 替换为你的后端API URL

const api = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// 请求拦截器，用于添加token到请求头
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers['Authorization'] = 'Bearer ' + token;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default {
  register(user) {
    return api.post('user/register', user);
  },
  login(accountNumber, password) {
    return api.post('user/login', { accountNumber, password });
  },
  forgotPassword(accountNumber) {
    return api.post('user/forgot-password', { accountNumber });
  },
  resetPassword(token, newPassword) {
    return api.post('user/reset-password', { token, newPassword });
  },
  getUserProfile() {
    return api.get('user/profile');
  },
  updateUserProfile(user) {
    return api.put('user/profile', user);
  },
};
