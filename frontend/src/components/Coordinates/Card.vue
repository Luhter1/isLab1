<script lang="ts" setup>
import { toRef } from 'vue'
import { CoordinateDto } from '@/interfaces/dto/coordinates/CoordinateDto'

interface Props {
  obj: CoordinateDto
}
const props = defineProps<Props>()
const data = toRef(props, 'obj')
</script>

<template>
  <el-card style="max-width: 480px">
    <template #header>
      <div class="card-header">
        <b>Coordinate</b>
      </div>
    </template>
    <p :key="data.id" class="text item">{{ 'ID: ' + data.id }}</p>
    <template #footer>
        <p :key="data.x" class="text item">{{ 'X: ' + data.x }}</p>
        <p :key="data.y" class="text item">{{ 'Y: ' + data.y }}</p>
        <p :key="data.createdBy.username " class="text item">{{ 'Creator: ' + data.createdBy.username }}</p>
    </template>
  </el-card>
</template>