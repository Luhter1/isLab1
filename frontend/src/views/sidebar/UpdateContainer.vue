<script lang="ts" setup>
import { computed, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router'
import type { Component } from 'vue'
import { ValidationFactory } from '@/utils/validationFactory'
import GenericUpdate from '@/components/Common/GenericUpdate.vue'

import { updateLocation } from '@/services/LocationService'
// import locationUpdateForm from '@/components/Location/UpdateForm.vue'

import { updateCoordinate } from '@/services/CoordinatesService'
import coordinateUpdateForm from '@/components/Coordinates/UpdateForm.vue'

import { updateDragonHead } from '@/services/DragonHeadService'
// import locationDragonHead from '@/components/DragonHead/UpdateForm.vue'

import { updateDragonCave } from '@/services/DragonCaveService'
// import locationDragonCave from '@/components/DragonCave/UpdateForm.vue'

import { updatePerson } from '@/services/PeopleService'
// import locationPerson from '@/components/Person/UpdateForm.vue'


import { updateDragon } from '@/services/DragonService'
// import locationDragon from '@/components/Dragon/UpdateForm.vue'



interface ComponentConfig {
  updateT: (id: number, location: any) => Promise<any>,
  formFieldsT: Component,
  formLabel: string,
}

const route = useRoute()
const router = useRouter()

const updateConfigs: Record<string, ComponentConfig> = {
  Dragon: {
    updateT: updateDragon,
    formFieldsT: null,
    formLabel: "Dragon",
  },

  Person: {
    updateT: updatePerson,
    formFieldsT: null,
    formLabel: "Person",

  },

  DragonHead: {
    updateT: updateDragonHead,
    formFieldsT: null,
    formLabel: "DragonHead",
  },

  DragonCave: {
    updateT: updateDragonCave,
    formFieldsT: null,
    formLabel: "DragonCave",
  },

  Location: {
    updateT: updateLocation,
    formFieldsT: null,
    formLabel: "Location",
  },

  Coordinate: {
    updateT: updateCoordinate,
    formFieldsT: coordinateUpdateForm,
    formLabel: "Coordinate",
  },
}

// Текущий тип из URL
const currentType = computed(() => route.params.type as string)
const currentConfig = computed(() => updateConfigs[currentType.value])

// Переход к другому типу
const handleMenuSelect = (type: string) => {
  router.push({
    path: `/update/${type}`,
    query: {}
  })
}

// Если тип не найден, редирект на первый доступный
watch(currentType, (type) => {
  if (!updateConfigs[type]) {
    const firstType = Object.keys(updateConfigs)[0]
    router.replace(`/update/${firstType}`)
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
          v-for="(config, key) in updateConfigs" 
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
        <el-breadcrumb-item>Update</el-breadcrumb-item>
        <el-breadcrumb-item>{{ currentType }}</el-breadcrumb-item>
      </el-breadcrumb>

      <el-divider />

      <GenericUpdate
        :key="`${currentType}-${route.query.id || 'new'}`"
        :updateT="currentConfig.updateT"
        :formFieldsT="currentConfig.formFieldsT"
        :formLabel="currentConfig.formLabel"
        :validationRules="ValidationFactory.createRules(currentConfig.formLabel)"
      />
    </el-main>
  </el-container>
</template>

<style scoped>
.el-breadcrumb {
  margin-bottom: 20px;
}
</style>