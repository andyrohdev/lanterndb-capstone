<template>
  <div class="dashboard-container">
    <div class="header">
    </div>
  </div>
  <div class="dashboard">
    <Card :key="'wishlist'" title="Wishlist" :items="wishlistItems" />
    <Card :key="'playing'" title="Playing" :items="playingItems" />
    <Card :key="'played'" title="Played" :items="playedItems" />
  </div>
</template>

<script>
import Card from "@/components/Card.vue";
import CollectionService from "../services/CollectionService";

export default {
  components: {
    Card,
  },
  data() {
    return {
      allItems: [],
    };
  },
  computed: {
    wishlistItems() {
      return this.allItems.filter(item => item.collection_id === 1);
    },
    playingItems() {
      return this.allItems.filter(item => item.collection_id === 2);
    },
    playedItems() {
      return this.allItems.filter(item => item.collection_id === 3);
    },
  },
  created() {
    this.fetchCollections();
  },
  methods: {
    fetchCollections() {
      Promise.all([
        CollectionService.getCollections(1),
        CollectionService.getCollections(2),
        CollectionService.getCollections(3)
      ])
      .then((responses) => {
        this.allItems = [
          ...responses[0].data,
          ...responses[1].data,
          ...responses[2].data
        ];
      })
      .catch((error) => {
        console.error("Error retrieving collections", error);
      });
    }
  }
};
</script>

<style scoped>
.dashboard {
  display: flex;
  flex-wrap: wrap; /* Allows cards to wrap onto the next line */
  justify-content: space-around;
  padding: 20px;
  background: #121212; /* Dark background like GameDetails */
  color: #e0e0e0; /* Light text for contrast */
}

@media (max-width: 768px) {
  .dashboard {
    flex-direction: column;
    align-items: center;
  }
}
</style>
