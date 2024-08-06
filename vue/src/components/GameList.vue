<template>
  <div class="page-container">
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
        />
      </div>

      <div class="game-container" ref="scrollContainer">
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

    // Normalize the filterText and gameTitle for a more robust search
    return this.games.filter((game) => {
      if (!game || !game.name) {
        return false;
      }
      const gameTitle = game.name.trim().toLowerCase();

      // Check if the filterText is included in the gameTitle
      return gameTitle.includes(filterText);
    });
  },
},



  watch: {
    nameFilter: "performSearch",
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
        // Search games if there is a search term
        this.searching = true;
        this.currentPage = 1; // Reset page for search
        this.games = []; // Clear current games
        this.loadGames(this.currentPage); // Load new games based on search
      } else {
        // If search term is empty, reset to default game list
        this.searching = false;
        this.currentPage = 1; // Reset page for normal load
        this.games = []; // Clear current games
        this.loadGames(this.currentPage); // Load default games
      }
    },

    loadGames(page) {
      if (this.loading) return; // Prevent multiple requests

      this.loading = true;
      const loadMethod = this.searching
        ? GameService.searchGames
        : GameService.retrieveGames;

      loadMethod(page, this.nameFilter)
        .then((response) => {
          console.log("Games retrieved successfully:", response.data.results);
          this.games = this.games.concat(response.data.results);
          this.totalPages = Math.ceil(response.data.count / 12); // Adjust total pages
          this.loading = false;
        })
        .catch((error) => {
          console.error("Error retrieving games:", error);
          this.loading = false;
        });
    },

    normalizeString(str) {
      // Replace non-alphanumeric characters with spaces and convert to lower case
      return str
        .replace(/[^a-z0-9\s]/gi, "")
        .replace(/\s+/g, " ")
        .trim();
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
</script>


<style scoped>
.page-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  height: 100vh; /* Full height to allow scrolling */
  overflow: hidden; /* Prevent page-level scrolling */
}

.content-container {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 0 auto;
  padding: 3%;
  overflow: hidden; /* Prevent content-level scrolling */
}

.search-box {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-bottom: 3%;
}

.game-container {
  width: 100%;
  flex: 1; /* Grow to fill the available space */
  overflow-y: auto; /* Scrollable content */
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  padding: 1rem; /* Add some padding for better spacing */
}

.loading-spinner {
  margin: 20px;
}
</style>
