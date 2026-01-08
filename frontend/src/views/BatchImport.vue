<script lang="ts" setup>
import { ref, computed } from 'vue'
import { Upload, UploadFilled, RefreshLeft, Clock } from '@element-plus/icons-vue'
import api from '@/controllers/api'
import { API_BASE_URL, MAX_FILE_SIZE } from '@/config/constants'
import type { UploadFile, UploadFiles, UploadInstance } from 'element-plus'

interface OperationResult {
  index: number
  success: boolean
  message: string
}

interface ImportResult {
  successfulOperations: number
  failedOperations: number
  results: OperationResult[]
}

interface ImportError {
  response?: {
    data?: {
      message?: string
      failedOperations?: number
      results?: OperationResult[]
    }
  }
  message?: string
}


const ALLOWED_EXTENSIONS = ['.json']
const MESSAGES = {
  NO_FILE: 'Please select a file first',
  FILE_TOO_LARGE: 'File size exceeds 10 MB limit',
  IMPORT_FAILED: 'Import failed',
  INVALID_JSON: 'Invalid JSON file',
}
const exampleJson = `[
  {
    "type": "UPDATE",
    "resourceType": "coordinates",
    "resourceId": 1,
    "body": { "x": 100, "y": 200.5 }
  },
  {
    "type": "CREATE",
    "resourceType": "dragons",
    "body": {
      "name": "Dragon Name",
      "type": "FIRE",
      "character": "EVIL",
      "coordinates": { "x": 150, "y": 250.5 },
      "head": { "size": 10.5 }
    }
  }
]`


const uploadRef = ref<UploadInstance>()
const selectedFile = ref<File | null>(null)
const loading = ref(false)
const errorMessage = ref('')
const successMessage = ref('')
const importResult = ref<ImportResult | null>(null)


const isFormValid = computed(() => selectedFile.value !== null)
const isFormDisabled = computed(() => !isFormValid.value || loading.value)
const hasResults = computed(() => importResult.value?.results?.length ?? 0 > 0)


const formatFileSize = (bytes: number): string => {
  if (bytes === 0) return '0 Bytes'
  const k = 1024
  const sizes = ['Bytes', 'KB', 'MB', 'GB'] as const
  const i = Math.floor(Math.log(bytes) / Math.log(k))
  return `${(bytes / Math.pow(k, i)).toFixed(2)} ${sizes[i]}`
}

const clearMessages = (): void => {
  errorMessage.value = ''
  successMessage.value = ''
}

const resetState = (): void => {
  uploadRef.value?.clearFiles()
  selectedFile.value = null
  clearMessages()
  importResult.value = null
}

const validateFile = (file: File): string | null => {
  if (file.size > MAX_FILE_SIZE) {
    return MESSAGES.FILE_TOO_LARGE
  }
  return null
}


const handleFileChange = (file: UploadFile, _fileList: UploadFiles): void => {
  if (!file.raw) return
  
  const validationError = validateFile(file.raw)
  if (validationError) {
    errorMessage.value = validationError
    uploadRef.value?.clearFiles()
    return
  }
  
  selectedFile.value = file.raw
  clearMessages()
  importResult.value = null
}

const handleFileRemove = (): void => {
  selectedFile.value = null
  clearMessages()
  importResult.value = null
}

const handleReset = (): void => {
  resetState()
}

const handleCloseAlert = (type: 'error' | 'success'): void => {
  if (type === 'error') {
    errorMessage.value = ''
  } else {
    successMessage.value = ''
  }
}

const handleImport = async (): Promise<void> => {
  if (!selectedFile.value) {
    errorMessage.value = MESSAGES.NO_FILE
    return
  }

  loading.value = true
  clearMessages()
  importResult.value = null

  try {
    const formData = new FormData()
    formData.append('file', selectedFile.value)

    const response = await api.post<ImportResult>(
      `${API_BASE_URL}/batch-import`,
      formData,
      {
        headers: { 'Content-Type': 'multipart/form-data' },
      }
    )

    importResult.value = response.data
    
    const { successfulOperations, failedOperations } = response.data
    successMessage.value = `Import completed: ${successfulOperations} successful, ${failedOperations} failed`
    
  } catch (error) {
    handleImportError(error as ImportError)
  } finally {
    loading.value = false
    // Очищаем только файл, но не результаты
    uploadRef.value?.clearFiles()
    selectedFile.value = null
  }
}

const parseError = (message: string): string => {
  const header = "JSON Schema validation failed:";
  const MAX_LINES = 5; // Порог, после которого включается обрезка
  const regex = /\$\[(\d+)\]\.(.*?):\s*(.*?;)/g;
  const errors = [];
  let match;
  
  while ((match = regex.exec(message)) !== null) {
    errors.push(`  - operation ${match[1]} in ${match[2]}: ${match[3]}`);
  }

  if (errors.length === 0) return message;

  if (1 + errors.length > MAX_LINES) {
    const visibleErrors = errors.slice(0, MAX_LINES); 
    const hiddenCount = errors.length - MAX_LINES;
    
    return `${header}\n${visibleErrors.join('\n')}\n  ... (скрыто еще ${hiddenCount} ошибок)`;
  }

  return `${header}\n${errors.join('\n')}`;
}

