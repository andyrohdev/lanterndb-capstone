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
        <i class="bi bi-plus"></i>
      </button>
      <div
        class="dropdown-menu"
        :class="{ 'dropdown-menu-show': isDropDownOpen }"
      >
        <div
          class="dropdown-item"
          @click="handleItemClick(1)"
          :class="{ disabled: isItemDisabled(1) }"
        >
          <i class="bi bi-heart" :style="{ color: isItemDisabled(1) ? '#666' : 'rgb(244, 130, 9)' }"></i>
          Add to Wishlist
        </div>
        <div class="divider-horizontal"></div>
        <div
          class="dropdown-item"
          @click="handleItemClick(2)"
          :class="{ disabled: isItemDisabled(2) }"
        >
          <i class="bi bi-play-circle" :style="{ color: isItemDisabled(2) ? '#666' : 'rgb(244, 130, 9)' }"></i>
          Add to Playing
        </div>
        <div class="divider-horizontal"></div>
        <div
          class="dropdown-item"
          @click="handleItemClick(3)"
          :class="{ disabled: isItemDisabled(3) }"
        >
          <i class="bi bi-check-circle" :style="{ color: isItemDisabled(3) ? '#666' : 'rgb(244, 130, 9)' }"></i>
          Add to Played
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import CollectionService from "../services/CollectionService";
import GameService from "../services/GameService";
import { useToast } from 'vue-toastification';

export default {
  props: {
    game: Object,
  },
  data() {
    return {
      isDropDownOpen: false,
      lanternDbRating: null,
      addedCollections: new Set(),
      toast: useToast(),
    };
  },
  computed: {
    isLoggedIn() {
      const user = this.$store.state.user;
      return !!user && !!user.id;
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
    showNotification(message, type = 'success') {
      switch (type) {
        case 'success':
          this.toast.success(message, {
            position: 'top-center',
            timeout: 3000,
            theme: 'dark',
          });
          break;
        case 'error':
          this.toast.error(message, {
            position: 'top-center',
            timeout: 3000,
            theme: 'dark',
          });
      }
    },
    handleItemClick(collection_id) {
      if (this.isItemDisabled(collection_id)) return;

      this.checkIfGameInCollection(collection_id);
    },
    checkIfGameInCollection(collection_id) {
      GameService.getCollections(collection_id)
        .then((response) => {
          const existingGames = response.data;
          const gameAlreadyInCollection = existingGames.some(
            (game) => game.title === this.game.name
          );

          if (gameAlreadyInCollection) {
            this.showNotification("Game is already in this collection.", 'error');
            this.addedCollections.add(collection_id); // Disable button for this collection
          } else {
            this.addToCollection(collection_id);
          }
        })
        .catch((error) => {
          console.error("Error checking collection:", error);
          this.showNotification("Failed to check collection. Try again.", 'error');
        });
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
          this.addedCollections.add(collection_id);
          this.closeDropDown();
          this.showNotification("It has Successfully been added!");
        })
        .catch((error) => {
          console.error(`Error adding game to ${collection_id} collection`, error);
          this.closeDropDown();
          this.showNotification("Something went wrong!");
        });
    },
    isItemDisabled(collection_id) {
      return this.addedCollections.has(collection_id);
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
    this.fetchLanternDbRating();
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
  padding: 10px;
}

.game-title {
  font-size: 1.2em;
  margin: 0;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  width: 200px;
  box-sizing: border-box;
}

.game-rating {
  color: #666;
  margin-top: 8px;
  margin-bottom: 20px;
}

.lantern-db-rating {
  color: #666;
}

.dropdown {
  position: absolute;
  right: 0px;
  z-index: 1003;
}

.button-menu {
  background: #00000096;
  border: none;
  border-radius: 30px 0px 0px 30px;
  cursor: pointer;
  font-size: 1rem;
  transition: #33333300 0.3s, transform 0.3s;
  display: flex;
  align-items: center;
  justify-content: center;
  color: rgb(255, 115, 0);
  font-size: 1.5rem;
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
  top: 100%;
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

.dropdown-item.disabled {
  background-color: #666;
  color: #999;
  cursor: not-allowed;
}

.divider-horizontal {
  height: 1px;
  background-color: #00000033;
  margin: 5px 0;
}
.vue-toastification__toast--dark {
  background-color: #333;
  color: #050505;
  border-radius: 4px;
}

.vue-toastification__toast--dark .vue-toastification__toast-body {
  font-size: 14px;
  padding: 10px 15px;
}

.vue-toastification__toast--dark .vue-toastification__toast-icon {
  color: #fff;
}

@media (max-width: 600px) {
  .game-card {
    width: 20%;
    height: auto;
    margin: 1%;
  }
}
</style>
