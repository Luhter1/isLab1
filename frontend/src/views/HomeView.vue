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
        <!-- Динамический компонент таблицы -->
        <component :is="currentTableComponent" />
        
        <!-- Actions column -->
        <el-table-column label="Actions" width="200" fixed="right">
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