<script lang="ts" setup>
import { computed, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router'
import type { Component } from 'vue'
import { ValidationFactory } from '@/utils/validationFactory'
import GenericCreate from '@/components/Common/GenericCreate.vue'

import LocationService from '@/services/LocationService'
import LocationCreateForm from '@/components/Location/CreateForm.vue'

import CoordinateService from '@/services/CoordinatesService'
import CoordinateCreateForm from '@/components/Coordinates/CreateForm.vue'

import DragonHeadService from '@/services/DragonHeadService'
import DragonHeadCreateForm from '@/components/DragonHead/CreateForm.vue'

import DragonCaveService from '@/services/DragonCaveService'
import DragonCaveCreateForm from '@/components/DragonCave/CreateForm.vue'

import PersonService from '@/services/PeopleService'
import PersonCreateForm from '@/components/Person/CreateForm.vue'

import DragonService from '@/services/DragonService'
import DragonCreateForm from '@/components/Dragon/CreateForm.vue'



interface ComponentConfig {
  createT: (createDto: any) => Promise<any>,
  formFieldsT: Component,
  formLabel: string,
}

const route = useRoute()
const router = useRouter()

const createConfigs: Record<string, ComponentConfig> = {
  Dragon: {
    createT: DragonService.create,
    formFieldsT: DragonCreateForm,
    formLabel: "Dragon",
  },

  Person: {
    createT: PersonService.create,
    formFieldsT: PersonCreateForm,
    formLabel: "Person",

  },

  DragonCave: {
    createT: DragonCaveService.create,
    formFieldsT: DragonCaveCreateForm,
    formLabel: "DragonCave",
  },

  DragonHead: {
    createT: DragonHeadService.create,
    formFieldsT: DragonHeadCreateForm,
    formLabel: "DragonHead",
  },

  Coordinate: {
    createT: CoordinateService.create,
    formFieldsT: CoordinateCreateForm,
    formLabel: "Coordinate",
  },

  Location: {
    createT: LocationService.create,
    formFieldsT: LocationCreateForm,
    formLabel: "Location",
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