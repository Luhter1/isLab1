<script setup lang="ts">
import { ref, reactive, watch, onMounted, computed } from 'vue'
import { ElMessage, FormInstance, FormRules } from 'element-plus'
import { ValidationFactory } from '@/utils/validationFactory'
import { PersonCreateDto } from '@/interfaces/dto/people/PersonCreateDto'
import PersonService from '@/services/PeopleService'
import { Color } from '@/interfaces/models/Colors'; 
import { DateTime } from 'luxon';

interface TeamData {
  teamName: string
  memberCount: number
}

interface CreatedTeam {
  teamName: string
  memberCount: number
  members: PersonCreateDto[]
}

// Реактивные переменные
const activeTab = ref<string>('0')
const teamFormRef = ref<FormInstance>()
const memberFormRefs = ref<Map<number, FormInstance>>(new Map())

const teamData = reactive<TeamData>({
  teamName: '',
  memberCount: 3
})

const teamMembers = ref<PersonCreateDto[]>([])
const createdTeam = ref<CreatedTeam | null>(null)

// Константы
const COLORS: Color[] = [Color.RED, Color.BLUE, Color.YELLOW, Color.ORANGE]

// Правила валидации
const teamRules = reactive<FormRules<TeamData>>({
  teamName: [
    { required: true, message: 'Введите название команды', trigger: 'blur' },
    { min: 3, message: 'Название должно содержать минимум 3 символа', trigger: 'blur' }
  ],
  memberCount: [
    { required: true, message: 'Укажите количество членов', trigger: 'change' }
  ]
})

// Функции для работы со случайными значениями
const getRandomItem = <T>(array: T[]): T => {
  return array[Math.floor(Math.random() * array.length)]
}

const getRandomNumber = (min: number, max: number): number => {
  return Math.floor(Math.random() * (max - min + 1)) + min
}

const generateRandomString = (length: number): string => {
  const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789'
  let result = ''
  for (let i = 0; i < length; i++) {
    result += chars.charAt(Math.floor(Math.random() * chars.length))
  }
  return result
}

// Функции для генерации данных членов команды
const generateMemberName = (index: number): string => {
  const teamName = teamData.teamName || 'Team'
  return index === 0 ? `${teamName}_Leader` : `${teamName}_Member_${index}`
}

const generatePassportId = (name: string): string => {
  return `${name}_${generateRandomString(8)}`
}

const createMember = (index: number): PersonCreateDto => {
  const memberName = generateMemberName(index)
  
  return {
    name: memberName,
    eyeColor: getRandomItem(COLORS),
    hairColor: getRandomItem(COLORS),
    locationId: null,
    birthday: DateTime.now().toUTC(),
    height: getRandomNumber(160, 200),
    weight: getRandomNumber(60, 100),
    passportId: generatePassportId(memberName)
  }
}

// Инициализация членов команды
const initializeTeamMembers = (): void => {
  teamMembers.value = []
  for (let i = 0; i < teamData.memberCount; i++) {
    teamMembers.value.push(createMember(i))
  }
}

// Обработчики событий
const handleMemberCountChange = (value: number): void => {
  if (value > teamMembers.value.length) {
    // Добавляем новых членов
    for (let i = teamMembers.value.length; i < value; i++) {
      teamMembers.value.push(createMember(i))
    }
  } else {
    // Удаляем лишних членов
    teamMembers.value = teamMembers.value.slice(0, value)
  }
  activeTab.value = '0'
}

// Обновляем имена всем членам команды
const updateMemberNames = (): void => {
  teamMembers.value.forEach((member, index) => {
    const newName = generateMemberName(index)
    member.name = newName
    member.passportId = generatePassportId(newName)
  })
}

const getMemberTabLabel = (index: number): string => {
  return index === 0 ? '👑 Лидер' : `⚔️ Боец ${index}`
}

// Управление рефами форм членов команды
const setMemberFormRef = (el: FormInstance | null, index: number): void => {
  if (el) {
    memberFormRefs.value.set(index, el)
  }
}

// Создание команды
const createTeam = async (): Promise<void> => {
  try {
    // Валидация формы команды
    if (!teamFormRef.value) return
    await teamFormRef.value.validate()
    
    // Валидация форм членов команды
    for (let i = 0; i < teamMembers.value.length; i++) {
      const formRef = memberFormRefs.value.get(i)
      if (formRef) {
        await formRef.validate()
        PersonService.create(teamMembers.value[i])
      }
    }
    
  } catch (error) {
    ElMessage.error('Пожалуйста, заполните все обязательные поля')
  }
}

