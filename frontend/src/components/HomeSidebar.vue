<script setup>
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import { useUserStore } from '@/stores/userStore'
import {
  Fold,
  Expand,
  Edit,
  Delete,
  View,
  DocumentAdd
} from '@element-plus/icons-vue'

const userStore = useUserStore()
const isCollapse = ref(true)
const handleToggle = () => {
  isCollapse.value = !isCollapse.value
}

// Получаем текущий маршрут
const route = useRoute()

// Определяем текущий тип из URL или используем значение по умолчанию
const currentType = computed(() => {
  // Извлекаем тип из текущего пути (например, из /update/DragonCave получаем DragonCave)
  const pathParts = route.path.split('/')
  if (pathParts.length >= 3) {
    return pathParts[2] // Возвращаем тип из URL
  }
  return 'Dragon' // Значение по умолчанию
})

// Создаем динамические пути для меню
const menuPaths = computed(() => ({
  view: `/view/${currentType.value}`,
  create: `/create/${currentType.value}`,
  update: `/update/${currentType.value}`,
  delete: `/delete/${currentType.value}`
}))
</script>

<template>
  <el-aside :width="isCollapse ? '64px' : '200px'">
    <div class="toggle-button" @click="handleToggle">
      <el-icon class="toggle-icon">
        <Fold v-if="!isCollapse" />
        <Expand v-else />
      </el-icon>
    </div>

    <el-menu
      :router="true"
      :collapse="isCollapse"
      :collapse-transition="false"
      :default-active="route.path"
      class="sidebar-menu"
    >
      <el-menu-item :index="menuPaths.view" class="menu-item">
        <el-icon><View /></el-icon>
        <template #title>View</template>
      </el-menu-item>
      <el-menu-item :index="menuPaths.create" v-if="userStore.isLoggedIn" class="menu-item">
        <el-icon><DocumentAdd /></el-icon>
        <template #title>Create</template>
      </el-menu-item>
      <el-menu-item :index="menuPaths.update" v-if="userStore.isLoggedIn" class="menu-item">
        <el-icon><Edit /></el-icon>
        <template #title>Update</template>
      </el-menu-item>
      <el-menu-item :index="menuPaths.delete" v-if="userStore.isLoggedIn" class="menu-item">
        <el-icon><Delete /></el-icon>
        <template #title>Delete</template>
      </el-menu-item>
    </el-menu>
  </el-aside>
</template>

<style scoped lang="scss">
.el-aside {
  background-color: var(--dm-bg-secondary);
  border-right: 1px solid var(--dm-border-default);
  transition: all 0.3s ease;
  box-shadow: 2px 0 8px rgba(0, 0, 0, 0.3);
}

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

    .toggle-icon {
      filter: drop-shadow(0 0 8px var(--dm-accent-primary-glow));
    }
  }

  .toggle-icon {
    font-size: 20px;
    transition: all var(--dm-transition-normal);
  }
}

.sidebar-menu {
  border-right: none;
  height: calc(100vh - 110px);
  background: transparent;
  padding: 8px 0;
}

.menu-item {
  color: var(--dm-text-secondary) !important;
  margin: 4px 8px;
  border-radius: 8px;
  transition: all var(--dm-transition-normal);
  position: relative;

  &:hover {
    background-color: var(--dm-bg-tertiary) !important;
    color: var(--dm-accent-primary) !important;

    .el-icon {
      filter: drop-shadow(0 0 6px var(--dm-accent-primary-glow));
    }
  }

  &.is-active {
    background: linear-gradient(90deg,
      var(--dm-accent-primary-dim) 0%,
      transparent 100%) !important;
    color: var(--dm-accent-primary) !important;
    border-left: 3px solid var(--dm-accent-primary);

    .el-icon {
      filter: drop-shadow(0 0 8px var(--dm-accent-primary-glow));
    }
  }

  .el-icon {
    font-size: 18px;
    transition: all var(--dm-transition-normal);
  }
}

/* Custom scrollbar for menu when expanded */
.el-aside:not(:has(.el-menu--collapse)) .sidebar-menu {
  overflow-y: auto;

  &::-webkit-scrollbar {
    width: 6px;
  }

  &::-webkit-scrollbar-track {
    background: transparent;
  }

  &::-webkit-scrollbar-thumb {
    background: var(--dm-bg-elevated);
    border-radius: 3px;

    &:hover {
      background: var(--dm-text-tertiary);
    }
  }
}
</style>
