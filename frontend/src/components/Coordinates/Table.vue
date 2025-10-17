<template>
  <!-- Отдельная колонка X -->
  <el-table-column 
    prop="x" 
    label="X" 
    width="120"
    align="center"
  >
    <template #default="{ row }">
      <el-tag :type="row.x >= 0 ? 'success' : 'danger'">
        {{ row.x.toFixed(2) }}
      </el-tag>
    </template>
  </el-table-column>
  
  <!-- Отдельная колонка Y -->
  <el-table-column 
    prop="y" 
    label="Y" 
    width="120"
    align="center"
  >
    <template #default="{ row }">
      <el-tag :type="row.y >= 0 ? 'success' : 'danger'">
        {{ row.y.toFixed(2) }}
      </el-tag>
    </template>
  </el-table-column>
</template>