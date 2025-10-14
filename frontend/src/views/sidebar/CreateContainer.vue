<script lang="ts" setup>
import { computed, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router'
import type { Component } from 'vue'
import { ValidationFactory } from '@/utils/validationFactory'
import GenericCreate from '@/components/Common/GenericCreate.vue'

import { createLocation } from '@/services/LocationService'
import locationCreateForm from '@/components/Location/CreateForm.vue'

import { createCoordinate } from '@/services/CoordinatesService'
import coordinateCreateForm from '@/components/Coordinates/CreateForm.vue'

import { createDragonHead } from '@/services/DragonHeadService'
import locationDragonHead from '@/components/DragonHead/CreateForm.vue'

import { createDragonCave } from '@/services/DragonCaveService'
import locationDragonCave from '@/components/DragonCave/CreateForm.vue'

import { createPerson } from '@/services/PeopleService'
import locationPerson from '@/components/Person/CreateForm.vue'


import { createDragon } from '@/services/DragonService'


interface ComponentConfig {
  createT: (createDto: any) => Promise<any>,
  formFieldsT: Component,
  formLabel: string,
}

const route = useRoute()
const router = useRouter()

const createConfigs: Record<string, ComponentConfig> = {
  Dragon: {
    createT: createDragon,
    formFieldsT: null,
    formLabel: "Dragon",
  },

  Person: {
    createT: createPerson,
    formFieldsT: locationPerson,
    formLabel: "Person",

  },

  DragonHead: {
    createT: createDragonHead,
    formFieldsT: locationDragonHead,
    formLabel: "DragonHead",
  },

  DragonCave: {
    createT: createDragonCave,
    formFieldsT: locationDragonCave,
    formLabel: "DragonCave",
  },

  Location: {
    createT: createLocation,
    formFieldsT: locationCreateForm,
    formLabel: "Location",
  },

  Coordinate: {
    createT: createCoordinate,
    formFieldsT: coordinateCreateForm,
    formLabel: "Coordinate",
  },
}

// Текущий тип из URL
const currentType = computed(() => route.params.type as string)
const currentConfig = computed(() => createConfigs[currentType.value])

// Переход к другому типу
const handleMenuSelect = (type: string) => {
  router.push({
    path: `/create/${type}`,
    query: {}
  })
}

// Если тип не найден, редирект на первый доступный
watch(currentType, (type) => {
  if (!createConfigs[type]) {
    const firstType = Object.keys(createConfigs)[0]
    router.replace(`/create/${firstType}`)
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
          v-for="(config, key) in createConfigs" 
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
        <el-breadcrumb-item>Create</el-breadcrumb-item>
        <el-breadcrumb-item>{{ currentType }}</el-breadcrumb-item>
      </el-breadcrumb>

      <el-divider />

      <GenericCreate
        :key="`${currentType}-${route.query.id || 'new'}`"
        :createT="currentConfig.createT"
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