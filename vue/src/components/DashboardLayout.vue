<template>
    <div class="dashboard">
      <header class="dashboard-header">
        <div class="logo">
          <img src="path/to/logo.png" alt="Logo">
          <span>AppName</span>
        </div>
        <div class="user-profile">
          <img src="path/to/user-profile.jpg" alt="User Profile" class="profile-icon">
        </div>
      </header>
      <main class="dashboard-content">
        <slot></slot>
      </main>
      <footer class="dashboard-footer">
        <p>&copy; 2024 AppName. All rights reserved.</p>
      </footer>
    </div>
  </template>
  
  <script>
  export default {
    name: 'DashboardLayout'
  };
  </script>
  
  <style scoped>
  .dashboard {
    display: flex;
    flex-direction: column;
    min-height: 100vh;
  }
  
  .dashboard-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 16px;
    background-color: #333;
    color: #fff;
  }
  
  .logo {
    display: flex;
    align-items: center;
  }
  
  .logo img {
    height: 40px;
    margin-right: 8px;
  }
  
  .user-profile .profile-icon {
    height: 40px;
    width: 40px;
    border-radius: 50%;
  }
  
  .dashboard-content {
    flex: 1;
    padding: 16px;
  }
  
  .dashboard-footer {
    padding: 16px;
    background-color: #333;
    color: #fff;
    text-align: center;
  }
  </style>