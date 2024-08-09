import { createStore as _createStore } from 'vuex';
import axios from 'axios';

export function createStore(currentToken, currentUser) {
  let store = _createStore({
    state: {
      token: currentToken || '',
      user: currentUser || {},
      reviews: []
    },
    mutations: {
      SET_AUTH_TOKEN(state, token) {
        state.token = token;
        localStorage.setItem('token', token);
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      },
      SET_USER(state, user) {
        state.user = user;
        localStorage.setItem('user', JSON.stringify(user));
      },
      LOGOUT(state) {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        state.token = '';
        state.user = {};
        axios.defaults.headers.common = {};
      },
      SET_REVIEWS(state, reviews) {
        state.reviews = reviews;
      },
      UPDATE_REVIEW(state, updatedReview) {
        const index = state.reviews.findIndex(review => review.review_id === updatedReview.review_id);
        if (index !== -1) {
          state.reviews.splice(index, 1, updatedReview);
        }
      },
      DELETE_REVIEW(state, review_id) {
        state.reviews = state.reviews.filter(review => review.review_id !== review_id);
      }
    },
    actions: {
      fetchReviews({ commit }, user_id) {
        return axios.get(`http://localhost:9000/user/reviews/${user_id}`)
          .then(response => {
            commit('SET_REVIEWS', response.data);
          });
      },
      updateReview({ commit }, updatedReview) {
        return axios.put('http://localhost:9000/user/reviews', updatedReview)
          .then(response => {
            commit('UPDATE_REVIEW', updatedReview);
          });
      },
      deleteReview({ commit }, review_id) {
        return axios.delete('http://localhost:9000/user/reviews', {
          data: { review_id }
        })
          .then(() => {
            commit('DELETE_REVIEW', review_id);
          });
      }
    },
  });
  return store;
}
