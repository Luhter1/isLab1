<script lang="ts" setup>
import { toRef } from 'vue'
import { DragonDto } from '@/interfaces/dto/dragons/DragonDto'
import cardCoordinate from '../Coordinates/Card.vue'
import cardDragonCave from '../DragonCave/Card.vue'
import cardPerson from '../Person/Card.vue'
import cardDragonHead from '../DragonHead/Card.vue'

interface Props {
  obj: DragonDto
}
const props = defineProps<Props>()
const data = toRef(props, 'obj')
</script>

<template>
  <div class="cards-container">
    <el-card class="card-item">
      <template #header>
        <div class="card-header">
          <b>Person</b>
        </div>
      </template>
      <p :key="data.id" class="text item">{{ 'ID: ' + data.id }}</p>
      <template #footer>
        <p :key="data.name" class="text item">{{ 'Name: ' + data.name }}</p>
        <p :key="data.coordinates.id" class="text item">{{ 'Coordinate ID: ' + data.coordinates.id }}</p>
        <p :key="data.hairColor" class="text item">{{ 'Hair color: ' + data.hairColor }}</p>
        <p :key="data.location.name" class="text item">{{ 'Location name: ' + data.location.name }}</p>
        <p :key="data.birthday.toString()" class="text item">{{ 'Birthday: ' + data.birthday.toString() }}</p>
        <p :key="data.height" class="text item">{{ 'Height: ' + data.height }}</p>
        <p :key="data.weight" class="text item">{{ 'Weight: ' + data.weight }}</p>
        <p :key="data.passportId" class="text item">{{ 'PassportId: ' + data.passportId }}</p>
        <p :key="data.createdBy.username" class="text item">{{ 'Creator: ' + data.createdBy.username }}</p>
      </template>
    </el-card>

    <component
      :is="cardCoordinate"
      :obj="data.coordinates"
      class="card-item"
    />
  </div>
</template>

<style scoped>
.cards-container {
  display: flex;
  flex-wrap: wrap;
  gap: 16px;
}

.card-item {
  flex: 1 1 300px; /* Карточки будут минимум 300px, но могут растягиваться */
  min-width: 300px;
  max-width: 480px; /* Сохраняем ваш максимальный размер */
}
</style>