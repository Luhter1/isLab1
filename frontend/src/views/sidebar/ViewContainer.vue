<script lang="ts" setup>
import { shallowRef, markRaw, type Component } from 'vue';
import GenericGetById from '@/components/Common/GenericGetById.vue'

import CardLocation from '@/components/Location/Card.vue'
import { getLocation } from '@/services/LocationService'



interface ComponentConfig {
  component: any
  props: {
    getT: (id: number) => Promise<any>
    cardT: any
    formLabel?: string
  }
}

const componentMap: Record<string, ComponentConfig> = {
  Location: {
    component: markRaw(GenericGetById),
    props: {
      getT: getLocation,
      cardT: markRaw(CardLocation),
      formLabel: "Location",
    }
  },

}

const activeComponentConfig = shallowRef(componentMap.Location)

const handleMenuSelect = (index: string) => {
  activeComponentConfig.value = componentMap[index]
}
</script>

<template>
  <el-container>
    <el-aside :width="'200px'">
      <el-menu 
        :collapse="false" 
        class="sidebar-menu"
        @select="handleMenuSelect"
        default-active="Location">
        
        <el-menu-item index="Location">
          <span>Location</span>
        </el-menu-item>

        <el-menu-item index="User">
          <span>User</span>
        </el-menu-item>

        <el-menu-item index="Product">
          <span>Product</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-main>
      <KeepAlive>
        <component 
          :is="activeComponentConfig.component" 
          v-bind="activeComponentConfig.props"
        />
      </KeepAlive>
    </el-main>
  </el-container>
</template>