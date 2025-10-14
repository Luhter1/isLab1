<script lang="ts" setup>
import { computed } from 'vue'
import { CoordinateCreateDto } from '@/interfaces/dto/coordinates/CoordinateCreateDto'

const props = defineProps<{
  obj: CoordinateCreateDto
}>()

const emit = defineEmits<{
  'update:obj': [value: CoordinateCreateDto]
}>()

// Computed свойства для двусторонней связи
const x = computed({
  get: () => props.obj?.x,
  set: (value) => {
    emit('update:obj', { ...props.obj, x: value })
  }
})

const y = computed({
  get: () => props.obj?.y, 
  set: (value) => {
    emit('update:obj', { ...props.obj, y: value })
  }
})
</script>

<template>
  <el-form-item label="X" prop="x">
    <el-input v-model="x" type="number"/>
  </el-form-item>

  <el-form-item label="Y" prop="y">
    <el-input v-model="y" type="number"/>
  </el-form-item>
</template>