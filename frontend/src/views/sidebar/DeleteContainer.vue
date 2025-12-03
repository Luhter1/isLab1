<script lang="ts" setup>
import { computed, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router'
import GenericDeleteById from '@/components/Common/GenericDeleteById.vue'

import LocationService from '@/services/LocationService'
import LocationTable from '@/components/Location/Table.vue'

import CoordinateService from '@/services/CoordinatesService'
import CoordinateTable from '@/components/Coordinates/Table.vue'

import DragonHeadService from '@/services/DragonHeadService'
import DragonHeadTable from '@/components/DragonHead/Table.vue'

import DragonCaveService from '@/services/DragonCaveService'
import DragonCaveTable from '@/components/DragonCave/Table.vue'

import PersonService from '@/services/PeopleService'
import PeopleTable from '@/components/Person/Table.vue'

import DragonService from '@/services/DragonService'
import DragonTable from '@/components/Dragon/Table.vue'



interface ComponentConfig {
  service,
  tableComponent,
  deleteT: (id: number) => Promise<any>,
  formLabel: string,
}

const route = useRoute()
const router = useRouter()

const deleteConfigs: Record<string, ComponentConfig> = {
  Dragon: {
    service: DragonService,
    tableComponent: DragonTable,
    deleteT: DragonService.delete,
    formLabel: "Dragon",
  },

  Person: {
    service: PersonService,
    tableComponent: PeopleTable,
    deleteT: PersonService.delete,
    formLabel: "Person",

  },

  DragonCave: {
    service: DragonCaveService,
    tableComponent: DragonCaveTable,
    deleteT: DragonCaveService.delete,
    formLabel: "DragonCave",
  },

  DragonHead: {
    service: DragonHeadService,
    tableComponent: DragonHeadTable,
    deleteT: DragonHeadService.delete,
    formLabel: "DragonHead",
  },

  Coordinate: {
    service: CoordinateService,
    tableComponent: CoordinateTable,
    deleteT: CoordinateService.delete,
    formLabel: "Coordinate",
  },

  Location: {
    service: LocationService,
    tableComponent: LocationTable,
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
        :service="currentConfig.service"
        :tableComponent="currentConfig.tableComponent"
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