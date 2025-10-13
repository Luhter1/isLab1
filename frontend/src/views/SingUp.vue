<script lang="ts" setup>
import Auth from '../components/AuthComponent.vue'
import { register } from '@/services/AuthService'
</script>

<template>
  <Auth msg="Регистрация" :auth_func="register"/>
</template>
