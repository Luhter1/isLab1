<script setup lang="ts">
import { useUserStore } from '@/stores/userStore'
import { UserFilled } from '@element-plus/icons-vue'

const userStore = useUserStore()
</script>

<template>
  <h1>Dragon Management System</h1>

  <el-menu mode="horizontal" :router="true" :ellipsis="false">
    <el-menu-item index="/">Dashboard</el-menu-item>
    <el-menu-item index="/analytics">Analytics</el-menu-item>
    <el-menu-item index="/killer-team">Killer Team</el-menu-item>
  </el-menu>

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

  <el-space wrap v-else>
    <RouterLink to="/create/Dragon">
      <el-button type="danger">
        +ADD Dragon
      </el-button>
    </RouterLink>

    <el-menu mode="horizontal" :router="true" :collapse="true" :ellipsis="true" :ellipsis-icon="UserFilled">
      <el-menu-item index="/">Dashboard</el-menu-item>
      <el-menu-item index="/logout">Выход</el-menu-item>
    </el-menu>
  </el-space>
</template>
