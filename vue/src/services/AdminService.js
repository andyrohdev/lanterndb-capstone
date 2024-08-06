import axios from 'axios';

export default {

  fetchUsers() {
    return axios.get('/admin')
  },

  deleteUser(id) {
    return axios.delete('/admin', { data: { id } })
  }

}
