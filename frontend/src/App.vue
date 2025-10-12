<script setup>
import { RouterView } from 'vue-router'
import MenuAuth from './components/MenuAuth.vue'
</script>

<template>
  <el-container class="app-container">
    <el-header>

      <div class="header-content">
        <h1>Dragon Management System</h1>

        <el-menu mode="horizontal" router="true" :ellipsis="false">
          <el-menu-item index="/">Dashboard</el-menu-item>
          <el-menu-item index="/analytics">Analytics</el-menu-item>
          <el-menu-item index="/killer">Killer</el-menu-item>
        </el-menu>

        <MenuAuth />
      </div>
    </el-header>

    <el-main>
      <RouterView />
    </el-main>
  </el-container>
</template>

<style lang="scss">
.app-container {
  height: 100vh;
  
  .el-header {
    background-color: #fff;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    padding: 0;
    
    .header-content {
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 0 20px;
      
      h1 {
        margin: 10px;
        font-size: 24px;
        color: #303133;
      }
      
      .el-menu {
        border: none;
      }
    }
  }
  
  .el-main {
    background-color: #f5f7fa;
    padding: 20px;
  }
}
</style>