import axios from 'axios';

const API_URL = 'http://localhost:9000/';

export default {
  getCollections(collectionId) {
    return axios.get(`${API_URL}collections/${collectionId}`);
  },
  
  addToCollections(gameData) {
    console.log("POST request data:", gameData);
    return axios.post(`${API_URL}collections`, gameData)
      .then(response => {
        console.log("POST request successful:", response.data);
        return response;
      })
      .catch(error => {
        console.error("POST request error:", error);
        throw error;
      });
  }
};
