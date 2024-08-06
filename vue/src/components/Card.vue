<template>
  <div class="dashboard-card">
    <h3>{{ title }}</h3>
    <ul>
      <li v-for="item in items" :key="item.collectionListId">{{ item.title }}</li>

        <div class="dropdown">
          <button
            class="btn btn-secondary dropdown-toggle"
            type="button"
            data-bs-toggle="dropdown"
            aria-expanded="false"
          >
          </button>
          <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="#" @click="addToCollection(1)">Add to Wishlist</a></li>
            <li><a class="dropdown-item" href="#" @click="addToCollection(2)">Add to Playing</a></li>
            <li><a class="dropdown-item" href="#" @click="addToCollection(3)">Add to Played</a></li>
          </ul>
        </div>
    </ul>
  </div>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      required: true
    },
    items: {
      type: Array,
      required: true
    }
  }
};
</script>

<style scoped>
.dashboard-card {
  background-color: #ffffff;
  border: 1px solid #dcdcdc;
  border-radius: 8px;
  padding: 16px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  margin: 16px;
}
.dashboard-card h3 {
  margin-top: 0;
}
.dashboard-card ul {
  list-style-type: none;
  padding-left: 0;
}
.dashboard-card li {
  padding: 8px 0;
  border-bottom: 1px solid #dcdcdc;
}
.dashboard-card li:last-child {
  border-bottom: none;
}
</style>
