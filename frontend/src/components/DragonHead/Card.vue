<script lang="ts" setup>
import { toRef } from 'vue'
import { DragonHeadDto } from '@/interfaces/dto/dragonheads/DragonHeadDto'

interface Props {
  obj: DragonHeadDto
}
const props = defineProps<Props>()
const data = toRef(props, 'obj')
</script>

<template>
  <el-card style="max-width: 480px">
    <template #header>
      <div class="card-header">
        <b>DragonHead</b>
      </div>
    </template>
    <p :key="data.id" class="text item">{{ 'ID: ' + data.id }}</p>
    <template #footer>
        <p :key="data.size" class="text item">{{ 'Size: ' + data.size }}</p>
        <p :key="data.createdBy.username " class="text item">{{ 'Creator: ' + data.createdBy.username }}</p>
    </template>
  </el-card>
</template>