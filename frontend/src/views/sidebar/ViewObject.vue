<script lang="ts" setup>
import { ref, shallowRef, markRaw } from 'vue';
import LocationView from '@/components/Location/View.vue'

const componentMap = {
  Location: markRaw(LocationView),
};

const activeComponent = shallowRef(componentMap.Location);

const handleMenuSelect = (index) => {
  activeComponent.value = componentMap[index];
};
</script>

<template>
    <el-container>
      <el-aside :width="'200px'">
        <el-menu 
            :collapse="false" 
            class="sidebar-menu"
            @select="handleMenuSelect"
            default-active="create">
            
            <el-menu-item index="Location">
              <span>Location</span>
            </el-menu-item>

        </el-menu>
      </el-aside>

      <el-main>
        <KeepAlive>
            <component :is="activeComponent" />
        </KeepAlive>
      </el-main>
    </el-container>
</template>
