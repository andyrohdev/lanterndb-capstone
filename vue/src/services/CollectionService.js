import axios from 'axios';

const API_URL = 'http://localhost:9000/';

export default {

  getCollections(collectionId) {
    return axios.get(`${API_URL}collections/${collectionId}`);
  },

  addToCollections(collectionId, userId, title, genre) {
    return axios.post(`${API_URL}collections/${collectionId}`, {userId, title, genre});
  }
  // Add Game title and Game genre

};

