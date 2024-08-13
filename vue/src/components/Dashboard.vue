<template>
  <div class="dashboard-container d-flex" id="main">
    <!-- Sidebar -->
    <div :class="['sidebar', { hidden: !sidebarVisible }]">
      <button v-if="sidebarVisible" class="btn btn-outline-light sidebar-toggle" @click="toggleSidebar">
        <i class="bi bi-chevron-left"></i>
      </button>
      <button v-if="!sidebarVisible" class="btn btn-outline-light sidebar-expand-toggle" @click="toggleSidebar">
        <i class="bi bi-chevron-right"></i>
      </button>
      <nav class="sidebar-nav">
        <h4>Dashboard</h4>
        <ul class="nav flex-column">
          <li class="nav-item">
            <a class="nav-link text-light" href="#" @click="setActiveSection('collections')">
              <i class="bi bi-collection"></i>Collections
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link text-light" href="#" @click="setActiveSection('ratings')">
              <i class="bi bi-star"></i>Ratings
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link text-light" href="#" @click="setActiveSection('reviews')">
              <i class="bi bi-chat"></i>Reviews
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link text-light" href="#" @click="setActiveSection('comments')">
              <i class="bi bi-chat-dots"></i>Comments
            </a>
          </li>
        </ul>
      </nav>
    </div>

    <!-- Main Content -->
    <div :class="['dashboard-content', { 'with-sidebar': sidebarVisible }]">
      <!-- Collections Section -->
      <div v-if="activeSection === 'collections'" class="section-card">
        <h2>Collections</h2>
        <div class="collection-container">
          <Card :key="'wishlist'" title="Wishlist" :items="wishlistItems" />
          <Card :key="'playing'" title="Playing" :items="playingItems" />
          <Card :key="'played'" title="Played" :items="playedItems" />
        </div>
      </div>

      <!-- Ratings Section -->
      <div v-if="activeSection === 'ratings'" class="section-card">
        <h2>Your Ratings</h2>
        <div v-if="userRatings.length > 0" class="vertical-scrollable">
          <div class="dashboard-card">
            <div class="games-grid">
              <RatingCard
                v-for="rating in userRatings"
                :key="rating.rating_id"
                :rating="rating"
                :fetch-username="fetchUsername"
                @ratingUpdated="fetchUserRatings"
                @ratingDeleted="fetchUserRatings"
              />
            </div>
          </div>
        </div>
        <p v-else class="no-ratings-message">You haven't rated any games yet.</p>
      </div>

      <!-- Reviews Section -->
      <div v-if="activeSection === 'reviews'" class="section-card">
        <h2>Reviews</h2>
        <div v-if="Array.isArray(reviews) && reviews.length > 0" class="vertical-scrollable">
          <div class="dashboard-card">
            <div class="games-grid">
              <ReviewCard
                v-for="review in reviews"
                :key="review.review_id"
                :review="review"
                @review-updated="fetchUserReviews"
              />
            </div>
          </div>
        </div>
        <div v-else-if="!loadingReviews" class="no-reviews-message">
          No reviews found.
        </div>
      </div>

      <!-- Comments Section -->
      <div v-if="activeSection === 'comments'" class="section-card">
        <h2>Your Comments</h2>
        <div v-if="userComments.length > 0" class="vertical-scrollable">
          <div class="dashboard-card">
            <div class="games-grid">
              <div v-for="comment in userComments" :key="comment.comment_id" class="comment-card">
                <div class="card">
                  <div class="card-body">
                    <p class="comment-text">{{ comment.comment_text }}</p>
                    <p class="comment-author">Commented on Review ID: {{ comment.review_id }}</p>
                  </div>
                </div>
              </div>
            </div>
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
      sidebarVisible: true,
      activeSection: 'collections', // Track the currently active section
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
    toggleSidebar() {
      this.sidebarVisible = !this.sidebarVisible;
    },
    setActiveSection(section) {
      this.activeSection = section;
    },
  },
};
</script>

<style scoped>
#main {
  display: flex;
  flex-wrap: wrap;
}
.dashboard-container {
  min-height: 100vh;
  display: flex;
  color: white;
}

