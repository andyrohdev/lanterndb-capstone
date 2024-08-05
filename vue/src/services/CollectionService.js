import axios from 'axios';

const API_URL = 'http://localhost:9000/';

export default {

  getCollections(collectionId) {
    return axios.get(`${API_URL}collections/${collectionId}`);
  },

  addToCollections(collectionId, userId, title, genre) {
    return axios.post(`${API_URL}collections/${collectionId}`, {userId, title, genre});
  },
  updateCollection(collectionId, gameId, updatedGame) {
    return axios.put(`${API_URL}collections/${collection}/games/${gameId}`, updatedGame);
  },

  deleteFromCollections(collectionId, gameId) {
    return axios.delete(`${API_URL}collections/${collection}/games/${gameId}`);
  }
  // Add Game title and Game genre

};

