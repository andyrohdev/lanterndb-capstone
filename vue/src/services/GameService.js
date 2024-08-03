import axios from 'axios';

export default {

  retrieveGames() {
    return axios.get('/games')
  }

}
