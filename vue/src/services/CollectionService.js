import axios from 'axios';

const API_URL = 'http://localhost:9000/';

export default {
  getCollections(collectionId) {
    return axios.get(`${API_URL}collections/${collectionId}`);
  },
  
  addToCollections({ user_id, title, genre, collection_id }) {
    return axios.post(`${API_URL}collections`, { user_id, title, genre, collection_id });
  }
};
