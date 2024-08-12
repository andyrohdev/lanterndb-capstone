<template>
    <div class="lantern-db-rating">
      <!-- Rating flames -->
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
      <!-- Display game name and username -->
      <p class="rating-info">
        for {{ rating.game_title }}<br />
        by {{ fetchUsername(rating.user_id) }}
      </p>
      <!-- Edit and Delete buttons -->
      <div v-if="rating.user_id === user.id" class="rating-actions">
        <button @click="openEditForm" class="btn btn-secondary">
          Edit
        </button>
        <button @click="confirmDelete" class="btn btn-danger">
          Delete
        </button>
      </div>
  
      <!-- Rating Edit Form Modal -->
      <div v-if="isEditing" class="rating-form-modal">
        <div class="rating-form-content">
          <h3>Edit Your Rating</h3>
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
            Update
          </button>
          <button @click="closeEditForm" class="btn btn-secondary">
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
    props: {
      rating: Object,
      fetchUsername: Function, // Pass the fetchUsername method as a prop
    },
    data() {
      return {
        isEditing: false,
        newRating: this.rating.rating_score,
        hoverRating: 0,
      };
    },
    computed: {
      ...mapState(["user"]),
    },
    methods: {
      // Open the edit form modal
      openEditForm() {
        this.isEditing = true;
      },
      // Close the edit form modal
      closeEditForm() {
        this.isEditing = false;
        this.newRating = this.rating.rating_score;
        this.hoverRating = 0;
      },
      // Submit the edited rating
      submitRating() {
        const updatedRatingData = {
          rating_id: this.rating.rating_id,
          rating_score: this.newRating,
          user_id: this.user.id,
          game_id: this.rating.game_id,
          game_title: this.rating.game_title,
        };
  
        GameService.updateRating(updatedRatingData)
          .then(() => {
            this.isEditing = false;
            this.$emit("ratingUpdated");
          })
          .catch((error) => {
            console.error("Error updating rating:", error);
          });
      },
      // Confirm deletion of the rating
      confirmDelete() {
        if (confirm("Are you sure you want to delete this rating?")) {
          const ratingData = {
            rating_id: this.rating.rating_id,
            user_id: this.user.id,
          };
  
          GameService.deleteRating(ratingData)
            .then(() => {
              this.$emit("ratingDeleted");
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
  .lantern-db-rating {
    padding: 10px;
    margin-right: 10px;
    min-width: 35%;
    text-align: center;
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
  
  .rating-info {
    font-size: 1rem;
    margin-top: 5px;
    color: #e0e0e0;
  }
  
  .rating-actions {
    margin-top: 10px;
  }
  
  .rating-actions .btn {
    margin-right: 5px;
  }
  
  /* Modal styles for the edit form */
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
    z-index: 1000; /* Ensure it overlays above other content */
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
  
  .rating-form-content h3 {
    margin-top: 0;
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
  </style>
  