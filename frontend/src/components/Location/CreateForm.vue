<script lang="ts" setup>
import { computed } from 'vue'
import { LocationCreateDto } from '@/interfaces/dto/locations/LocationCreateDto'

const props = defineProps<{
  obj: LocationCreateDto
}>()

const emit = defineEmits<{
  'update:obj': [value: LocationCreateDto]
}>()

// Computed свойства для двусторонней связи
const localObj = computed({
  get() {
    return props.obj
  },
  set(newValue) {
    // При изменении любого поля, мы эмитируем событие `update:obj` с новым состоянием объекта.
    emit('update:obj', newValue)
  }
})
</script>

<template>
  <el-form-item label="X" prop="x">
    <el-input v-model="localObj.x" type="number"/>
  </el-form-item>

  <el-form-item label="Y" prop="y">
    <el-input v-model="localObj.y" type="number"/>
  </el-form-item>

  <el-form-item label="Z" prop="z">
    <el-input v-model="localObj.z" type="number"/>
  </el-form-item>

  <el-form-item label="Name" prop="name">
    <el-input v-model="localObj.name" type="number"/>
  </el-form-item>
</template>