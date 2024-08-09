<template>
    <div class="admin-user-profile">
      <h1>User Profile</h1>
      <div v-if="user">
        <h2>{{ user.username }}</h2>
        <p><strong>Role:</strong> {{ user.authorities[0].name }}</p>
        <h3>{{ user.id }}</h3>
  
        <div class="dashboard-content">
          <h2>Collections</h2>
          <div class="dashboard">
            <Card :key="'wishlist'" title="Wishlist" :items="wishlistItems" />
            <Card :key="'playing'" title="Playing" :items="playingItems" />
            <Card :key="'played'" title="Played" :items="playedItems" />
          </div>
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
                />
              </div>
            </div>
            <div v-else-if="!loadingReviews" class="no-reviews-message">
              No reviews found.
            </div>
          </div>
        </div>
      </div>
      <div v-else>
        <p>Loading user information...</p>
      </div>
    </div>
  </template>
  
  <script>
  import Card from "@/components/Card.vue";
  import ReviewCard from "@/components/ReviewCard.vue";
  import adminService from "../services/AdminService";
  
  export default {
    components: {
      Card,
      ReviewCard,
    },
    data() {
      return {
        user: null,
        allItems: [],
        reviews: [],
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
    },
  };
  </script>
  
  <style scoped>
  .admin-user-profile {
    padding: 20px;
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
  .reviews-container {
    margin-top: 40px;
    border-top: 2px solid #444;
    padding-top: 20px;
    background-color: inherit;
    display: flex;
    flex-direction: column;
    align-items: center;
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
  </style>
  