// Сброс формы
const resetForm = (): void => {
  teamData.teamName = ''
  teamData.memberCount = 3
  initializeTeamMembers()
  createdTeam.value = null
  activeTab.value = '0'
  teamFormRef.value?.resetFields()
  memberFormRefs.value.clear()
}

// Watchers
watch(() => teamData.teamName, (newName) => {
  updateMemberNames()
})

// Lifecycle
onMounted(() => {
  initializeTeamMembers()
})
</script>

<template>
  <div class="dragon-slayers-page">
    <div class="container">
      <h1>⚔️ Создание команды убийц драконов</h1>
      
      <el-card class="main-card">
        <!-- Основная информация о команде -->
        <div class="team-info-section">
          <h2>Информация о команде</h2>
          <el-form 
            ref="teamFormRef" 
            :model="teamData" 
            :rules="teamRules"
            label-width="150px"
          >
            <el-form-item label="Название команды" prop="teamName">
              <el-input 
                v-model="teamData.teamName" 
                placeholder="Введите название команды"
                clearable
              />
            </el-form-item>
            
            <el-form-item label="Количество членов" prop="memberCount">
              <el-input-number 
                v-model="teamData.memberCount"
                :min="1"
                :max="8"
                @change="handleMemberCountChange"
              />
            </el-form-item>
          </el-form>
        </div>

        <!-- Члены команды -->
        <div class="members-section" v-if="teamData.memberCount > 0">
          <h2>Члены команды</h2>
          
          <el-tabs v-model="activeTab" type="card">
            <el-tab-pane 
              v-for="(member, index) in teamMembers" 
              :key="index"
              :label="getMemberTabLabel(index)"
              :name="String(index)"
            >
              <el-form 
                :ref="(el: any) => setMemberFormRef(el, index)"
                :model="member"
                :rules="ValidationFactory.createRules('Person')"
                label-width="120px"
                class="member-form"
              >
                <el-row :gutter="20">
                  <el-col :span="12">
                    <el-form-item label="Name" prop="name">
                      <el-input 
                        v-model="member.name" 
                        placeholder="Name" 
                        :disabled="true"
                      />
                    </el-form-item>
                  </el-col>
                  
                  <el-col :span="12">
                    <el-form-item label="Role">
                      <el-tag :type="index === 0 ? 'danger' : 'info'">
                        {{ index === 0 ? '👑 Лидер' : '⚔️ Боец #' + index }}
                      </el-tag>
                    </el-form-item>
                  </el-col>
                </el-row>

                <el-row :gutter="20">
                  <el-col :span="12">
                    <el-form-item label="Eye color" prop="eyeColor">
                      <el-select 
                        v-model="member.eyeColor" 
                        placeholder="Eye color"
                      >
                        <el-option label="RED" value="RED" />
                        <el-option label="BLUE" value="BLUE" />
                        <el-option label="YELLOW" value="YELLOW" />
                        <el-option label="ORANGE" value="ORANGE" />
                      </el-select>
                    </el-form-item>
                  </el-col>
                  
                  <el-col :span="12">
                    <el-form-item label="Hair color" prop="hairColor">
                      <el-select 
                        v-model="member.hairColor" 
                        placeholder="Hair color"
                      >
                        <el-option label="RED" value="RED" />
                        <el-option label="BLUE" value="BLUE" />
                        <el-option label="YELLOW" value="YELLOW" />
                        <el-option label="ORANGE" value="ORANGE" />
                      </el-select>
                    </el-form-item>
                  </el-col>
                </el-row>

                <el-row :gutter="20">
                  <el-col :span="12">
                    <el-form-item label="Height" prop="height">
                      <el-input 
                        v-model.number="member.height" 
                        type="number" 
                        placeholder="Height (cm)"
                      >
                        <template #append>см</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  
                  <el-col :span="12">
                    <el-form-item label="Weight" prop="weight">
                      <el-input 
                        v-model.number="member.weight" 
                        type="number" 
                        placeholder="Weight (kg)"
                      >
                        <template #append>кг</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                </el-row>

                <el-row :gutter="20">
                  <el-col :span="12">
                    <el-form-item label="Birthday" prop="birthday">
                        <el-date-picker
                        v-model="member.birthday"
                        type="datetime"
                        placeholder="Birthday"
                        />
                    </el-form-item>
                  </el-col>
                  
                  <el-col :span="12">
                    <el-form-item label="Passport ID" prop="passportId">
                      <el-input 
                        v-model="member.passportId" 
                        placeholder="Passport ID" 
                        :disabled="true"
                      />
                    </el-form-item>
                  </el-col>
                </el-row>

                <el-form-item label="Location ID" prop="locationId">
                  <el-input 
                    v-model="member.locationId" 
                    type="number" 
                    placeholder="Location ID (optional)"
                  />
                </el-form-item>
              </el-form>
            </el-tab-pane>
          </el-tabs>
        </div>

        <!-- Кнопки действий -->
        <div class="actions-section">
          <el-button @click="resetForm">
            Сбросить
          </el-button>
          <el-button type="primary" @click="createTeam">
            Создать команду
          </el-button>
        </div>
      </el-card>

      <!-- Предпросмотр созданной команды -->
      <el-card v-if="createdTeam" class="preview-card">
        <h2>✅ Команда создана</h2>
        <div class="team-preview">
          <h3>{{ createdTeam.teamName }}</h3>
          <el-table :data="createdTeam.members" stripe>
            <el-table-column prop="name" label="Имя" width="200" />
            <el-table-column label="Роль" width="120">
              <template #default="scope">
                <el-tag :type="scope.$index === 0 ? 'danger' : 'info'">
                  {{ scope.$index === 0 ? 'Лидер' : 'Боец' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="eyeColor" label="Цвет глаз" />
            <el-table-column prop="hairColor" label="Цвет волос" />
            <el-table-column prop="height" label="Рост">
              <template #default="scope">
                {{ scope.row.height }} см
              </template>
            </el-table-column>
            <el-table-column prop="weight" label="Вес">
              <template #default="scope">
                {{ scope.row.weight }} кг
              </template>
            </el-table-column>
            <el-table-column prop="passportId" label="Паспорт" width="200" />
          </el-table>
        </div>
      </el-card>
    </div>
  </div>
</template>

<style scoped lang="scss">
.dragon-slayers-page {
  min-height: 100vh;
  padding: 20px;
  background-color: var(--dm-bg-primary);
}

.container {
  max-width: 1200px;
  margin: 0 auto;
}

h1 {
  text-align: center;
  font-size: 2.5rem;
  margin-bottom: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 15px;

  &::before {
    content: '⚔️';
    font-size: 3rem;
    filter: drop-shadow(0 0 15px var(--dm-accent-cyan-glow));
    animation: swing 2s ease-in-out infinite;
  }

  &::after {
    content: '🐉';
    font-size: 3rem;
    filter: drop-shadow(0 0 15px var(--dm-accent-danger-glow));
    animation: float 3s ease-in-out infinite;
  }
}

@keyframes swing {
  0%, 100% { transform: rotate(-10deg); }
  50% { transform: rotate(10deg); }
}

@keyframes float {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-10px); }
}

