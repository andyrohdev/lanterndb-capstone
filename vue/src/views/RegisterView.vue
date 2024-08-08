

<template>
  <div class="register-back">
   
      <div class="register-container">
        <form v-on:submit.prevent="register" class="form-container">
          <h1>Create Account</h1>
          <div role="alert" v-if="registrationErrors">
            {{ registrationErrorMsg }}
          </div>
          <div class="form-input-group">
            <label for="username">Username</label>
            <div class="input-icon-wrapper">
            <input type="text" id="username" v-model="user.username" required autofocus />
            <i class="bi bi-person icon" aria-hidden="true"></i>
          </div>
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
          <p><router-link v-bind:to="{ name: 'login' }" id="hyper-link">Already have an account? Log in.</router-link></p>
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
.submit-button:hover {
  background-color: #d14b06;
}
input:focus {
  outline: none;
  border-color: #d14b06;
  box-shadow: 0 0 5px #d14b06;
}
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
  align-items: center;
  width: 70%;
  background-color: #e0c5c5c2;
  border-radius: 40px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}




.register-container {
  display: flex;
  justify-content: space-between;
  align-items: stretch;
  width: 48%;
  background-image: linear-gradient(to left,  rgb(158, 54, 10), #29292943, #29292943);
  border-radius: 8px;
  color: white;
  padding-left: 10px;
}




.form-container {
  width: 48%;
  text-align: center;
}




.benefits-container {
  height: 50;
  width: 48%;
  background-image: linear-gradient(to left,  rgb(158, 54, 10));
  border-radius: 8px;
  color: white;
  padding: 20px;
}




.form-input-group {
  margin-bottom: 1rem;
}




label {
  display: block;
  margin-bottom: 5px;
}




.input-icon-wrapper {
  position: relative;
}




input {
  width: 100%;
  padding: 0.75rem;
  border-radius: 15px;
  border: 1px solid #444;
  margin-bottom: 0.5rem;
  box-shadow: 1px 4px 4px rgba(29, 29, 29, 0.904);
  padding-right: 30px;
  padding-left: 30px;
}




.icon {
  position: absolute;
  right: 0.75rem;
  top: 50%;
  transform: translateY(-50%);
  color: rgb(16, 16, 16);
  font-size: 20px;
}




.submit-button {
  padding: 0.5rem 1rem;
  margin-top: 1rem;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
  display: block;
  margin: auto;
  background-color:rgb(158, 54, 10) ;
}
#hyper-link{
  color: rgb(158, 54, 10) ;
  text-decoration: none;
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




@media (max-width: 768px) {
  .register-container {
    flex-direction: column;
    align-items: center;
  }




  .form-container,
  .benefits-container {
    width: 100%;
    margin-bottom: 20px;
  }
}
</style>
