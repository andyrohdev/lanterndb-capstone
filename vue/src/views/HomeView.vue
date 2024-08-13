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
  <div class="register-to">
    <p>
      Register now to gain access to:
    </p>
 </div>

 <div class="info-boxes-section">
  <div class="info-box">
    Create and Manage Collections
    <div class="info-box-description">
      <p>
        Easily and efficiently manage your game collection by seamlessly adding new titles, categorizing them into specific genres or themes, and keeping track of your favorites all within one intuitive and user-friendly interface.
         With this powerful tool, you can ensure that your entire game library is organized and readily accessible, allowing you to focus more on enjoying your games rather than worrying about where to find them. 
         Whether you're a casual player or a dedicated enthusiast, this feature will streamline the way you handle your gaming collection, making it easier than ever to stay on top of your ever-growing list of games.
      </p>
    </div>
  </div>
  <div class="info-box">
    Read and Leave Reviews
    <div class="info-box-description">
      <p>Dive into detailed game reviews from fellow gamers and share your own insights to help others make informed choices.</p>
    </div>
  </div>
  <div class="info-box">
    Rate Games you've Played
    <div class="info-box-description">
      <p>Express your opinion by rating games and explore ratings from other users to see how your preferences align with the community.</p>
    </div>
  </div>
  <div class="info-box">
    Comment on Reviews
    <div class="info-box-description">
      <p>Join the conversation by commenting on reviews, sharing your thoughts, and engaging with the gaming community.</p>
    </div>
  </div>
  <div class="info-box">
    Whatever Else
    <div class="info-box-description">
      <p>Uncover a variety of exciting additional features and enhancements designed to significantly enrich your overall gaming experience and interactions on LanternDB.
         With these new tools and options, you can dive deeper into your favorite games, explore unique functionalities, and enjoy an even more immersive and engaging platform. 
         Whether it's discovering hidden gems or leveraging advanced analytics, these enhancements aim to provide you with an unparalleled experience, keeping you at the forefront of the gaming world and allowing you to make the most out of every moment spent on LanternDB.</p>
    </div>
  </div>
</div>
    
    <div class="featured-games-section">
      <h2>Featured Games: </h2>
      <div class="games-container">
        <div v-for="game in featuredGames" :key="game.id" class="game-card" @click="redirectToGameDetails(game.id)">
          <img :src="game.imageUrl" :alt="game.name" class="game-image" />
          <p>{{ game.name }}</p>
        </div>
      </div>
    </div>
    <div class="about-us-section">
  <h2>About Us</h2>
  <p>
    LanternDB is dedicated to illuminating your gaming experience. 
    We provide comprehensive reviews, ratings, and in-depth articles on the latest and greatest games. 
    Our mission is to help you discover, enjoy, and share your favorite games with the community. 
    Whether you're a casual gamer or a hardcore enthusiast, LanternDB offers a vibrant platform to enhance your gaming world.
  </p>
</div>

<div class="faq-section">
      <h2>FAQ</h2>
      <p>
        Here you'll find answers to the most frequently asked questions about LanternDB.
        If you have any other questions, feel free to reach out to our support team.
      </p>
      <!-- You can add more FAQ content or sections here -->
    </div>

    <!-- ... existing code ... -->
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

.register-to{
  color: white;
  text-align: center;
  font-size: 30px;
  text-decoration: underline; /* Add this line */
  text-decoration-color: #d14b06; /* Change this to your desired color */
  text-decoration-style: solid;

}

.info-boxes-section {
  display: flex;
  flex-wrap: nowrap; /* Ensure the boxes stay in a single line */
  overflow-x: auto;  /* Allow horizontal scrolling if needed */
  padding: 20px;
  gap: 20px; /* Add space between the info boxes */
  padding-bottom: 70px;
}


.info-box {
  color: white;
  display: flex; /* Enables flexbox centering */
  flex-direction: column; /* Align children vertically */
  justify-content: center; /* Center vertically */
  align-items: center; /* Center horizontally */
  position: relative;
  flex: 1;
  margin: 0; /* Reset margin */
  padding: 20px;
  text-align: center;
  background-color: rgba(158, 54, 10, 0.404);
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
  overflow: hidden;
  transition: background-color 0.3s ease;
  min-width: 150px; /* Ensures boxes have a consistent width */
  min-height: 135px;
  font-size: 28px;
  padding-bottom: 70px;
}

.info-box:hover {
  background-color: #292929;
}

.info-box-description {
  max-height: 0;
  overflow: hidden;
  transition: max-height 9s ease-in-out;
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background-color: #292929;
  padding: 0 10px;
  font-size: 14px;
}

.info-box:hover .info-box-description {
  max-height: 300px; /* Adjust to fit the content */
  padding-top: 10px; /* Optional: Add padding for visual separation */
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

.about-us-section {
  padding: 20px;
  background-image: linear-gradient(to right, rgb(158, 54, 10), #29292943, #29292943);
  color: white;
  margin-top: 20px;
  text-align: left; /* Align text to the left */
  width: 40%; /* Control the width to match previous section */
  margin-left: 10vw; /* Center the section horizontally */
  box-sizing: border-box; /* Ensure padding is included in the width */
  padding-top: 50px;
}

.about-us-section h2 {
  font-size: 2.5rem; /* Increase the font size */
  margin-bottom: 15px;
}

.about-us-section p {
  font-size: 1.3rem; /* Adjust font size */
  line-height: 1.6; /* Improve readability */
  margin: 0; /* Remove default margin */
}

.faq-section {
  padding: 20px;
  background-image: linear-gradient(to right, rgb(158, 54, 10), #29292943, #29292943);
  color: white;
  margin-top: 20px;
  text-align: left;
  width: 40%;
  margin-left: 10vw;
  box-sizing: border-box;
  padding-top: 50px;
}

.faq-section h2 {
  font-size: 2.5rem;
  margin-bottom: 15px;
}

.faq-section p {
  font-size: 1.3rem;
  line-height: 1.6;
  margin: 0;
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
