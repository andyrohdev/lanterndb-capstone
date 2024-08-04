<template>
  <div class="page-container" ref="scrollContainer">
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

      <div class="game-container">
        <game-card
          v-bind:game="game"
          v-bind:key="game.id"
          v-for="game in filteredGames"
        />
      </div>

      <!-- Loading Spinner -->
      <div v-if="loading" class="loading-spinner">Loading...</div>
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
      totalPages: 10, // Update this dynamically if needed
      loading: false, // Loading state to prevent multiple requests
    };
  },

  computed: {
    filteredGames() {
      const filterText = this.nameFilter.toLowerCase();
      return this.games.filter((game) => {
        if (!game || !game.name) {
          return false;
        }
        const gameTitle = game.name.toLowerCase();
        return filterText === "" ? true : gameTitle.includes(filterText);
      });
    },
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
    loadGames(page) {
      if (this.loading) return; // Prevent multiple requests

      this.loading = true;
      GameService.retrieveGames(page)
        .then((response) => {
          console.log("Games retrieved successfully:", response.data.results);
          this.games = this.games.concat(response.data.results);
          this.loading = false;
        })
        .catch((error) => {
          console.error("Error retrieving games:", error);
          this.loading = false;
        });
    },

    checkScroll() {
      const scrollContainer = this.$refs.scrollContainer;
      console.log(
        "Scroll position:",
        scrollContainer.scrollTop,
        "Scroll height:",
        scrollContainer.scrollHeight,
        "Client height:",
        scrollContainer.clientHeight
      );
      const bottom =
        scrollContainer.scrollHeight <=
        scrollContainer.scrollTop + scrollContainer.clientHeight + 1;
      console.log("At bottom:", bottom);

      if (bottom && this.currentPage < this.totalPages) {
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
  justify-content: center;
  width: 100%;
  overflow-y: auto; /* Ensure scrolling */
  height: 100vh; /* Make sure it's tall enough to scroll */
}


.game-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}

.search-box {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-bottom: 3%;
}

.content-container {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 0 auto;
  padding: 3%;
}

.loading-spinner {
  margin: 20px;
}
</style>
