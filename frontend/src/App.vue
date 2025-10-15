<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { RouterView } from 'vue-router'
import MenuAuth from './components/MenuAuth.vue'
import WebSocketService from '@/controllers/websocket'
import { 
  Fold, 
  Expand,
  Edit,
  Delete,
  View
} from '@element-plus/icons-vue'

const isCollapse = ref(true)
const handleToggle = () => {
  isCollapse.value = !isCollapse.value
}

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
        <h1>Dragon Management System</h1>

        <el-menu mode="horizontal" :router="true" :ellipsis="false">
          <el-menu-item index="/">Dashboard</el-menu-item>
          <el-menu-item index="/analytics">Analytics</el-menu-item>
          <el-menu-item index="/killer">Killer</el-menu-item>
        </el-menu>

        <MenuAuth />
      </div>
    </el-header>

    <el-container>
      <el-aside :width="isCollapse ? '64px' : '200px'">
        <div class="toggle-button" @click="handleToggle">
          <el-icon>
            <Fold v-if="!isCollapse" />
            <Expand v-else />
          </el-icon>
        </div>
        
        <el-menu 
          :router="true" 
          :collapse="isCollapse"
          :collapse-transition="false"
          class="sidebar-menu"
        >
          <el-menu-item index="/view/Dragon">
            <el-icon><View /></el-icon>
            <template #title>View</template>
          </el-menu-item>
          <el-menu-item index="/create/Dragon">
            <el-icon><DocumentAdd /></el-icon>
            <template #title>Create</template>
          </el-menu-item>
          <el-menu-item index="/update/Dragon">
            <el-icon><Edit /></el-icon>
            <template #title>Update</template>
          </el-menu-item>
          <el-menu-item index="/delete/Dragon">
            <el-icon><Delete /></el-icon>
            <template #title>Delete</template>
          </el-menu-item>
        </el-menu>
      </el-aside>

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