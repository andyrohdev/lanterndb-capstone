<template>
    <nav class="navbar">
      <div class="navbar-left">
        <router-link to="/" class="logo-container" v-if="showLogo || showSiteName">
             <img v-if="showLogo" src="../assets/logoPlaceholder.png" alt="Logo" class="logo">
        <span class="website-name" v-if="showSiteName">LanternDB</span>
        </router-link>
      </div>
      <div class="navbar-right">

        <div v-if="isAdmin" class="admin-button">
            <router-link to="/admin" class="navbar-link">Admin</router-link>
        </div>


        <div class="browser-button">
            <router-link v-if="ShowBrowserLink" to="/games" class="navbar-link">Browse</router-link>
        </div>
        <div class="divider"></div>
        <div class="profile-link">
            <router-link v-if="showProfileLink" to="/dashboard" class="navbar-link"><i class="bi bi-person-circle"></i></router-link>
        </div>
      </div>
    </nav>
</template>

<script>
import 'bootstrap-icons/font/bootstrap-icons.css'

export default{
    props:{
        showLogo: {
            type: Boolean,
            default: true
        },
        showSiteName: {
            type: Boolean,
            default: true
        },
        ShowBrowserLink:{
            type: Boolean,
            default: true
        },
        showProfileLink: {
            type: Boolean,
            default: true
        }
    },
    computed: {
        isAdmin() {
            const user = this.$store.state.user;
            if (user.authorities && user.authorities.length > 0) {
               return user.authorities[0].name === 'ROLE_ADMIN';
            }
            return false;
            
        }
    }
}

</script>

<style scoped>
.navbar{
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: rgb(5, 5, 5);
    color: white;
    padding: 10px;
    font-family:'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande', 'Lucida Sans', Arial, sans-serif
}
.logo-container{
    text-decoration: none;
    color: white;
    display: flex;
    align-items: center;
    position: relative;
    overflow: hidden;
}
.logo-container::before{
    content: '';
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 1px;
    background: linear-gradient(45deg, rgba(255, 165, 0, 0.7), rgb(233, 89, 28));
    transform: scaleX(0);
    transform-origin: bottom right;
    transition: transform 0.5s ease-in-out; 
    z-index: 1;
}
.logo-container:hover::before {
  transform: scaleX(1);
  transform-origin: bottom left;
}
.logo{
    height: 60px;
    margin-right: 10px;
    position: relative;
    z-index: 2;
}

.navbar-left{
    display: flex;
    align-items: center;

}
.navbar-right{
    display: flex;
    align-items: center;
    gap: 5px;
    

}

.website-name{
    font-size: 25px;
    z-index: 2;
}

.navbar-link{
    text-decoration: none;
    padding: 10px;
    display: flex;
    align-items: center;
    font-size: 15px;
    color: white;
    border-bottom: 2px solid transparent;
    transition: border-color 0.3s ease;
    
}
.navbar-link:hover{
    border-bottom: 2px solid rgb(158, 54, 10);
}
.navbar-link::after{
    content: '';
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 2px;
    background-color: rgb(158, 54, 10);
    transform: scaleX(0);
    transform-origin: bottom right;
    transition: transform 0.4s ease; 
}
.navbar-link:hover::after{
    transform: scaleX(1);
    transform-origin: bottom left;
}

.navbar-right .divider{
    margin-left: 24px;
    margin-right: 24px;
    width: 1px;
    height: 36px;
    background-color: rgb(226, 211, 190);
    min-width: 1px;
}
 .navbar-link{
    font-size: 20px;
}


</style>