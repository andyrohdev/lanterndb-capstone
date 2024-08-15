<template>
  <div class="home-page">
    <header class="header-section">
      <!-- Video Background -->
      <video
        v-if="!isMobile"
        autoplay
        muted
        loop
        preload="auto"
        class="background-video"
      >
        <source :src="videoSrc" type="video/mp4" />
        Your browser does not support the video tag.
      </video>
      <div class="header-content">
        <div class="header-text">
          <h1>
            <span class="glow">Lantern</span><span class="flame">DB</span>
          </h1>
          <h2>Illuminate Your Gaming World</h2>
          <p>
            <span class="fade-in-line"
              >Enjoy reviews, ratings, in-depth articles,</span
            ><br />
            <span class="fade-in-line"
              >and easily add your favorite games to your collections</span
            ><br />
            <span class="fade-in-line">for everyone to enjoy.</span>
          </p>
        </div>
      </div>
    </header>

    <div class="featured-games-section">
      <div class="call-to-action-card">
        <div class="cta-content">
          <h2>Your Gaming Journey Starts Here</h2>
          <p>Join our community to explore and share your favorite games.</p>
          <router-link to="/register" class="cta-button">Get Started</router-link>

        </div>
      </div>

      <div class="info-boxes-section">
        <div class="info-box-wrapper" v-for="(box, index) in infoBoxes" :key="index">
          <div class="info-box">
            <div class="info-box-front">
              {{ box.title }}
            </div>
            <div class="info-box-back">
              <p>{{ isMobile ? box.shortDescription : box.description }}</p>
            </div>
          </div>
        </div>
      </div>

      <h2>Featured Games:</h2>
      <div class="featured-games-accordion">
        <div
          v-for="game in featuredGames"
          :key="game.id"
          class="featured-game-item"
          @mouseover="setActiveGame(game.id)"
          @mouseleave="clearActiveGame"
          :class="{ 'active': activeGame === game.id }"
        >
          <img :src="game.imageUrl" :alt="game.name" class="game-image" />
          <div class="game-info">
            <h3>{{ game.name }}</h3>
            <router-link :to="{ name: 'game-details', params: { gameId: game.id } }" class="view-details-button">
              View Details
            </router-link>
          </div>
        </div>
      </div>
    </div>

    <div class="about-us-section" ref="aboutUsSection">
      <h2>About Us</h2>
      <p>
        LanternDB is dedicated to illuminating your gaming experience.
        We provide comprehensive reviews, ratings, and in-depth articles on the latest and greatest games.
        Our mission is to help you discover, enjoy, and share your favorite games with the community.
        Whether you're a casual gamer or a hardcore enthusiast, LanternDB offers a vibrant platform to enhance your gaming world.
      </p>
    </div>

    <div class="faq-section" ref="faqSection">
      <h2>FAQ</h2>
      <div class="faq-list">
        <div
          v-for="(faq, index) in faqs"
          :key="index"
          class="faq-card"
          :class="{ 'active': activeAccordion === index }"
        >
          <div class="faq-title" @click="toggleAccordion(index)">
            <h3>{{ faq.question }}</h3>
            <span>{{ activeAccordion === index ? '-' : '+' }}</span>
          </div>
          <transition name="faq-accordion">
            <div v-show="activeAccordion === index" class="faq-content">
              <p>{{ faq.answer }}</p>
            </div>
          </transition>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import videoSrc from "@/assets/animated-homepage.mp4"; // Import the video source

