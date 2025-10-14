<script lang="ts" setup>
import { computed } from 'vue'
import { PersonCreateDto } from '@/interfaces/dto/people/PersonCreateDto'

const props = defineProps<{
  obj: PersonCreateDto
}>()

const emit = defineEmits<{
  'update:obj': [value: PersonCreateDto]
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
  <el-form-item label="Name" prop="name">
    <el-input v-model="localObj.name" clearable/>
  </el-form-item>

  <el-form-item label="Eye color" prop="eyeColor">
    <el-select v-model="localObj.eyeColor" placeholder="Eye color" clearable>
      <el-option label="RED" value="RED" />
      <el-option label="BLUE" value="BLUE" />
      <el-option label="YELLOW" value="YELLOW" />
      <el-option label="ORANGE" value="ORANGE" />
    </el-select>
  </el-form-item>

  <el-form-item label="Hair color" prop="hairColor">
    <el-select v-model="localObj.hairColor" placeholder="Hair color" clearable>
      <el-option label="RED" value="RED" />
      <el-option label="BLUE" value="BLUE" />
      <el-option label="YELLOW" value="YELLOW" />
      <el-option label="ORANGE" value="ORANGE" />
    </el-select>
  </el-form-item>

</template>