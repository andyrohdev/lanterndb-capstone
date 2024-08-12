<template>
  <div class="home-page">
    <header class="header-section">
      <!-- Video Background -->
      <video autoplay muted loop preload="auto" class="background-video">
        <source :src="videoSrc" type="video/mp4" />
        Your browser does not support the video tag.
      </video>
      <div class="header-content">
        <h1>Welcome to <span class="glow">Lantern</span><span class="flame">DB</span></h1>
        <h2>Illuminate Your Gaming World</h2>
        <p>
          Step into LanternDB, the ultimate database for gaming enthusiasts.
          Discover detailed information on more than 800,000 video games, from
          beloved classics to cutting-edge new releases. Dive into reviews,
          ratings, and in-depth articles that bring your favorite games to
          life.
        </p>
        <p>
          Whether you're searching for a hidden gem or staying updated on the
          latest trends, LanternDB is your go-to resource for everything gaming.
        </p>
      </div>
    </header>
    <div class="featured-games-section">
      <h2>Featured Games: </h2>
      <div class="games-container">
        <div 
          v-for="game in featuredGames" 
          :key="game.id" 
          class="game-card"
          @click="redirectToGameDetails(game.id)"
        >
          <img :src="game.imageUrl" :alt="game.name" class="game-image" />
          <p>{{ game.name }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import videoSrc from '@/assets/animated-homepage2.mp4'; // Import the video source

export default {
  data() {
    return {
      videoSrc, // Use the imported video source
      featuredGames: [
        { id: 3498, name: "Grand Theft Auto V", imageUrl: "https://media.rawg.io/media/games/20a/20aa03a10cda45239fe22d035c0ebe64.jpg" },
        { id: 4200, name: "Portal 2", imageUrl: "https://media.rawg.io/media/games/2ba/2bac0e87cf45e5b508f227d281c9252a.jpg" },
        { id: 3328, name: "The Witch 3:Wild Hunt", imageUrl: "https://media.rawg.io/media/games/618/618c2031a07bbff6b4f611f10b6bcdbc.jpg" },
        { id: 4291, name: "Counter-Strike Global Offensive", imageUrl: "https://media.rawg.io/media/games/736/73619bd336c894d6941d926bfd563946.jpg" },
      ],
    };
  },
  methods: {
    redirectToGameDetails(gameId) {
      this.$router.push({ name: 'game-details', params: { gameId: gameId } });
    },
  },
};
</script>

<style scoped>
.home-page {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  position: relative;
  z-index: 1;
}

.header-section {
  position: relative;
  width: 100%;
  height: 100vh;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: flex-start; /* Align content to the left */
}




.background-video {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: -1;
  object-fit: cover; /* Ensure the video covers the entire area */
  object-position: center; /* Center the video */
}


.header-content {
  position: relative;
  z-index: 2;
  color: white;
  text-align: center; /* Align text to the left */
  padding: 0 20px;
  margin-left: 10vw;
  width: 40%; /* Control the width of the text block */
  height: 70%;
}

.header-content h1 {
  font-size: 6rem; /* Adjust the size as needed */
  margin-bottom: 20px; /* You can adjust or add margin as needed */
  padding-bottom: 100px;
}
.header-content p {
  font-size: 1.3rem; /* Adjust the size as needed */
  margin-bottom: 20px; /* You can adjust or add margin as needed */
}
.header-content h2 {
  font-size: 3rem; /* Adjust the size as needed */
  margin-bottom: 20px; /* You can adjust or add margin as needed */
  font-style: italic;
  
}

.flame {

animation: colorFlow 20s ease-in-out infinite, scaleFlow 1.2s ease-in-out infinite;

}

@keyframes colorFlow {
0% {
  color: #ff4400;
  /* Dark Orange */
}

25% {
  color: #d32406;
  /* Tomato Red */
}

50% {
  color: #f85b00;
  /* Light Orange */
}

75% {
  color: #fa8704;
  /* Darker Orange */
}

100% {
  color: #ff4400;
  /* Back to Dark Orange */
}
}

@keyframes scaleFlow {
0% {
  transform: scale(1);
}

50% {
  transform: scale(1.1);
  /* Slightly enlarged */
}

100% {
  transform: scale(1);
}
}

.featured-games-section {
  padding: 20px;
  background-color: #121212;
  color: white
}

.games-container {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
}

.game-card {
  width: 23%;
  margin: 10px 0;
  cursor: pointer;
  text-align: center;
  border-radius: 10px;
  overflow: hidden;
  background-color: #292929;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.game-card:hover {
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.3);
}

.game-image {
  width: 100%;
  height: auto;
  border-bottom: 2px solid #d14b06;
}
.glow {
  font-size: 2rem; /* Adjust size as needed */
  color: #fff;
  text-align: center;
  font-size: 6rem;
  font-weight: bold; /* Optional: make text bold for better visibility */
  -webkit-animation: glow 1.5s ease-in-out infinite alternate;
  -moz-animation: glow 1.5s ease-in-out infinite alternate;
  animation: glow 1.5s ease-in-out infinite alternate;
}

@keyframes glow {
  from {
    text-shadow: 0 0 10px #fff, 0 0 20px #fff, 0 0 30px #e67b00, 0 0 40px #e67b00, 0 0 50px #e67b00, 0 0 60px #e67b00, 0 0 70px#e67b00;
  }
  to {
    text-shadow: 0 0 20px #fff, 0 0 30px #F5C277 0 0 40px #F5C277, 0 0 50px #F5C277, 0 0 60px #ff4400, 0 0 70px #ff4400, 0 0 80px #ff4400;
  }
}



@media (max-width: 768px) {
  .game-card {
    width: 48%;
  }
}

@media (max-width: 480px) {
  .game-card {
    width: 100%;
  }
}
</style>
