<template>
    <nav class="navbar">
        <div class="navbar-left">
            <router-link to="/" class="logo-container" v-if="showLogo || showSiteName">
                <img v-if="showLogo" src="../assets/lantern3.png" alt="Logo" class="logo" >
                <span class="website-name" v-if="showSiteName"><span class="title-lantern">Lantern</span><span class="DB-title">DB</span></span>
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
            <div class="profile-link" @click.stop="toggleDropDown">
                <i class="bi bi-person-circle"></i>
                <div :class="['dropdown-menu', { 'dropdown-menu-show': isDropDownOpen }]">
                    <div class="dropdown-item user-info">
                        <i class="bi bi-person"></i>
                        <span>{{ userName }}</span>
                    </div>
                    <div class="divider-horizontal"></div>
                    <router-link v-if="showProfileLink" to="/dashboard" class="dropdown-item">
                        <i class="bi bi-house-door"></i>
                        Dashboard
                    </router-link>
                    <router-link to="/Settings" class="dropdown-item">
                        <i class="bi bi-gear"></i>
                        Settings
                    </router-link>
                    <div class="divider-horizontal"></div>
                    <router-link v-if="$store.state.token" to="/logout" class="dropdown-item">
                        <i class="bi bi-box-arrow-right"></i>
                        Logout
                    </router-link>
                </div>
            </div>
        </div>
    </nav>
</template>

<script>
import 'bootstrap-icons/font/bootstrap-icons.css'

export default {
    props: {
        showLogo: {
            type: Boolean,
            default: true
        },
        showSiteName: {
            type: Boolean,
            default: true
        },
        ShowBrowserLink: {
            type: Boolean,
            default: true
        },
        showProfileLink: {
            type: Boolean,
            default: true
        },
        showDivider: {
            type: Boolean,
            default: true
        },
        
    },
    data() {
        return {
            isDropDownOpen: false,
        };
    },
    computed: {
        isAdmin() {
            const user = this.$store.state.user;
            return user.authorities && user.authorities.length > 0 && user.authorities[0].name === 'ROLE_ADMIN';
        },
        userName() {
            return this.$store.state.user.username || 'User Name';
        }
    },
    methods: {
        toggleDropDown() {
            this.isDropDownOpen = !this.isDropDownOpen;
        },
        closeDropDown() {
            this.isDropDownOpen = false;
        },
        handleClickOutside(event) {
            if (this.isDropDownOpen && !this.$el.contains(event.target)) {
                this.closeDropDown();
            }
        }
    },
    mounted() {
        document.addEventListener('click', this.handleClickOutside);
    },
    beforeDestroy() {
        document.removeEventListener('click', this.handleClickOutside);
    }
}
</script>

<style scoped>
.navbar {
  display: flex;
  position: sticky;
  top: 0;
  left: 0;
  right: 0;
  justify-content: space-between;
  align-items: center;
  background-color: rgb(5, 5, 5);
  color: white;
  padding: 10px;
  font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande', 'Lucida Sans', Arial, sans-serif;
  z-index: 1000;
}

.logo-container {
    text-decoration: none;
    color: white;
    display: flex;
    align-items: center;
    position: relative;
    overflow: hidden;
    z-index: 2;
}

.logo-container::before {
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

.logo {
    height: 60px;
    margin-right: 10px;
    position: relative;
    z-index: 2;
}

.navbar-left {
    display: flex;
    align-items: center;
}

.navbar-right {
    display: flex;
    align-items: center;
    gap: 5px;
}

.website-name {
    font-size: 25px;
    z-index: 2;
}

.navbar-link {
    text-decoration: none;
    padding: 10px;
    display: flex;
    align-items: center;
    font-size: 15px;
    color: white;
    border-bottom: 2px solid transparent;
    transition: border-color 0.3s ease;
    z-index: 2;
}

.navbar-link:hover {
    border-bottom: 2px solid rgb(251, 77, 3);
}

.navbar-link::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 2px;
    background-color: rgb(251, 77, 3);
    transform: scaleX(0);
    transform-origin: bottom right;
    transition: transform 1s ease-in-out;
}

.navbar-link:hover::after {
    transform: scaleX(1);
    transform-origin: bottom left;
}

.navbar-right .divider {
    margin-left: 24px;
    margin-right: 24px;
    width: 1px;
    height: 36px;
    background-color: rgb(226, 211, 190);
    min-width: 1px;
}

.navbar-link {
    font-size: 20px;
}

.profile-link {
    cursor: pointer;
    position: relative;
    padding: 10px;
}

.profile-link i {
    font-size: 24px;
    transition: transform 0.3s ease;
}
.profile-link:hover{
    transform: scale(1.1);
}

.dropdown-menu {
    position: absolute;
    top: 100%;
    right: 0;
    background-color: rgba(55, 55, 53, 0.711);
    color: rgb(253, 250, 250);
    border: 2px solid #ffffff;
    border-radius: 4px;
    box-shadow: 0px 8px 16px rgba(35, 34, 34, 0.907);
    width: 200px;
    display: none;
    z-index: 1000;
}

.dropdown-menu-show {
    display: block;
}

.dropdown-item {
    display: flex;
    align-items: center;
    padding: 10px;
    text-decoration: none;
    color: rgb(255, 255, 255);
}

.dropdown-item i {
    margin-right: 8px;
}

.divider-horizontal {
    height: 1px;
    background-color: #ffffff;
    margin: 5px 0;
}

.user-info {
    font-weight: bold;
    background-color: transparent;
    user-select: none;
    pointer-events: none; /* Disable hover and click */
}

.title-lantern {
    color: #F5C277;
}

.DB-title {
    color: #F97401;
}

.user-info:hover {
    background-color: transparent;
}
</style>
