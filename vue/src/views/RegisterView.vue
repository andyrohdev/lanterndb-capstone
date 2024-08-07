
<template>
  <div class="register-back">
    <div class="register-content">
      <div class="register-container">
        <form v-on:submit.prevent="register" class="form-container">
          <h1>Create Account</h1>
          <div role="alert" v-if="registrationErrors">
            {{ registrationErrorMsg }}
          </div>
          <div class="form-input-group">
            <label for="username">Username</label>
            <input type="text" id="username" v-model="user.username" required autofocus />
            <i class="bi bi-person icon" aria-hidden="true"></i>
          </div>
          <div class="form-input-group">
            <label for="password">Password</label>
            <div class="input-icon-wrapper">
              <input type="password" id="password" v-model="user.password" required />
            <i class="bi bi-lock-fill icon" aria-hidden="true"></i>
            </div>
          </div>
          <div class="form-input-group">
            <label for="confirmPassword">Confirm Password</label>
            <div class="input-icon-wrapper">
              <input type="password" id="confirmPassword" v-model="user.confirmPassword" required />
            <i class="bi bi-lock-fill icon" aria-hidden="true"></i>
            </div>
            
          </div>
          <button type="submit" class="submit-button">Create Account</button>
          <p><router-link v-bind:to="{ name: 'login' }">Already have an account? Log in.</router-link></p>
        </form>

       <div class="benefits-container">
        <div class="rightside-div">
          <h2>Benefits of Registering</h2>
          <ul>
            <li>Access exclusive content</li>
            <li>Personalized recommendations</li>
            <li>Save your favorite items</li>
            <li>Get regular updates</li>
          </ul>
        </div>
        </div> 
      </div>
        
    </div>
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
.register-back {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: url('@/assets/art2.jpg') no-repeat center center fixed;
  background-size: cover;
  margin: 0;
  color: white;
  overflow: hidden;
}

.register-content {
  display: flex;
  justify-content: center;
  align-items: stretch;
  width: 70%;
  background-color: #e0c5c5c2;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  border-radius: 40px 40px 40px 40px;
}
.benefits-container{
  padding: 2rem;
}
.register-container {
  border-radius: 8px 40px 40px 8px;
  background-color: #2c2c2c;
  color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  
}

.benefits-container {
  
  background-color: #ad9f4ae5;
  border-radius: 40px 40px 40px 40px;
  color: white;
  width: 50%;
  height: 100%;
}
.form-input-group {
  margin-bottom: 1rem;
}

label {
  display: block;
  margin-bottom: 5px;
}
.input-icon-wrapper{
  position: relative;
  width: 100%;
}
input{
  width: calc(100%-30px);
  padding: 0.75rem;
  border-radius: 15px;
  border: 1px solid #444;
  margin-bottom: 0.5rem;
  box-shadow: 1px 4px 4px rgba(29, 29, 29, 0.904);
  padding-right: 30px;
}
.icon {
  position: absolute;
  right: 10%;
  top: 50%;
  transform: translateY(-50%);
  color: white;
  font-size: 20px;
}
.submit-button {
  padding: 0.5rem 1rem;
  margin-top: 1rem;
  background-color: rgba(238, 236, 232, 0.763);
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
  display: block;
  margin: auto;
 
}

.benefits-container h2 {
  margin-bottom: 1rem;
}

.benefits-container ul {
  list-style-type: disc;
  padding-left: 1rem;
  text-align: left;
  
}

.benefits-container li {
  margin-bottom: 0.5rem;
 
}
.form-container{
  text-align: center;
  width: 50%;
  padding-top: 20px;
}
.form-container button{
  margin: auto;
  margin-bottom: 1rem;
}
.rightside-div{
  text-align: center;
}
</style>