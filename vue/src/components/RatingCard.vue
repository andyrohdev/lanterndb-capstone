<template>
    <div class="lantern-db-ratings-container">
      <h2>LanternDB Ratings</h2>
      <div class="lantern-db-ratings-scrollable">
        <div
          v-for="rating in lanternDbRatings"
          :key="rating.rating_id"
          class="lantern-db-rating"
        >
          <div class="rating-flames">
            <i
              v-for="flame in 5"
              :key="flame"
              :class="[
                'bi',
                'bi-fire',
                { filled: flame <= rating.rating_score },
              ]"
            ></i>
          </div>
          <p>{{ fetchUsername(rating.user_id) }}</p>
          <div v-if="rating.user_id === user.id" class="rating-actions">
            <button @click="editRating(rating)" class="btn btn-secondary">
              Edit
            </button>
            <button
              @click="deleteRating(rating.rating_id)"
              class="btn btn-danger"
            >
              Delete
            </button>
          </div>
        </div>
      </div>
      <!-- Add Rating Button -->
      <div v-if="isUserLoggedIn" class="add-rating-button">
        <button @click="openRatingForm" class="btn btn-primary">
          Add Rating
        </button>
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
    </div>
  </template>
  
  <script>
  import { mapState } from "vuex";
  import GameService from "../services/GameService";
  
  export default {
    data() {
      return {
        lanternDbRatings: [],
        lanternDbRating: null,
        showRatingForm: false,
        newRating: null,
        hoverRating: 0,
        users: {},
        editingRatingId: null, // Track the ID of the rating being edited
      };
    },
    props: {
      gameId: {
        type: Number,
        required: true,
      },
    },
    computed: {
      ...mapState(["token", "user"]),
      isUserLoggedIn() {
        return !!this.token;
      },
    },
    mounted() {
      this.fetchLanternDbRatings();
      this.fetchUsers();
    },
    methods: {
      fetchLanternDbRatings() {
        GameService.getLanternDbRatings(this.gameId)
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
      openRatingForm() {
        this.showRatingForm = true;
      },
      closeRatingForm() {
        this.showRatingForm = false;
        this.newRating = null;
        this.hoverRating = 0;
        this.editingRatingId = null;
      },
      editRating(rating) {
        this.newRating = rating.rating_score;
        this.editingRatingId = rating.rating_id;
        this.openRatingForm();
      },
      submitRating(event) {
        if (this.newRating === null) {
          alert("Please provide a rating.");
          return;
        }
  
        const ratingData = {
          rating_score: this.newRating,
          user_id: this.$store.state.user.id,
          game_id: this.gameId,
          game_title: this.game.name, // Ensure game title is included
        };
  
        const buttonText = event.target.innerText;
  
        if (buttonText === "Update") {
          ratingData.rating_id = this.editingRatingId;
          GameService.updateRating(ratingData)
            .then(() => {
              this.fetchLanternDbRatings();
              this.closeRatingForm();
            })
            .catch((error) => {
              console.error("Error updating rating:", error);
            });
        } else if (buttonText === "Submit") {
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
  
      deleteRating(ratingId) {
        if (confirm("Are you sure you want to delete this rating?")) {
          const ratingData = {
            rating_id: ratingId,
            user_id: this.$store.state.user.id,
          };
          GameService.deleteRating(ratingData)
            .then(() => {
              this.fetchLanternDbRatings();
            })
            .catch((error) => {
              console.error("Error deleting rating:", error);
            });
        }
      },
    },
  };
  </script>
  
  <style scoped>
  .lantern-db-ratings-container {
    margin-top: 20px;
    border-top: 2px solid #444;
    padding-top: 20px;
  }
  
  .lantern-db-ratings-scrollable {
    display: flex;
    overflow-x: auto;
    padding: 10px;
  }
  
  .lantern-db-rating {
    padding: 10px;
    margin-right: 10px;
    min-width: 35%;
    text-align: center;
  }
  
  .lantern-db-rating:last-child {
    margin-right: 0;
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
  
  .add-rating-button {
    margin-top: 10px;
    display: flex;
    justify-content: center;
  }
  
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
  
  .rating-form-content {
    background: #fff;
    color: #000;
    padding: 20px;
    border-radius: 8px;
    width: 80%;
    max-width: 600px;
    text-align: left;
  }
  </style>
  