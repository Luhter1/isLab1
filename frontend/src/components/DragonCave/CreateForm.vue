<script lang="ts" setup>
import { computed } from 'vue'
import { DragonCaveCreateDto } from '@/interfaces/dto/dragoncaves/DragonCaveCreateDto'

const props = defineProps<{
  obj: DragonCaveCreateDto
}>()

const emit = defineEmits<{
  'update:obj': [value: DragonCaveCreateDto]
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
  <el-form-item label="Depth" prop="depth">
    <el-input v-model="localObj.depth" type="number" placeholder="Depth" clearable/>
  </el-form-item>
</template>