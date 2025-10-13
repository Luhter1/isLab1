<script lang="ts" setup>
import { toRef } from 'vue'
import { DragonCaveDto } from '@/interfaces/dto/dragoncaves/DragonCaveDto'

interface Props {
  obj: DragonCaveDto
}
const props = defineProps<Props>()
const data = toRef(props, 'obj')
</script>

<template>
  <el-card style="max-width: 480px">
    <template #header>
      <div class="card-header">
        <b>DragonCave</b>
      </div>
    </template>
    <p :key="data.id" class="text item">{{ 'ID: ' + data.id }}</p>
    <template #footer>
        <p :key="data.depth" class="text item">{{ 'Depth: ' + data.depth }}</p>
        <p :key="data.createdBy.username " class="text item">{{ 'Creator: ' + data.createdBy.username }}</p>
    </template>
  </el-card>
</template>