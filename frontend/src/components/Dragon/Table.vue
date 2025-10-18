<script lang="ts" setup>
import { 
  User,
  Location,
} from '@element-plus/icons-vue'

// Цветовая схема для тегов
const getColorType = (color: string | null) => {
  if (!color) return 'info'
  
  const colorMap = {
    RED: 'danger',
    BLUE: 'primary',
    YELLOW: 'warning',
    ORANGE: 'warning'
  }
  return colorMap[color] || 'info'
}

// Форматирование типа дракона
const getTypeIcon = (type: string | null) => {
  if (!type) return '❓'
  
  const typeIcons = {
    WATER: '💧',
    AIR: '🌪️',
    FIRE: '🔥',
    UNDERGROUND: '⛏️'
  }
  return typeIcons[type] || '🐉'
}

// Форматирование характера
const getCharacterType = (character: string | null) => {
  if (!character) return 'info'
  
  const characterMap = {
    CUNNING: 'warning',
    GOOD: 'success',
    CHAOTIC: 'danger',
    CHAOTIC_EVIL: 'danger',
    FICKLE: 'info'
  }
  return characterMap[character] || 'info'
}

// Форматирование возраста
const formatAge = (age: number | null) => {
  if (age === null || age === undefined) return 'Unknown'
  if (age > 1000) return `${(age / 1000).toFixed(1)}k`
  return age.toString()
}

// Цвета для возраста
const getAgeColor = (age: number | null) => {
  if (age === null || age === undefined) return '#909399' // неизвестный
  if (age < 100) return '#67C23A' // молодой
  if (age < 500) return '#E6A23C' // средний
  if (age < 1000) return '#F56C6C' // старый
  return '#909399' // древний
}

// Получение текста для возраста
const getAgeText = (age: number | null) => {
  if (age === null || age === undefined) return 'Unknown age'
  return `${age} years old`
}
</script>

<template>

  <el-table-column 
    prop="name" 
    label="Name" 
    min-width="100"
  >
    <template #default="{ row }">
      <strong>{{ row.name }}</strong>
    </template>
  </el-table-column>
  
  <el-table-column 
    prop="age" 
    label="Age" 
    min-width="110" 
    align="center"
  >
    <template #default="{ row }">
      <el-tooltip v-if="row.age !== null" :content="getAgeText(row.age)">
        <el-tag 
          :color="getAgeColor(row.age)" 
          style="color: white; border: none;"
        >
          {{ formatAge(row.age) }}
        </el-tag>
      </el-tooltip>
      <span v-else class="empty-value">Unknown</span>
    </template>
  </el-table-column>
  
  <el-table-column 
    prop="color" 
    label="Color" 
    min-width="120"
  >
    <template #default="{ row }">
      <el-tag 
        v-if="row.color" 
        :type="getColorType(row.color)"
        effect="dark"
      >
        {{ row.color }}
      </el-tag>
      <span v-else class="empty-value">Unknown</span>
    </template>
  </el-table-column>
  
  <el-table-column 
    prop="type" 
    label="Type" 
    min-width="165"
  >
    <template #default="{ row }">
      <div v-if="row.type" class="type-cell">
        <span class="type-icon">{{ getTypeIcon(row.type) }}</span>
        <el-tag type="info" effect="plain">{{ row.type }}</el-tag>
      </div>
      <span v-else class="empty-value">Unknown</span>
    </template>
  </el-table-column>
  
  <el-table-column 
    prop="character" 
    label="Character" 
    min-width="150"
    align="center"
  >
    <template #default="{ row }">
      <el-tag class="char-cell"
        :type="getCharacterType(row.character)"
        effect="plain"
      >
        {{ row.character }}
      </el-tag>
    </template>
  </el-table-column>

  <el-table-column 
    prop="head" 
    label="Head" 
    min-width="150"
    align="center"
  >
    <template #default="{ row }">
      <el-tooltip v-if="row.head !== null && row.head !== undefined" :content="`Head id: ${row.head.id}`">
        <el-tag 
          :type="row.head ? 'success' : 'danger'"
          effect="dark"
        >
          {{ row.head.size ? row.head?.size : 'none' }}
        </el-tag>
      </el-tooltip>
      <span v-else class="empty-value">Unknown</span>
    </template>
  </el-table-column>
  
  <el-table-column 
    label="Cave" 
    min-width="120"
    align="center"
  >
    <template #header>
      <span>
        <el-icon><Location /></el-icon>
        Cave
      </span>
    </template>
    <template #default="{ row }">
      <el-tooltip v-if="row.cave?.depth !== null && row.cave?.depth !== undefined" :content="`Cave id: ${row.cave.id}`">
        <el-tag type="success" effect="plain">
          {{ row.cave.depth }}m
        </el-tag>
      </el-tooltip>
      <el-tag v-else type="info" effect="plain">
        No cave
      </el-tag>
    </template>
  </el-table-column>
  
  <el-table-column 
    label="Status" 
    min-width="150"
    align="center"
  >
    <template #default="{ row }">
      <div v-if="row.killer?.name" class="killer-cell">
        <el-tooltip :content="`Killer id: ${row.killer.id}`">
          <el-tag type="danger" effect="dark">
            <el-icon><User /></el-icon>
            {{ row.killer.name }}
          </el-tag>
        </el-tooltip>
      </div>
      <el-tag v-else type="success" effect="plain">
        Alive
      </el-tag>
    </template>
  </el-table-column>
</template>

<style scoped lang="scss">
.type-cell {
  display: flex;
  align-items: center;
  gap: 5px;
  
  .type-icon {
    font-size: 18px;
  }
}

.type-unk-cell{
  margin-left: 30px;
}

.char-cell{
  width: 100%;
}

.killer-cell {
  .el-tag {
    display: inline-flex;
    align-items: center;
    gap: 4px;
  }
}

.empty-value {
  color: #c0c4cc;
  font-style: italic;
}
</style>