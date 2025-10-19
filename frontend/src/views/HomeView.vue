<script lang="ts" setup>
import { computed, onMounted, ref, watch } from 'vue'
import { Search } from '@element-plus/icons-vue'
import { useUserStore } from '@/stores/userStore'

import DragonService from '@/services/DragonService'
import DragonTable from '@/components/Dragon/Table.vue'

import CoordinatesService from '@/services/CoordinatesService'
import CoordinatesTable from '@/components/Coordinates/Table.vue'

import DragonCaveService from '@/services/DragonCaveService'
import DragonCaveTable from '@/components/DragonCave/Table.vue'

import DragonHeadService from '@/services/DragonHeadService'
import DragonHeadTable from '@/components/DragonHead/Table.vue'

import LocationService from '@/services/LocationService'
import LocationTable from '@/components/Location/Table.vue'

import PersonService from '@/services/PeopleService'
import PersonTable from '@/components/Person/Table.vue'

const userStore = useUserStore()

// Типы данных для отображения
const dataTypes = [
  { 
    value: 'dragons', 
    label: 'Dragons', 
    service: DragonService, 
    component: DragonTable,
    filterableFields: [
      { field: 'name', label: 'Name', type: 'string' },
      { field: 'killer.name', label: 'Killer', type: 'string' },
      { field: 'color', label: 'Color', type: 'enum', options: ['RED', 'BLUE', 'YELLOW', 'ORANGE'] },
      { field: 'type', label: 'Type', type: 'enum', options: ['WATER', 'UNDERGROUND', 'AIR', 'FIRE'] },
      { field: 'character', label: 'Character', type: 'enum', options: ['EVIL', 'CHAOTIC_EVIL', 'FICKLE'] }
    ]
  },
  { value: 'coordinates', label: 'Coordinates', service: CoordinatesService, component: CoordinatesTable, filterableFields: [] },
  { value: 'coordinates', label: 'Coordinates', service: CoordinatesService, component: CoordinatesTable, filterableFields: [] },
  { value: 'dragon-cave', label: 'Dragon Cave', service: DragonCaveService, component: DragonCaveTable, filterableFields: [] },
  { value: 'dragon-head', label: 'Dragon Head', service: DragonHeadService, component: DragonHeadTable, filterableFields: [] },
  { 
    value: 'location', 
    label: 'Location', 
    service: LocationService, 
    component: LocationTable, 
    filterableFields: [
      { field: 'name', label: 'Name', type: 'string' },
    ] 
  },
  { 
    value: 'person', 
    label: 'Person', 
    service: PersonService, 
    component: PersonTable, 
    filterableFields: [
      { field: 'name', label: 'Name', type: 'string' },
      { field: 'location.name', label: 'Location', type: 'string' },
      { field: 'passportId', label: 'Passport', type: 'string' },
      { field: 'eyeColor', label: 'Eye Color', type: 'enum', options: ['RED', 'BLUE', 'YELLOW', 'ORANGE'] },
      { field: 'hairColor', label: 'Hair Color', type: 'enum', options: ['RED', 'BLUE', 'YELLOW', 'ORANGE'] },
    ]
  },
]

// Текущий выбранный тип данных
const selectedDataType = ref('dragons')

// Состояние фильтров
const filters = ref<Record<string, string>>({})
const showFilters = ref(false)
const filterInputs = ref<Record<string, string>>({})

// Получаем текущий тип данных, сервис и компонент
const currentDataType = computed(() => {
  return dataTypes.find(t => t.value === selectedDataType.value)
})
const currentService = computed(() => {
  const type = dataTypes.find(t => t.value === selectedDataType.value)
  return type ? type.service : DragonService
})
const currentTableComponent = computed(() => {
  const type = dataTypes.find(t => t.value === selectedDataType.value)
  return type ? type.component : DragonTable
})

