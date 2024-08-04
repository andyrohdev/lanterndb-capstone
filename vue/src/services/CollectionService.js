import axios from 'axios';

const API_URL = 'http://localhost:9000/api';

export const getItems = () => {
  return axios.get(`${API_URL}/items`);
};