<script lang="ts" setup>
import { computed } from 'vue'
import { DragonCreateDto } from '@/interfaces/dto/dragons/DragonCreateDto'
import EntitySelectField from '@/components/Common/EntitySelectField.vue'
import PeopleService from '@/services/PeopleService'
import PeopleTable from '@/components/Person/Table.vue'
import DragonCaveService from '@/services/DragonCaveService'
import DragonCaveTable from '@/components/DragonCave/Table.vue'
import CoordinatesService from '@/services/CoordinatesService'
import CoordinatesTable from '@/components/Coordinates/Table.vue'
import DragonHeadService from '@/services/DragonHeadService'
import DragonHeadTable from '@/components/DragonHead/Table.vue'

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
    <EntitySelectField
      v-model="localObj.coordinatesId"
      label="Select coordinates"
      placeholder="Нажмите для выбора координат"
      :service="CoordinatesService"
      :table-component="CoordinatesTable"
    />
  </el-form-item>

  <el-form-item label="Cave ID" prop="caveId">
    <EntitySelectField
      v-model="localObj.caveId"
      label="Select cave"
      placeholder="Нажмите для выбора пещеры"
      :service="DragonCaveService"
      :table-component="DragonCaveTable"
    />
  </el-form-item>

  <el-form-item label="Killer ID" prop="killerId">
    <EntitySelectField
      v-model="localObj.killerId"
      label="Select killer"
      placeholder="Нажмите для выбора убийцы"
      :service="PeopleService"
      :table-component="PeopleTable"
    />
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

  <el-form-item label="Head ID" prop="headId">
    <EntitySelectField
      v-model="localObj.headId"
      label="Select head"
      placeholder="Нажмите для выбора головы дракона"
      :service="DragonHeadService"
      :table-component="DragonHeadTable"
    />
  </el-form-item>
</template>