.main-card {
  margin-bottom: 20px;

  :deep(.el-card__body) {
    background-color: var(--dm-bg-secondary);
  }
}

.team-info-section,
.members-section {
  margin-bottom: 30px;
}

.team-info-section h2,
.members-section h2 {
  color: var(--dm-accent-cyan);
  margin-bottom: 20px;
  border-bottom: 2px solid var(--dm-border-default);
  padding-bottom: 10px;
  display: flex;
  align-items: center;
  gap: 10px;
  text-shadow: 0 0 10px var(--dm-accent-cyan-glow);
}

.member-form {
  padding: 25px;
  background: var(--dm-bg-tertiary);
  border-radius: 12px;
  border: 1px solid var(--dm-border-default);

  :deep(.el-form-item__label) {
    color: var(--dm-text-secondary);
    font-weight: 600;
  }

  :deep(.el-input__wrapper) {
    background-color: var(--dm-bg-elevated);
    border-color: var(--dm-border-default);
    color: var(--dm-text-primary);

    &:hover {
      border-color: var(--dm-accent-cyan);
    }

    &.is-focus {
      border-color: var(--dm-accent-cyan);
      box-shadow: var(--dm-glow-cyan);
    }
  }

  :deep(.el-select .el-select__wrapper) {
    background-color: var(--dm-bg-elevated);
    border-color: var(--dm-border-default);
    color: var(--dm-text-primary);

    &:hover {
      border-color: var(--dm-accent-cyan);
    }

    &.is-focus {
      border-color: var(--dm-accent-cyan);
      box-shadow: var(--dm-glow-cyan);
    }
  }

  :deep(.el-input-number) {
    width: 200px;

    .el-input__wrapper {
      background-color: var(--dm-bg-elevated);
      border-color: var(--dm-border-default);
    }
  }

  :deep(.el-date-picker) {
    .el-input__wrapper {
      background-color: var(--dm-bg-elevated);
      border-color: var(--dm-border-default);
    }
  }
}

