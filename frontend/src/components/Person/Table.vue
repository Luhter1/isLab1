<!-- components/Person/Table.vue -->
<script lang="ts" setup>
import { 
  Calendar,
  Location,
  View as EyeIcon,
  Postcard,
  TrendCharts,
} from '@element-plus/icons-vue'

// Форматирование даты
const formatDate = (date: string | null) => {
  if (!date) return '-'
  return new Date(date).toLocaleDateString('en-US', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  })
}

// Форматирование даты рождения с знаком зодиака
const formatBirthday = (date: string | null) => {
  if (!date) return 'Unknown'
  
  const d = new Date(date)
  const month = d.getMonth() + 1
  const day = d.getDate()
  
  return formatDate(date)
}


// Форматирование даты и времени
const formatDateTime = (date: string | null | undefined) => {
  if (!date) return '-'
  return new Date(date).toLocaleString('en-US', {
    year: 'numeric',
    month: 'short',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  })
}

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

// Форматирование паспорта
const formatPassportID = (passportID: string) => {
  // Форматирование паспорта, например: XXXX-XXXXXX -> ****-****XX
  if (passportID.length > 4) {
    return '****-****' + passportID.slice(-2)
  }
  return passportID
}
</script>

<template> 
  <!-- Name -->
  <el-table-column 
    prop="name" 
    label="Name" 
    min-width="100"
    sortable="custom"
  >
    <template #default="{ row }">
      <strong>{{ row.name }}</strong>
    </template>
  </el-table-column>
  
  <!-- Birthday -->
  <el-table-column 
    prop="birthday" 
    label="Birthday" 
    min-width="140"
  >
    <template #header>
      <span>
        <el-icon><Calendar /></el-icon>
        Birthday
      </span>
    </template>
    <template #default="{ row }">
      <div v-if="row.birthday" class="birthday-cell">
      <el-tooltip
        :content="formatDateTime(row.birthday)"
      >
        <span>{{ formatBirthday(row.birthday) }}</span>
      </el-tooltip>
      </div>
      <span v-else class="empty-value">Unknown</span>
    </template>
  </el-table-column>
  
  <!-- Eye Color -->
  <el-table-column 
    prop="eyeColor" 
    label="Eye Color" 
    min-width="120"
    sortable="custom"
  >
    <template #header>
      <span>
        <el-icon><EyeIcon /></el-icon>
        Eye Color
      </span>
    </template>
    <template #default="{ row }">
      <el-tag 
        v-if="row.eyeColor" 
        :type="getColorType(row.eyeColor)"
        effect="dark"
      >
        {{ row.eyeColor }}
      </el-tag>
      <span v-else class="empty-value">Unknown</span>
    </template>
  </el-table-column>
  
  <!-- Hair Color -->
  <el-table-column 
    prop="hairColor" 
    label="Hair Color" 
    min-width="120"
    sortable="custom"
  >
    <template #default="{ row }">
      <el-tag 
        v-if="row.hairColor" 
        :type="getColorType(row.hairColor)"
        effect="dark"
      >
        {{ row.hairColor }}
      </el-tag>
      <span v-else class="empty-value">Unknown</span>
    </template>
  </el-table-column>
  
  <!-- Physical Info (combined) -->
  <el-table-column 
    label="Physical Info" 
    min-width="120"
  >
    <template #header>
      <span>
        <el-icon><TrendCharts /></el-icon>
        Physical Info
      </span>
    </template>
    <template #default="{ row }">
      <div class="physical-info">
        <div class="physical-row">
          <span class="label">H:</span>
          <el-tag type="info" effect="plain">{{ row.height }} cm</el-tag>
        </div>
        <div class="physical-row">
          <span class="label">W:</span>
          <el-tag v-if="row.weight" type="info" effect="plain">
            {{ row.weight }} kg
          </el-tag>
          <span v-else class="empty-value">N/A</span>
        </div>
      </div>
    </template>
  </el-table-column>
  
  <!-- Location -->
  <el-table-column 
    prop="location.name" 
    label="Location" 
    min-width="100"
    sortable="custom"
  >
    <template #header>
      <span>
        <el-icon><Location /></el-icon>
        Location
      </span>
    </template>
    <template #default="{ row }">
      <el-tag v-if="row.locationId || row.location" type="success" effect="plain">
        <el-icon><Location /></el-icon>
        {{ row.location?.name || `ID: ${row.locationId}` }}
      </el-tag>
      <span v-else class="empty-value">No location</span>
    </template>
  </el-table-column>
  
  <!-- Passport ID -->
  <el-table-column 
    prop="passportID" 
    label="Passport ID" 
    min-width="130"
    sortable="custom"
  >
    <template #header>
      <span>
        <el-icon><Postcard /></el-icon>
        Passport
      </span>
    </template>
    <template #default="{ row }">
      <el-tooltip :content="row.passportId" placement="top">
        <el-tag type="warning" effect="plain">
          <el-icon><Postcard /></el-icon>
          {{ formatPassportID(row.passportId) }}
        </el-tag>
      </el-tooltip>
    </template>
  </el-table-column>
</template>

<style scoped lang="scss">
.birthday-cell {
  display: flex;
  align-items: center;
  gap: 6px;
  flex-wrap: wrap;
  
  .zodiac {
    font-size: 16px;
    cursor: pointer;
  }
}

.physical-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
  
  .physical-row {
    display: flex;
    align-items: center;
    gap: 6px;
    
    .label {
      font-weight: 500;
      color: #606266;
      width: 20px;
    }
  }
}

.empty-value {
  color: #c0c4cc;
  font-style: italic;
}
</style>