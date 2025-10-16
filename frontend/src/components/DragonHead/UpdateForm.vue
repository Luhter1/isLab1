<script lang="ts" setup>
import { computed, ref } from 'vue'
import { DragonHeadUpdateDto } from '@/interfaces/dto/dragonheads/DragonHeadUpdateDto'

const props = defineProps<{
  obj: DragonHeadUpdateDto
}>()

const emit = defineEmits<{
  'update:obj': [value: DragonHeadUpdateDto]
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
    label: "Size", 
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
  
  <el-form-item label="Size" prop="size" v-if="value2.find((e) => e === 1)">
    <el-input v-model="localObj.size" type="number" placeholder="Size" clearable/>
  </el-form-item>
</template>