<script lang="ts" setup>
import { computed, ref } from 'vue'
import { LocationUpdateDto } from '@/interfaces/dto/locations/LocationUpdateDto'

const props = defineProps<{
  obj: LocationUpdateDto
}>()

const emit = defineEmits<{
  'update:obj': [value: LocationUpdateDto]
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
    label: "Name", 
    value: 1,
  },
  {
    label: "X", 
    value: 2,
  },
  {
    label: "Y", 
    value: 3,
  },
  {
    label: "Z", 
    value: 4,
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
  
  <el-form-item label="Name" prop="name" v-if="value2.find((e) => e === 1)">
    <el-input v-model="localObj.name" placeholder="Name" clearable/>
  </el-form-item>

  <el-form-item label="X" prop="x" v-if="value2.find((e) => e === 2)">
    <el-input v-model="localObj.x" type="number" placeholder="X" clearable/>
  </el-form-item>

  <el-form-item label="Y" prop="y" v-if="value2.find((e) => e === 3)">
    <el-input v-model="localObj.y" type="number" placeholder="Y" clearable/>
  </el-form-item>

  <el-form-item label="Z" prop="z" v-if="value2.find((e) => e === 4)">
    <el-input v-model="localObj.z" type="number" placeholder="Z" clearable/>
  </el-form-item>
</template>