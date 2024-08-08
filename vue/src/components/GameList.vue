<template>
  <div class="page-container">
    <div class="video-background">
      <video autoplay muted loop class="video-back">
      <source src="../assets/Ember_Overlay_with_Blur_1.mp4" type="video/mp4">
    </video>
    </div>
    <div class="content-container">
      <h1 class="header">Game Search</h1>
      <div class="search-box">
        <label for="gameName">Find Your Next Game</label>
        <input
          type="text"
          name="gameName"
          id="gameName"
          v-model="nameFilter"
          autocomplete="off"
          placeholder="Search for a game..."
        />
      </div>

      <div class="game-container" ref="scrollContainer">
        <!-- Display "No games found" if there are no games -->
        <div v-if="!loading && filteredGames.length === 0" class="no-games-message">
          No games found.
        </div>

        <game-card
          v-bind:game="game"
          v-bind:key="game.id"
          v-for="game in filteredGames"
        />
      </div>

      <!-- Loading Spinner -->
      <!-- <div v-if="loading" class="loading-spinner">Loading...</div> -->
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

    debouncedPerformSearch: debounce(function() {
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
  return function(...args) {
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

html{
  margin: 0;
  padding: 0;
  height: 100%;
  overflow: hidden;
}
.page-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  height: 100vh; /* Full height to allow scrolling */
  overflow: hidden;
  color: white; /* Prevent page-level scrolling */
  padding-top: 3rem;
  
}

.content-container {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 0 auto;
  padding: 3%;
  overflow: hidden;
  position: relative;
  z-index: 1;
  
   /* Prevent content-level scrolling */
  
  }

.search-box {
  width: 100%;
  max-width: 600px;
  margin-bottom: 2rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-bottom: 3%;
  
}
.search-box label{
  font-size: 1rem;
  color: white;
  margin-bottom: 0.5rem;
}

.search-box input{
  width: 100%;
  max-width: 100%;
  padding: 0.5rem 1rem;
  border: 1px solid #0c0c0c;
  background-color: #55504bb2;
  color: white;
  font-size: 1rem;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.21);
}

.search-box input::placeholder{
  color: rgb(146, 140, 140);
}
.search-box input:hover{
  color: white;
  transition: white 0.3s ease;
}
.search-box input:focus::placeholder,
.search-box input:hover::placeholder{
  color: white;
}



.game-container {
  width: 85%;
  flex: 1; /* Grow to fill the available space */
  overflow-y: auto; /* Scrollable content */
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  /* Add some padding for better spacing */
  background-color: rgba(52, 48, 48, 0.151);
  height:100vh;
  border-radius: 30px;
  

}

.no-games-message {
  font-size: 1.2rem;
  color: #fffefe;
  text-align: center;
  margin: 20px 0;
}

.loading-spinner {
  margin: 20px;
}

.video-background{
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  z-index: -1;
  background-size: cover;

}
.background-video{
  position: absolute;
  top: 50%;
  left: 50%;
  min-width: 100%;
  min-height: 100%;
  object-fit:contain;
  transform: translate(-50%, -50%);
}
::-webkit-scrollbar {
  width: 5px;
  
}
::-webkit-scrollbar-track{
  background-color: #fffefe00;
  border-radius: 10px;
}
::-webkit-scrollbar-thumb{
  background: rgba(136, 136, 153, 0);
  border-radius: 10px;

}
::-webkit-scrollbar-thumb:hover{
  background-color: #555;
}



@media (max-width: 600px) {
  .game-card {
    width: 48%; /* Adjust width to 48% to allow for margins */
  }
}
</style>


