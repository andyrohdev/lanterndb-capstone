<template>
    <div class="admin-user-profile">
      <h1>User Profile</h1>
      <div v-if="user">
        <h2>{{ user.username }}</h2>
        <p><strong>Role:</strong> {{ user.authorities[0].name }}</p>
        <h3>{{ user.id }}</h3>
        <!-- Add more user details as needed -->
      </div>
      <div v-else>
        <p>Loading user information...</p>
      </div>
    </div>
  </template>
  
  <script>
  import adminService from "../services/AdminService";
  
  export default {
    data() {
      return {
        user: null
      };
    },
    created() {
      const userId = parseInt(this.$route.params.userId, 10);
      this.fetchUserDetails(userId);
    },
    methods: {
      fetchUserDetails(userId) {
        adminService.fetchUsers()
          .then(response => {
            this.user = response.data.find(user => user.id === userId);
          })
          .catch(error => {
            console.error("Error fetching user details:", error);
          });
      }
    }
  };
  </script>
  
  <style scoped>
  .admin-user-profile {
    padding: 20px;
  }
  </style>
  