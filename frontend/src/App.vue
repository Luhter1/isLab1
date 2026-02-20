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
  background-color: var(--dm-bg-primary);

  .el-header {
    background-color: var(--dm-bg-secondary);
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.4);
    border-bottom: 1px solid var(--dm-border-default);
    padding: 0;
    z-index: 100;

    .header-content {
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 0 20px;
      height: 100%;

      h1 {
        margin: 10px;
        font-size: 24px;
        color: var(--dm-text-primary);
        text-shadow: 0 0 10px var(--dm-accent-primary-glow);
      }

      .el-menu {
        border: none;
        background: transparent;
      }
    }
  }

  .el-aside {
    background-color: var(--dm-bg-secondary);
    border-right: 1px solid var(--dm-border-default);
    transition: width 0.3s;
    box-shadow: 2px 0 8px rgba(0, 0, 0, 0.3);

    .toggle-button {
      height: 50px;
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      border-bottom: 1px solid var(--dm-border-default);
      color: var(--dm-text-secondary);
      transition: all var(--dm-transition-normal);

      &:hover {
        background-color: var(--dm-bg-tertiary);
        color: var(--dm-accent-primary);
      }
    }

    .sidebar-menu {
      border-right: none;
      height: calc(100vh - 110px);
      background: transparent;
    }
  }

  .el-main {
    background-color: var(--dm-bg-primary);
    padding: 20px;
  }
}

/* Override Element Plus menu styles for dark theme */
.el-menu {
  background-color: transparent !important;
}

.el-menu--horizontal .el-menu-item {
  color: var(--dm-text-secondary) !important;
  border-bottom: 2px solid transparent !important;

  &:hover {
    background-color: var(--dm-bg-tertiary) !important;
    color: var(--dm-accent-primary) !important;
  }

  &.is-active {
    color: var(--dm-accent-primary) !important;
    border-bottom: 2px solid var(--dm-accent-primary) !important;
  }
}

.el-menu--horizontal .el-sub-menu__title {
  color: var(--dm-text-secondary) !important;

  &:hover {
    background-color: var(--dm-bg-tertiary) !important;
    color: var(--dm-accent-primary) !important;
  }
}

/* Vertical menu styles */
.el-menu-item {
  color: var(--dm-text-secondary) !important;

  &:hover {
    background-color: var(--dm-bg-tertiary) !important;
    color: var(--dm-accent-primary) !important;
  }

  &.is-active {
    background-color: var(--dm-bg-elevated) !important;
    color: var(--dm-accent-primary) !important;
    border-left: 3px solid var(--dm-accent-primary);
  }
}
</style>
