<template>
    <div class="game-details">
      <div class="banner-container">
        <img :src="game.background_image || 'https://via.placeholder.com/1200x400.png?text=No+Image+Available'" alt="Game banner" class="game-banner">

      </div>
      <div class="details-content">
        <h1>{{ game.name || "Unknown Title" }}</h1>
        <p class="game-genres">Genres: {{ formattedGenres }}</p>
        <p class="game-rating">Rating: {{ game.rating || "N/A" }}</p>
      </div>
    </div>
  </template>
  
  <script>
  import { ref, onMounted, computed } from 'vue';
  import { useRoute } from 'vue-router';
  import GameService from '../services/GameService'; 
  
  export default {
    setup() {
      const route = useRoute();
      const game = ref({
        genres: [],
        background_image: '',
        name: '',
        rating: 'N/A'
      });
  
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
  
      return {
        game,
        formattedGenres
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
  