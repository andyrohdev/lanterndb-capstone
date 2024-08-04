<template>
  <div class="dashboard-container">
    <div class="header">
      <div class="title">Dashboard</div>
      <div class="profile-icon">
        <i class="bi bi-person-circle"></i>
      </div>
    </div>
  </div>
  <div class="dashboard">
    <Card v-bind:key="wishlist" title="Wishlist" :items="wishlist" />
    <Card v-bind:key="playing" title="Playing" :items="playing" />
    <Card v-bind:key="played" title="Played" :items="played" />

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
  </div>
</template>
  
  <script>
  import Card from '@/components/Card.vue';
  import CollectionService from '../services/CollectionService';


  export default {
    components: {
      Card
    },
    data() {
      return {
        wishlist: [
        
          
        ],
        playing: [
        
        ],
        played: [
        
        ]
        
      };
    },
    created() {
          
      CollectionService.getCollections(1).then((response) => {
        this.wishlist = response.data;
      })
      .catch((error)=> {
        console.error('error retrieving collection', error)
      })

    },
     
  
};
  
  
  </script>
  
  <style scoped>
.dashboard {
  display: flex;
  justify-content: space-around;
  padding: 20px;
  background: #ecf0f1;
}
</style>