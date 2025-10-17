<script lang="ts" setup>
import { computed, onMounted, ref, watch } from 'vue'
import DragonService from '@/services/DragonService'
import DragonTable from '@/components/Dragon/Table.vue'

import CoordinatesService from '@/services/CoordinatesService'
import CoordinatesTable from '@/components/Coordinates/Table.vue'

// Типы данных для отображения
const dataTypes = [
  { value: 'dragons', label: 'Dragons', service: DragonService, component: DragonTable },
  { value: 'coordinates', label: 'Coordinates', service: CoordinatesService, component: CoordinatesTable },
  // Добавьте другие типы данных здесь
]

// Текущий выбранный тип данных
const selectedDataType = ref('dragons')

// Получаем текущий сервис и компонент
const currentService = computed(() => {
  const type = dataTypes.find(t => t.value === selectedDataType.value)
  return type ? type.service : DragonService
})

const currentTableComponent = computed(() => {
  const type = dataTypes.find(t => t.value === selectedDataType.value)
  return type ? type.component : DragonTable
})

// Вычисляемые свойства на основе текущего сервиса
const tableData = computed(() => currentService.value.state.objects)
const loading = computed(() => currentService.value.state.loading)
const totalItems = computed(() => currentService.value.state.totalObjects)
const name = computed(() => currentService.value.getName())

const currentPage = computed({
  get: () => currentService.value.state.currentPage,
  set: (val) => currentService.value.updatePage(val)
})

const pageSize = computed({
  get: () => currentService.value.state.pageSize,
  set: (val) => currentService.value.updatePageSize(val)
})

