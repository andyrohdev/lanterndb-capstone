<template>
    <div class="game-details">
      <div class="banner-container">
        <img
          :src="game.background_image || 'https://via.placeholder.com/1200x400.png?text=No+Image+Available'"
          alt="Game banner"
          class="game-banner"
        />
      </div>
      <div class="details-content">
        <h1>{{ game.name || 'Unknown Title' }}</h1>
        <p class="game-genres">Genres: {{ formattedGenres }}</p>
        <p class="game-rating">Rating: {{ game.rating || 'N/A' }}</p>
  
        <div v-if="isUserLoggedIn" class="add-to-collection">
          <button @click="addToCollection(1)" class="btn btn-primary">Add to Wishlist</button>
          <button @click="addToCollection(2)" class="btn btn-primary">Add to Playing</button>
          <button @click="addToCollection(3)" class="btn btn-primary">Add to Played</button>
        </div>
  
        <!-- Reviews Section -->
        <div class="reviews-container">
          <h2>Reviews</h2>
          <div v-if="Array.isArray(reviews) && reviews.length > 0" class="reviews-section">
            <ReviewCard
              v-for="review in reviews"
              :key="review.review_id"
              :review="review"
            />
          </div>
          <div v-else-if="!loading" class="no-reviews-message">No reviews found.</div>
  
          <div v-if="isUserLoggedIn" class="add-review-button">
            <button @click="openReviewForm" class="btn btn-primary">Add Review</button>
          </div>
        </div>
      </div>
  
      <div v-if="showReviewForm" class="review-form-modal">
        <div class="review-form-content">
          <h3>Submit Your Review</h3>
          <input v-model="newReviewTitle" placeholder="Review Title" class="review-title-input" />
          <textarea v-model="newReviewContent" placeholder="Write your review here..." rows="4"></textarea>
          <button @click="submitReview" class="btn btn-primary">Submit</button>
          <button @click="closeReviewForm" class="btn btn-secondary">Cancel</button>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import { mapState } from 'vuex';
  import GameService from "../services/GameService";
  import CollectionService from "../services/CollectionService.js";
  import ReviewCard from './ReviewCard.vue';
  
  export default {
    components: {
      ReviewCard,
    },
    data() {
      return {
        game: {
          genres: [],
          background_image: "",
          name: "",
          rating: "N/A",
        },
        reviews: [],
        loading: true,
        showReviewForm: false,
        newReviewTitle: "",
        newReviewContent: ""
      };
    },
    computed: {
      ...mapState(['token']),
      isUserLoggedIn() {
        return !!this.token;
      },
      formattedGenres() {
        return Array.isArray(this.game.genres) && this.game.genres.length > 0
          ? this.game.genres.map((genre) => genre.name).join(", ")
          : "N/A";
      }
    },
    mounted() {
      const game_id = this.$route.params.gameId;
      GameService.getGameDetails(game_id)
        .then((response) => {
          this.game = response.data;
          return GameService.getGameReviews(game_id);
        })
        .then((response) => {
          this.reviews = Array.isArray(response.data) ? response.data : [];
          this.loading = false;
        })
        .catch((error) => {
          console.error("Error fetching game details or reviews:", error);
          this.loading = false;
        });
    },
    methods: {
      addToCollection(collection_id) {
        const genre = this.game.genres && this.game.genres.length > 0
          ? this.game.genres[0].name
          : 'Unknown Genre';
  
        const gameData = {
          title: this.game.name,
          genre: genre,
          collection_id,
        };
  
        CollectionService.addToCollections(gameData)
          .then((response) => {
            console.log(`Game added to ${collection_id} collection`, response);
          })
          .catch((error) => {
            console.error(`Error adding game to ${collection_id} collection`, error);
          });
      },
      openReviewForm() {
        this.showReviewForm = true;
      },
      closeReviewForm() {
        this.showReviewForm = false;
        this.newReviewTitle = "";
        this.newReviewContent = "";
      },
      submitReview() {
        if (!this.newReviewTitle.trim() || !this.newReviewContent.trim()) {
          alert("Please provide both a title and content for your review.");
          return;
        }
  
        const reviewData = {
          game_id: this.$route.params.gameId,
          review_title: this.newReviewTitle,
          review_text: this.newReviewContent,
        };
  
        GameService.addReview(reviewData)
          .then(() => {
            return GameService.getGameReviews(this.$route.params.gameId);
          })
          .then((response) => {
            this.reviews = Array.isArray(response.data) ? response.data : [];
            this.closeReviewForm();
          })
          .catch((error) => {
            console.error("Error submitting review:", error);
          });
      }
    }
  };
  </script>
  
  <style scoped>
  .game-details {
  background-color: #121212;
  color: #e0e0e0;
  text-align: center;
  /* Removed fixed height */
  min-height: 100vh; /* Ensures the background color covers at least the viewport height */
  display: flex;
  flex-direction: column;
}

.banner-container {
  position: relative;
  width: 100%;
  height: 400px; /* Fixed height for the banner */
  overflow: hidden;
  margin-bottom: 20px;
  flex-shrink: 0; /* Prevent shrinking */
}

.game-banner {
  width: 100%;
  height: 100%; /* Ensure the image covers the entire container */
  object-fit: cover;
}

.details-content {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  box-sizing: border-box;
  flex-grow: 1; /* Ensure this section grows to fill the remaining space */
}

h1 {
  font-size: 3rem;
  margin: 10px 0;
  font-weight: 700;
}

.game-genres,
.game-rating {
  font-size: 1.5rem;
  margin: 5px 0;
  font-weight: 300;
}

.add-to-collection {
  margin-top: 20px;
}

.btn-primary {
  margin: 5px;
}

.reviews-container {
  margin-top: 40px;
  border-top: 2px solid #444;
  padding-top: 20px;
  background-color: inherit; /* Ensure background color is consistent */
}

.reviews-section {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
  margin-top: 20px;
}

@media (max-width: 768px) {
  .reviews-section {
    grid-template-columns: 1fr; /* Switch to a single column on smaller screens */
  }
}

.review {
  margin: 10px 0;
}

.no-reviews-message {
  font-size: 1.2rem;
  color: #888;
  text-align: center;
  margin: 20px 0;
}

.add-review-button {
  margin-top: 20px;
}

.review-form-modal {
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

.review-form-content h3 {
  margin-top: 0;
}

.review-form-content input,
.review-form-content textarea {
  width: 100%;
  margin-bottom: 10px;
}

.review-title-input {
  margin-bottom: 10px;
}

.btn-primary {
  margin-right: 10px;
}

@media (max-width: 768px) {
  .game-details {
    padding: 10px;
  }

  h1 {
    font-size: 2.5rem;
  }

  .game-genres,
  .game-rating {
    font-size: 1.2rem;
  }
}

  </style>
  