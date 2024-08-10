<template>
    <div class="game-details">
      <div class="banner-container">
        <img
          :src="
            game.background_image ||
            'https://via.placeholder.com/1200x400.png?text=No+Image+Available'
          "
          alt="Game banner"
          class="game-banner"
        />
      </div>
      <div class="details-content">
        <h1>{{ game.name || "Unknown Title" }}</h1>
        <p class="game-genres">Genres: {{ formattedGenres }}</p>
        <p class="game-rating">Rating: {{ game.rating || "N/A" }}</p>
  
        <div v-if="isUserLoggedIn" class="add-to-collection">
          <button @click="addToCollection(1)" class="btn btn-primary">
            Add to Wishlist
          </button>
          <button @click="addToCollection(2)" class="btn btn-primary">
            Add to Playing
          </button>
          <button @click="addToCollection(3)" class="btn btn-primary">
            Add to Played
          </button>
        </div>
  
        <!-- Reviews Section -->
        <div class="reviews-container">
          <h2>Reviews</h2>
          <div
            v-if="Array.isArray(reviews) && reviews.length > 0"
            class="reviews-scrollable"
          >
            <div class="reviews-section">
              <ReviewCard
                v-for="review in reviews"
                :key="review.review_id"
                :review="review"
                @review-updated="fetchReviews"
              />
            </div>
          </div>
          <div v-else-if="!loading" class="no-reviews-message">
            No reviews found.
          </div>
  
          <div v-if="isUserLoggedIn" class="add-review-rating-buttons">
            <button @click="openReviewForm" class="btn btn-primary">
              Add Review
            </button>
            <button @click="openRatingForm" class="btn btn-primary">
              Add Rating
            </button>
          </div>
        </div>
      </div>
  
      <!-- Rating Form Modal -->
      <div v-if="showRatingForm" class="rating-form-modal">
        <div class="rating-form-content">
          <h3>Submit Your Rating</h3>
          <div class="rating-flames">
            <i
              v-for="flame in 5"
              :key="flame"
              :class="['bi', 'bi-fire', { 'filled': flame <= hoverRating || flame <= newRating }]"
              @mouseover="hoverRating = flame"
              @mouseleave="hoverRating = 0"
              @click="newRating = flame"
            ></i>
          </div>
          <button @click="submitRating" class="btn btn-primary">Submit</button>
          <button @click="closeRatingForm" class="btn btn-secondary">Cancel</button>
        </div>
      </div>
  
      <!-- Review Form Modal -->
      <div v-if="showReviewForm" class="review-form-modal">
        <div class="review-form-content">
          <h3>Submit Your Review</h3>
          <input
            v-model="newReviewTitle"
            placeholder="Review Title"
            class="review-title-input"
          />
          <textarea
            v-model="newReviewContent"
            placeholder="Write your review here."
            rows="4"
          ></textarea>
          <button @click="submitReview" class="btn btn-primary">Submit</button>
          <button @click="closeReviewForm" class="btn btn-secondary">
            Cancel
          </button>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import { mapState } from "vuex";
  import GameService from "../services/GameService";
  import CollectionService from "../services/CollectionService.js";
  import ReviewCard from "./ReviewCard.vue";
  
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
        newReviewContent: "",
        showRatingForm: false,
        newRating: null,
        hoverRating: 0,
      };
    },
    computed: {
      ...mapState(["token", "user"]),
      isUserLoggedIn() {
        return !!this.token;
      },
      formattedGenres() {
        return Array.isArray(this.game.genres) && this.game.genres.length > 0
          ? this.game.genres.map((genre) => genre.name).join(", ")
          : "N/A";
      },
    },
    mounted() {
      this.fetchGameDetails();
    },
    methods: {
      fetchGameDetails() {
        const game_id = this.$route.params.gameId;
        GameService.getGameDetails(game_id)
          .then((response) => {
            this.game = response.data;
            return this.fetchReviews();
          })
          .catch((error) => {
            console.error("Error fetching game details or reviews:", error);
            this.loading = false;
          });
        console.log("Game details fetched for game ID:", game_id);
      },
      fetchReviews() {
        const game_id = this.$route.params.gameId;
        GameService.getGameReviews(game_id)
          .then((response) => {
            this.reviews = Array.isArray(response.data) ? response.data : [];
            this.loading = false;
          })
          .catch((error) => {
            console.error("Error fetching reviews:", error);
            this.loading = false;
          });
      },
      addToCollection(collection_id) {
        const genre =
          this.game.genres && this.game.genres.length > 0
            ? this.game.genres[0].name
            : "Unknown Genre";
  
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
            console.error(
              `Error adding game to ${collection_id} collection`,
              error
            );
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
            return this.fetchReviews();
          })
          .then(() => {
            this.closeReviewForm();
          })
          .catch((error) => {
            console.error("Error submitting review:", error);
          });
      },
      openRatingForm() {
        this.showRatingForm = true;
      },
      closeRatingForm() {
        this.showRatingForm = false;
        this.newRating = null;
        this.hoverRating = 0;
      },
      submitRating() {
        if (this.newRating === null) {
          alert("Please provide a rating.");
          return;
        }
  
        const ratingData = {
          rating_score: this.newRating,
          user_id: this.$store.state.user.id,
          game_id: this.$route.params.gameId,
          game_title: this.game.name,
        };
  
        console.log("Submitting rating:", ratingData);
  
        GameService.addRating(ratingData)
          .then(() => {
            console.log("Rating submitted successfully");
            this.closeRatingForm();
            this.fetchGameDetails();
          })
          .catch((error) => {
            console.error("Error submitting rating:", error);
          });
      },
    },
  };
  </script>
  
  <style scoped>
  .game-details {
    background-color: #121212;
    color: #e0e0e0;
    text-align: center;
    min-height: 100vh;
    display: flex;
    flex-direction: column;
  }
  
  .add-review-rating-buttons {
    margin-top: 20px;
    display: flex;
    justify-content: center;
    gap: 20px;
  }
  
  .banner-container {
    position: relative;
    width: 100%;
    height: 400px;
    overflow: hidden;
    margin-bottom: 20px;
    flex-shrink: 0;
  }
  
  .game-banner {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
  
  .details-content {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
    box-sizing: border-box;
    flex-grow: 1;
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
    background-color: inherit;
  }
  
  .reviews-scrollable {
    max-height: 400px;
    overflow-y: auto;
    width: 100%;
    display: flex;
    justify-content: center;
    scrollbar-width: thin;
    scrollbar-color: #4a4a4a #121212;
  }
  
  .reviews-scrollable::-webkit-scrollbar {
    width: 8px;
  }
  
  .reviews-scrollable::-webkit-scrollbar-track {
    background: #121212;
  }
  
  .reviews-scrollable::-webkit-scrollbar-thumb {
    background-color: #4a4a4a;
    border-radius: 10px;
    border: 2px solid transparent;
  }
  
  .reviews-scrollable::-webkit-scrollbar-thumb:hover {
    background-color: #5c5c5c;
  }
  
  .reviews-scrollable::-webkit-scrollbar-corner,
  .reviews-scrollable::-webkit-scrollbar-button {
    display: none;
  }
  
  .reviews-section {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    gap: 20px;
    margin-top: 20px;
    width: 100%;
    max-width: 1200px;
  }
  
  .no-reviews-message {
    font-size: 1.2rem;
    color: #888;
    text-align: center;
    margin: 20px 0;
  }
  
  .review-form-modal,
  .rating-form-modal {
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
  
  .review-form-content,
  .rating-form-content {
    background: #fff;
    color: #000;
    padding: 20px;
    border-radius: 8px;
    width: 80%;
    max-width: 600px;
    text-align: left;
  }
  
  .review-form-content h3,
  .rating-form-content h3 {
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
  
  .rating-flames {
    display: flex;
    justify-content: center;
    margin-bottom: 15px;
  }
  
  .rating-flames .bi {
    font-size: 2rem;
    color: #ccc;
    cursor: pointer;
    margin: 0 5px;
    transition: color 0.3s;
  }
  
  .rating-flames .bi.filled {
    color: #f39c12;
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
  