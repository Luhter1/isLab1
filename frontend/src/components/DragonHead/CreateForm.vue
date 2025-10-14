<script lang="ts" setup>
import { computed } from 'vue'
import { DragonHeadCreateDto } from '@/interfaces/dto/dragonheads/DragonHeadCreateDto'

const props = defineProps<{
  obj: DragonHeadCreateDto
}>()

const emit = defineEmits<{
  'update:obj': [value: DragonHeadCreateDto]
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
    <el-input v-model="localObj.size" type="number"/>
  </el-form-item>
</template>