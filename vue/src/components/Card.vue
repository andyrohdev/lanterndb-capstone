<template>
    <div class="card">
      <h3>{{ title }}</h3>
      <ul>
        <li v-for="item in items" :key="item.id">{{ item.name }}</li>
      </ul>
    </div>
  </template>
  
  <script>
  export default {
    props: {
      title: {
        type: String,
        required: true
      },
      items: {
        type: Array,
        required: true
      }
    }
  };
  </script>
  
  <style>
  .card {
    background-color: #ffffff;
    border: 1px solid #dcdcdc;
    border-radius: 8px;
    padding: 16px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    margin: 16px;
  }
  .card h3 {
    margin-top: 0;
  }
  .card ul {
    list-style-type: none;
    padding-left: 0;
  }
  .card li {
    padding: 8px 0;
    border-bottom: 1px solid #dcdcdc;
  }
  .card li:last-child {
    border-bottom: none;
  }
  </style>