<script lang="ts" setup>
import { toRef } from 'vue'
import { DragonDto } from '@/interfaces/dto/dragons/DragonDto'
import cardCoordinate from '../Coordinates/Card.vue'
import cardDragonCave from '../DragonCave/Card.vue'
import cardPerson from '../Person/Card.vue'
import cardDragonHead from '../DragonHead/Card.vue'
import { displayValue } from '../utils/displayValue'

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
          <b>Dragon</b>
        </div>
      </template>
      <p :key="data.id" class="text item">{{ 'ID: ' + data.id }}</p>
      <template #footer>
        <p :key="data.name" class="text item">{{ 'Name: ' + data.name }}</p>
        <p :key="data.coordinates.id" class="text item">{{ 'Coordinate ID: ' + data.coordinates.id }}</p>
        <p :key="data.cave?.id" class="text item">{{ 'Cave ID: ' + displayValue(data.cave?.id) }}</p>
        <p :key="data.killer?.name" class="text item">{{ 'Killer name: ' + displayValue(data.killer?.name) }}</p>
        <p :key="data.age" class="text item">{{ 'Age: ' + displayValue(data.age) }}</p>
        <p :key="data.color" class="text item">{{ 'Color: ' + displayValue(data.color) }}</p>
        <p :key="data.type" class="text item">{{ 'Type: ' + displayValue(data.color) }}</p>
        <p :key="data.character" class="text item">{{ 'Character: ' + data.character }}</p>
        <p :key="data.head?.id" class="text item">{{ 'Head ID: ' + displayValue(data.head?.id) }}</p>
        <p :key="data.createdBy.username" class="text item">{{ 'Creator: ' + data.createdBy.username }}</p>
      </template>
    </el-card>

    <component
      :is="cardCoordinate"
      :obj="data.coordinates"
      class="card-item"
    />

    <component
      :is="cardDragonCave"
      :obj="data.cave"
      v-if="data.cave"
      class="card-item"
    />

    <component
      :is="cardPerson"
      :obj="data.killer"
      v-if="data.killer"
      class="card-item"
    />

    <component
      :is="cardDragonHead"
      :obj="data.head"
      v-if="data.head"
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