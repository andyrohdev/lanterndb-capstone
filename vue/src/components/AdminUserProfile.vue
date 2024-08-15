<template>
  <div class="dashboard-container d-flex" id="main">
    <!-- Sidebar -->
    <div
      :class="['sidebar', { hidden: !sidebarVisible }]"
      @click="sidebarVisible = false"
    >
      <button
        class="btn btn-outline-light sidebar-toggle"
        @click.stop="toggleSidebar"
      >
        <i class="bi bi-chevron-left"></i>
      </button>
      <nav class="sidebar-nav">
        <h4>User Profile</h4>
        <ul class="nav flex-column">
          <li class="nav-item">
            <a
              class="nav-link text-light"
              href="#"
              @click="setActiveSection('collections')"
            >
              <i class="bi bi-collection"></i>Collections
            </a>
          </li>
          <li class="nav-item">
            <a
              class="nav-link text-light"
              href="#"
              @click="setActiveSection('ratings')"
            >
              <i class="bi bi-star"></i>Ratings
            </a>
          </li>
          <li class="nav-item">
            <a
              class="nav-link text-light"
              href="#"
              @click="setActiveSection('reviews')"
            >
              <i class="bi bi-chat"></i>Reviews
            </a>
          </li>
          <li class="nav-item">
            <a
              class="nav-link text-light"
              href="#"
              @click="setActiveSection('comments')"
            >
              <i class="bi bi-chat-dots"></i>Comments
            </a>
          </li>
        </ul>
      </nav>
    </div>

    <!-- Main Content -->
    <div :class="['dashboard-content', { 'with-sidebar': sidebarVisible }]">
      <div v-if="user">
        <!-- Collections Section -->
        <div v-if="activeSection === 'collections'" class="section-card">
          <div class="collections-header">
            <h2>Collections</h2>
          </div>
          <div class="collection-container">
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
        <div v-if="activeSection === 'ratings'" class="section-card">
          <h2>Ratings</h2>
          <div v-if="userRatings.length > 0" class="vertical-scrollable">
            <div class="games-grid ratings-grid">
              <RatingCard
                v-for="rating in userRatings"
                :key="rating.rating_id"
                :rating="rating"
                :fetch-username="fetchUsername"
              />
            </div>
          </div>
          <p v-else class="no-ratings-message">
            No ratings found.
          </p>
        </div>

        <!-- Reviews Section -->
        <div v-if="activeSection === 'reviews'" class="section-card">
          <h2>Reviews</h2>
          <div
            v-if="Array.isArray(reviews) && reviews.length > 0"
            class="vertical-scrollable"
          >
            <div class="games-grid reviews-grid">
              <ReviewCard
                v-for="review in reviews"
                :key="review.review_id"
                :review="review"
              />
            </div>
          </div>
          <div v-else-if="!loadingReviews" class="no-reviews-message">
            No reviews found.
          </div>
        </div>

        <!-- Comments Section -->
        <div v-if="activeSection === 'comments'" class="section-card">
          <h2>Comments</h2>
          <div v-if="userComments.length > 0" class="vertical-scrollable">
            <div class="games-grid comments-grid">
              <div
                v-for="comment in userComments"
                :key="comment.comment_id"
                class="comment-card"
              >
                <div class="card">
                  <div class="card-body">
                    <p class="comment-text">{{ comment.comment_text }}</p>
                    <p class="comment-author">
                      Commented on Review ID: {{ comment.review_id }}
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <p v-else class="no-comments-message">
            No comments found.
          </p>
        </div>
      </div>
      <div v-else>
        <p class="loading-message">Loading user information...</p>
      </div>
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
      sidebarVisible: true,
      activeSection: "collections",
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
        .then((response) => {
          this.user = response.data.find((user) => user.id === userId);
          this.fetchCollections(userId);
          this.fetchUserReviews(userId);
          this.fetchUserRatings(userId);
          this.fetchUserComments(userId);
        })
        .catch((error) => {
          console.error("Error fetching user details:", error);
        });
    },
    fetchCollections(userId) {
      Promise.all([
        adminService.fetchUserCollections(1, userId),
        adminService.fetchUserCollections(2, userId),
        adminService.fetchUserCollections(3, userId),
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
    fetchUserReviews(userId) {
      adminService.fetchUserReviews(userId)
        .then((response) => {
          this.reviews = response.data;
          this.loadingReviews = false;
        })
        .catch((error) => {
          console.error("Error retrieving user reviews:", error);
          this.loadingReviews = false;
        });
    },
    fetchUserRatings(userId) {
      GameService.getUserRatings(userId)
        .then((response) => {
          this.userRatings = response.data;
        })
        .catch((error) => {
          console.error("Error retrieving user ratings:", error);
        });
    },
    fetchUserComments(userId) {
      GameService.getCommentsForSpecificUser(userId)
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
/* Styles matching the Dashboard component */

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
  background: rgba(20, 20, 19, 0.9); /* Slight transparency */
  padding: 20px;
  overflow-y: auto;
  transform: translateX(0);
  transition: transform 0.3s ease;
  padding-top: 80px;
  z-index: 500; /* Ensure it's on top of the dashboard content */
}

.sidebar.hidden {
  transform: translateX(-80%);
}

.sidebar-toggle,
.sidebar-expand-toggle,
.sidebar-toggle-outside {
  position: absolute;
  top: 83px;
  border: none;
  background: transparent;
  color: white;
  font-size: 24px;
  cursor: pointer;
  z-index: 1060;
}

.sidebar-toggle {
  right: 0px;
}

.sidebar-expand-toggle {
  left: 0px;
}

.dashboard-content {
  padding: 20px;
  flex-grow: 1;
  transition: margin-left 0.3s ease, width 0.3s ease;
  margin-left: 250px;
}

.dashboard-content.with-sidebar {
  margin-left: 250px;
}

.sidebar.hidden + .dashboard-content {
  margin-left: 40px;
}

.sidebar-nav {
  margin-top: 50px;
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

.nav-link i {
  margin-right: 10px;
  font-size: 1.2rem;
}

/* Card Container for Ratings, Reviews, Comments */
.dashboard-card {
  background-color: #1e1e1e;
  border: 1px solid #333;
  border-radius: 8px;
  padding: 16px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  margin: 16px;
  width: 100%;
  max-height: 600px;
  overflow-y: auto;
  scrollbar-width: thin;
  scrollbar-color: #4a4a4a #121212;
}

.dashboard-card::-webkit-scrollbar {
  width: 8px;
}

.dashboard-card::-webkit-scrollbar-track {
  background: #121212;
}

.dashboard-card::-webkit-scrollbar-thumb {
  background-color: #4a4a4a;
  border-radius: 10px;
  border: 2px solid transparent;
}

.dashboard-card::-webkit-scrollbar-thumb:hover {
  background-color: #5c5c5c;
}

.games-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
  gap: 10px;
  margin-top: 20px;
}

.ratings-grid {
  grid-template-columns: repeat(3, 1fr);
}

.reviews-grid {
  grid-template-columns: repeat(2, 1fr);
}

.comments-grid {
  grid-template-columns: repeat(2, 1fr);
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

/* Media Query for screens up to 1000px - Display One Rating, Review, and Comment Per Row */
@media (max-width: 1000px) {
  .ratings-grid {
    grid-template-columns: repeat(1, 1fr); /* Display 1 rating per row */
  }

  .reviews-grid,
  .comments-grid {
    grid-template-columns: repeat(1, 1fr); /* Display 1 review/comment per row */
  }
}

/* Media Query for screens up to 900px - Full screen overlay for the sidebar */
@media (max-width: 900px) {
  .sidebar {
    width: 100%;
    transform: translateX(0%);
    background: rgba(20, 20, 19, 0.95); /* Increased transparency */
  }

  .sidebar.hidden {
    transform: translateX(-94.5%);
  }

  .dashboard-content {
    margin-left: 0;
  }

  .collection-container {
    flex-wrap: wrap;
    justify-content: center;
    overflow-x: visible;
  }
}
</style>
