<script lang="ts" setup>
</script>

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
    margin-top: 15px;
    padding-top: 15px;
    border-top: 1px solid #ebeef5;
    
    .el-button {
      margin-left: 10px;
    }
  }
  
  .el-input-number {
    width: 100%;
  }
  
  .el-date-picker {
    width: 100%;
  }
}

.column-settings {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.coordinate-cell {
  display: flex;
  align-items: center;
  gap: 6px;
  flex-wrap: wrap;
  
  .el-tag {
    cursor: pointer;
    transition: transform 0.2s;
    
    &:hover {
      transform: scale(1.05);
    }
  }
}

.user-cell {
  display: flex;
  align-items: center;
  gap: 8px;
  
  .el-avatar {
    flex-shrink: 0;
  }
  
  span {
    font-weight: 500;
    color: #606266;
  }
}

.date-cell {
  display: flex;
  align-items: center;
  gap: 6px;
  
  span {
    color: #606266;
  }
}

.relative-time {
  color: #909399;
  font-style: italic;
}

.empty-value {
  color: #c0c4cc;
  font-style: italic;
}

// Анимация для новых и недавно измененных записей
@keyframes pulse {
  0% {
    box-shadow: 0 0 0 0 rgba(67, 194, 58, 0.4);
  }
  70% {
    box-shadow: 0 0 0 10px rgba(67, 194, 58, 0);
  }
  100% {
    box-shadow: 0 0 0 0 rgba(67, 194, 58, 0);
  }
}

.el-tag {
  &[type="success"][effect="dark"] {
    animation: pulse 2s infinite;
  }
}
</style>