export default {
  data() {
    return {
      videoSrc, // Use the imported video source
      isMobile: false,
      featuredGames: [
        {
          id: 3498,
          name: "Grand Theft Auto V",
          imageUrl:
            "https://media.rawg.io/media/games/20a/20aa03a10cda45239fe22d035c0ebe64.jpg",
        },
        {
          id: 4200,
          name: "Portal 2",
          imageUrl:
            "https://media.rawg.io/media/games/2ba/2bac0e87cf45e5b508f227d281c9252a.jpg",
        },
        {
          id: 3328,
          name: "The Witcher 3: Wild Hunt",
          imageUrl:
            "https://media.rawg.io/media/games/618/618c2031a07bbff6b4f611f10b6bcdbc.jpg",
        },
        {
          id: 4291,
          name: "Counter-Strike Global Offensive",
          imageUrl:
            "https://media.rawg.io/media/games/736/73619bd336c894d6941d926bfd563946.jpg",
        },
      ],
      activeGame: null,
      infoBoxes: [
        {
          title: "Create and Manage Collections",
          description:
            "Easily manage your game collection by seamlessly adding new titles, and keeping track of your favorites all within one intuitive and user-friendly interface.",
          shortDescription: "Easily manage and track your games.",
        },
        {
          title: "Read and Leave Reviews",
          description:
            "Discover a vast collection of detailed game reviews written by fellow gamers who share your passion.",
          shortDescription: "Discover reviews by fellow gamers.",
        },
        {
          title: "Rate Games You've Played",
          description:
            "Express your opinion by rating games you've experienced, contributing to the collective understanding of how a game resonates with the community.",
          shortDescription: "Rate and share your experiences.",
        },
        {
          title: "Comment on Reviews",
          description:
            "Join discussions by commenting on reviews, share your thoughts, or offer counterpoints, fostering richer dialogue.",
          shortDescription: "Comment and discuss reviews.",
        },
        {
          title: "Discover More",
          description:
            "Uncover additional features and enhancements designed to enrich your gaming experience on LanternDB.",
          shortDescription: "Explore new features.",
        },
      ],
      faqs: [
        { question: "How many games are in the browse feature?", answer: "More than 500,000 games can be found!" },
        { question: "Is there a fee or monthly payment involved with registration?", answer: "No, all features are 100% FREE!" },
        { question: "Can I contribute reviews?", answer: "Absolutely! You can contribute reviews and share your experiences with other gamers." },
        { question: "What platforms are supported?", answer: "LanternDB supports games across multiple platforms including PC, consoles, and mobile devices." },
        { question: "How do I manage my game collections?", answer: "LanternDB offers intuitive tools to add, remove, and organize games in your collections with ease." }
      ],
      activeAccordion: null,
    };
  },
  mounted() {
    this.detectMobile();
    this.addScrollAnimations();
    window.addEventListener('resize', this.detectMobile);
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.detectMobile);
  },
  methods: {
    detectMobile() {
      this.isMobile = window.innerWidth <= 768;
    },
    setActiveGame(gameId) {
      this.activeGame = gameId;
    },
    clearActiveGame() {
      this.activeGame = null;
    },
    addScrollAnimations() {
      const aboutSection = this.$refs.aboutUsSection;
      const faqSection = this.$refs.faqSection;
      const featuredGamesSection = this.$el.querySelector('.featured-games-section');

      const observer = new IntersectionObserver((entries) => {
        entries.forEach((entry) => {
          if (entry.isIntersecting) {
            entry.target.classList.add("in-view");
          } else {
            entry.target.classList.remove("in-view");
          }
        });
      }, {
        threshold: 0.2,
      });

      observer.observe(aboutSection);
      observer.observe(faqSection);
      observer.observe(featuredGamesSection);
    },
    toggleAccordion(index) {
      this.activeAccordion = this.activeAccordion === index ? null : index;
    },
  },
};
</script>

<style scoped>
/* Overall Page Styles */
.home-page {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  position: relative;
  z-index: 1;
}

/* Header Section with Video Background */
.header-section {
  position: relative;
  width: 100%;
  height: 100vh;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}

.background-video {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: -1;
  object-fit: cover;
  object-position: center;
}

.header-content {
  position: relative;
  z-index: 2;
  color: white;
  text-align: center;
  padding: 0 20px;
  display: flex;
  align-items: center;
  justify-content: flex-start;
  width: 100%;
}

.header-text {
  max-width: 33%;
  margin-left: 5%;
  margin-right: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center; /* Center text */
}

.header-content h1 {
  font-size: 6rem;
  margin-bottom: 20px;
}

.header-content h2 {
  font-size: 3rem;
  margin-bottom: 20px;
  font-style: italic;
}

