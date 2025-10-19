<script lang="ts" setup>
import { onMounted } from "vue"
import DragonService from '@/services/DragonService'

const name = 'DragonInfoPage'

const deepestCaveDragon = {
    name: "test",
    age: "test",
    caveDepth: "test",
    color: "test",
}
</script>

<template>
  <div class="dragon-info-page">
    <div class="container">
      <h1>🐉 Информация о драконах</h1>
      
      <!-- Блок со средним возрастом -->
      <div class="info-card">
        <h2>📊 Статистика</h2>
        <div class="stat-item">
          <span class="label">Средний возраст драконов:</span>
          <span class="value">10 лет</span>
        </div>
        <div class="stat-item">
          <span class="label">Всего драконов:</span>
          <span class="value">{{ DragonService.getTotalObjects() }}</span>
        </div>
      </div>

      <!-- Блок с драконом из самой глубокой пещеры -->
      <div class="info-card">
        <h2>🏔️ Дракон из самой глубокой пещеры</h2>
        <div v-if="deepestCaveDragon" class="dragon-details">
          <div class="detail-item">
            <span class="label">Имя:</span>
            <span class="value">{{ deepestCaveDragon.name }}</span>
          </div>
          <div class="detail-item">
            <span class="label">Возраст:</span>
            <span class="value">{{ deepestCaveDragon.age }} лет</span>
          </div>
          <div class="detail-item">
            <span class="label">Глубина пещеры:</span>
            <span class="value">{{ deepestCaveDragon.caveDepth }} м</span>
          </div>
          <div class="detail-item">
            <span class="label">Цвет:</span>
            <span class="value">{{ deepestCaveDragon.color }}</span>
          </div>
        </div>
        <div v-else class="no-data">
          Данные не найдены
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.dragon-info-page {
  min-height: 100vh;
  padding: 20px;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.container {
  max-width: 1000px;
  margin: 0 auto;
}

h1 {
  text-align: center;
  color: grey;
  font-size: 2.5rem;
  margin-bottom: 30px;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
}

.info-card {
  background: white;
  border-radius: 15px;
  padding: 25px;
  margin-bottom: 25px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
}

.info-card h2 {
  color: #764ba2;
  margin-bottom: 20px;
  font-size: 1.5rem;
}

.stat-item,
.detail-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
  border-bottom: 1px solid #e0e0e0;
}

.stat-item:last-child,
.detail-item:last-child {
  border-bottom: none;
}

.label {
  font-weight: 600;
  color: #555;
}

.value {
  font-size: 1.2rem;
  color: #764ba2;
  font-weight: bold;
}

.dragon-details {
  background: #f8f9fa;
  padding: 15px;
  border-radius: 10px;
}

.dragons-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 15px;
}

.dragons-table thead {
  background: #764ba2;
  color: white;
}

.dragons-table th,
.dragons-table td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #e0e0e0;
}

.dragons-table tbody tr:hover {
  background: #f8f9fa;
}

.dragons-table tbody tr:last-child td {
  border-bottom: none;
}

.no-data {
  text-align: center;
  color: #999;
  padding: 20px;
  font-style: italic;
}

/* Адаптивность */
@media (max-width: 768px) {
  h1 {
    font-size: 2rem;
  }
  
  .info-card {
    padding: 15px;
  }
  
  .dragons-table {
    font-size: 0.9rem;
  }
  
  .dragons-table th,
  .dragons-table td {
    padding: 8px;
  }
}
</style>