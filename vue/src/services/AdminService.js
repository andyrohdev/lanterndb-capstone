import axios from 'axios';

export default {

  fetchUsers() {
    return axios.get('/admin')
  },

  deleteUser(id) {
    return axios.delete('/admin', { data: { id } })
  },

  fetchUserCollections(collection_id, user_id) {
    axios.get('http://localhost:8080/api/resource', {
        params: {
          user_id,
          collection_id
        }
      })
      .then(response => {
        console.log('Response from server:', response.data);
      })
      .catch(error => {
        console.error('Error:', error);
      });
  }

}
