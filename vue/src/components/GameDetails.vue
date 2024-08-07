<template>
    <div class="game-details">
      <div class="banner-container">
        <img :src="game.background_image || 'https://via.placeholder.com/1200x400.png?text=No+Image+Available'" alt="Game banner" class="game-banner">
      </div>
      <div class="details-content">
        <h1>{{ game.name || "Unknown Title" }}</h1>
        <p class="game-genres">Genres: {{ formattedGenres }}</p>
        <p class="game-rating">Rating: {{ game.rating || "N/A" }}</p>
        <!-- Add button to add to collection -->
        <div v-if="isUserLoggedIn" class="add-to-collection">
          <button @click="addToCollection(1)" class="btn btn-primary">Add to Wishlist</button>
          <button @click="addToCollection(2)" class="btn btn-primary">Add to Playing</button>
          <button @click="addToCollection(3)" class="btn btn-primary">Add to Played</button>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import { ref, onMounted, computed } from 'vue';
  import { useRoute } from 'vue-router';
  import { useStore } from 'vuex';
  import GameService from '../services/GameService'; 
  import CollectionService from '../services/CollectionService';
  
  export default {
    setup() {
      const store = useStore();
      const route = useRoute();
      const game = ref({
        genres: [],
        background_image: '',
        name: '',
        rating: 'N/A'
      });
      const isUserLoggedIn = computed(() => !!store.state.token);
  
      const formattedGenres = computed(() => {
        return (Array.isArray(game.value.genres) && game.value.genres.length > 0)
          ? game.value.genres.map(genre => genre.name).join(', ')
          : 'N/A';
      });
  
      onMounted(() => {
        const gameId = route.params.gameId;
        GameService.getGameDetails(gameId).then(response => {
          game.value = response.data;
        });
      });
  
      function addToCollection(collection_id) {
        if (!isUserLoggedIn.value) {
          alert('Please log in to add games to your collection.');
          return;
        }
  
        const genre = game.value.genres && game.value.genres.length > 0
          ? game.value.genres[0].name
          : 'Unknown Genre';
  
        const gameData = {
          title: game.value.name,
          genre: genre,
          collection_id,
        };
  
        CollectionService.addToCollections(gameData)
          .then((response) => {
            console.log(`Game added to ${collection_id} collection`, response);
          })
          .catch((error) => {
            console.error(`Error adding game to ${collection_id} collection`, error);
          });
      }
  
      return {
        game,
        formattedGenres,
        isUserLoggedIn,
        addToCollection
      };
    }
  };
  </script>
  
  <style scoped>
  .game-details {
    background-color: #121212;
    color: #e0e0e0;
    text-align: center;
    padding: 20px;
    height: 100vh;
    overflow: hidden;
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
  