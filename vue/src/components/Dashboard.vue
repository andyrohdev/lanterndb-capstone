<template>
  <div class="dashboard-container">
    <div class="header"></div>
    <div class="dashboard-content">
      <h2>Collections</h2>
      <div class="dashboard">
        <Card :key="'wishlist'" title="Wishlist" :items="wishlistItems" />
        <Card :key="'playing'" title="Playing" :items="playingItems" />
        <Card :key="'played'" title="Played" :items="playedItems" />
      </div>
      <div class="reviews-container">
        <h2>Reviews</h2>
        <div v-if="Array.isArray(reviews) && reviews.length > 0" class="reviews-section">
          <ReviewCard v-for="review in reviews" :key="review.review_id" :review="review" />
        </div>
        <div v-else-if="!loadingReviews" class="no-reviews-message">No reviews found.</div>
      </div>
    </div>
  </div>
</template>

<script>
import Card from "@/components/Card.vue";
import ReviewCard from "@/components/ReviewCard.vue";
import CollectionService from "../services/CollectionService";
import GameService from "../services/GameService";

export default {
  components: {
    Card,
    ReviewCard,
  },
  data() {
    return {
      allItems: [],
      reviews: [],
      loadingReviews: true,
    };
  },
  computed: {
    wishlistItems() {
      return this.allItems.filter(item => item.collection_id === 1);
    },
    playingItems() {
      return this.allItems.filter(item => item.collection_id === 2);
    },
    playedItems() {
      return this.allItems.filter(item => item.collection_id === 3);
    },
  },
  created() {
    this.fetchCollections();
    this.fetchUserReviews();
  },
  methods: {
    fetchCollections() {
      Promise.all([
        CollectionService.getCollections(1),
        CollectionService.getCollections(2),
        CollectionService.getCollections(3)
      ])
      .then((responses) => {
        this.allItems = [
          ...responses[0].data,
          ...responses[1].data,
          ...responses[2].data
        ];
      })
      .catch((error) => {
        console.error("Error retrieving collections", error);
      });
    },
    fetchUserReviews() {
      const user_id = this.$store.state.user.id;
      console.log("Fetching reviews for user ID:", user_id);
      GameService.getReviewsForUser(user_id)
        .then((response) => {
          console.log("Reviews data received:", response.data);
          this.reviews = response.data;
          this.loadingReviews = false;
        })
        .catch((error) => {
          console.error("Error retrieving user reviews:", error);
          this.loadingReviews = false;
        });
    }
  }
};
</script>

<style scoped>
.dashboard-container {
  min-height: 100vh; /* Full height of the viewport */
  display: flex;
  flex-direction: column;
  background: #121212; /* Dark background like GameDetails */
  color: #e0e0e0; /* Light text for contrast */
}

.dashboard-content {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  padding: 20px;
}

.dashboard {
  display: flex;
  flex-wrap: wrap; /* Allows cards to wrap onto the next line */
  justify-content: space-around;
}

.reviews-container {
  margin-top: 40px;
  border-top: 2px solid #444;
  padding-top: 20px;
}

.reviews-section {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
  margin-top: 20px;
}

.no-reviews-message {
  font-size: 1.2rem;
  color: #888;
  text-align: center;
  margin: 20px 0;
}

@media (max-width: 768px) {
  .dashboard {
    flex-direction: column;
    align-items: center;
  }
  .reviews-section {
    grid-template-columns: 1fr; /* Switch to a single column on smaller screens */
  }
}
</style>
