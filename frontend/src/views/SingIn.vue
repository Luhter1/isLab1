<script lang="ts" setup>
import Auth from '../components/Auth.vue'
import AuthService from '../controllers/AuthController'
</script>

<template>
  <Auth msg="Вход" :auth_func="AuthService.login"/>
</template>