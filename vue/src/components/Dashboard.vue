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
      <Card title="Wishlist" :items="wishlist" />
      <Card title="Playing" :items="playing" />
      <Card title="Played" :items="played" />

      <div class="search-box">
        <label for="gameName">Find Your Next Game</label>
        <input type="text" name="gameName" id="gameName" v-model="nameFilter" autocomplete="off"/>
      </div>
    </div>
  </template>
  
  <script>
  import Card from '@/components/Card.vue';


  export default {
    components: {
      Card
    },
    data() {
      return {
        wishlist: [
        { id: 1, name: 'Game 1' },
        { id: 2, name: 'Game 2' },
        { id: 3, name: 'Game 3' },
          
        ],
        playing: [
        { id: 1, name: 'Game 4' },
        { id: 2, name: 'Game 5' },
        ],
        played: [
        { id: 1, name: 'Game 6' },
        { id: 2, name: 'Game 7' },
        { id: 3, name: 'Game 8' },
        ]
        
      };
    }
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