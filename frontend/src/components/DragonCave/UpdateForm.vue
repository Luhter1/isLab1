<script lang="ts" setup>
import { computed, ref } from 'vue'
import { DragonCaveUpdateDto } from '@/interfaces/dto/dragoncaves/DragonCaveUpdateDto'

const props = defineProps<{
  obj: DragonCaveUpdateDto
}>()

const emit = defineEmits<{
  'update:obj': [value: DragonCaveUpdateDto]
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

const value2 = ref([])
const fields = [
  {
    label: "Depth", 
    value: 1,
  },
]
</script>

<template>
  <el-form-item label="Select fields">
    <el-select
      v-model="value2"
      placeholder="Select fields"
      clearable
      multiple
    >
      <el-option
        v-for="item in fields"
        :key="item.value"
        :label="item.label"
        :value="item.value"
      />
    </el-select>
  </el-form-item>
  
  <el-form-item label="Depth" prop="depth" v-if="value2.find((e) => e === 1)">
    <el-input v-model="localObj.depth" type="number" placeholder="Depth" clearable/>
  </el-form-item>
</template>