<script lang="ts" setup>
import Auth from '../components/Auth.vue'
import AuthService from '../controllers/AuthController'
</script>

<template>
  <Auth msg="Регистрация" :auth_func="AuthService.register"/>
</template>
