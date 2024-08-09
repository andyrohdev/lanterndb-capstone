<template>
  <div class="home-page">
    <!-- Header Section -->
    <header class="header">
      <div class="brand">
        <img src="path_to_logo" alt="Brand Logo" class="logo">
        <span class="brand-name">Brand Name</span>
      </div>
      <div class="nav-links">
        <button class="btn">Browse</button>
        <button class="btn">Profile</button>
        <button class="btn">Login</button>
      </div>
    </header>

    <!-- Main Navigation Bar -->
    <nav class="main-nav">
      <ul>
        <li><a href="#">Home</a></li>
        <li><a href="#">About</a></li>
        <li><a href="#">Blog</a></li>
        <li><a href="#">FAQ</a></li>
      </ul>
    </nav>

    <!-- Game Display Section -->
    <div class="game-display">
      <div v-if="selectedGame" class="game-info">
        <h2>{{ selectedGame.name }}</h2>
        <img :src="selectedGame.image" alt="Game Image">
        <button @click="playTrailer" class="play-button">Play Trailer</button>
      </div>
      <div v-else>
        <p>Select a game to see details.</p>
      </div>
    </div>

    <!-- Game Slider Section -->
    <nav class="game-slider">
      <button @click="prevGame" class="slider-btn">◀</button>
      <ul>
        <li v-for="game in games" :key="game.id" @click="selectGame(game)">
          <img :src="game.thumbnail" alt="Game Thumbnail">
        </li>
      </ul>
      <button @click="nextGame" class="slider-btn">▶</button>
    </nav>

    <!-- Footer Section -->
    <footer class="footer">
      <!-- Additional footer content here -->
    </footer>
  </div>
</template>
<script>
export default {
  data() {
    return {
      games: [
        { id: 1, name: 'Game 1', thumbnail: 'path_to_thumbnail1', image: 'path_to_image1' },
        { id: 2, name: 'Game 2', thumbnail: 'path_to_thumbnail2', image: 'path_to_image2' },
        { id: 3, name: 'Game 3', thumbnail: 'path_to_thumbnail3', image: 'path_to_image3' },
        // Add more games here
      ],
      selectedGame: null,
    };
  },
  methods: {
    selectGame(game) {
      this.selectedGame = game;
    },
    playTrailer() {
      alert(`Playing trailer for ${this.selectedGame.name}`);
    },
    prevGame() {
      // Logic for sliding to the previous game
    },
    nextGame() {
      // Logic for sliding to the next game
    },
  },
};
</script>
<style scoped>
.home-page {
  display: flex;
  flex-direction: column;
  height: 100vh;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  background-color: #333;
  color: white;
}

.brand {
  display: flex;
  align-items: center;
}

.logo {
  height: 50px;
  margin-right: 10px;
}

.nav-links .btn {
  margin-left: 10px;
  background-color: #555;
  color: white;
  border: none;
  padding: 10px 15px;
  cursor: pointer;
  border-radius: 5px;
}

.main-nav {
  background-color: #444;
  text-align: center;
  padding: 10px;
}

.main-nav ul {
  list-style: none;
  margin: 0;
  padding: 0;
  display: inline-block;
}

.main-nav ul li {
  display: inline;
  margin: 0 15px;
}

.main-nav ul li a {
  color: white;
  text-decoration: none;
  font-size: 18px;
}

.game-display {
  flex-grow: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f5f5f5;
  padding: 20px;
}

.game-info {
  text-align: center;
}

.play-button {
  margin-top: 20px;
  padding: 10px 20px;
  background-color: #333;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.game-slider {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #333;
  padding: 10px;
}

.game-slider ul {
  display: flex;
  list-style: none;
  overflow-x: auto;
  padding: 0;
  margin: 0 10px;
}

.game-slider ul li {
  margin-right: 10px;
}

.game-slider ul li img {
  width: 100px;
  height: auto;
  cursor: pointer;
}

.slider-btn {
  background-color: #555;
  color: white;
  border: none;
  padding: 10px;
  cursor: pointer;
  border-radius: 5px;
}

.footer {
  background-color: #333;
  padding: 20px;
  color: white;
  text-align: center;
}
@media (max-width: 768px) {
  .header, .main-nav, .game-slider, .footer {
    flex-direction: column;
    text-align: center;
  }

  .nav-links .btn {
    margin: 5px 0;
  }

  .game-slider ul {
    justify-content: center;
    margin: 0;
  }
}
</style>
