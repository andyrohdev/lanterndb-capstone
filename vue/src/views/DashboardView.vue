<template>
    <div class="dashboard-container">
      <header class="dashboard-header">
       <div class = "logo">Game Logo</div>
       <div class="navigation">
        <div >
            <router-link to = "/home">Home</router-link>
        </div>
        <div>
            <router-link to = "/profile">Profile</router-link>
        </div>
        <div>
            <router-link to = "/settings">Settings</router-link>
        </div>
        
        <div>
            <router-link to = "/store">Store</router-link>
        </div>
        <div>
            <router-link to = "/wishlist">wishlist</router-link>
        </div>
        <div>
            <router-link to = "/playing">playing</router-link>
        </div>
        <router-link to = "/played">played</router-link>
       </div>
       <div ><i class="bi bi-person-circle"></i></div>
       
       <div class="user-info"></div>
       
      </header>
  
      <main class="dashboard-main">
       
      </main>
     
      <footer class="dashboard-footer">
       
      </footer>
    </div>
  </template>
  <script>
//   import UserProfile from '@/components/UserProfile.vue';
//   import Statistics from '@/components/Statistics.vue';
//   import Achievements from '@/components/Achievements.vue';
//   import Friends from '@/components/Friends.vue';
//   import GameNews from '@/components/GameNews.vue';
  
//   export default {
//     components: {
//     //   UserProfile,
//     //   Statistics,
//     //   Achievements,
//     //   Friends,
//     //   GameNews
//     // },
//     data() {
//       return {
//         user: {
//           profilePicture: 'profile-pic.jpg',
//           username: 'PlayerOne',
//           level: 25,
//           xpProgress: 70,
//           achievements: [
//             { id: 1, image: 'badge1.jpg' },
//             { id: 2, image: 'badge2.jpg' }
//           ]
//         },
//         statistics: {
//           totalPlaytime: 120,
//           matchesPlayed: 300,
//           winLossRatio: 1.5,
//           recentMatches: [
//             { id: 1, summary: 'Match 1: Win' },
//             { id: 2, summary: 'Match 2: Loss' },
//             { id: 3, summary: 'Match 3: Win' }
//           ]
//         },
//         achievements: [
//           { id: 1, name: 'Achievement 1', status: 'Completed' },
//           { id: 2, name: 'Achievement 2', status: 'In Progress' },
//           { id: 3, name: 'Achievement 3', status: 'Locked' }
//         ],
//         rewards: {
//           currency: 5000
//         },
//         friends: [
//           { id: 1, name: 'Friend 1', status: 'Online' },
//           { id: 2, name: 'Friend 2', status: 'Offline' },
//           { id: 3, name: 'Friend 3', status: 'Online' }
//         ],
//         recentActivity: [
//           { id: 1, summary: 'Friend 1 unlocked an achievement' }
//         ],
//         newsList: [
//           { id: 1, title: 'Update 1.1 Released', content: 'New features and bug fixes...' },
//           { id: 2, title: 'New Event Announced', content: 'Join the summer event starting next week...' }
//         ]
//       };
//     }
//   }
//   </script>
  
  <style>
  .dashboard-container {
    display: flex;
    flex-direction: column;
    min-height: 100vh;
  }
  .dashboard-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 20px;
    background-color: #2c3e50;
    color: white;
  }
  .logo {
    font-size: 24px;
    font-weight: bold;
  }
  .navigation {
    display: flex;
    gap: 15px;
  }
  .navigation a {
    color: white;
    text-decoration: none;
  }
  .user-info {
    display: flex;
    align-items: center;
    gap: 10px;
  }
  .user-info img {
    width: 40px;
    height: 40px;
    border-radius: 50%;
  }
  .dashboard-main {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
    gap: 20px;
    padding: 20px;
    background: #ecf0f1;
    flex-grow: 1;
  }
  .dashboard-footer {
    display: flex;
    justify-content: space-between;
    padding: 10px 20px;
    background-color: #2c3e50;
    color: white;
  }
  .dashboard-footer a {
    color: white;
    text-decoration: none;
    margin-right: 15px;
  }
  .social-media a {
    margin-left: 10px;
  }
  </style>