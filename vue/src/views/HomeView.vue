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
    <span class="fade-in-line">Enjoy reviews, ratings, in-depth articles,</span><br>
    <span class="fade-in-line">and easily add your favorite games to your collections</span><br>
    <span class="fade-in-line">for everyone to enjoy.</span>
  </p>
</div>
    </header>
    <div class="featured-games-section">
      <h2>Featured Games: </h2>
      <div class="games-container">
        <div v-for="game in featuredGames" :key="game.id" class="game-card" @click="redirectToGameDetails(game.id)">
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
  justify-content: flex-start;
  /* Align content to the left */
}




.background-video {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: -1;
  object-fit: cover;
  /* Ensure the video covers the entire area */
  object-position: center;
  /* Center the video */
}


.header-content {
  position: relative;
  z-index: 2;
  color: white;
  text-align: center;
  /* Align text to the left */
  padding: 0 20px;
  margin-left: 10vw;
  width: 40%;
  /* Control the width of the text block */
  height: 70%;
}

.header-content h1 {
  font-size: 6rem;
  /* Adjust the size as needed */
  margin-bottom: 20px;
  /* You can adjust or add margin as needed */
  padding-bottom: 100px;
}

.header-content p {
  font-size: 1.3rem;
  /* Adjust the size as needed */
  margin-bottom: 20px;
  /* You can adjust or add margin as needed */
}

.header-content h2 {
  font-size: 3rem;
  /* Adjust the size as needed */
  margin-bottom: 20px;
  /* You can adjust or add margin as needed */
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
  background-color: #1212127e;
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
  font-size: 2rem;
  /* Adjust size as needed */
  color: #F5C277;
  text-align: center;
  font-size: 6rem;
  font-weight: bold;
  /* Optional: make text bold for better visibility */
  -webkit-animation: flame 8s infinite alternate ease-in-out;
  -moz-animation: flame 8s infinite alternate ease-in-out;
  animation: flame 8s infinite alternate ease-in-out;
}

@keyframes flame {
  0% {
    text-shadow: 0 0 8px rgba(255, 140, 0, 0.5),
      0 0 12px rgba(255, 140, 0, 0.5),
      0 0 16px rgba(255, 69, 0, 0.3),
      0 0 20px rgba(255, 69, 0, 0.3),
      0 0 24px rgba(255, 69, 0, 0.3);
    transform: translateY(0px);
  }

  10% {
    text-shadow: 0 0 12px rgba(255, 160, 0, 0.5),
      0 0 18px rgba(255, 160, 0, 0.5),
      0 0 24px rgba(255, 99, 71, 0.4),
      0 0 30px rgba(255, 99, 71, 0.4),
      0 0 36px rgba(255, 99, 71, 0.4);
    transform: translateY(-1px);
  }

  20% {
    text-shadow: 0 0 10px rgba(255, 140, 0, 0.4),
      0 0 16px rgba(255, 140, 0, 0.4),
      0 0 22px rgba(255, 69, 0, 0.3),
      0 0 28px rgba(255, 69, 0, 0.3),
      0 0 34px rgba(255, 69, 0, 0.3);
    transform: translateY(0px);
  }

  30% {
    text-shadow: 0 0 14px rgba(255, 165, 0, 0.5),
      0 0 22px rgba(255, 165, 0, 0.5),
      0 0 30px rgba(255, 99, 71, 0.4),
      0 0 38px rgba(255, 99, 71, 0.4),
      0 0 46px rgba(255, 99, 71, 0.4);
    transform: translateY(-2px);
  }

  40% {
    text-shadow: 0 0 10px rgba(255, 140, 0, 0.4),
      0 0 16px rgba(255, 140, 0, 0.4),
      0 0 22px rgba(255, 69, 0, 0.3),
      0 0 28px rgba(255, 69, 0, 0.3),
      0 0 34px rgba(255, 69, 0, 0.3);
    transform: translateY(0px);
  }

  50% {
    text-shadow: 0 0 16px rgba(255, 170, 0, 0.5),
      0 0 24px rgba(255, 170, 0, 0.5),
      0 0 32px rgba(255, 99, 71, 0.4),
      0 0 40px rgba(255, 99, 71, 0.4),
      0 0 48px rgba(255, 99, 71, 0.4);
    transform: translateY(-1px);
  }

  60% {
    text-shadow: 0 0 12px rgba(255, 140, 0, 0.4),
      0 0 20px rgba(255, 140, 0, 0.4),
      0 0 28px rgba(255, 69, 0, 0.3),
      0 0 36px rgba(255, 69, 0, 0.3),
      0 0 44px rgba(255, 69, 0, 0.3);
    transform: translateY(0px);
  }

  70% {
    text-shadow: 0 0 14px rgba(255, 160, 0, 0.5),
      0 0 22px rgba(255, 160, 0, 0.5),
      0 0 30px rgba(255, 99, 71, 0.4),
      0 0 38px rgba(255, 99, 71, 0.4),
      0 0 46px rgba(255, 99, 71, 0.4);
    transform: translateY(-1px);
  }

  80% {
    text-shadow: 0 0 10px rgba(255, 140, 0, 0.4),
      0 0 16px rgba(255, 140, 0, 0.4),
      0 0 22px rgba(255, 69, 0, 0.3),
      0 0 28px rgba(255, 69, 0, 0.3),
      0 0 34px rgba(255, 69, 0, 0.3);
    transform: translateY(0px);
  }

  90% {
    text-shadow: 0 0 16px rgba(255, 165, 0, 0.5),
      0 0 24px rgba(255, 165, 0, 0.5),
      0 0 32px rgba(255, 99, 71, 0.4),
      0 0 40px rgba(255, 99, 71, 0.4),
      0 0 48px rgba(255, 99, 71, 0.4);
    transform: translateY(-2px);
  }

  100% {
    text-shadow: 0 0 8px rgba(255, 140, 0, 0.4),
      0 0 12px rgba(255, 140, 0, 0.4),
      0 0 16px rgba(255, 69, 0, 0.3),
      0 0 20px rgba(255, 69, 0, 0.3),
      0 0 24px rgba(255, 69, 0, 0.3);
    transform: translateY(0px);
  }
}

.fade-in-line {
  display: block; /* Ensure each line is treated as a block */
  opacity: 0;
  animation: fadeInAnimation 2s ease forwards;
}

.fade-in-line:first-child {
  animation-delay: 1s;
}

.fade-in-line:nth-of-type(2) {
  animation-delay: 2s;
}

.fade-in-line:nth-of-type(3) {
  animation-delay: 3s;
}

/* Add more nth-child selectors if you have more lines */

@keyframes fadeInAnimation {
  to {
    opacity: 1;
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
}</style>
