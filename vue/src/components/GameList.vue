<template>
  <div class="page-container">
    <div class="content-container">
      <h1 class="header">Game Search</h1>

      <div class="search-box">
        <label for="gameName">Find Your Next Game</label>
        <input type="text" name="gameName" id="gameName" v-model="nameFilter" />
      </div>

      <div class="game-container">
        <game-card
          v-bind:game="game"
          v-bind:key="game.id"
          v-for="game in filteredGames"
        />
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
    };
  },

  computed: {
    filteredGames() {
      const filterText = this.nameFilter.toLowerCase(); // Convert filter text to lowercase

      return this.games.filter((game) => {
        // Ensure game and game.title are defined
        if (!game || !game.name) {
          return false;
        }

        const gameTitle = game.name.toLowerCase(); // Convert game title to lowercase
        return filterText === "" ? true : gameTitle.includes(filterText); // Perform case-insensitive comparison
      });
    },
  },

  created() {
    console.log("Component created, attempting to retrieve games...");
    GameService.retrieveGames()
      .then((response) => {
        console.log("Games retrieved successfully:", response.data.results);
        this.games = response.data.results;
      })
      .catch((error) => {
        console.error("Error retrieving games:", error);
      });
  },
};
</script>

<style>
.game-container {
  /* Game Container Alignment */
  display: flex;
  flex-wrap: wrap;

  /* GameCard Alignment */
  gap: 16px;
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
.page-container {
  display: flex;
  justify-content: center;
  width: 100%;
}
</style>