.sidebar {
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  width: 250px;
  background: #141413c5;
  padding: 20px;
  overflow-y: auto;
  transition: transform 0.3s ease;
  transform: translateX(0);
  margin-top: 80px;
}

.sidebar.hidden {
  transform: translateX(-250%);
}

.sidebar-toggle, .sidebar-expand-toggle {
  position: absolute;
  top: 3px;
  right: 10px;
  z-index: 1000;
  border: none;
  background: transparent;
  color: white;
  font-size: 24px;
  cursor: pointer;
}

.sidebar-toggle {
  right: 0px;
}

.sidebar-expand-toggle {
  left: 0px;
}

.sidebar-nav {
  margin-top: 50px;
}

.dashboard-content {
  margin-left: 250px;
  padding: 20px;
  flex-grow: 1;
  transition: margin-left 0.3s ease;
}

.dashboard-content.with-sidebar {
  margin-left: 250px;
}

.sidebar.hidden + .dashboard-content {
  margin-left: 0;
}
.sidebar-nav h4 {
  color: #ccc;
  font-size: 1.5rem;
  margin-bottom: 20px;
}

.nav {
  list-style: none;
  padding: 0;
  margin: 0;
}

.nav-item {
  margin-bottom: 10px;
}

.nav-link {
  display: flex;
  align-items: center;
  padding: 10px;
  border-radius: 5px;
  color: #fff;
  text-decoration: none;
  font-size: 1.1rem;
  transition: background-color 0.3s, color 0.3s;
}

.nav-link:hover,
.nav-link.active {
  background-color: #444;
  color: #fff;
}

/* Add icon styling if using icons */
.nav-link i {
  margin-right: 10px;
  font-size: 1.2rem;
}

/* Section Card for Consistency */
.section-card {
  margin-top: 40px;
  padding-top: 20px;
  background-color: inherit;
}

/* Vertical Scrollable for Ratings, Reviews, Comments */
.vertical-scrollable {
  max-height: 400px;
  overflow-y: auto;
  scrollbar-width: thin;
  scrollbar-color: #4a4a4a #121212;
  padding: 10px;
}

/* Styling the Scrollbar */
.vertical-scrollable::-webkit-scrollbar {
  width: 8px;
}

.vertical-scrollable::-webkit-scrollbar-track {
  background: #121212;
}

.vertical-scrollable::-webkit-scrollbar-thumb {
  background-color: #4a4a4a;
  border-radius: 10px;
  border: 2px solid transparent;
}

.vertical-scrollable::-webkit-scrollbar-thumb:hover {
  background-color: #5c5c5c;
}

/* Collection Container with Dynamic Expanding */
.collection-container {
  display: flex;
  flex-wrap: nowrap;
  overflow-x: auto;
  padding: 10px;
  scrollbar-width: thin;
  scrollbar-color: #4a4a4a #121212;
}

.collection-container::-webkit-scrollbar {
  height: 8px;
}

.collection-container::-webkit-scrollbar-track {
  background: #121212;
}

.collection-container::-webkit-scrollbar-thumb {
  background-color: #4a4a4a;
  border-radius: 10px;
  border: 2px solid transparent;
}

.collection-container::-webkit-scrollbar-thumb:hover {
  background-color: #5c5c5c;
}

.dashboard-card {
  background-color: #1e1e1e;
  border: 1px solid #333;
  border-radius: 8px;
  padding: 16px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  margin: 16px;
  width: 100%;
}

/* .games-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
  gap: 10px;
  margin-top: 20px;
} */

.comment-card .card,
.ratings-container .card,
.reviews-container .card,
.collection-container .card {
  background-color: #1e1e1e;
  border: 1px solid #333;
  border-radius: 8px;
  padding: 16px;
  color: #e0e0e0;
}

.no-ratings-message,
.no-reviews-message,
.no-comments-message {
  font-size: 1.2rem;
  color: #888;
  text-align: center;
  margin-top: 10px;
}

@media (max-width: 900px) {
  .dashboard-content.with-sidebar {
    margin-left: 0;
  }
  
  .sidebar {
    width: 100%;
    transform: none;
    z-index: 1050;
  }
  .sidebar-toggle, .sidebar-expand-toggle {
    right: 20px;
  }
}
</style>
