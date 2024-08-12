<template>
  <div class="game-card">
    <router-link
      :to="{ name: 'game-details', params: { gameId: game.id } }"
      class="game-link"
    >
      <img
        :src="
          game.background_image ||
          'https://via.placeholder.com/300x400.png?text=No+Image+Available'
        "
        alt="Game image"
        class="game-image"
        loading="lazy"
      />
      <div class="card-content">
        <div class="text-content">
          <h2 class="game-title">{{ game.name || "Unknown Title" }}</h2>
          <p class="game-rating">Critic's Rating: {{ game.rating || "N/A" }}</p>
          <p class="lantern-db-rating">LanternDB Rating: {{ lanternDbRating || "N/A" }}</p>
        </div>
      </div>
    </router-link>
    <!-- Show the dropdown only if the user is logged in -->
    <div v-if="isLoggedIn" class="dropdown" @click.stop>
      <button class="button-menu" @click="toggleDropDown">
        <i class="bi bi-menu-button"></i>
      </button>
      <div
        class="dropdown-menu"
        :class="{ 'dropdown-menu-show': isDropDownOpen }"
      >
        <div class="dropdown-item" @click="addToCollection(1)">
          <i class="bi bi-heart" style="color: rgb(244, 130, 9)"></i> Add to
          Wishlist
        </div>
        <div class="divider-horizontal"></div>
        <div class="dropdown-item" @click="addToCollection(2)">
          <i class="bi bi-play-circle" style="color: rgb(244, 130, 9)"></i> Add
          to Playing
        </div>
        <div class="divider-horizontal"></div>
        <div class="dropdown-item" @click="addToCollection(3)">
          <i class="bi bi-check-circle" style="color: rgb(244, 130, 9)"></i> Add
          to Played
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import CollectionService from "../services/CollectionService";
import GameService from "../services/GameService"; // Import your GameService to fetch ratings

export default {
  props: {
    game: Object,
  },
  data() {
    return {
      isDropDownOpen: false,
      lanternDbRating: null, // Store the LanternDB rating
    };
  },
  computed: {
    isLoggedIn() {
      const user = this.$store.state.user;
      return !!user && !!user.id; // Check if user is logged in
    },
  },
  methods: {
    toggleDropDown() {
      this.isDropDownOpen = !this.isDropDownOpen;
    },
    closeDropDown() {
      this.isDropDownOpen = false;
    },
    handleClickOutside(event) {
      if (this.isDropDownOpen && !this.$el.contains(event.target)) {
        this.closeDropDown();
      }
    },
    addToCollection(collection_id) {
      const genre =
        this.game.genres && this.game.genres.length > 0
          ? this.game.genres[0].name
          : "Unknown Genre";

      const gameData = {
        title: this.game.name,
        genre: genre,
        collection_id,
      };

      CollectionService.addToCollections(gameData)
        .then((response) => {
          console.log(`Game added to ${collection_id} collection`, response);
          this.closeDropDown(); // Close the dropdown after adding to the collection
        })
        .catch((error) => {
          console.error(
            `Error adding game to ${collection_id} collection`,
            error
          );
          this.closeDropDown(); // Close the dropdown even if there's an error
        });
    },
    fetchLanternDbRating() {
      GameService.getLanternDbRatings(this.game.id)
        .then((response) => {
          if (response.data.length > 0) {
            const ratings = response.data.map(rating => rating.rating_score);
            this.lanternDbRating = (ratings.reduce((sum, rating) => sum + rating, 0) / ratings.length).toFixed(1);
          } else {
            this.lanternDbRating = "N/A";
          }
        })
        .catch((error) => {
          console.error("Error fetching LanternDB rating:", error);
          this.lanternDbRating = "N/A";
        });
    },
  },
  mounted() {
    document.addEventListener("click", this.handleClickOutside);
    this.fetchLanternDbRating(); // Fetch the LanternDB rating when the component is mounted
  },
  beforeUnmount() {
    document.removeEventListener("click", this.handleClickOutside);
  },
};
</script>

<style scoped>
.game-card {
  width: 200px;
  box-shadow: 1px 4px 8px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  overflow: hidden;
  position: relative;
  margin: 2%;
  background-color: #28272900;
}

.game-link {
  text-decoration: none;
  color: inherit;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.game-image {
  width: 100%;
  height: 220px;
  object-fit: cover;
}

.text-content {
  flex: 1;
}

.card-content {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: space-around;
}

.game-title {
  font-size: 1.2em;
  margin: 0;
}

.game-rating {
  color: #666;
  margin-top: 8px;
}

.lantern-db-rating {
  color: #666;
}

.dropdown {
  position: absolute;
  bottom: 10px;
  right: 10px;
  z-index: 1003;
}

.button-menu {
  background: #333;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  padding: 7px;
  font-size: 1rem;
  transition: #333 0.3s, transform 0.3s;
  display: flex;
  align-items: center;
  justify-content: center;
  color: rgb(244, 130, 9);
}

.button-menu:hover {
  background-color: #1b1919;
  transform: scale(1.1);
}

.button-menu:focus {
  outline: none;
}

.dropdown-menu {
  position: absolute;
  bottom: 100%;
  right: 0;
  background-color: #393434;
  color: white;
  border: 1px solid black;
  box-shadow: 0px 8px 16px rgba(0, 0, 0, 0.3);
  width: 180px;
  display: none;
  z-index: 1000;
  overflow: visible;
}

.dropdown-menu-show {
  display: block;
}

.dropdown-item {
  display: flex;
  align-items: center;
  padding: 10px;
  text-decoration: none;
  cursor: pointer;
  color: white;
}

.dropdown-item i {
  margin-right: 8px;
}

.divider-horizontal {
  height: 1px;
  background-color: #00000033;
  margin: 5px 0;
}

@media (max-width: 600px) {
  .game-card {
    width: 20%;
    height: auto;
    margin: 1%;
  }
}
</style>
