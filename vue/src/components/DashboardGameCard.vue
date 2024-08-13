<template>
    <div class="dashboard-game-card">
      <div class="card-image" @click="goToGameDetails">
        <img :src="gameImage" alt="Game Image" class="game-image" />
        <div class="game-title-overlay">
          <h3 class="game-title">{{ game.title }}</h3>
        </div>
        <div class="three-dots-icon" @click.stop="toggleDropdown">
          ⋮
        </div>
      </div>
      <div v-if="showDropdown" class="dropdown-menu">
        <button @click="moveToWishlist">Move to Wishlist</button>
        <button @click="moveToPlaying">Move to Playing</button>
        <button @click="moveToPlayed">Move to Played</button>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    props: {
      game: {
        type: Object,
        required: true,
      },
    },
    data() {
      return {
        showDropdown: false,
      };
    },
    computed: {
      gameImage() {
        return this.game.image || "default-game-image.jpg"; // Provide a default image if not available
      },
    },
    methods: {
      toggleDropdown() {
        this.showDropdown = !this.showDropdown;
      },
      goToGameDetails() {
        this.$router.push({ name: 'GameDetails', params: { id: this.game.id } });
      },
      moveToWishlist() {
        this.$emit('moveToCollection', this.game, 'wishlist');
        this.showDropdown = false;
      },
      moveToPlaying() {
        this.$emit('moveToCollection', this.game, 'playing');
        this.showDropdown = false;
      },
      moveToPlayed() {
        this.$emit('moveToCollection', this.game, 'played');
        this.showDropdown = false;
      },
    },
  };
  </script>
  
  <style scoped>
  .dashboard-game-card {
    position: relative;
    width: 200px;
    height: 300px;
    margin: 15px;
    cursor: pointer;
    border-radius: 10px;
    overflow: hidden;
  }
  
  .card-image {
    position: relative;
    width: 100%;
    height: 100%;
  }
  
  .game-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
  
  .game-title-overlay {
    position: absolute;
    bottom: 0;
    width: 100%;
    background: rgba(0, 0, 0, 0.5);
    padding: 10px;
    box-sizing: border-box;
  }
  
  .game-title {
    margin: 0;
    color: white;
    font-size: 1.2rem;
    text-align: center;
  }
  
  .three-dots-icon {
    position: absolute;
    top: 10px;
    right: 10px;
    font-size: 1.5rem;
    color: white;
  }
  
  .dropdown-menu {
    position: absolute;
    top: 40px;
    right: 10px;
    background: #333;
    padding: 10px;
    border-radius: 5px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
  }
  
  .dropdown-menu button {
    background: none;
    border: none;
    color: white;
    padding: 5px 10px;
    width: 100%;
    text-align: left;
    cursor: pointer;
  }
  
  .dropdown-menu button:hover {
    background: #444;
  }
  </style>
  