// Доступные поля для фильтрации
const filterableFields = computed(() => {
  return currentDataType.value?.filterableFields || []
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

// Счетчик активных фильтров
const activeFiltersCount = computed(() => {
  return Object.keys(filters.value).length
})

// Применение фильтров
const applyFilters = () => {
  filters.value = { ...filterInputs.value }

  filterInputs.value = {}
  // Удаляем пустые фильтры
  Object.keys(filters.value).forEach(key => {
    if (!filters.value[key] || filters.value[key].trim() === '') {
      delete filters.value[key]
    }
  })
  
  // Сбрасываем на первую страницу при применении фильтров
  currentPage.value = 1
  
  // Обновляем фильтры в сервисе и перезагружаем данные
  currentService.value.setFilters(filters.value)
  currentService.value.fetchObjects()
}

// Сброс фильтров
const resetFilters = () => {
  filters.value = {}
  filterInputs.value = {}
  currentService.value.clearFilters()
  currentService.value.fetchObjects()
}

// Быстрая фильтрация (поиск)
const quickSearch = ref('')
const quickSearchField = ref('name')

const handleQuickSearch = () => {
  if (quickSearch.value.trim()) {
    filters.value = {
      [quickSearchField.value]: quickSearch.value
    }
    filterInputs.value = { ...filters.value }
    currentService.value.setFilters(filters.value)
    currentService.value.fetchObjects()
  }
}

// Очистка быстрого поиска
const clearQuickSearch = () => {
  quickSearch.value = ''
  resetFilters()
}

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

// Обработчик изменения сортировки
const handleSortChange = ({ prop, order }: { prop: string; order: string | null }) => {
  const prepOrder = order === 'descending' ? 'desc' : order === 'ascending' ? 'asc' : 'null'

  currentService.value.addSort(prop, prepOrder)
  currentService.value.fetchObjects()
}

onMounted(() => {
  currentService.value.fetchObjects()
})

// Следим за изменением типа данных
watch(selectedDataType, () => {
  resetFilters()
  currentService.value.fetchObjects()
  currentService.value.resetSort()
})
</script>

<template>
  <div class="data-table">
    <el-card>
      <!-- Header с выбором типа данных -->
      <template #header>
        <div class="card-header">
          <div class="header-left">
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

            <!-- Быстрый поиск -->
            <el-input
              v-model="quickSearch"
              placeholder="Quick search..."
              clearable
              @clear="clearQuickSearch"
              @keyup.enter="handleQuickSearch"
              style="width: 250px"
            >
              <template #prepend>
                <el-select v-model="quickSearchField" style="width: 100px">
                  <el-option
                    v-for="field in filterableFields"
                    :key="field.field"
                    :label="field.label"
                    :value="field.field"
                  />
                </el-select>
              </template>
              <template #append>
                <el-button :icon="Search" @click="handleQuickSearch" />
              </template>
            </el-input>
          </div>

          <div class="header-right">
            <!-- Кнопка фильтров -->
            <el-badge :value="activeFiltersCount" :hidden="activeFiltersCount === 0">
              <el-button 
                @click="showFilters = !showFilters"
                :type="activeFiltersCount > 0 ? 'primary' : 'default'"
              >
                <el-icon><Filter /></el-icon>
                Filters
              </el-button>
            </el-badge>

            <el-button @click="currentService.fetchObjects()">
              <el-icon><RefreshRight /></el-icon>
              Refresh
            </el-button>
            
            <el-button type="primary">
              <router-link :to="`/create/${name}`">
                <el-icon><Plus /></el-icon>
                Add New
              </router-link>
            </el-button>
          </div>
        </div>
      </template>

      <!-- Панель фильтров -->
      <el-collapse-transition>
        <div v-show="showFilters" class="filter-panel">
          <el-divider content-position="left">
            <el-icon><Filter /></el-icon>
            Advanced Filters
          </el-divider>
          
          <el-form label-width="120px">
            <el-row :gutter="20">
              <el-col 
                v-for="field in filterableFields" 
                :key="field.field"
                :span="12"
              >
                <el-form-item :label="field.label">
                  <!-- Для enum полей показываем select -->
                  <el-select
                    v-if="field.type === 'enum'"
                    v-model="filterInputs[field.field]"
                    clearable
                    :placeholder="`Select ${field.label}`"
                    style="width: 100%"
                  >
                    <el-option
                      v-for="option in field.options"
                      :key="option"
                      :label="option"
                      :value="option"
                    />
                  </el-select>
                  
                  <!-- Для строковых полей показываем input -->
                  <el-input
                    v-else
                    v-model="filterInputs[field.field]"
                    clearable
                    :placeholder="`Enter ${field.label}`"
                  />
                </el-form-item>
              </el-col>
            </el-row>
            
            <el-form-item>
              <el-button type="primary" @click="applyFilters">
                Apply Filters
              </el-button>
              <el-button @click="resetFilters">
                Reset All
              </el-button>
              <el-text v-if="activeFiltersCount > 0" type="info" style="margin-left: 20px">
                {{ activeFiltersCount }} filter(s) active
              </el-text>
            </el-form-item>
          </el-form>
        </div>
      </el-collapse-transition>

      <!-- Активные фильтры в виде тегов -->
      <div v-if="activeFiltersCount > 0" class="active-filters">
        <el-text type="info" style="margin-right: 10px">Active filters:</el-text>
        <el-tag
          v-for="(value, key) in filters"
          :key="key"
          closable
          @close="delete filters[key]; applyFilters()"
          style="margin-right: 8px"
        >
          {{ key }}: {{ value }}
        </el-tag>
        <el-button 
          link 
          type="danger" 
          size="small"
          @click="resetFilters"
        >
          Clear all
        </el-button>
      </div>

      <!-- Table -->
      <el-table
        :data="tableData"
        v-loading="loading"
        style="width: 100%"
        :key="selectedDataType"
        @sort-change="handleSortChange"
        :default-sort="{ prop: 'id', order: 'asc' }"
      >

        <el-table-column 
          prop="id" 
          label="ID" 
          width="80" 
          fixed="left"
          sortable="custom"
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
            <el-button size="small" type="primary" v-if="userStore.isLoggedIn">
              <router-link :to="`/update/${name}?id=${row.id}`">
                <el-icon><Edit /></el-icon>
              </router-link>
            </el-button>
            <el-button size="small" type="danger" v-if="userStore.isLoggedIn">
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
    
    .header-left {
      display: flex;
      gap: 12px;
      align-items: center;
    }
    
    .header-right {
      display: flex;
      gap: 12px;
      align-items: center;
    }
  }

  .filter-panel {
    padding: 20px;
    background-color: #f5f7fa;
    border-radius: 4px;
    margin-bottom: 20px;
  }

  .active-filters {
    padding: 12px;
    background-color: #ecf5ff;
    border-left: 4px solid #409eff;
    margin-bottom: 20px;
    display: flex;
    align-items: center;
    flex-wrap: wrap;
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