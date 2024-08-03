<template>
  <div class="page-container">
    <div class="content-container">
    <h1 class="header">Game Search</h1>

    <div class="search-box">
      <label for="">Find Your Next Game</label>
      <input type="text" name="gameName" id="gameName" v-model="nameFilter" />
    </div>

    <div class="game-container">
      <game-card v-bind:game="game" v-bind:key="game.id" v-for="game in filteredGames" />
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
      return this.games.filter((game) => {
        return this.nameFilter === '' || game.name.includes(this.nameFilter);
      });
    },
  },

  created() {
    GameService.retrieveGames().then((response) => {
      this.games = response.data.results;
    }).catch((error) => {
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
  max-width: 90%;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 3%;
}
.page-container {
  display: flex;
  justify-content: center;
}

</style>