.glow {
  font-size: 6rem;
  color: #f5c277;
  font-weight: bold;
  animation: flicker 2s infinite;
  text-shadow: 0 0 8px rgba(255, 140, 0, 0.7),
               0 0 20px rgba(255, 69, 0, 0.5),
               0 0 30px rgba(255, 99, 71, 0.4),
               0 0 40px rgba(255, 99, 71, 0.3),
               0 0 50px rgba(255, 69, 0, 0.2);
}

@keyframes flicker {
  0% {
    opacity: 1;
    text-shadow: 0 0 8px rgba(255, 140, 0, 0.7),
                 0 0 20px rgba(255, 69, 0, 0.5),
                 0 0 30px rgba(255, 99, 71, 0.4),
                 0 0 40px rgba(255, 99, 71, 0.3),
                 0 0 50px rgba(255, 69, 0, 0.2);
  }
  10% {
    opacity: 0.9;
    text-shadow: 0 0 12px rgba(255, 170, 0, 0.8),
                 0 0 22px rgba(255, 99, 71, 0.6),
                 0 0 32px rgba(255, 69, 0, 0.5),
                 0 0 42px rgba(255, 99, 71, 0.4),
                 0 0 52px rgba(255, 69, 0, 0.3);
  }
  20% {
    opacity: 1;
    text-shadow: 0 0 15px rgba(255, 140, 0, 0.6),
                 0 0 25px rgba(255, 99, 71, 0.5),
                 0 0 35px rgba(255, 69, 0, 0.4),
                 0 0 45px rgba(255, 99, 71, 0.3),
                 0 0 55px rgba(255, 69, 0, 0.2);
  }
  30% {
    opacity: 0.8;
    text-shadow: 0 0 10px rgba(255, 170, 0, 0.7),
                 0 0 20px rgba(255, 99, 71, 0.6),
                 0 0 30px rgba(255, 69, 0, 0.4),
                 0 0 40px rgba(255, 99, 71, 0.3),
                 0 0 50px rgba(255, 69, 0, 0.2);
  }
  40% {
    opacity: 1;
    text-shadow: 0 0 12px rgba(255, 140, 0, 0.8),
                 0 0 22px rgba(255, 99, 71, 0.5),
                 0 0 32px rgba(255, 69, 0, 0.3),
                 0 0 42px rgba(255, 99, 71, 0.4),
                 0 0 52px rgba(255, 69, 0, 0.3);
  }
  100% {
    opacity: 1;
    text-shadow: 0 0 8px rgba(255, 140, 0, 0.7),
                 0 0 20px rgba(255, 69, 0, 0.5),
                 0 0 30px rgba(255, 99, 71, 0.4),
                 0 0 40px rgba(255, 99, 71, 0.3),
                 0 0 50px rgba(255, 69, 0, 0.2);
  }
}

@keyframes flame {
  0% {
    text-shadow: 0 0 8px rgba(255, 140, 0, 0.5), 0 0 12px rgba(255, 140, 0, 0.5),
      0 0 16px rgba(255, 69, 0, 0.3), 0 0 20px rgba(255, 69, 0, 0.3),
      0 0 24px rgba(255, 69, 0, 0.3);
  }
  100% {
    text-shadow: 0 0 16px rgba(255, 170, 0, 0.5),
      0 0 24px rgba(255, 170, 0, 0.5), 0 0 32px rgba(255, 99, 71, 0.4),
      0 0 40px rgba(255, 99, 71, 0.4), 0 0 48px rgba(255, 99, 71, 0.4);
  }
}

