<template>
    <div class="review-card">
      <h3 class="review-title">{{ review.review_title }}</h3>
      <p class="game-title">Game: {{ gameTitle }}</p>
      <p class="review-text">{{ review.review_text }}</p>
      <p class="review-username">Reviewed by: {{ username }}</p>
  
      <div v-if="isReviewAuthor" class="review-actions">
        <button @click="toggleEditModal" class="btn btn-secondary">Edit</button>
        <button @click="deleteReview" class="btn btn-danger">Delete</button>
      </div>
  
      <!-- Edit Review Modal -->
      <div v-if="editModalVisible" class="edit-modal">
        <div class="review-form-content">
          <h3>Edit Your Review</h3>
          <input
            v-model="editTitle"
            placeholder="Review Title"
            class="review-title-input"
          />
          <textarea
            v-model="editText"
            placeholder="Write your review here."
            rows="4"
          ></textarea>
          <button @click="submitEdit" class="btn btn-primary">
            Save Changes
          </button>
          <button @click="toggleEditModal" class="btn btn-secondary">
            Cancel
          </button>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import { mapActions } from 'vuex';
  import GameService from "../services/GameService";
  
  export default {
    props: {
      review: Object,
    },
    data() {
      return {
        username: "Loading...",
        gameTitle: "Loading...",
        editModalVisible: false,
        editTitle: "",
        editText: "",
      };
    },
    computed: {
      isReviewAuthor() {
        const user = this.$store.state.user;
        return this.review.user_id === user.id;
      },
    },
    mounted() {
      this.fetchUsername(this.review.user_id);
      this.fetchGameTitle(this.review.game_id);
      this.resetEditForm();
    },
    methods: {
      ...mapActions(['updateReview']),
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
      fetchGameTitle(gameId) {
        GameService.getGameDetails(gameId)
          .then((response) => {
            this.gameTitle = response.data.name || "Unknown Game";
          })
          .catch((error) => {
            console.error("Error fetching game title:", error);
            this.gameTitle = "Unknown Game";
          });
      },
      toggleEditModal() {
        this.editModalVisible = !this.editModalVisible;
        this.resetEditForm();
      },
      resetEditForm() {
        this.editTitle = this.review.review_title;
        this.editText = this.review.review_text;
      },
      submitEdit() {
        console.log("Submit edit for review:", this.review.review_id);
        const updatedReview = {
          review_id: this.review.review_id,
          game_id: this.review.game_id,
          review_title: this.editTitle,
          review_text: this.editText,
          user_id: this.review.user_id
        };
  
        this.updateReview(updatedReview)
          .then(() => {
            console.log("Review updated successfully");
            this.editModalVisible = false;
            this.$emit('review-updated');
            console.log('review emitted');
          })
          .catch(error => {
            console.error("Error updating review:", error);
          });
      },
      deleteReview() {
        console.log("Delete review:", this.review.review_id);
        // Logic to handle deleting the review
      },
    },
  };
  </script>
  
  
  
  
  <style scoped>
  .review-card {
    background-color: #1e1e1e;
    border: 1px solid #333;
    border-radius: 8px;
    padding: 16px;
    color: #e0e0e0;
    margin: 16px;
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
  
  .game-title {
    font-size: 1rem;
    margin-bottom: 10px;
    font-weight: bold;
  }
  
  .review-actions {
    margin-top: 10px;
  }
  
  .btn-secondary,
  .btn-danger,
  .btn-primary {
    margin-right: 10px;
    color: #fff;
  }
  
  .edit-modal {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
  }
  
  .review-form-content {
    background: #fff;
    color: #000;
    padding: 20px;
    border-radius: 8px;
    width: 80%;
    max-width: 600px;
    text-align: left;
  }
  
  .review-form-content input,
  .review-form-content textarea {
    width: 100%;
    margin-bottom: 10px;
  }
  
  .review-title-input {
    margin-bottom: 10px;
  }
  
  .review-form-content h3 {
    margin-top: 0;
  }
  
  input,
  textarea {
    display: block;
    width: 100%;
    margin-bottom: 10px;
  }
  </style>
  