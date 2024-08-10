import axios from 'axios';

const API_URL = 'http://localhost:9000/';

export default {
  retrieveGames(page) {
    return axios.get(`https://api.rawg.io/api/games?key=916f7023c320440daebd11eb40e66e2e&page=${page}&page_size=12`);
  },

  searchGames(page, searchTerm) {
    return axios.get(`https://api.rawg.io/api/games?key=916f7023c320440daebd11eb40e66e2e&page=${page}&page_size=12&search=${searchTerm}`);
  },

  getGameDetails(gameId) {
    return axios.get(`https://api.rawg.io/api/games/${gameId}?key=916f7023c320440daebd11eb40e66e2e`);
  },

  getGameReviews(gameId) {
    return axios.get(`${API_URL}reviews/${gameId}`);
  },

  addReview(reviewData) {
    console.log("POST request data:", reviewData);
    return axios.post(`${API_URL}reviews`, reviewData)
      .then(response => {
        console.log("POST request successful:", response.data);
        return response;
      })
      .catch(error => {
        console.error("POST request error:", error);
        throw error;
      });
  },

  addRating(ratingData) {
    console.log("POST request data:", ratingData);
    return axios.post(`${API_URL}rating`, ratingData)
      .then(response => {
        console.log("POST request successful:", response.data);
        return response;
      })
      .catch(error => {
        console.error("POST request error:", error);
        throw error;
      });
  },

  fetchUsers() {
    return axios.get('/admin')
  },
};
