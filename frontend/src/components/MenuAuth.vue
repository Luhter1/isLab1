<script setup lang="ts">
import { useUserStore } from '@/stores/userStore'

const userStore = useUserStore()
</script>

<template>
  <el-space wrap v-if="!userStore.isLoggedIn">
    <RouterLink to="/sing-in">
      <el-button type="primary" round>
        sing-in
      </el-button>
    </RouterLink>

    <RouterLink to="/sing-up">
      <el-button type="primary" plain round>
        sing-up
      </el-button>
    </RouterLink>
  </el-space>
</template>
