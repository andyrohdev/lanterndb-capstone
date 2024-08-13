<template>
  <div class="game-details">
    <!-- Banner and Details -->
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
      <div class="ratings-container">
        <p class="game-rating">Critic's Rating: {{ game.rating || "N/A" }}</p>
        <p class="lantern-db-rating">
          Lantern DB Rating: {{ lanternDbRating || "N/A" }}
        </p>
      </div>

      <!-- Add to Collection Buttons -->
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

      <!-- LanternDB Ratings Section -->
      <div class="lantern-db-ratings-container">
        <h2>LanternDB Ratings</h2>
        <div v-if="lanternDbRatings.length > 0" class="lantern-db-ratings-scrollable">
          <RatingCard
            v-for="rating in lanternDbRatings"
            :key="rating.rating_id"
            :rating="rating"
            @ratingUpdated="fetchLanternDbRatings"
            @ratingDeleted="fetchLanternDbRatings"
            :fetch-username="fetchUsername"
          />
        </div>
        <p v-else class="no-ratings-message">No LanternDB Ratings available.</p>

        <!-- Add Rating Button -->
        <div v-if="isUserLoggedIn" class="add-rating-button">
          <button @click="openRatingForm" class="btn btn-primary">
            Add Rating
          </button>
        </div>
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
        <div v-if="isUserLoggedIn" class="add-review-button">
          <button @click="openReviewForm" class="btn btn-primary">
            Add Review
          </button>
        </div>
      </div>
    </div>

    <!-- Rating Form Modal -->
    <div v-if="showRatingForm" class="rating-form-modal">
      <div class="rating-form-content">
        <h3>
          {{ editingRatingId ? "Edit Your Rating" : "Submit Your Rating" }}
        </h3>
        <div class="rating-flames">
          <i
            v-for="flame in 5"
            :key="flame"
            :class="[
              'bi',
              'bi-fire',
              { filled: flame <= hoverRating || flame <= newRating },
            ]"
            @mouseover="hoverRating = flame"
            @mouseleave="hoverRating = 0"
            @click="newRating = flame"
          ></i>
        </div>
        <button @click="submitRating" class="btn btn-primary">
          {{ editingRatingId ? "Update" : "Submit" }}
        </button>
        <button @click="closeRatingForm" class="btn btn-secondary">
          Cancel
        </button>
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
import RatingCard from "./RatingCard.vue";

export default {
  components: {
    ReviewCard,
    RatingCard,
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
      lanternDbRatings: [],
      lanternDbRating: null,
      users: {},
      editingRatingId: null,
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
    this.fetchLanternDbRatings();
    this.fetchUsers();
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
    fetchLanternDbRatings() {
      const game_id = this.$route.params.gameId;
      GameService.getLanternDbRatings(game_id)
        .then((response) => {
          this.lanternDbRatings = response.data;
          this.lanternDbRating = this.calculateAverageRating(response.data);
        })
        .catch((error) => {
          console.error("Error fetching Lantern DB ratings:", error);
        });
    },
    calculateAverageRating(ratings) {
      if (!ratings.length) return null;
      const total = ratings.reduce(
        (sum, rating) => sum + rating.rating_score,
        0
      );
      return (total / ratings.length).toFixed(1);
    },
    fetchUsers() {
      GameService.fetchUsers()
        .then((response) => {
          response.data.forEach((user) => {
            this.users[user.id] = user.username;
          });
        })
        .catch((error) => {
          console.error("Error fetching users:", error);
        });
    },
    fetchUsername(userId) {
      return this.users[userId] || "Anonymous";
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
    openRatingForm(rating) {
      this.showRatingForm = true;
      this.editingRatingId = rating ? rating.rating_id : null;
      this.newRating = rating ? rating.rating_score : null;
    },
    closeRatingForm() {
      this.showRatingForm = false;
      this.newRating = null;
      this.hoverRating = 0;
      this.editingRatingId = null;
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
        game_title: this.game.name, // Include the game title in the payload
      };

      if (this.editingRatingId) {
        // PUT request for editing an existing rating
        ratingData.rating_id = this.editingRatingId;
        GameService.updateRating(ratingData)
          .then(() => {
            this.fetchLanternDbRatings();
            this.closeRatingForm();
          })
          .catch((error) => {
            console.error("Error updating rating:", error);
          });
      } else {
        // POST request for adding a new rating
        GameService.addRating(ratingData)
          .then(() => {
            this.fetchLanternDbRatings();
            this.closeRatingForm();
          })
          .catch((error) => {
            console.error("Error submitting rating:", error);
          });
      }
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
  object-position: top; /* This aligns the image to display the top half */
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

.game-genres {
  font-size: 1.5rem;
  margin: 5px 0;
  font-weight: 300;
}

.ratings-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 10px 0;
  gap: 20px; /* Ensure consistent space between ratings */
}

.game-rating,
.lantern-db-rating {
  font-size: 1.5rem;
  margin: 0;
  font-weight: 300;
  line-height: 1.5;
  vertical-align: middle;
  display: inline-block;
}

.add-to-collection {
  margin-top: 20px;
}

.btn-primary {
  margin: 5px;
}

.add-rating-button {
  margin-top: 10px;
  display: flex;
  justify-content: center;
}

.lantern-db-ratings-container {
  margin-top: 20px;
  border-top: 2px solid #444;
  padding-top: 20px;
}

.lantern-db-ratings-scrollable {
  display: flex;
  overflow-x: auto;
  padding: 10px;
  scrollbar-width: thin;
  scrollbar-color: #4a4a4a #121212;
}

.lantern-db-ratings-scrollable::-webkit-scrollbar {
  height: 8px;
}

.lantern-db-ratings-scrollable::-webkit-scrollbar-track {
  background: #121212;
}

.lantern-db-ratings-scrollable::-webkit-scrollbar-thumb {
  background-color: #4a4a4a;
  border-radius: 10px;
  border: 2px solid transparent;
}

.lantern-db-ratings-scrollable::-webkit-scrollbar-thumb:hover {
  background-color: #5c5c5c;
}

.no-ratings-message {
  font-size: 1.2rem;
  color: #888;
  text-align: center;
  margin-top: 10px;
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

.rating-flames .bi {
  font-size: 2rem;
  color: #ccc;
  cursor: pointer;
  margin: 0 2px;
  transition: color 0.3s;
}

.rating-flames .bi.filled {
  color: #f39c12;
}

@media (max-width: 768px) {
  h1 {
    font-size: 2.5rem;
  }

  .game-genres,
  .game-rating,
  .lantern-db-rating {
    font-size: 1.2rem;
  }
}
</style>
