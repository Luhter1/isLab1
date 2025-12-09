<template>
  <div class="batch-import-page">
    <el-card>
      <template #header>
        <div class="card-header">
          <h2>
            <el-icon><Upload /></el-icon>
            Batch Import
          </h2>
        </div>
      </template>

      <div class="import-content">
        <el-alert
          v-if="errorMessage"
          :title="errorMessage"
          type="error"
          :closable="true"
          @close="errorMessage = ''"
          style="margin-bottom: 20px"
        />

        <el-alert
          v-if="successMessage"
          :title="successMessage"
          type="success"
          :closable="true"
          @close="successMessage = ''"
          style="margin-bottom: 20px"
        />

        <el-upload
          ref="uploadRef"
          :auto-upload="false"
          :on-change="handleFileChange"
          :on-remove="handleFileRemove"
          :limit="1"
          accept=".json"
          drag
        >
          <el-icon class="el-icon--upload"><upload-filled /></el-icon>
          <div class="el-upload__text">
            Drop JSON file here or <em>click to upload</em>
          </div>
          <template #tip>
            <div class="el-upload__tip">
              JSON file with batch operations (max 1024 operations)
            </div>
          </template>
        </el-upload>

        <div v-if="selectedFile" class="file-info">
          <el-descriptions :column="2" border>
            <el-descriptions-item label="File Name">
              {{ selectedFile.name }}
            </el-descriptions-item>
            <el-descriptions-item label="File Size">
              {{ formatFileSize(selectedFile.size) }}
            </el-descriptions-item>
          </el-descriptions>
        </div>

        <div class="actions">
          <el-button
            type="primary"
            :disabled="!selectedFile || loading"
            :loading="loading"
            @click="handleImport"
            size="large"
          >
            <el-icon><Upload /></el-icon>
            Import File
          </el-button>
          <el-button
            :disabled="!selectedFile || loading"
            @click="handleReset"
            size="large"
          >
            <el-icon><RefreshLeft /></el-icon>
            Reset
          </el-button>
        </div>

        <div v-if="importResult" class="import-result">
          <el-divider content-position="left">
            <el-icon><Document /></el-icon>
            Import Results
          </el-divider>

          <el-descriptions :column="3" border>
            <el-descriptions-item label="Total Operations">
              <el-tag>{{ importResult.totalOperations }}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="Successful">
              <el-tag type="success">{{ importResult.successfulOperations }}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="Failed">
              <el-tag type="danger">{{ importResult.failedOperations }}</el-tag>
            </el-descriptions-item>
          </el-descriptions>

          <div v-if="importResult.results && importResult.results.length > 0" class="results-list">
            <el-table :data="importResult.results" style="width: 100%" max-height="400">
              <el-table-column prop="index" label="Index" width="80" />
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
        </div>

        <el-divider>
          <el-icon><InfoFilled /></el-icon>
          Instructions
        </el-divider>

        <el-card shadow="never" class="instructions-card">
          <h3>File Format</h3>
          <p>The JSON file should contain an array of operations. Each operation must have:</p>
          <ul>
            <li><strong>type</strong>: "CREATE", "UPDATE", or "DELETE"</li>
            <li><strong>resourceType</strong>: "coordinates", "dragon-caves", "dragon-heads", "dragons", "locations", or "people"</li>
            <li><strong>resourceId</strong>: (required for UPDATE/DELETE) - ID of the resource</li>
            <li><strong>body</strong>: (required for CREATE/UPDATE) - Object data</li>
          </ul>

          <h3>Nested Objects</h3>
          <p>When creating objects with nested objects (e.g., Dragon with coordinates, head, cave, killer), 
          you can provide the nested object directly in the body. The system will create nested objects 
          in the same transaction.</p>

          <h3>Example</h3>
          <pre><code>[
  {
    "type": "CREATE",
    "resourceType": "coordinates",
    "body": {
      "x": 100,
      "y": 200.5
    }
  },
  {
    "type": "CREATE",
    "resourceType": "dragons",
    "body": {
      "name": "Dragon Name",
      "type": "FIRE",
      "character": "EVIL",
      "coordinates": {
        "x": 150,
        "y": 250.5
      },
      "head": {
        "size": 10.5
      }
    }
  }
]</code></pre>
        </el-card>
      </div>
    </el-card>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import { Upload, UploadFilled, RefreshLeft, Document, InfoFilled } from '@element-plus/icons-vue'
