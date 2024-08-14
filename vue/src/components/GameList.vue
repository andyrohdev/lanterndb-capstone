<template>
  <div class="page-container">
    <div class="video-background">
      <video autoplay muted loop class="video-back">
        <source src="../assets/Ember_Overlay_with_Blur_1.mp4" type="video/mp4">
      </video>
    </div>
    <div class="content-container">
      <h1 class="header"><span class="lantern-color">Lantern</span><span class="flame">DB</span> lights the way. <br/> Find your
        next favorite game with our comprehensive search feature.</h1>
      <div class="search-box-container">
        <div class="search-box">
          <label for="gameName"></label>
          <input type="text" name="gameName" id="gameName" v-model="nameFilter" autocomplete="off"
            placeholder="Search for a game..." />
        </div>
      </div>

      <div class="game-container" ref="scrollContainer">
        <!-- Display "No games found" if there are no games -->
        <div v-if="!loading && filteredGames.length === 0" class="no-games-message">
          No games found.
        </div>

        <!-- Display the game cards if games are found -->
        <game-card v-bind:game="game" v-bind:key="game.id" v-for="game in filteredGames" />

        <!-- Loading Spinner -->
        <div v-if="loading" class="loading-overlay">
          <img src="../assets/karl-duche-karlduche.gif" alt="Loading..." />
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import GameService from "../services/GameService.js";
import GameCard from "./GameCard.vue";

export default {
  components: {
    GameCard,
  },
 

  data() {
    return {
      nameFilter: "",
      games: [],
      currentPage: 1,
      totalPages: 10,
      loading: false,
      searching: false,
    };
  },

  computed: {
    filteredGames() {
      const filterText = this.nameFilter.trim().toLowerCase();
      if (filterText === "") {
        return this.games;
      }

      return this.games.filter((game) => {
        if (!game || !game.name) {
          return false;
        }
        const gameTitle = game.name.trim().toLowerCase();
        return gameTitle.includes(filterText);
      });
    },
  },

  watch: {
    nameFilter: "debouncedPerformSearch",
  },

  created() {
    this.loadGames(this.currentPage);
    this.$nextTick(() => {
      this.$refs.scrollContainer.addEventListener("scroll", this.checkScroll);
    });
  },

  mounted() {
    this.$refs.scrollContainer.addEventListener("scroll", this.checkScroll);
  },

  beforeUnmount() {
    this.$refs.scrollContainer.removeEventListener("scroll", this.checkScroll);
  },

  methods: {
    performSearch() {
      if (this.nameFilter.trim()) {
        this.searching = true;
        this.currentPage = 1;
        this.games = [];
        this.loadGames(this.currentPage);
      } else {
        this.searching = false;
        this.currentPage = 1;
        this.games = [];
        this.loadGames(this.currentPage);
      }
    },

    debouncedPerformSearch: debounce(function () {
      this.performSearch();
    }, 300),

    loadGames(page) {
      if (this.loading) return;

      this.loading = true;
      const loadMethod = this.searching
        ? GameService.searchGames
        : GameService.retrieveGames;

      loadMethod(page, this.nameFilter)
        .then((response) => {
          console.log("Games retrieved successfully:", response.data.results);
          this.games = this.games.concat(response.data.results);
          this.totalPages = Math.ceil(response.data.count / 12);
          this.loading = false;
        })
        .catch((error) => {
          console.error("Error retrieving games:", error);
          this.loading = false;
        });
    },

    checkScroll() {
      const scrollContainer = this.$refs.scrollContainer;
      const scrollHeight = scrollContainer.scrollHeight;
      const scrollTop = scrollContainer.scrollTop;
      const clientHeight = scrollContainer.clientHeight;
      const scrollPosition = (scrollTop + clientHeight) / scrollHeight;

      if (
        scrollPosition >= 0.75 &&
        this.currentPage < this.totalPages &&
        !this.loading
      ) {
        this.currentPage += 1;
        this.loadGames(this.currentPage);
      }
    },
  },
};

