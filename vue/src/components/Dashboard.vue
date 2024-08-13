<template>
  <div class="dashboard-container">
    <div class="dashboard-content">
      <!-- Collections Section -->
      <h2>Collections</h2>
      <div class="dashboard">
        <Card :key="'wishlist'" title="Wishlist" :items="wishlistItems" />
        <Card :key="'playing'" title="Playing" :items="playingItems" />
        <Card :key="'played'" title="Played" :items="playedItems" />
      </div>

      <!-- Ratings Section -->
      <div class="ratings-container">
        <h2>Your Ratings</h2>
        <div v-if="userRatings.length > 0" class="ratings-scrollable">
          <RatingCard
            v-for="rating in userRatings"
            :key="rating.rating_id"
            :rating="rating"
            :fetch-username="fetchUsername"
            @ratingUpdated="fetchUserRatings"
            @ratingDeleted="fetchUserRatings"
          />
        </div>
        <p v-else class="no-ratings-message">You haven't rated any games yet.</p>
      </div>

      <!-- Reviews Section -->
      <div class="reviews-container">
        <h2>Reviews</h2>
        <div v-if="Array.isArray(reviews) && reviews.length > 0" class="reviews-scrollable">
          <div class="reviews-section">
            <ReviewCard
              v-for="review in reviews"
              :key="review.review_id"
              :review="review"
              @review-updated="fetchUserReviews"
            />
          </div>
        </div>
        <div v-else-if="!loadingReviews" class="no-reviews-message">
          No reviews found.
        </div>
      </div>

      <!-- Comments Section -->
      <div class="comments-container">
        <h2>Your Comments</h2>
        <div v-if="userComments.length > 0" class="comments-scrollable">
          <div v-for="comment in userComments" :key="comment.comment_id" class="comment-card">
            <p class="comment-text">{{ comment.comment_text }}</p>
            <p class="comment-author">Commented on Review ID: {{ comment.review_id }}</p>
            <!-- Add any additional comment details here -->
          </div>
        </div>
        <p v-else class="no-comments-message">You haven't commented on any reviews yet.</p>
      </div>
    </div>
  </div>
</template>

<script>
import Card from "@/components/Card.vue";
import ReviewCard from "@/components/ReviewCard.vue";
import RatingCard from "@/components/RatingCard.vue";
import CollectionService from "../services/CollectionService";
import GameService from "../services/GameService";
import { mapState, mapActions } from "vuex";

export default {
  components: {
    Card,
    ReviewCard,
    RatingCard,
  },
  data() {
    return {
      allItems: [],
      userRatings: [],
      userComments: [], // To store comments
      loadingReviews: true,
    };
  },
  computed: {
    ...mapState(["reviews", "user"]),
    wishlistItems() {
      return this.allItems.filter((item) => item.collection_id === 1);
    },
    playingItems() {
      return this.allItems.filter((item) => item.collection_id === 2);
    },
    playedItems() {
      return this.allItems.filter((item) => item.collection_id === 3);
    },
  },
  created() {
    this.fetchCollections();
    this.fetchUserRatings();
    this.fetchUserReviews();
    this.fetchUserComments(); // Fetch comments when the component is created
  },
  methods: {
    ...mapActions(["fetchReviews"]),
    fetchCollections() {
      Promise.all([
        CollectionService.getCollections(1),
        CollectionService.getCollections(2),
        CollectionService.getCollections(3),
      ])
        .then((responses) => {
          this.allItems = [
            ...responses[0].data,
            ...responses[1].data,
            ...responses[2].data,
          ];
        })
        .catch((error) => {
          console.error("Error retrieving collections", error);
        });
    },
    fetchUserRatings() {
      const userId = this.$store.state.user.id;
      GameService.getUserRatings(userId)
        .then((response) => {
          this.userRatings = response.data;
        })
        .catch((error) => {
          console.error("Error retrieving user ratings:", error);
        });
    },
    fetchUserReviews() {
      const user_id = this.$store.state.user.id;
      this.fetchReviews(user_id)
        .then(() => {
          this.loadingReviews = false;
        })
        .catch((error) => {
          console.error("Error retrieving user reviews:", error);
          this.loadingReviews = false;
        });
    },
    fetchUserComments() {
      const user_id = this.$store.state.user.id;
      GameService.getCommentsForSpecificUser(user_id)
        .then((response) => {
          this.userComments = response.data;
        })
        .catch((error) => {
          console.error("Error retrieving user comments:", error);
        });
    },
    fetchUsername(userId) {
      return this.user && this.user.id === userId ? this.user.username : "Anonymous";
    },
  },
};
</script>

<style scoped>
.dashboard-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background: #121212;
  color: #e0e0e0;
}

.body-dashboard {
  padding: 0;
  margin: 0;
  background-color: #121212;
}

.dashboard-content {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  padding: 20px;
}

.dashboard {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  margin-bottom: 40px;
}

/* Ratings Section */
.ratings-container {
  margin-top: 40px;
  border-top: 2px solid #444;
  padding-top: 20px;
  background-color: inherit;
}

.ratings-scrollable {
  display: flex;
  overflow-x: auto;
  padding: 10px;
  scrollbar-width: thin;
  scrollbar-color: #4a4a4a #121212;
}

.ratings-scrollable::-webkit-scrollbar {
  height: 8px;
}

.ratings-scrollable::-webkit-scrollbar-track {
  background: #121212;
}

.ratings-scrollable::-webkit-scrollbar-thumb {
  background-color: #4a4a4a;
  border-radius: 10px;
  border: 2px solid transparent;
}

.ratings-scrollable::-webkit-scrollbar-thumb:hover {
  background-color: #5c5c5c;
}

.no-ratings-message {
  font-size: 1.2rem;
  color: #888;
  text-align: center;
  margin-top: 10px;
}

/* Reviews Section */
.reviews-container {
  margin-top: 40px;
  border-top: 2px solid #444;
  padding-top: 20px;
  background-color: inherit;
  margin-bottom: 5%;
}

.reviews-scrollable {
  max-height: 400px;
  overflow-y: auto;
  width: 100%;
  display: flex;
  justify-content: center;
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

@media (max-width: 900px) {
  .dashboard {
    flex-direction: column;
    align-items: center;
  }

  .reviews-section {
    grid-template-columns: 1fr;
    max-width: 100%;
  }
}

.dashboard-content h2 {
  text-align: center;
  width: 100%;
}

.comments-container {
  margin-top: 40px;
  border-top: 2px solid #444;
  padding-top: 20px;
  background-color: inherit;
  margin-bottom: 5%;
}

.comments-scrollable {
  max-height: 400px;
  overflow-y: auto;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.comment-card {
  background-color: #1e1e1e;
  border: 1px solid #333;
  border-radius: 8px;
  padding: 16px;
  color: #e0e0e0;
  margin-bottom: 10px;
  width: 100%;
  max-width: 600px;
}

.comment-text {
  font-size: 1rem;
  margin-bottom: 5px;
}

.comment-author {
  font-size: 0.9rem;
  color: #888;
}

.no-comments-message {
  font-size: 1.2rem;
  color: #888;
  text-align: center;
  margin-top: 10px;
}
</style>
