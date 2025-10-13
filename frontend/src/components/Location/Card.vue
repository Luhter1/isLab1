<script lang="ts" setup>
import { toRef } from 'vue'
import { LocationDto } from '@/interfaces/dto/locations/LocationDto'

interface Props {
  obj: LocationDto
}
const props = defineProps<Props>()
const data = toRef(props, 'obj')
</script>

<template>
  <el-card style="max-width: 480px">
    <template #header>
      <div class="card-header">
        <b>Location</b>
      </div>
    </template>
    <p :key="data.id" class="text item">{{ 'ID: ' + data.id }}</p>
    <template #footer>
        <p :key="data.x" class="text item">{{ 'Name: ' + data.name }}</p>
        <p :key="data.x" class="text item">{{ 'X: ' + data.x }}</p>
        <p :key="data.y" class="text item">{{ 'Y: ' + data.y }}</p>
        <p :key="data.z" class="text item">{{ 'Z: ' + data.z }}</p>
        <p :key="data.createdBy.username " class="text item">{{ 'Creator: ' + data.createdBy.username }}</p>
    </template>
  </el-card>
</template>