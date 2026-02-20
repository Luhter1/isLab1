<script lang="ts" setup>
import { onMounted, watch, ref } from "vue"
import DragonService from '@/services/DragonService'
import type { DragonResultDto } from '@/interfaces/dto/specialoperations/DragonResultDto';
import type { AverageAgeDto } from '@/interfaces/dto/specialoperations/AverageAgeDto';

const AverageAgeData = ref({} as AverageAgeDto)
const DeepestCaveDragon = ref({} as DragonResultDto)

const deepestCaveDragon = {
    name: "test",
    age: "test",
    caveDepth: "test",
    color: "test",
}

watch(() => DragonService.getTotalObjects(), async (newValue) => {
    AverageAgeData.value = await DragonService.getAverageAge()
    DeepestCaveDragon.value = await DragonService.getDeepestCaveDragon()
})

onMounted(async () => {
    AverageAgeData.value = await DragonService.getAverageAge()
    DeepestCaveDragon.value = await DragonService.getDeepestCaveDragon()
})
</script>

<template>
  <div class="dragon-info-page">
    <div class="container">
      <h1 class="page-title">
        <span class="title-icon">🐉</span>
        <span class="title-text">Dragon Analytics</span>
      </h1>

      <!-- Блок со средним возрастом -->
      <div class="info-card stats-card">
        <h2 class="card-title">
          <span class="card-icon">📊</span>
          Statistics
        </h2>
        <div class="stat-item">
          <span class="label">Average Age:</span>
          <span class="value" v-if="AverageAgeData.averageAge">{{ AverageAgeData.averageAge }}</span>
          <span class="value error" v-else>{{ AverageAgeData.errorMessage }}</span>
        </div>
        <div class="stat-item">
          <span class="label">Total Dragons:</span>
          <span class="value">{{ DragonService.getTotalObjects() }}</span>
        </div>
      </div>

      <!-- Блок с драконом из самой глубокой пещеры -->
      <div class="info-card dragon-card">
        <h2 class="card-title">
          <span class="card-icon">🏔️</span>
          Dragon from Deepest Cave
        </h2>
        <div v-if="DeepestCaveDragon.dragon" class="dragon-details">
          <div class="detail-item">
            <span class="label">Name:</span>
            <span class="value">{{ DeepestCaveDragon.dragon.name }}</span>
          </div>
          <div class="detail-item" v-if="DeepestCaveDragon.dragon.age">
            <span class="label">Age:</span>
            <span class="value">{{ DeepestCaveDragon.dragon.age }} years</span>
          </div>
          <div class="detail-item">
            <span class="label">Cave Depth:</span>
            <span class="value">{{ DeepestCaveDragon.dragon.cave.depth }} m</span>
          </div>
          <div class="detail-item" v-if="DeepestCaveDragon.dragon.color">
            <span class="label">Color:</span>
            <el-tag type="primary" effect="dark">{{ DeepestCaveDragon.dragon.color }}</el-tag>
          </div>
          <div class="detail-item" v-if="DeepestCaveDragon.dragon.type">
            <span class="label">Type:</span>
            <span class="value">{{ DeepestCaveDragon.dragon.type }}</span>
          </div>
          <div class="detail-item" v-if="DeepestCaveDragon.dragon.character">
            <span class="label">Character:</span>
            <span class="value">{{ DeepestCaveDragon.dragon.character }}</span>
          </div>
        </div>
        <div v-else class="no-data">
          {{ DeepestCaveDragon.errorMessage }}
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.dragon-info-page {
  min-height: 100vh;
  padding: 20px;
  background-color: var(--dm-bg-primary);
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.container {
  max-width: 1000px;
  margin: 0 auto;
}

.page-title {
  text-align: center;
  font-size: 2.5rem;
  margin-bottom: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 15px;

  .title-icon {
    font-size: 3rem;
    filter: drop-shadow(0 0 15px var(--dm-accent-purple-glow));
    animation: float 3s ease-in-out infinite;
  }

  .title-text {
    background: var(--dm-gradient-purple);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
    font-weight: 700;
  }
}

@keyframes float {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-10px); }
}

.info-card {
  background: var(--dm-bg-secondary);
  border: 1px solid var(--dm-border-default);
  border-radius: 16px;
  padding: 30px;
  margin-bottom: 25px;
  box-shadow: var(--dm-shadow-lg);
  transition: all var(--dm-transition-normal);

  &:hover {
    transform: translateY(-4px);
    box-shadow: var(--dm-shadow-xl), var(--dm-glow-purple);
  }

  &.stats-card {
    border-left: 4px solid var(--dm-accent-purple);
  }

  &.dragon-card {
    border-left: 4px solid var(--dm-accent-cyan);
  }
}

.card-title {
  color: var(--dm-text-primary);
  margin-bottom: 25px;
  font-size: 1.5rem;
  display: flex;
  align-items: center;
  gap: 10px;

  .card-icon {
    font-size: 1.8rem;
    filter: drop-shadow(0 0 10px var(--dm-accent-purple-glow));
  }
}

.stat-item,
.detail-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 0;
  border-bottom: 1px solid var(--dm-border-default);

  &:last-child {
    border-bottom: none;
  }

  .label {
    font-weight: 600;
    color: var(--dm-text-secondary);
    font-size: 1.1rem;
  }

  .value {
    font-size: 1.3rem;
    color: var(--dm-accent-purple);
    font-weight: bold;
    text-shadow: 0 0 10px var(--dm-accent-purple-glow);

    &.error {
      color: var(--dm-accent-danger);
      text-shadow: 0 0 10px var(--dm-accent-danger-glow);
    }
  }
}

.dragon-details {
  background: var(--dm-bg-tertiary);
  padding: 20px;
  border-radius: 12px;
  border: 1px solid var(--dm-border-default);
}

.no-data {
  text-align: center;
  color: var(--dm-text-tertiary);
  padding: 30px;
  font-style: italic;
  font-size: 1.1rem;
}

/* Element Plus tag override */
:deep(.el-tag) {
  background-color: var(--dm-accent-purple-dim);
  color: var(--dm-accent-purple);
  border-color: var(--dm-accent-purple);
}

/* Responsive */
@media (max-width: 768px) {
  .page-title {
    font-size: 2rem;
    flex-direction: column;

    .title-icon {
      font-size: 2.5rem;
    }
  }

  .info-card {
    padding: 20px;
  }

  .stat-item,
  .detail-item {
    flex-direction: column;
    align-items: flex-start;
    gap: 8px;
  }
}
</style>