const handleImportError = (error: ImportError): void => {
  const responseData = error.response?.data

  if (responseData?.results?.length) {
    importResult.value = responseData as ImportResult
    errorMessage.value = `Import failed: ${responseData.failedOperations} operations failed`
  } else if(responseData?.message){
    if(responseData.message.startsWith("JSON Schema validation failed: ")){
      errorMessage.value = parseError(responseData.message)
    }else{
      errorMessage.value = responseData.message
    }
  } else {
    errorMessage.value = error.message ?? MESSAGES.IMPORT_FAILED
  }
}
</script>

<template>
  <div class="batch-import-page">
    <el-card>
      <template #header>
        <div class="card-header">
          <h2>
            <el-icon><Upload /></el-icon>
            Batch Import
          </h2>
          <router-link 
            to="/batch-import/history" 
            class="history-link"
          >
            <el-button type="primary" link>
              <el-icon><Clock /></el-icon>
              View History
            </el-button>
          </router-link>
        </div>
      </template>

      <div class="import-content">
        <el-alert
          v-if="errorMessage"
          :title="errorMessage"
          type="error"
          closable
          class="alert-message"
          @close="handleCloseAlert('error')"
        />

        <el-alert
          v-if="successMessage"
          :title="successMessage"
          type="success"
          closable
          class="alert-message"
          @close="handleCloseAlert('success')"
        />

        <!-- Загрузка файла -->
        <el-upload
          ref="uploadRef"
          :auto-upload="false"
          :on-change="handleFileChange"
          :on-remove="handleFileRemove"
          :limit="1"
          :accept="ALLOWED_EXTENSIONS.join(',')"
          drag
        >
          <el-icon class="el-icon--upload"><UploadFilled /></el-icon>
          <div class="el-upload__text">
            Drop JSON file here or <em>click to upload</em>
          </div>
          <template #tip>
            <div class="el-upload__tip">
              JSON file with batch operations (max 1024 operations, up to 10 MB)
            </div>
          </template>
        </el-upload>

        <!-- Информация о файле -->
        <Transition name="fade">
          <div v-if="selectedFile" class="file-info">
            <el-descriptions :column="2" border size="small">
              <el-descriptions-item label="File Name">
                {{ selectedFile.name }}
              </el-descriptions-item>
              <el-descriptions-item label="File Size">
                {{ formatFileSize(selectedFile.size) }}
              </el-descriptions-item>
            </el-descriptions>
          </div>
        </Transition>

        <!-- Кнопки действий -->
        <div class="actions">
          <el-button
            type="primary"
            size="large"
            :disabled="isFormDisabled"
            :loading="loading"
            @click="handleImport"
          >
            <el-icon><Upload /></el-icon>
            Import File
          </el-button>
          <el-button
            size="large"
            :disabled="isFormDisabled"
            @click="handleReset"
          >
            <el-icon><RefreshLeft /></el-icon>
            Reset
          </el-button>
        </div>

        <!-- Результаты импорта -->
        <Transition name="fade">
          <div v-if="importResult && hasResults" class="import-result">
            <el-divider>Import Results</el-divider>
            
            <el-table :data="importResult.results" max-height="400">
              <el-table-column prop="index" label="#" width="60" />
              <el-table-column label="Status" width="100">
                <template #default="{ row }">
                  <el-tag :type="row.success ? 'success' : 'danger'">
                    {{ row.success ? 'Success' : 'Failed' }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="message" label="Message" />
            </el-table>
          </div>
        </Transition>

        <el-divider />

        <!-- Инструкции -->
        <el-card shadow="never" class="instructions-card">
          <template #header>
            <h3>JSON Format Example</h3>
          </template>
          <pre><code>{{ exampleJson }}</code></pre>
        </el-card>
      </div>
    </el-card>
  </div>
</template>

<style scoped lang="scss">
.batch-import-page {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.card-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  
  h2 {
    margin: 0;
    display: flex;
    align-items: center;
    gap: 10px;
  }
}

.history-link {
  text-decoration: none;
}

.import-content {
  .alert-message {
    white-space: pre-wrap;
    margin-bottom: 20px;
  }

  .file-info {
    margin: 20px 0;
  }

  .actions {
    margin: 20px 0;
    display: flex;
    gap: 10px;
  }

  .import-result {
    margin-top: 20px;
  }
}

.instructions-card {
  background-color: var(--el-fill-color-light);

  h3 {
    margin: 0;
    color: var(--el-color-primary);
    font-size: 16px;
  }

  pre {
    background-color: #2d2d2d;
    color: #f8f8f2;
    padding: 15px;
    border-radius: 4px;
    overflow-x: auto;
    margin: 0;

    code {
      font-family: 'Fira Code', 'Courier New', monospace;
      font-size: 13px;
      line-height: 1.5;
    }
  }
}
</style>