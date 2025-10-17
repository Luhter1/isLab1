<script setup>
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import { useUserStore } from '@/stores/userStore'
import { 
  Fold, 
  Expand,
  Edit,
  Delete,
  View
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
      <el-menu-item :index="menuPaths.view">
        <el-icon><View /></el-icon>
        <template #title>View</template>
      </el-menu-item>
      <el-menu-item :index="menuPaths.create" v-if="userStore.isLoggedIn">
        <el-icon><DocumentAdd /></el-icon>
        <template #title>Create</template>
      </el-menu-item>
      <el-menu-item :index="menuPaths.update" v-if="userStore.isLoggedIn">
        <el-icon><Edit /></el-icon>
        <template #title>Update</template>
      </el-menu-item>
      <el-menu-item :index="menuPaths.delete" v-if="userStore.isLoggedIn">
        <el-icon><Delete /></el-icon>
        <template #title>Delete</template>
      </el-menu-item>
    </el-menu>
  </el-aside>
</template>