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

  updateRating(ratingData) {
    return axios.put(`${API_URL}user/ratings`, ratingData)
      .then(response => {
        console.log("PUT request successful:", response.data);
        return response;
      })
      .catch(error => {
        console.error("PUT request error:", error);
        throw error;
      });
  },

  deleteRating(ratingData) {
    return axios({
      method: 'delete',
      url: `${API_URL}user/ratings`,
      data: ratingData,
    });
  },


  fetchUsers() {
    return axios.get('/admin');
  },

  getLanternDbRatings(gameId) {
    return axios.get(`${API_URL}ratings/${gameId}`);
  },

  getUserRatings(userId) {
    return axios.get(`${API_URL}user/ratings/${userId}`);
  },

  // Fetch comments for a specific review
  getComments(review_id) {
    return axios.get(`${API_URL}/comments`, {
      params: { review_id },
    });
  },

  // Add a new comment
  addComment(commentData) {
    return axios.post(`${API_URL}/comments`, commentData);
  },

  // Update an existing comment
  updateComment(commentData) {
    return axios.put(`${API_URL}/user/comments`, commentData);
  },

  // Delete a comment by its ID
  deleteComment(comment_id) {
    return axios.delete(`${API_URL}/comments/${comment_id}`);
  },
};
