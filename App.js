import axios from 'axios';

const API = axios.create({
  baseURL: 'http://localhost:8080/api',
});

// Auto-attach JWT token to every request
API.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Auth
export const login = (data) => API.post('/auth/login', data);
export const register = (data) => API.post('/auth/register', data);

// Accounts
export const getAccounts = () => API.get('/accounts');
export const createAccount = (data) => API.post('/accounts/create', data);
export const getAccount = (id) => API.get(`/accounts/${id}`);

// Transactions
export const deposit = (data) => API.post('/transactions/deposit', data);
export const withdraw = (data) => API.post('/transactions/withdraw', data);
export const transfer = (data) => API.post('/transactions/transfer', data);
export const getAccountTransactions = (accountId) => API.get(`/transactions/account/${accountId}`);

export default API;
