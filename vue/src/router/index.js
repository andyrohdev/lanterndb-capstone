import { createRouter as createRouter, createWebHistory } from 'vue-router'
import { useStore } from 'vuex'

// Import components
import HomeView from '../views/HomeView.vue';
import LoginView from '../views/LoginView.vue';
import LogoutView from '../views/LogoutView.vue';
import RegisterView from '../views/RegisterView.vue';
import GamesListView from '../views/GamesListView.vue';
import DashboardView from  '../views/DashboardView.vue';
import AdminPageView from '../views/AdminPageView.vue'
import GameDetailsView from '../views/GameDetailsView.vue'
import AdminUserProfileView from '../views/AdminUserProfileView.vue'

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */
const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/login",
    name: "login",
    component: LoginView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/logout",
    name: "logout",
    component: LogoutView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/register",
    name: "register",
    component: RegisterView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/games",
    name: "games",
    component: GamesListView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path:"/dashboard",
    name:"dashboard",
    component: DashboardView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path:"/admin",
    name:"admin",
    component: AdminPageView,
    meta: {
      requiresAuth: true,
      requiresAdmin: true
    }
  },
  {
    path:"/game-details/:gameId",
    name:"game-details",
    component: GameDetailsView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/admin/user-profile/:userId",
    name: "admin-user-profile",
    component: AdminUserProfileView,
    props: true,
    meta: {
      requiresAuth: true,
      requiresAdmin: true
    }
  },
  
    
  
];

// Create the router
const router = createRouter({
  history: createWebHistory(),
  routes: routes
});

router.beforeEach((to) => {

  // Get the Vuex store
  const store = useStore();

  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);
  const requiresAdmin = to.matched.some(x => x.meta.requiresAdmin);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    return {name: "login"};
  }

  if (requiresAdmin) {
    const user = store.state.user;
    const isAdmin = user && user.authorities && user.authorities.some(auth => auth.name === 'ROLE_ADMIN');
    if (!isAdmin) {
      return { name: 'home' };
    }
    
  }

  // Otherwise, do nothing and they'll go to their next destination
});

export default router;
