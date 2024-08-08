<template>
    <div class="review-card">
      <h3 class="review-title">{{ review.review_title }}</h3>
      <p class="review-text">{{ review.review_text }}</p>
      <p class="review-username">Reviewed by: {{ username }}</p>
    </div>
  </template>
  
  <script>
  import GameService from "../services/GameService";
  
  export default {
    props: {
      review: Object,
    },
    data() {
      return {
        username: "Loading...",
      };
    },
    mounted() {
      this.fetchUsername(this.review.user_id);
    },
    methods: {
      fetchUsername(userId) {
        GameService.fetchUsers()
          .then((response) => {
            const user = response.data.find((user) => user.id === userId);
            this.username = user ? user.username : "Anonymous";
          })
          .catch((error) => {
            console.error("Error fetching username:", error);
            this.username = "Anonymous";
          });
      },
    },
  };
  </script>
  
  <style scoped>
  .review-card {
    background-color: #1e1e1e;
    border: 1px solid #333;
    border-radius: 8px;
    padding: 15px;
    margin-bottom: 15px;
    color: #e0e0e0;
  }
  
  .review-title {
    font-size: 1.2rem;
    margin-bottom: 10px;
    font-weight: bold;
  }
  
  .review-text {
    font-size: 1rem;
    margin-bottom: 10px;
  }
  
  .review-username {
    font-size: 0.9rem;
    color: #888;
  }
  </style>
  