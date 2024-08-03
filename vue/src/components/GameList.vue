<template>
  <div>
    <h1>Game Search</h1>

    <div>
      <label for="">Search Game: </label>
      <input type="text" name="gameName" id="gameName" v-model="nameFilter" />
    </div>

    <div class="game-container">
      <game-card v-bind:game="game" v-bind:key="game.title" v-for="game in filteredGames" />
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
          return this.games.filter( (game) => {
            return this.nameFilter == '' ? true : game.title.includes(this.nameFilter);
        });
    }
  },
  methods: {
    
  },
  created() {
    GameService.retrieveGames().then((response) => {
      this.games = response.data;
    });
  }
};
</script>

<style>
</style>