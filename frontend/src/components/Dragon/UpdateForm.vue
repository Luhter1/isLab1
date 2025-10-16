<script lang="ts" setup>
import { ref, computed } from 'vue'
import { DragonCreateDto } from '@/interfaces/dto/dragons/DragonCreateDto'
import CreateForm from './CreateForm.vue'

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

const value2 = ref([])
const fields = [
  {
    label: "Name", 
    value: 1,
  },
  {
    label: "Coordinates ID", 
    value: 2,
  },
  {
    label: "Cave ID", 
    value: 3,
  },
  {
    label: "Killer ID", 
    value: 4,
  },
  {
    label: "Age", 
    value: 5,
  },
  {
    label: "Color", 
    value: 6,
  },
  {
    label: "Type", 
    value: 7,
  },
  {
    label: "Character", 
    value: 8,
  },
]

const change = () => {
  console.log(value2.value.find((e) => e === 1))
}
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


  <el-form-item label="Coordinates ID" prop="coordinatesId" v-if="value2.find((e) => e === 2)">
    <el-input v-model="localObj.coordinatesId" type="number" placeholder="Coordinates ID" clearable/>
  </el-form-item>

  <el-form-item label="Cave ID" prop="caveId" v-if="value2.find((e) => e === 3)">
    <el-input v-model="localObj.caveId" type="number" placeholder="Cave ID" clearable/>
  </el-form-item>

  <el-form-item label="Killer ID" prop="killerId" v-if="value2.find((e) => e === 4)">
    <el-input v-model="localObj.killerId" type="number" placeholder="Killer ID" clearable/>
  </el-form-item>

  <el-form-item label="Age" prop="age" v-if="value2.find((e) => e === 5)">
    <el-input v-model="localObj.age" type="number" placeholder="Age" clearable/>
  </el-form-item>

  <el-form-item label="Color" prop="color" v-if="value2.find((e) => e === 6)">
    <el-select v-model="localObj.color" placeholder="Color" clearable>
      <el-option label="RED" value="RED" />
      <el-option label="BLUE" value="BLUE" />
      <el-option label="YELLOW" value="YELLOW" />
      <el-option label="ORANGE" value="ORANGE" />
    </el-select>
  </el-form-item>

  <el-form-item label="Type" prop="type" v-if="value2.find((e) => e === 7)">
    <el-select v-model="localObj.type" placeholder="Type" clearable>
      <el-option label="WATER" value="WATER" />
      <el-option label="UNDERGROUND" value="UNDERGROUND" />
      <el-option label="AIR" value="AIR" />
      <el-option label="FIRE" value="FIRE" />
    </el-select>
  </el-form-item>

  <el-form-item label="Character" prop="character" v-if="value2.find((e) => e === 8)">
    <el-select v-model="localObj.character" placeholder="Character" clearable>
      <el-option label="EVIL" value="EVIL" />
      <el-option label="CHAOTIC EVIL" value="CHAOTIC_EVIL" />
      <el-option label="FICKLE" value="FICKLE" />
    </el-select>
  </el-form-item>
</template>