.flame {
  animation: colorFlow 20s ease-in-out infinite, scaleFlow 1.2s ease-in-out infinite;
  background: linear-gradient(45deg, #ff4500, #ff8c00, #ffa500);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

@keyframes colorFlow {
  0% { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
  100% { background-position: 0% 50%; }
}

@keyframes scaleFlow {
  0% { transform: scale(1); }
  50% { transform: scale(1.1); }
  100% { transform: scale(1); }
}

.fade-in-line {
  display: block;
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

@keyframes fadeInAnimation {
  to { opacity: 1; }
}

/* Featured Games Section with integrated Call to Action */
.featured-games-section {
  padding: 100px 20px 60px 20px;
  background: linear-gradient(to right, rgba(30, 30, 30, 0.9), rgba(50, 50, 50, 0.9)); /* Adjusted gradient */
  color: white;
  position: relative;
  overflow: hidden;
  opacity: 1;
  transform: none;
  transition: none;
  z-index: 1;
}

.featured-games-section:before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 200%;
  height: 100%;
  background: radial-gradient(circle at 50% 100%, rgba(20, 20, 20, 0.7), transparent); /* Lighter gradient for visibility */
  animation: wave 4s infinite alternate ease-in-out;
}

@keyframes wave {
  0% {
    transform: translateX(0);
  }
  100% {
    transform: translateX(-50%);
  }
}

.call-to-action-card {
  padding: 40px 20px;
  background: rgba(255, 102, 0, 0.8); /* Matching color */
  color: white;
  text-align: center;
  border-radius: 10px;
  margin-bottom: 50px;
  position: relative;
  overflow: hidden;
  z-index: 2; /* Ensure this card stays on top */
}

.call-to-action-card:before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 200%;
  height: 100%;
  background: radial-gradient(circle at 50% 100%, rgba(255, 255, 255, 0.5), transparent); /* Keep the same background for call-to-action */
  animation: wave 4s infinite alternate ease-in-out;
}

.cta-content {
  position: relative;
  z-index: 2; /* Ensure the content, including the button, stays on top */
}

.cta-content h2 {
  font-size: 3rem;
  margin-bottom: 20px;
}

.cta-content p {
  font-size: 1.5rem;
  margin-bottom: 30px;
}

.cta-button {
  padding: 15px 30px;
  background-color: #FF6800; /* Updated color */
  color: white;
  border-radius: 5px;
  text-decoration: none;
  transition: background-color 0.3s ease;
  z-index: 3;
  position: relative;
}

.cta-button:hover {
  background-color: #F3540C; /* Darker blue on hover */
}

/* Info Cards Section */
.info-boxes-section {
  display: flex;
  flex-wrap: wrap; /* Switch to wrap for flexibility */
  justify-content: center;
  gap: 20px;
  padding: 20px;
}

.info-box-wrapper {
  perspective: 1000px;
  flex: 1;
  min-width: 250px;
  max-width: 300px; /* Max width to limit card size */
  height: 200px;
}

.info-box {
  position: relative;
  width: 100%;
  height: 100%;
  transform-style: preserve-3d;
  transform: rotateY(0);
  transition: transform 0.6s ease;
  display: flex;
  align-items: center; /* Ensure content remains centered */
  justify-content: center;
  text-align: center; /* Center text inside cards */
  padding: 10px; /* Add padding to prevent text from touching the edges */
}

.info-box-wrapper:hover .info-box {
  transform: rotateY(180deg); /* Flip the card */
}

.info-box-front, .info-box-back {
  position: absolute;
  width: 100%;
  height: 100%;
  backface-visibility: hidden;
  display: flex;
  align-items: center; /* Center content vertically */
  justify-content: center; /* Center content horizontally */
  padding: 15px; /* Adjust padding for text wrapping */
  text-align: center; /* Ensure text is centered */
}

.info-box-front {
  background-color: #2929299f;
  color: white;
}

.info-box-back {
  background-color: #FA8903;
  color: black;
  transform: rotateY(180deg); /* Back face is hidden initially */
  text-align: center;
  padding: 0 15px;
  font-size: 14px; /* Reduce font size for better readability */
}

/* Featured Games */
.featured-games-accordion {
  display: flex;
  justify-content: center;
  gap: 10px;
  height: 300px;
}

.featured-game-item {
  flex: 1;
  transition: flex 0.6s ease-in-out, transform 0.6s ease-in-out;
  overflow: hidden;
  position: relative;
  cursor: pointer;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  z-index: 1;
}

.featured-game-item.active {
  flex: 4;
  transform: scale(1.01);
  z-index: 10;
}

.featured-game-item img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.6s ease-in-out;
}

.featured-game-item.active img {
  transform: scale(1.01);
}

.game-info {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background: rgba(0, 0, 0, 0.6);
  color: white;
  padding: 10px;
  text-align: center;
  opacity: 0;
  transition: opacity 0.6s ease-in-out;
}

.featured-game-item.active .game-info {
  opacity: 1;
}

.view-details-button {
  display: inline-block;
  margin-top: 10px;
  padding: 5px 15px;
  background-color: #ff6700;
  color: white;
  border-radius: 5px;
  text-decoration: none;
  transition: background-color 0.3s ease;
}

.view-details-button:hover {
  background-color: #e65c00;
}

/* About Us and FAQ Sections with Scroll Animations */
.about-us-section,
.faq-section {
  padding: 40px;
  background: rgba(0, 0, 0, 0.7); /* Transparent black background */
  color: white;
  margin: 50px 10vw;
  text-align: center;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.5);
  border-radius: 15px;
  transform: translateY(20px);
  opacity: 0;
  transition: all 0.6s ease-in-out;
}