// Форматирование даты
const formatDate = (date: string | null | undefined) => {
  if (!date) return '-'
  return new Date(date).toLocaleDateString('en-US', {
    year: 'numeric',
    month: 'short',
    day: 'numeric'
  })
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

// Относительное время
const getRelativeTime = (date: string | null | undefined) => {
  if (!date) return 'Never'
  
  const now = new Date()
  const past = new Date(date)
  const diffMs = now.getTime() - past.getTime()
  const diffMins = Math.floor(diffMs / 60000)
  const diffHours = Math.floor(diffMs / 3600000)
  const diffDays = Math.floor(diffMs / 86400000)
  
  if (diffMins < 1) return 'Just now'
  if (diffMins < 60) return `${diffMins} min ago`
  if (diffHours < 24) return `${diffHours} hours ago`
  if (diffDays < 7) return `${diffDays} days ago`
  if (diffDays < 30) return `${Math.floor(diffDays / 7)} weeks ago`
  if (diffDays < 365) return `${Math.floor(diffDays / 30)} months ago`
  return `${Math.floor(diffDays / 365)} years ago`
}

// Проверка на недавно измененные записи
const isRecentlyModified = (date: string | null | undefined) => {
  if (!date) return false
  const hourAgo = new Date(Date.now() - 3600000)
  return new Date(date) > hourAgo
}


onMounted(() => {
  currentService.value.fetchObjects()
})

// Следим за изменением типа данных
watch(selectedDataType, () => {
  currentService.value.fetchObjects()
})
</script>

<template>
  <div class="data-table">
    <el-card>
      <!-- Header с выбором типа данных -->
      <template #header>
        <div class="card-header">
          <el-select 
            v-model="selectedDataType" 
            placeholder="Select data type"
            size="large"
            style="width: 200px"
          >
            <el-option
              v-for="type in dataTypes"
              :key="type.value"
              :label="type.label"
              :value="type.value"
            />
          </el-select>
          
          <el-button type="primary">
            <router-link :to="`/create/${name}`">
              <el-icon><Plus /></el-icon>
              Add New
            </router-link>
          </el-button>
        </div>
      </template>

      <!-- Table -->
      <el-table
        :data="tableData"
        v-loading="loading"
        style="width: 100%"
        :key="selectedDataType"
      >

        <el-table-column 
          prop="id" 
          label="ID" 
          width="80" 
          fixed="left"
        />
        <!-- Динамический компонент таблицы -->
        <component :is="currentTableComponent" />

        <!-- Created By -->
        <el-table-column 
          label="Created By" 
          align="center"
        >
          <template #header>
            <span>
              <el-icon><User /></el-icon>
              Created By
            </span>
          </template>
          <template #default="{ row }">
            <div v-if="row.createdBy" class="user-cell">
              <span>{{ row.createdBy.username }}</span>
            </div>
            <span v-else class="empty-value">System</span>
          </template>
        </el-table-column>
        
        <!-- Created At -->
        <el-table-column 
          prop="createdAt" 
          label="Created" 
          align="center"
        >
          <template #header>
            <span>
              <el-icon><Calendar /></el-icon>
              Created
            </span>
          </template>
          <template #default="{ row }">
            <el-tooltip 
              v-if="row.createdAt"
              :content="formatDateTime(row.createdAt)"
            >
              <div class="date-cell">
                <span>{{ formatDate(row.createdAt) }}</span>
              </div>
            </el-tooltip>
            <span v-else class="empty-value">-</span>
          </template>
        </el-table-column>
        
        <!-- Updated By -->
        <el-table-column 
          label="Updated By" 
          align="center"
        >
          <template #header>
            <span>
              <el-icon><Edit /></el-icon>
              Updated By
            </span>
          </template>
          <template #default="{ row }">
            <div v-if="row.updatedBy" class="user-cell">
              <span>{{ row.updatedBy.username }}</span>
            </div>
            <span v-else class="empty-value">-</span>
          </template>
        </el-table-column>
        
        <!-- Updated At -->
        <el-table-column 
          prop="updatedAt" 
          label="Updated" 
          align="center"
        >
          <template #header>
            <span>
              <el-icon><Calendar /></el-icon>
              Updated
            </span>
          </template>
          <template #default="{ row }">
            <el-tooltip 
              v-if="row.updatedAt"
              :content="formatDateTime(row.updatedAt)"
            >
              <div class="date-cell">
                <span>{{ formatDate(row.updatedAt) }}</span>
              </div>
            </el-tooltip>
            <span v-else class="empty-value">Never</span>
          </template>
        </el-table-column>
        
        <!-- Last Modified (relative time) -->
        <el-table-column 
          label="Last Modified" 
          width="130"
        >
          <template #header>
            <span>
              <el-icon><Timer /></el-icon>
              Last Modified
            </span>
          </template>
          <template #default="{ row }">
            <el-tooltip
              :content="formatDateTime(row.updatedAt || row.createdAt)"
            >
              <span class="relative-time">
                {{ getRelativeTime(row.updatedAt || row.createdAt) }}
              </span>
            </el-tooltip>
          </template>
        </el-table-column>
        
        <!-- Actions column -->
        <el-table-column label="Actions" align="center" width="200px" fixed="right">
          <template #default="{ row }">
            <el-button size="small">
              <router-link :to="`/view/${name}?id=${row.id}`">
                <el-icon><View /></el-icon>
              </router-link>
            </el-button>
            <el-button size="small" type="primary">
              <router-link :to="`/update/${name}?id=${row.id}`">
                <el-icon><Edit /></el-icon>
              </router-link>
            </el-button>
            <el-button size="small" type="danger">
              <router-link :to="`/delete/${name}?id=${row.id}`">
                <el-icon><Delete /></el-icon>
              </router-link>
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- Pagination -->
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[10, 20, 50, 100]"
        :total="totalItems"
        layout="total, sizes, prev, pager, next, jumper"
        class="pagination"
      />
    </el-card>
  </div>
</template>

<style scoped lang="scss">
.data-table {
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    gap: 20px;
  }

  .filters {
    margin-bottom: 20px;
  }

  .pagination {
    margin-top: 20px;
    display: flex;
    justify-content: center;
  }

  a {
    text-decoration: none;
    color: inherit;
    display: flex;
    align-items: center;
  }
}
</style>