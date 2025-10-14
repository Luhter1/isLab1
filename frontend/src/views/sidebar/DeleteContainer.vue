<script lang="ts" setup>
import { computed, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router'
import GenericDeleteById from '@/components/Common/GenericDeleteById.vue'

import { deleteLocation } from '@/services/LocationService'

import { deleteCoordinate } from '@/services/CoordinatesService'

import { deleteDragonHead } from '@/services/DragonHeadService'

import { deleteDragonCave } from '@/services/DragonCaveService'

import { deletePerson } from '@/services/PeopleService'

import { deleteDragon } from '@/services/DragonService'



interface ComponentConfig {
  deleteT: (id: number) => Promise<any>,
  formLabel: string,
}

const route = useRoute()
const router = useRouter()

const deleteConfigs: Record<string, ComponentConfig> = {
  Dragon: {
    deleteT: deleteDragon,
    formLabel: "Dragon",
  },

  Person: {
    deleteT: deletePerson,
    formLabel: "Person",

  },

  DragonHead: {
    deleteT: deleteDragonHead,
    formLabel: "DragonHead",
  },

  DragonCave: {
    deleteT: deleteDragonCave,
    formLabel: "DragonCave",
  },

  Location: {
    deleteT: deleteLocation,
    formLabel: "Location",
  },

  Coordinate: {
    deleteT: deleteCoordinate,
    formLabel: "Coordinate",
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