.about-us-section.in-view,
.faq-section.in-view {
  transform: translateY(0);
  opacity: 1;
}

.about-us-section h2,
.faq-section h2 {
  font-size: 3rem;
  margin-bottom: 25px;
}

.about-us-section p,
.faq-section p {
  font-size: 1.4rem;
  line-height: 1.8;
}

/* FAQ Accordion */
.faq-list {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.faq-card {
  margin-bottom: 20px;
  background-color: rgba(33, 33, 33, 0.9);
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
  overflow: hidden;
  width: 100%;
  max-width: 800px;
  transition: transform 0.3s ease-in-out;
}

.faq-title {
  display: flex;
  justify-content: space-between;
  padding: 15px;
  background-color: rgba(50, 50, 50, 0.9);
  cursor: pointer;
  color: white;
}

.faq-title:hover {
  background-color: rgba(70, 70, 70, 0.9);
}

.faq-content {
  padding: 15px;
  background-color: rgba(40, 40, 40, 0.9);
  color: white;
  max-height: 0;
  overflow: hidden;
  transition: max-height 0.4s ease;
}

.faq-card.active .faq-content {
  max-height: 500px; /* Adjust based on content */
}

/* Accordion Transition */
.faq-accordion-enter-active,
.faq-accordion-leave-active {
  transition: max-height 0.4s ease;
}

.faq-accordion-enter,
.faq-accordion-leave-to {
  max-height: 0;
  overflow: hidden;
}

/* Responsive Adjustments for Mobile */
@media (max-width: 768px) {
  .header-text {
    max-width: 100%;
    margin: 0 auto;
  }

  .header-content h1 {
    font-size: 4rem; /* Reduce font size for smaller screens */
  }

  .header-content h2 {
    font-size: 2rem; /* Reduce font size for smaller screens */
  }

  .glow {
    font-size: 4rem; /* Ensure 'Lantern' scales down */
  }

  .flame {
    font-size: 4rem; /* Ensure 'DB' scales down */
  }

  .featured-games-section {
    padding: 60px 20px;
  }

  .info-boxes-section {
    flex-wrap: wrap;
    gap: 10px; /* Adjust gap for mobile */
  }

  .info-box-wrapper {
    min-width: 45%;
    height: 150px;
  }

  .info-box {
    padding: 15px; /* Increase padding on mobile for better spacing */
  }

  .about-us-section,
  .faq-section {
    margin: 20px 5vw;
    padding: 20px;
  }

  .about-us-section h2,
  .faq-section h2 {
    font-size: 2.5rem;
  }

  .about-us-section p,
  .faq-section p {
    font-size: 1.2rem;
  }
}

@media (max-width: 550px) {
  .header-content h1 {
    font-size: 3rem; /* Further reduce font size for very small screens */
  }

  .glow {
    font-size: 3rem; /* Ensure 'Lantern' scales down */
  }

  .flame {
    font-size: 3rem; /* Ensure 'DB' scales down */
  }

  .header-content h2 {
    font-size: 1.8rem; /* Further reduce font size for very small screens */
  }

  .header-text {
    padding: 0 10px; /* Add padding to avoid text clipping */
  }

  .info-box-wrapper {
    min-width: 100%;
    height: auto; /* Allow height to adjust based on content */
  }

  .info-box {
    padding: 20px; /* Further increase padding for better readability */
  }
}


</style>