.actions-section {
  text-align: center;
  padding-top: 20px;
  border-top: 1px solid var(--dm-border-default);
}

.actions-section .el-button {
  margin: 0 10px;
  min-width: 150px;
  transition: all var(--dm-transition-normal);

  &.el-button--primary {
    background: var(--dm-gradient-cyan);
    border-color: var(--dm-accent-cyan);
    color: white;

    &:hover {
      transform: translateY(-2px);
      box-shadow: var(--dm-glow-cyan);
    }
  }
}

.preview-card {
  background: var(--dm-bg-secondary);
  border-color: var(--dm-border-default);

  :deep(.el-card__body) {
    background-color: var(--dm-bg-secondary);
  }

  h2 {
    color: var(--dm-accent-success);
    margin-bottom: 20px;
    text-shadow: 0 0 10px var(--dm-accent-success-glow);
    display: flex;
    align-items: center;
    gap: 10px;

    &::before {
      content: '✅';
    }
  }
}

.team-preview h3 {
  color: var(--dm-accent-cyan);
  margin-bottom: 15px;
  font-size: 1.5rem;
  text-shadow: 0 0 10px var(--dm-accent-cyan-glow);
}

/* Custom table for preview */
:deep(.el-table) {
  background-color: var(--dm-bg-tertiary);
  color: var(--dm-text-primary);

  th.el-table__cell {
    background-color: var(--dm-bg-elevated);
    color: var(--dm-text-primary);
    border-color: var(--dm-border-default);
  }

  td.el-table__cell {
    border-color: var(--dm-border-default);
  }

  .el-table__body tr {
    background-color: var(--dm-bg-tertiary);

    & > td {
      background-color: var(--dm-bg-tertiary);
    }

    &:hover > td {
      background-color: var(--dm-bg-elevated);
    }
  }
}

/* Tabs customization */
:deep(.el-tabs) {
  .el-tabs__item {
    color: var(--dm-text-secondary);
    transition: all var(--dm-transition-normal);

    &:hover {
      color: var(--dm-accent-cyan);
    }

    &.is-active {
      color: var(--dm-accent-cyan);
      text-shadow: 0 0 10px var(--dm-accent-cyan-glow);
    }
  }

  .el-tabs__active-bar {
    background: var(--dm-gradient-cyan);
    box-shadow: var(--dm-glow-cyan);
  }

  .el-tabs__nav-wrap::after {
    background-color: var(--dm-border-default);
  }
}

/* Tag customization */
:deep(.el-tag) {
  &.el-tag--danger {
    background-color: var(--dm-accent-danger-dim);
    color: var(--dm-accent-danger);
    border-color: var(--dm-accent-danger);
  }

  &.el-tag--info {
    background-color: var(--dm-bg-elevated);
    color: var(--dm-text-secondary);
    border-color: var(--dm-border-default);
  }
}

/* Input number styling */
:deep(.el-input-number) {
  .el-input-number__decrease,
  .el-input-number__increase {
    background-color: var(--dm-bg-tertiary);
    color: var(--dm-text-secondary);
    border-color: var(--dm-border-default);

    &:hover {
      color: var(--dm-accent-cyan);
    }
  }
}

/* Responsive */
@media (max-width: 768px) {
  h1 {
    font-size: 1.8rem;

    &::before,
    &::after {
      font-size: 2rem;
    }
  }

  .el-col {
    margin-bottom: 10px;
  }

  .member-form {
    padding: 15px;
  }
}
</style>