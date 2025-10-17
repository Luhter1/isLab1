<script lang="ts" setup>
import { computed, watch, markRaw } from 'vue';
import { useRoute, useRouter } from 'vue-router'
import GenericGetById from '@/components/Common/GenericGetById.vue'

import CardLocation from '@/components/Location/Card.vue'
import LocationService from '@/services/LocationService'

import CardCoordinate from '@/components/Coordinates/Card.vue'
import CoordinateService from '@/services/CoordinatesService'

import CardDragonHead from '@/components/DragonHead/Card.vue'
import DragonHeadService from '@/services/DragonHeadService'

import CardDragonCave from '@/components/DragonCave/Card.vue'
import DragonCaveService from '@/services/DragonCaveService'

import CardPerson from '@/components/Person/Card.vue'
import PersonService from '@/services/PeopleService'

import CardDragon from '@/components/Dragon/Card.vue'
import DragonService from '@/services/DragonService'

interface ComponentConfig {
  getT: (id: number) => Promise<any>
  cardT: any
  formLabel: string
}

const route = useRoute()
const router = useRouter()

const viewConfigs: Record<string, ComponentConfig> = {
  Dragon: {
    getT: DragonService.getById,
    cardT: markRaw(CardDragon),
    formLabel: "Dragon",

  },

  Person: {
    getT: PersonService.getById,
    cardT: markRaw(CardPerson),
    formLabel: "Person",

  },

  DragonCave: {
    getT: DragonCaveService.getById,
    cardT: markRaw(CardDragonCave),
    formLabel: "DragonCave",
  },

  DragonHead: {
    getT: DragonHeadService.getById,
    cardT: markRaw(CardDragonHead),
    formLabel: "DragonHead",
  },

  Coordinate: {
    getT: CoordinateService.getById,
    cardT: markRaw(CardCoordinate),
    formLabel: "Coordinate",
  },

  Location: {
    getT: LocationService.getById,
    cardT: markRaw(CardLocation),
    formLabel: "Location",
  },
}

// Текущий тип из URL
const currentType = computed(() => route.params.type as string)
const currentConfig = computed(() => viewConfigs[currentType.value])

// Переход к другому типу
const handleMenuSelect = (type: string) => {
  router.push({
    path: `/view/${type}`,
    query: {}
  })
}

// Если тип не найден, редирект на первый доступный
watch(currentType, (type) => {
  if (!viewConfigs[type]) {
    const firstType = Object.keys(viewConfigs)[0]
    router.replace(`/view/${firstType}`)
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
          v-for="(config, key) in viewConfigs" 
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
        <el-breadcrumb-item>View</el-breadcrumb-item>
        <el-breadcrumb-item>{{ currentType }}</el-breadcrumb-item>
        <el-breadcrumb-item v-if="route.query.id">
          ID: {{ route.query.id }}
        </el-breadcrumb-item>
      </el-breadcrumb>

      <el-divider />

      <GenericGetById
        :key="`${currentType}-${route.query.id || 'new'}`"
        :getT="currentConfig.getT"
        :cardT="currentConfig.cardT"
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