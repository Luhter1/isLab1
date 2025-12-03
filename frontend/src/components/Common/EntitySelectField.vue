<script lang="ts" setup>
import { computed, ref } from 'vue'
import type { Component } from 'vue'

const props = defineProps<{
  modelValue: number | null | undefined
  label?: string
  placeholder?: string
  service
  tableComponent: Component
}>()

const emit = defineEmits<{
  (e: 'update:modelValue', value: number | null): void
}>()

const dialogVisible = ref(false)

const value = computed({
  get: () => props.modelValue,
  set: (val) => emit('update:modelValue', val ?? null),
})

const tableData = computed(() => props.service.state.objects)
const loading = computed(() => props.service.state.loading)

const currentPage = computed({
  get: () => props.service.state.currentPage ?? 1,
  set: (val) => props.service.updatePage?.(val),
})

const pageSize = computed({
  get: () => props.service.state.pageSize ?? 10,
  set: (val) => props.service.updatePageSize?.(val),
})

const openDialog = async () => {
  dialogVisible.value = true
  await props.service.fetchObjects()
}

const handleRowDblClick = (row: any) => {
  value.value = Number(row.id)
  dialogVisible.value = false
}

const handleSelectClick = (row: any) => {
  value.value = Number(row.id)
  dialogVisible.value = false
}
</script>

<template>
  <!-- Поле ввода, которое открывает диалог -->
  <el-input
    v-model="value"
    type="number"
    :placeholder="placeholder || 'Click to select...'"
    clearable
    @focus="openDialog"
  >
    <template #append>
      <el-button @click="openDialog">Выбрать</el-button>
    </template>
  </el-input>

  <!-- Диалог с таблицей -->
  <el-dialog
    v-model="dialogVisible"
    :title="label || 'Select item'"
    width="80%"
  >
    <el-table
      :data="tableData"
      v-loading="loading"
      style="width: 100%"
      @row-dblclick="handleRowDblClick"
    >
      <!-- ID -->
      <el-table-column
        prop="id"
        label="ID"
        width="80"
        fixed="left"
      />

      <!-- Динамические колонки конкретной сущности -->
      <component :is="tableComponent" />

      <!-- Кнопка выбора -->
      <el-table-column label="Выбрать" width="120" align="center">
        <template #default="{ row }">
          <el-button
            type="primary"
            size="small"
            @click="handleSelectClick(row)"
          >
            Выбрать
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      v-if="props.service.state.totalObjects !== undefined"
      v-model:current-page="currentPage"
      v-model:page-size="pageSize"
      :total="props.service.state.totalObjects"
      :page-sizes="[10, 20, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper"
      style="margin-top: 16px; text-align: center"
    />

    <template #footer>
      <el-button @click="dialogVisible = false">Закрыть</el-button>
    </template>
  </el-dialog>
</template>