import api from '@/controllers/api'
import { API_BASE_URL } from '@/config/constants'
import type { UploadFile, UploadFiles, UploadInstance } from 'element-plus'

interface ImportResult {
  totalOperations: number
  successfulOperations: number
  failedOperations: number
  results: Array<{
    index: number
    success: boolean
    message: string
  }>
}

const uploadRef = ref<UploadInstance>()
const selectedFile = ref<File | null>(null)
const loading = ref(false)
const errorMessage = ref('')
const successMessage = ref('')
const importResult = ref<ImportResult | null>(null)

const handleFileChange = (file: UploadFile, fileList: UploadFiles) => {
  if (file.raw) {
    selectedFile.value = file.raw
    errorMessage.value = ''
    successMessage.value = ''
    importResult.value = null
  }
}

const handleFileRemove = () => {
  selectedFile.value = null
  errorMessage.value = ''
  successMessage.value = ''
  importResult.value = null
}

const handleReset = () => {
  uploadRef.value?.clearFiles()
  selectedFile.value = null
  errorMessage.value = ''
  successMessage.value = ''
  importResult.value = null
}

const formatFileSize = (bytes: number): string => {
  if (bytes === 0) return '0 Bytes'
  const k = 1024
  const sizes = ['Bytes', 'KB', 'MB', 'GB']
  const i = Math.floor(Math.log(bytes) / Math.log(k))
  return Math.round(bytes / Math.pow(k, i) * 100) / 100 + ' ' + sizes[i]
}

const handleImport = async () => {
  if (!selectedFile.value) {
    errorMessage.value = 'Please select a file first'
    return
  }

  loading.value = true
  errorMessage.value = ''
  successMessage.value = ''
  importResult.value = null

  try {
    const formData = new FormData()
    formData.append('file', selectedFile.value)

    const response = await api.post(`${API_BASE_URL}/batch-import`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    })

    importResult.value = response.data
    successMessage.value = `Import completed: ${response.data.successfulOperations} successful, ${response.data.failedOperations} failed`
  } catch (error: any) {
    console.error('Import error:', error)
    if (error.response?.data) {
      if (error.response.data.results && error.response.data.results.length > 0) {
        importResult.value = error.response.data
        errorMessage.value = `Import failed: ${error.response.data.failedOperations} operations failed`
      } else {
        errorMessage.value = error.response.data.message || 'Import failed'
      }
    } else {
      errorMessage.value = error.message || 'Import failed'
    }
  } finally {
    loading.value = false
  }
}
</script>

<style scoped lang="scss">
.batch-import-page {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;

  .card-header {
    display: flex;
    align-items: center;
    gap: 10px;

    h2 {
      margin: 0;
      display: flex;
      align-items: center;
      gap: 10px;
    }
  }

  .import-content {
    .file-info {
      margin: 20px 0;
    }

    .actions {
      margin: 20px 0;
      display: flex;
      gap: 10px;
    }

    .import-result {
      margin-top: 30px;
    }

    .results-list {
      margin-top: 20px;
    }

    .instructions-card {
      margin-top: 20px;
      background-color: #f5f7fa;

      h3 {
        margin-top: 0;
        color: #409eff;
      }

      ul {
        margin: 10px 0;
        padding-left: 20px;
      }

      pre {
        background-color: #2d2d2d;
        color: #f8f8f2;
        padding: 15px;
        border-radius: 4px;
        overflow-x: auto;
        margin-top: 10px;

        code {
          font-family: 'Courier New', monospace;
          font-size: 14px;
        }
      }
    }
  }
}
</style>

