import axios from 'axios';

export default {
  fetchUsers() {
    return axios.get('/admin');
  },

  deleteUser(id) {
    return axios.delete('/admin', { data: { id } });
  },

  fetchUserCollections(collection_id, user_id) {
    return axios.get('http://localhost:9000/admin/user/collections', {
      params: {
        collection_id,
        user_id
      }
    });
  },

  fetchUserReviews(user_id) {
    return axios.get('http://localhost:9000/admin/user/reviews', {
      params: {
        user_id
      }
    });
  }
};
