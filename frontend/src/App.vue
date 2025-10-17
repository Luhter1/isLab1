<script setup>
import { onMounted, onUnmounted } from 'vue'
import { RouterView } from 'vue-router'
import HomeHeader from './components/HomeHeader.vue'
import HomeSidebar from './components/HomeSidebar.vue'
import WebSocketService from '@/controllers/websocket'

onMounted(() => {
  WebSocketService.connect()
})

onUnmounted(() => {
  WebSocketService.disconnect()
})
</script>

<template>
  <el-container class="app-container">
    <el-header>
      <div class="header-content">
        <HomeHeader />
      </div>
    </el-header>

    <el-container>
      <HomeSidebar />

      <el-main>
        <RouterView />
      </el-main>
    </el-container>
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
  
  .el-aside {
    background-color: #fff;
    box-shadow: 2px 0 6px rgba(0, 0, 0, 0.1);
    transition: width 0.3s;
    
    .toggle-button {
      height: 50px;
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      border-bottom: 1px solid #e4e7ed;
      
      &:hover {
        background-color: #f5f7fa;
      }
    }
    
    .sidebar-menu {
      border-right: none;
      height: calc(100vh - 110px);
    }
  }
  
  .el-main {
    background-color: #f5f7fa;
    padding: 20px;
  }
}
</style>