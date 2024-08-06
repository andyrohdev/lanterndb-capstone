<template>
  <div id="register" class="register-container">
    <form v-on:submit.prevent="register">
      <h1>Create Account</h1>
      <div role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <div class="form-input-group">
        <label for="username">Username</label>
      </div>
      <div class="input-wrapper">
          <input type="text" id="username" v-model="user.username" required autofocus />
          <i class="bi bi-person icon" aria-hidden="true"></i>
        </div>
      <div class="form-input-group">
        <label for="password">Password</label>
      </div>
      <div class="input-wrapper">
          <input type="password" id="password" v-model="user.password" required />
          <i class="bi bi-lock-fill icon" aria-hidden="true"></i>
        </div>
        <br/>
      <div class="form-input-group">
        <label for="confirmPassword">Confirm Password</label>
      </div>
      <div class="input-wrapper">
          <input type="password" id="password" v-model="user.password" required />
          <i class="bi bi-lock-fill icon" aria-hidden="true"></i>
        </div>
      <br/>
      <button type="submit">Create Account</button>
      <br/> <br/>
      <p><router-link v-bind:to="{ name: 'login' }">Already have an account? Log in.</router-link></p>
    </form>
  </div>
</template>

<script>
import authService from '../services/AuthService';

export default {
  data() {
    return {
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style scoped>
.form-input-group {
  
  margin-bottom: 1rem;
}

label {
  margin-right: 0.5rem;
 display: block;
}

input {
  flex: 1;
  padding: 0.5rem;
  width: 200px; /* Adjust this width as needed */
}

button {
  padding: 0.5rem 1rem;
  margin-top: 1rem;
}

#register {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #333;
  margin: 0;
  color: white;
}
.register-container{
  background-color: #2c2c2c;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  width: 100%;
  max-width: 400px;
}
h1 {
  margin-bottom: 1rem;
  border-radius: 4px;
  margin-bottom: 1rem;
}
.create-account {
  background-color: rgba(238, 236, 232, 0.763);
  padding: .75rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
  margin-top: 1rem;
  margin-bottom: 1rem;
  display: flex;
  margin: auto;
}
.input-wrapper{
  position: relative;
}

input {
  width: 100%;
  padding: .75rem;
  border-radius: 4px;
  border: 1px solid #444;
}

.login-button {
  display: flex;
  margin-top: 10px;
  justify-content: center;
}

.icon {
  position: absolute;
  right: .75rem;
  top: 50%;
  transform: translateY(-50%);
  color: rgb(16, 16, 16);
  font-size: 20px;
}


</style>
