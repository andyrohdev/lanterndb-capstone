import axios from 'axios';

const API_URL = 'http://localhost:9000/';

export default {

 getCollections (collectionId) {
        return axios.get(`${API_URL}collections/${collectionId}`);
      },

};

 