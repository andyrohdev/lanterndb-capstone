<template>
    <div class="lantern-db-rating">
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
        <button @click="deleteRating(rating.rating_id)" class="btn btn-danger">
          Delete
        </button>
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
    computed: {
      ...mapState(["user"]),
    },
    methods: {
      editRating(rating) {
        this.$emit("openRatingForm", rating);
      },
      deleteRating(ratingId) {
        if (confirm("Are you sure you want to delete this rating?")) {
          const ratingData = {
            rating_id: ratingId,
            user_id: this.$store.state.user.id,
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
  
  .rating-actions {
    margin-top: 10px;
  }
  
  .rating-actions .btn {
    margin-right: 5px;
  }
  </style>
  