// Debounce function
function debounce(func, wait) {
  let timeout;
  return function (...args) {
    const later = () => {
      clearTimeout(timeout);
      func.apply(this, args); // Use func.apply to ensure correct 'this' context
    };
    clearTimeout(timeout);
    timeout = setTimeout(later, wait);
  };
}
</script>

<style scoped>
.page-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  height: 150vh;
  overflow: hidden;
  color: white;
  padding-top: 3rem;
}

.content-container {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 0 auto;
  overflow: hidden;
  position: relative;
  z-index: 1;
}

.search-box-container {
  display: flex;
  width: 90%;
  max-width: 1000px;
  margin-bottom: 2rem;
  align-items: center;
  gap: 10px;
}

.search-box {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.search-box label {
  font-size: 1rem;
  color: white;
  margin-bottom: 0.5rem;
}

.search-box input {
  width: 100%;
  min-height: 70px;
  padding: 0.5rem 1rem;
  border: 1px solid #0c0c0c;
  background-color: rgba(55, 55, 53, 0.711);
  color: white;
  font-size: 1rem;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.21);
}

.search-box input::placeholder {
  color: rgb(146, 140, 140);
}

.search-box input:hover {
  color: white;
  transition: white 0.3s ease;
}

.search-box input:focus::placeholder,
.search-box input:hover::placeholder {
  color: white;
}

.game-container {
  width: 85%;
  flex: 1;
  overflow-y: auto;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  background-color: rgba(52, 48, 48, 0.151);
  min-height: 100vh;
  border-radius: 30px;
  padding: 2%;
  height: 800px;
  position: relative;
  gap: 20px; /* Add spacing between cards */
}



.no-games-message {
  font-size: 1.2rem;
  color: #fffefe;
  text-align: center;
  margin: 20px 0;
}

.loading-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 9999;
}

.loading-overlay img {
  width: 300px;
  height: 300px;
}

.video-background {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  z-index: 1;
  background-size: cover;
}

.background-video {
  position: absolute;
  top: 50%;
  left: 50%;
  min-width: 100%;
  min-height: 100%;
  object-fit: contain;
  transform: translate(-50%, -50%);
}

::-webkit-scrollbar {
  width: 5px;
}

::-webkit-scrollbar-track {
  background-color: #fffefe00;
  border-radius: 10px;
}

::-webkit-scrollbar-thumb {
  background: rgba(136, 136, 153, 0);
  border-radius: 10px;
}

::-webkit-scrollbar-thumb:hover {
  background-color: #555;
}

.header {
  font-size: 2.0rem;
  margin-bottom: 2rem;
  text-align: center;
  margin-top: 2rem;
  font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande', 'Lucida Sans', Arial, sans-serif;
}

.flame {
  animation: colorFlow 20s ease-in-out infinite, scaleFlow 1.2s ease-in-out infinite;
}

@keyframes colorFlow {
  0% {
    color: #ff4400;
  }
  25% {
    color: #d32406;
  }
  50% {
    color: #f85b00;
  }
  75% {
    color: #fa8704;
  }
  100% {
    color: #ff4400;
  }
}

@keyframes scaleFlow {
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.1);
  }
  100% {
    transform: scale(1);
  }
}

.custom-footer {
  padding-top: 1rem;
  padding-bottom: 1.5rem;
  margin-top: 1.5rem;
  text-align: center;
  background-color: rgba(10, 9, 8, 0);
  z-index: 1;
}

.nav-list {
  list-style-type: none;
  padding: 0;
  margin: 0;
  display: flex;
  justify-content: center;
  border-bottom: 1px solid rgba(201, 95, 8, 0.562);
  padding-bottom: 1rem;
  margin-bottom: 1rem;
}

.nav-link {
  text-decoration: none;
  color: rgb(255, 255, 255);
  padding-left: .5rem;
  padding-right: .5rem;
  font-size: 1rem;
}

.nav-link:hover {
  color: orange;
  text-decoration: underline;
}

.footer-text {
  color: white;
}

.lantern-color {
  color: #F5C277;
}

@media (max-width: 600px) {
  .game-card {
    width: calc(50% - 20px); /* Adjust width for smaller screens */
  }
  .loading-overlay {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
  }

  .loading-overlay img {
    width: 300px;
    height: 300px;
  }
}
</style>
