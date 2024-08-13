<template>
  <div class="admin-user-profile">
    <h1 class="admin-user-heading">User Profile</h1>
    <div v-if="user">
      <h2 class="admin-user-heading">Username: {{ user.username }}</h2>
      <!-- <p class="admin-user-heading"><span>ROLE: </span> {{ user.authorities[0].name }}</p>
      <h3 class="admin-user-heading">{{ user.id }}</h3> -->

      <div class="dashboard-content">
        <div class="collections-container">
          <h2 class="admin-user-heading">Collections</h2>
          <div class="dashboard">
            <Card 
              :key="'wishlist'" 
              title="Wishlist" 
              :items="wishlistItems" 
              @refreshCollections="fetchCollections"
            />
            <Card 
              :key="'playing'" 
              title="Playing" 
              :items="playingItems" 
              @refreshCollections="fetchCollections"
            />
            <Card 
              :key="'played'" 
              title="Played" 
              :items="playedItems" 
              @refreshCollections="fetchCollections"
            />
          </div>
        </div>

        <!-- Ratings Section -->
        <div class="ratings-container">
          <h2 class="admin-user-heading">Ratings</h2>
          <div v-if="userRatings.length > 0" class="ratings-scrollable">
            <RatingCard
              v-for="rating in userRatings"
              :key="rating.rating_id"
              :rating="rating"
              :fetch-username="fetchUsername"
            />
          </div>
          <p v-else class="no-ratings-message">No ratings found.</p>
        </div>

        <!-- Reviews Section -->
        <div class="reviews-container">
          <h2 class="admin-user-heading">Reviews</h2>
          <div v-if="Array.isArray(reviews) && reviews.length > 0" class="reviews-scrollable">
            <div class="reviews-section">
              <ReviewCard
                v-for="review in reviews"
                :key="review.review_id"
                :review="review"
              />
            </div>
          </div>
          <div v-else-if="!loadingReviews" class="no-reviews-message">No reviews found.</div>
        </div>

        <!-- Comments Section -->
        <div class="comments-container">
          <h2 class="admin-user-heading">Comments</h2>
          <div v-if="userComments.length > 0" class="comments-scrollable">
            <div v-for="comment in userComments" :key="comment.comment_id" class="comment-card">
              <p class="comment-text">{{ comment.comment_text }}</p>
              <p class="comment-author">Commented on Review ID: {{ comment.review_id }}</p>
            </div>
          </div>
          <p v-else class="no-comments-message">No comments found.</p>
        </div>
      </div>
    </div>
    <div v-else>
      <p class="loading-message">Loading user information...</p>
    </div>
  </div>
</template>

<script>
import Card from "@/components/Card.vue";
import ReviewCard from "@/components/ReviewCard.vue";
import RatingCard from "@/components/RatingCard.vue";
import adminService from "../services/AdminService";
import GameService from "../services/GameService";

export default {
  components: {
    Card,
    ReviewCard,
    RatingCard,
  },
  data() {
    return {
      user: null,
      allItems: [],
      reviews: [],
      userRatings: [],
      userComments: [],
      loadingReviews: true,
    };
  },
  computed: {
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
    const userId = parseInt(this.$route.params.userId, 10);
    this.fetchUserDetails(userId);
  },
  methods: {
    fetchUserDetails(userId) {
      adminService.fetchUsers()
        .then(response => {
          this.user = response.data.find(user => user.id === userId);
          this.fetchCollections(userId);
          this.fetchUserReviews(userId);
          this.fetchUserRatings(userId);
          this.fetchUserComments(userId);
        })
        .catch(error => {
          console.error("Error fetching user details:", error);
        });
    },
    fetchCollections(userId) {
      Promise.all([
        adminService.fetchUserCollections(1, userId),
        adminService.fetchUserCollections(2, userId),
        adminService.fetchUserCollections(3, userId),
      ])
        .then(responses => {
          this.allItems = [
            ...responses[0].data,
            ...responses[1].data,
            ...responses[2].data,
          ];
        })
        .catch(error => {
          console.error("Error retrieving collections", error);
        });
    },
    fetchUserReviews(userId) {
      adminService.fetchUserReviews(userId)
        .then(response => {
          this.reviews = response.data;
          this.loadingReviews = false;
        })
        .catch(error => {
          console.error("Error retrieving user reviews:", error);
          this.loadingReviews = false;
        });
    },
    fetchUserRatings(userId) {
      GameService.getUserRatings(userId)
        .then(response => {
          this.userRatings = response.data;
        })
        .catch(error => {
          console.error("Error retrieving user ratings:", error);
        });
    },
    fetchUserComments(userId) {
      GameService.getCommentsForSpecificUser(userId)
        .then(response => {
          this.userComments = response.data;
        })
        .catch(error => {
          console.error("Error retrieving user comments:", error);
        });
    },
    fetchUsername(userId) {
      return this.user && this.user.id === userId ? this.user.username : "Anonymous";
    }
  }
};
</script>

<style scoped>
.admin-user-profile {
  padding: 20px;
  color: white; 
}
.dashboard-content {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  padding: 20px;
  color: white; 
}
.dashboard {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  margin-bottom: 40px;
  color: white; 
}
.ratings-container, .comments-container, .reviews-container, .collections-container {
  margin-top: 40px;
  border-top: 2px solid #444;
  padding-top: 20px;
  background-color: inherit;
  color: white;
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
.comments-scrollable, .reviews-scrollable {
  max-height: 400px;
  overflow-y: auto;
  width: 100%;
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;
}
.reviews-section {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
  margin-top: 20px;
  width: 100%;
  max-width: 1200px;
}
.no-ratings-message, .no-comments-message, .no-reviews-message, .loading-message {
  font-size: 1.2rem;
  color: white;
  text-align: center;
  margin: 20px 0;
}
.comment-card, .rating-card, .review-card {
  background-color: #1e1e1e;
  border: 1px solid #333;
  border-radius: 8px;
  padding: 16px;
  color: #e0e0e0;
  margin-bottom: 10px;
  width: 100%;
  max-width: 600px;
}
.admin-user-heading {
  color: white;
  text-align: center;
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
</style>
