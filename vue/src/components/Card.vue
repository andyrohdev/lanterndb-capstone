<template>
  <div class="dashboard-card">
    <h3>{{ title }}</h3>
    <div v-if="items.length > 0" class="games-grid">
      <CollectionGameCard
        v-for="item in items"
        :key="item.collection_list_id"
        :game="item"
        :collection-title="title"
        @updateCollections="refreshCollections"
      />
    </div>
    <div v-else class="no-games-message">
      No games found in collection.
    </div>
  </div>
</template>

<script>
import CollectionGameCard from './CollectionGameCard.vue';
import CollectionService from '../services/CollectionService';

export default {
  components: {
    CollectionGameCard
  },
  props: {
    title: {
      type: String,
      required: true
    },
    items: {
      type: Array,
      required: true
    }
  },
  methods: {
    refreshCollections() {
      this.$emit('refreshCollections');  // Emit event to parent component
    }
  }
};
</script>



<style scoped>
.dashboard-card {
  background-color: #1e1e1e;
  border: 1px solid #333;
  border-radius: 8px;
  padding: 16px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  margin: 16px;
  width: 100%;
}

.dashboard-card h3 {
  margin-top: 0;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  flex-shrink: 0;
}

.games-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
  gap: 10px;
  margin-top: 20px;
}

.no-games-message {
  text-align: center;
  color: #888;
  font-size: 1rem;
  margin-top: 20px;
}
</style>
