<template>
    <div class="admin-page">
      <h1>Admin Page</h1>
      <div class="user-cards">
        <div v-for="user in users" :key="user.id" class="user-card">
          <h3>{{ user.username }}</h3>
          <p>{{ user.authorities[0].name }}</p>
          <button 
            :disabled="user.id === loggedInUserId" 
            :class="{ disabled: user.id === loggedInUserId }"
            @click="removeUser(user.id)"
          >
            Delete
          </button>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import adminService from "../services/AdminService";
  import { mapState } from "vuex";
  
  export default {
    data() {
      return {
        users: []
      };
    },
    computed: {
      ...mapState(["user"]),
      loggedInUserId() {
        return this.user.id;
      }
    },
    created() {
      this.fetchUsers();
    },
    methods: {
      fetchUsers() {
        adminService.fetchUsers()
          .then(response => {
            this.users = response.data;
          })
          .catch(error => {
            console.error("Error fetching users:", error);
          });
        },
        removeUser(userId) {
          adminService.deleteUser(userId)
            .then(() => {
              this.users = this.users.filter(user => user.id !== userId);
            })
            .catch(error => {
              console.error("Error deleting user:", error);
            });
        }
      }
    };
  </script>
  
  <style scoped>
  .admin-page {
    padding: 20px;
  }
  
  .user-cards {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
  }
  
  .user-card {
    background-color: #f8f9fa;
    border: 1px solid #dee2e6;
    border-radius: 5px;
    padding: 15px;
    width: 200px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    text-align: center;
  }
  
  .user-card h3 {
    margin: 0;
    font-size: 1.2em;
  }
  
  .user-card p {
    margin: 5px 0 0;
    font-size: 1em;
    color: #6c757d;
  }
  
  .user-card button {
    margin-top: 10px;
    padding: 5px 10px;
    background-color: #dc3545;
    color: white;
    border: none;
    border-radius: 3px;
    cursor: pointer;
  }
  
  .user-card button:hover:not(.disabled) {
    background-color: #c82333;
  }
  
  .user-card button.disabled {
    background-color: #6c757d;
    cursor: not-allowed;
  }
  </style>
  