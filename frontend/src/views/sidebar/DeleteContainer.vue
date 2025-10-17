<script lang="ts" setup>
import { computed, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router'
import GenericDeleteById from '@/components/Common/GenericDeleteById.vue'

import LocationService from '@/services/LocationService'

import CoordinateService from '@/services/CoordinatesService'

import DragonHeadService from '@/services/DragonHeadService'

import DragonCaveService from '@/services/DragonCaveService'

import PersonService from '@/services/PeopleService'

import DragonService from '@/services/DragonService'



interface ComponentConfig {
  deleteT: (id: number) => Promise<any>,
  formLabel: string,
}

const route = useRoute()
const router = useRouter()

const deleteConfigs: Record<string, ComponentConfig> = {
  Dragon: {
    deleteT: DragonService.delete,
    formLabel: "Dragon",
  },

  Person: {
    deleteT: PersonService.delete,
    formLabel: "Person",

  },

  DragonCave: {
    deleteT: DragonCaveService.delete,
    formLabel: "DragonCave",
  },

  DragonHead: {
    deleteT: DragonHeadService.delete,
    formLabel: "DragonHead",
  },

  Coordinate: {
    deleteT: CoordinateService.delete,
    formLabel: "Coordinate",
  },

  Location: {
    deleteT: LocationService.delete,
    formLabel: "Location",
  },
}

// Текущий тип из URL
const currentType = computed(() => route.params.type as string)
const currentConfig = computed(() => deleteConfigs[currentType.value])

// Переход к другому типу
const handleMenuSelect = (type: string) => {
  router.push({
    path: `/delete/${type}`,
    query: {}
  })
}

// Если тип не найден, редирект на первый доступный
watch(currentType, (type) => {
  if (!deleteConfigs[type]) {
    const firstType = Object.keys(deleteConfigs)[0]
    router.replace(`/delete/${firstType}`)
  }
}, { immediate: true })
</script>

<template>
  <el-container v-if="currentConfig">
    <el-aside :width="'200px'">
      <el-menu 
        :collapse="false" 
        class="sidebar-menu"
        @select="handleMenuSelect"
        :default-active="currentType">

        <el-menu-item 
          v-for="(config, key) in deleteConfigs" 
          :key="key"
          :index="key">
          <span>{{ key }}</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-main>
      <h3>{{ currentConfig.formLabel }}</h3>

      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">Home</el-breadcrumb-item>
        <el-breadcrumb-item>Delete</el-breadcrumb-item>
        <el-breadcrumb-item>{{ currentType }}</el-breadcrumb-item>
        <el-breadcrumb-item v-if="route.query.id">
          ID: {{ route.query.id }}
        </el-breadcrumb-item>
      </el-breadcrumb>

      <el-divider />

      <GenericDeleteById
        :key="`${currentType}-${route.query.id || 'new'}`"
        :deleteT="currentConfig.deleteT"
        :formLabel="currentConfig.formLabel"
      />
    </el-main>
  </el-container>
</template>

<style scoped>
.el-breadcrumb {
  margin-bottom: 20px;
}
</style>