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
        <!-- Add button to add to collection -->
        <div v-if="isUserLoggedIn" class="add-to-collection">
          <button @click="addToCollection(1)" class="btn btn-primary">Add to Wishlist</button>
          <button @click="addToCollection(2)" class="btn btn-primary">Add to Playing</button>
          <button @click="addToCollection(3)" class="btn btn-primary">Add to Played</button>
        </div>
  
        <!-- Reviews Section -->
        <div class="reviews-container">
          <h2>Reviews</h2>
          <div v-if="Array.isArray(reviews) && reviews.length > 0" class="reviews-section">
            <div v-for="review in reviews" :key="review.review_id" class="review">
              <p><strong>Anonymous:</strong> {{ review.review_title }} - {{ review.review_text }}</p>
            </div>
          </div>
          <div v-else-if="!loading" class="no-reviews-message">No reviews found.</div>
  
          <!-- Add Review Button -->
          <div v-if="isUserLoggedIn" class="add-review-button">
            <button @click="openReviewForm" class="btn btn-primary">Add Review</button>
          </div>
        </div>
      </div>
  
      <!-- Review Form Modal -->
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
import { ref, onMounted, computed } from "vue";
import { useRoute } from "vue-router";
import { useStore } from "vuex";
import GameService from "../services/GameService";

export default {
  setup() {
    const store = useStore();
    const route = useRoute();
    const game = ref({
      genres: [],
      background_image: "",
      name: "",
      rating: "N/A",
    });
    const reviews = ref([]);
    const isUserLoggedIn = computed(() => !!store.state.token);
    const loading = ref(true);
    const showReviewForm = ref(false);
    const newReviewTitle = ref("");
    const newReviewContent = ref("");

    const formattedGenres = computed(() => {
      return Array.isArray(game.value.genres) && game.value.genres.length > 0
        ? game.value.genres.map((genre) => genre.name).join(", ")
        : "N/A";
    });

    onMounted(() => {
      const game_id = route.params.gameId;
      GameService.getGameDetails(game_id)
        .then((response) => {
          console.log("Game details response:", response.data);
          game.value = response.data;
          return GameService.getGameReviews(game_id);
        })
        .then((response) => {
          console.log("Reviews response:", response.data);
          reviews.value = Array.isArray(response.data) ? response.data : []; // Adjust based on actual response structure
          loading.value = false;
        })
        .catch((error) => {
          console.error("Error fetching game details or reviews:", error);
          loading.value = false;
        });
    });

    function openReviewForm() {
      showReviewForm.value = true;
    }

    function closeReviewForm() {
      showReviewForm.value = false;
      newReviewTitle.value = "";
      newReviewContent.value = "";
    }

    function submitReview() {
      if (!newReviewTitle.value.trim() || !newReviewContent.value.trim()) {
        alert("Please provide both a title and content for your review.");
        return;
      }

      const reviewData = {
        game_id: route.params.gameId,
        review_title: newReviewTitle.value,
        review_text: newReviewContent.value,
      };

      GameService.addReview(reviewData)
        .then(() => {
          // Reload reviews after adding a new review
          return GameService.getGameReviews(route.params.gameId);
        })
        .then((response) => {
          reviews.value = Array.isArray(response.data) ? response.data : []; // Adjust based on actual response structure
          closeReviewForm();
        })
        .catch((error) => {
          console.error("Error submitting review:", error);
        });
    }

    return {
      game,
      reviews,
      formattedGenres,
      isUserLoggedIn,
      loading,
      showReviewForm,
      newReviewTitle,
      newReviewContent,
      openReviewForm,
      closeReviewForm,
      submitReview,
    };
  },
};
</script>

  
  
  <style scoped>
.game-details {
  background-color: #121212;
  color: #e0e0e0;
  text-align: center;
  padding: 20px;
  height: 100vh;
  overflow-y: auto; /* Make sure content is scrollable if needed */
  display: flex;
  flex-direction: column;
}

.banner-container {
  position: relative;
  width: 100%;
  max-height: 400px;
  overflow: hidden;
  margin-bottom: 20px;
}

.game-banner {
  width: 100%;
  height: auto;
  object-fit: cover;
}

.details-content {
  max-width: 800px;
  margin: 0 auto;
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
  flex: 1; /* Allow reviews container to take up available space */
}

.reviews-section {
  margin-top: 20px;
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