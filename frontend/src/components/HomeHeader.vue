<script setup lang="ts">
import { useUserStore } from '@/stores/userStore'
import { UserFilled } from '@element-plus/icons-vue'

const userStore = useUserStore()
</script>

<template>
  <div class="home-header">
    <h1 class="logo">
      <span class="logo-icon">🐉</span>
      <span class="logo-text">Dragon Management</span>
    </h1>

    <el-menu mode="horizontal" :router="true" :ellipsis="false" class="nav-menu">
      <el-menu-item index="/">Dashboard</el-menu-item>
      <el-menu-item index="/analytics">Analytics</el-menu-item>
      <el-menu-item index="/killer-team">Killer Team</el-menu-item>
      <el-menu-item index="/search-by-name">Search By Name</el-menu-item>
      <el-menu-item index="/batch-import">Batch Import</el-menu-item>
    </el-menu>

    <div class="auth-buttons">
      <el-space wrap v-if="!userStore.isLoggedIn">
        <RouterLink to="/sing-in" class="nav-link">
          <el-button type="primary" round class="glow-btn">
            Sign In
          </el-button>
        </RouterLink>

        <RouterLink to="/sing-up" class="nav-link">
          <el-button type="primary" plain round class="glow-btn-plain">
            Sign Up
          </el-button>
        </RouterLink>
      </el-space>

      <el-space wrap v-else>
        <RouterLink to="/create/Dragon" class="nav-link">
          <el-button type="danger" class="glow-btn-danger">
            <el-icon><Plus /></el-icon>
            Add Dragon
          </el-button>
        </RouterLink>

        <el-menu mode="horizontal" :router="true" :collapse="true" :ellipsis="true" :ellipsis-icon="UserFilled" class="user-menu">
          <el-menu-item index="/">Dashboard</el-menu-item>
          <el-menu-item index="/logout">Logout</el-menu-item>
        </el-menu>
      </el-space>
    </div>
  </div>
</template>

<style scoped lang="scss">
.home-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 100%;
  width: 100%;
  gap: 20px;
}

.logo {
  display: flex;
  align-items: center;
  gap: 10px;
  margin: 0;
  font-size: 22px;
  color: var(--dm-text-primary);
  font-weight: 600;
  letter-spacing: 0.5px;
  transition: all var(--dm-transition-normal);

  .logo-icon {
    font-size: 28px;
    filter: drop-shadow(0 0 8px var(--dm-accent-primary-glow));
    transition: all var(--dm-transition-normal);
  }

  .logo-text {
    background: var(--dm-gradient-mixed);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
  }

  &:hover {
    .logo-icon {
      filter: drop-shadow(0 0 15px var(--dm-accent-primary-glow));
      transform: scale(1.1);
    }
  }
}

.nav-menu {
  flex: 1;
  justify-content: center;
  background: transparent !important;
  border: none !important;
}

.auth-buttons {
  display: flex;
  align-items: center;
}

.nav-link {
  text-decoration: none;
}

/* Gradient button styles */
.glow-btn {
  background: var(--dm-gradient-primary);
  border: none;
  color: white;
  font-weight: 500;
  transition: all var(--dm-transition-normal);

  &:hover {
    transform: translateY(-2px);
    box-shadow: var(--dm-glow-primary);
  }
}

.glow-btn-plain {
  color: var(--dm-accent-primary);
  border-color: var(--dm-accent-primary);
  background: transparent;
  font-weight: 500;
  transition: all var(--dm-transition-normal);

  &:hover {
    background: var(--dm-accent-primary-dim);
    border-color: var(--dm-accent-cyan);
    color: var(--dm-accent-cyan);
    box-shadow: var(--dm-glow-primary);
  }
}

.glow-btn-danger {
  background: var(--dm-gradient-danger);
  border: none;
  color: white;
  font-weight: 500;
  transition: all var(--dm-transition-normal);

  &:hover {
    transform: translateY(-2px);
    box-shadow: var(--dm-glow-danger);
  }

  .el-icon {
    margin-right: 4px;
  }
}

.user-menu {
  background: var(--dm-bg-tertiary) !important;
  border: 1px solid var(--dm-border-default) !important;
  border-radius: 20px;
  padding: 0 10px;
}

/* Responsive */
@media (max-width: 1200px) {
  .logo-text {
    display: none;
  }
}

@media (max-width: 768px) {
  .nav-menu {
    display: none;
  }
}
</style>
