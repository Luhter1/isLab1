<script lang="ts" setup>
import { computed, ref } from 'vue'
import { PersonUpdateDto } from '@/interfaces/dto/people/PersonUpdateDto'
import EntitySelectField from '@/components/Common/EntitySelectField.vue'
import LocationService from '@/services/LocationService'
import LocationTable from '@/components/Location/Table.vue'

const props = defineProps<{
  obj: PersonUpdateDto
}>()

const emit = defineEmits<{
  'update:obj': [value: PersonUpdateDto]
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
    label: "Eye color", 
    value: 2,
  },
  {
    label: "Hair color", 
    value: 3,
  },
  {
    label: "Location ID", 
    value: 4,
  },
  {
    label: "Birthday", 
    value: 5,
  },
  {
    label: "Height", 
    value: 6,
  },
  {
    label: "Weight", 
    value: 7,
  },
  {
    label: "Passport ID", 
    value: 8,
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

  <el-form-item label="Eye color" prop="eyeColor" v-if="value2.find((e) => e === 2)">
    <el-select v-model="localObj.eyeColor" placeholder="Eye color" clearable>
      <el-option label="RED" value="RED" />
      <el-option label="BLUE" value="BLUE" />
      <el-option label="YELLOW" value="YELLOW" />
      <el-option label="ORANGE" value="ORANGE" />
    </el-select>
  </el-form-item>

  <el-form-item label="Hair color" prop="hairColor" v-if="value2.find((e) => e === 3)">
    <el-select v-model="localObj.hairColor" placeholder="Hair color" clearable>
      <el-option label="RED" value="RED" />
      <el-option label="BLUE" value="BLUE" />
      <el-option label="YELLOW" value="YELLOW" />
      <el-option label="ORANGE" value="ORANGE" />
    </el-select>
  </el-form-item>

  <el-form-item label="Location ID" prop="locationId" v-if="value2.find((e) => e === 4)">
    <EntitySelectField
      v-model="localObj.locationId"
      label="Select location"
      placeholder="Нажмите для выбора локации"
      :service="LocationService"
      :table-component="LocationTable"
    />
  </el-form-item>

  <el-form-item label="Birthday" prop="birthday" v-if="value2.find((e) => e === 5)">
    <el-date-picker
      v-model="localObj.birthday"
      type="datetime"
      placeholder="Birthday"
    />
  </el-form-item>

  <el-form-item label="Height" prop="height" v-if="value2.find((e) => e === 6)">
    <el-input v-model="localObj.height" type="number" placeholder="Height" clearable/>
  </el-form-item>

  <el-form-item label="Weight" prop="weight" v-if="value2.find((e) => e === 7)">
    <el-input v-model="localObj.weight" type="number" placeholder="Weight" clearable/>
  </el-form-item>

  <el-form-item label="Passport ID" prop="passportId" v-if="value2.find((e) => e === 8)">
    <el-input v-model="localObj.passportId" placeholder="Passport ID" clearable/>
  </el-form-item>
</template>