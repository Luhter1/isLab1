<script lang="ts" setup>
import { toRef } from 'vue'
import { DragonDto } from '@/interfaces/dto/dragons/DragonDto'
import cardCoordinate from '../Coordinates/Card.vue'
import cardDragonCave from '../DragonCave/Card.vue'
import cardPerson from '../Person/Card.vue'
import cardDragonHead from '../DragonHead/Card.vue'
import { displayValue } from '@/utils/displayValue'

interface Props {
  obj: DragonDto
}
const props = defineProps<Props>()
const data = toRef(props, 'obj')
</script>

<template>
  <div class="cards-container">
    <!-- Основная карточка Dragon -->
    <el-card class="main-card">
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

  <!-- Секция связанных объектов -->
    <div class="related-objects">
      <h3 class="section-title">Related Objects</h3>
      
      <!-- Обязательные вложенные объекты -->
      <div class="nested-cards">
        <div class="nested-card-wrapper">
          <component
            :is="cardCoordinate"
            :obj="data.coordinates"
          />
        </div>

        <!-- Опциональные вложенные объекты -->
        <div v-if="data.cave" class="nested-card-wrapper">
          <component
            :is="cardDragonCave"
            :obj="data.cave"
          />
        </div>

        <div v-if="data.head" class="nested-card-wrapper">
          <component
            :is="cardDragonHead"
            :obj="data.head"
          />
        </div>
      </div>
      <!-- Person отдельно, так как он может содержать свои вложенные объекты -->
      <div v-if="data.killer" class="killer-section">
        <h4 class="subsection-title">Killer Information</h4>
        <div class="killer-wrapper">
          <component
            :is="cardPerson"
            :obj="data.killer"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.dragon-container {
  display: flex;
  flex-direction: column;
  gap: 24px;
  max-width: 1400px;
  margin: 0 auto;
}

.main-card {
  width: 100%;
  max-width: 600px;
}

.related-objects {
  background-color: #f5f7fa;
  border-radius: 8px;
  padding: 20px;
}

.section-title {
  margin: 0 0 16px 0;
  color: #303133;
  font-size: 18px;
  font-weight: 600;
}

.subsection-title {
  margin: 24px 0 16px 0;
  color: #606266;
  font-size: 16px;
  font-weight: 500;
}

.nested-cards {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 16px;
  margin-bottom: 20px;
}

.nested-card-wrapper {
  display: flex;
  justify-content: center;
}

.nested-card-wrapper :deep(.el-card) {
  width: 100%;
  max-width: 400px;
}

.killer-section {
  border-top: 1px solid #dcdfe6;
  padding-top: 20px;
}

.killer-wrapper {
  display: flex;
  flex-wrap: wrap;
  gap: 16px;
}

/* Переопределяем стили для вложенных Person карточек */
.killer-wrapper :deep(.cards-container) {
  width: 100%;
  margin: 0;
}

.killer-wrapper :deep(.card-item) {
  margin: 0;
}
</style>