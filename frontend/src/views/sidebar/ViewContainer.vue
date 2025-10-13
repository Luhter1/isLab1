<script lang="ts" setup>
import { ref, computed, watch } from 'vue'
import { shallowRef, markRaw } from 'vue';
import { useRoute, useRouter } from 'vue-router'
import GenericGetById from '@/components/Common/GenericGetById.vue'

import CardLocation from '@/components/Location/Card.vue'
import { getLocation } from '@/services/LocationService'

import CardCoordinate from '@/components/Coordinates/Card.vue'
import { getCoordinate } from '@/services/CoordinatesService'

import CardDragonHead from '@/components/DragonHead/Card.vue'
import { getDragonHead } from '@/services/DragonHeadService'

import CardDragonCave from '@/components/DragonCave/Card.vue'
import { getDragonCave } from '@/services/DragonCaveService'

import CardPerson from '@/components/Person/Card.vue'
import { getPerson } from '@/services/PeopleService'



interface ComponentConfig {
  component: any
  getT: (id: number) => Promise<any>
  cardT: any
  formLabel?: string
  initialId?: number | null

}

const route = useRoute()
const router = useRouter()

const viewConfigs: Record<string, ComponentConfig> = {
  Person: {
    component: markRaw(GenericGetById),
    getT: getPerson,
    cardT: markRaw(CardPerson),
    formLabel: "Person",

  },

  DragonHead: {
    component: markRaw(GenericGetById),
    getT: getDragonHead,
    cardT: markRaw(CardDragonHead),
    formLabel: "DragonHead",
  },

  DragonCave: {
    component: markRaw(GenericGetById),
    getT: getDragonCave,
    cardT: markRaw(CardDragonCave),
    formLabel: "DragonCave",
  },

  Location: {
    component: markRaw(GenericGetById),
    getT: getLocation,
    cardT: markRaw(CardLocation),
    formLabel: "Location",
  },

  Coordinate: {
    component: markRaw(GenericGetById),
    getT: getCoordinate,
    cardT: markRaw(CardCoordinate),
    formLabel: "Coordinate",
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