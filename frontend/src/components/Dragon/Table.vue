<script lang="ts" setup>
import { 
  User,
  Location,
  QuestionFilled
} from '@element-plus/icons-vue'

// Цветовая схема для тегов
const getColorType = (color: string | null) => {
  if (!color) return 'info'
  
  const colorMap = {
    RED: 'danger',
    BLUE: 'primary',
    YELLOW: 'warning',
    ORANGE: 'warning',
    GREEN: 'success',
    BLACK: 'info',
    WHITE: 'info'
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

// Форматирование даты
const formatDate = (date: string | null) => {
  if (!date) return '-'
  return new Date(date).toLocaleDateString('en-US', {
    year: 'numeric',
    month: 'short',
    day: 'numeric'
  })
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
    fixed="left"
  />
  
  <el-table-column 
    prop="age" 
    label="Age" 
    width="110" 
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
      <el-tag v-else type="info" effect="plain">
        <el-icon><QuestionFilled /></el-icon>
        Unknown
      </el-tag>
    </template>
  </el-table-column>
  
  <el-table-column 
    prop="color" 
    label="Color" 
    width="120"
  >
    <template #default="{ row }">
      <el-tag 
        v-if="row.color" 
        :type="getColorType(row.color)"
        effect="dark"
      >
        {{ row.color }}
      </el-tag>
      <el-tag v-else type="info" effect="plain">
        <el-icon><QuestionFilled /></el-icon>
        Unknown
      </el-tag>
    </template>
  </el-table-column>
  
  <el-table-column 
    prop="type" 
    label="Type" 
    width="200"
  >
    <template #default="{ row }">
      <div v-if="row.type" class="type-cell">
        <span class="type-icon">{{ getTypeIcon(row.type) }}</span>
        <el-tag type="info" effect="plain">{{ row.type }}</el-tag>
      </div>
      <el-tag v-else type="info" class="type-unk-cell" effect="plain">
        <el-icon><QuestionFilled /></el-icon>
        Unknown
      </el-tag>
    </template>
  </el-table-column>
  
  <el-table-column 
    prop="character" 
    label="Character" 
    width="150"
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
    width="150"
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
      <el-tag v-else type="info" effect="plain">
        <el-icon><QuestionFilled /></el-icon>
        Unknown
      </el-tag>
    </template>
  </el-table-column>
  
  <el-table-column 
    label="Cave" 
    width="120"
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
    width="150"
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
.table-toolbar {
  margin-bottom: 20px;
  
  .toolbar-actions {
    text-align: right;
  }
}

.filter-content {
  .filter-actions {
    text-align: right;
    margin-top: 10px;
    padding-top: 10px;
    border-top: 1px solid #ebeef5;
    
    .el-button {
      margin-left: 10px;
    }
  }
  
  .el-radio-group {
    display: flex;
    gap: 10px;
  }
}

.column-settings {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.name-cell {
  display: flex;
  align-items: center;
  gap: 5px;
  
  .info-icon {
    color: #909399;
    cursor: pointer;
    
    &:hover {
      color: #409eff;
    }
  }
}

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

.user-cell {
  display: flex;
  align-items: center;
  gap: 5px;
  color: #606266;
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