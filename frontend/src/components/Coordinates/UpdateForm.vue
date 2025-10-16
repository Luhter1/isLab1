<script lang="ts" setup>
import { computed } from 'vue'
import { CoordinateUpdateDto } from '@/interfaces/dto/coordinates/CoordinateUpdateDto'

const props = defineProps<{
  obj: CoordinateUpdateDto
}>()

const emit = defineEmits<{
  'update:obj': [value: CoordinateUpdateDto]
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
    <el-input v-model="localObj.x" type="number" placeholder="X" clearable/>
  </el-form-item>

  <el-form-item label="Y" prop="y">
    <el-input v-model="localObj.y" type="number" placeholder="Y" clearable/>
  </el-form-item>
</template>