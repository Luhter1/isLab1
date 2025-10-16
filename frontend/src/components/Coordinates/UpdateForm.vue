<script lang="ts" setup>
import { computed, ref } from 'vue'
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

const value2 = ref([])
const fields = [
  {
    label: "X", 
    value: 1,
  },
  {
    label: "Y", 
    value: 2,
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
  
  <el-form-item label="X" prop="x" v-if="value2.find((e) => e === 1)">
    <el-input v-model="localObj.x" type="number" placeholder="X" clearable/>
  </el-form-item>

  <el-form-item label="Y" prop="y" v-if="value2.find((e) => e === 2)">
    <el-input v-model="localObj.y" type="number" placeholder="Y" clearable/>
  </el-form-item>
</template>