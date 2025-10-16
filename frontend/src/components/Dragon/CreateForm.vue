<script lang="ts" setup>
import { computed } from 'vue'
import { DragonCreateDto } from '@/interfaces/dto/dragons/DragonCreateDto'

const props = defineProps<{
  obj: DragonCreateDto
}>()

const emit = defineEmits<{
  'update:obj': [value: DragonCreateDto]
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
    <el-input v-model="localObj.name" placeholder="Name" clearable/>
  </el-form-item>


  <el-form-item label="Coordinates ID" prop="coordinatesId">
    <el-input v-model="localObj.coordinatesId" type="number" placeholder="Coordinates ID" clearable/>
  </el-form-item>

  <el-form-item label="Cave ID" prop="caveId">
    <el-input v-model="localObj.caveId" type="number" placeholder="Cave ID" clearable/>
  </el-form-item>

  <el-form-item label="Killer ID" prop="killerId">
    <el-input v-model="localObj.killerId" type="number" placeholder="Killer ID" clearable/>
  </el-form-item>

  <el-form-item label="Age" prop="age">
    <el-input v-model="localObj.age" type="number" placeholder="Age" clearable/>
  </el-form-item>

  <el-form-item label="Color" prop="color">
    <el-select v-model="localObj.color" placeholder="Color" clearable>
      <el-option label="RED" value="RED" />
      <el-option label="BLUE" value="BLUE" />
      <el-option label="YELLOW" value="YELLOW" />
      <el-option label="ORANGE" value="ORANGE" />
    </el-select>
  </el-form-item>

  <el-form-item label="Type" prop="type">
    <el-select v-model="localObj.type" placeholder="Type" clearable>
      <el-option label="WATER" value="WATER" />
      <el-option label="UNDERGROUND" value="UNDERGROUND" />
      <el-option label="AIR" value="AIR" />
      <el-option label="FIRE" value="FIRE" />
    </el-select>
  </el-form-item>

  <el-form-item label="Character" prop="character">
    <el-select v-model="localObj.character" placeholder="Character" clearable>
      <el-option label="EVIL" value="EVIL" />
      <el-option label="CHAOTIC EVIL" value="CHAOTIC_EVIL" />
      <el-option label="FICKLE" value="FICKLE" />
    </el-select>
  </el-form-item>
</template>