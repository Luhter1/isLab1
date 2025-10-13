<script lang="ts" setup>
import Auth from '@/components/AuthComponent.vue'
import { login } from '@/services/AuthService'
</script>

<template>
  <Auth msg="Вход" :auth_func="login"/>
</template>