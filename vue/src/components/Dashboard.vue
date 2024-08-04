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
          
        ],
        playing: [
          
        ],
        played: [

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