import { useUserStore } from '@/stores/userStore'
import type { BatchImportHistoryDto } from '@/interfaces/dto/batchimport/BatchImportHistoryDto'
import BatchImportHistoryController from '@/controllers/BatchImportHistoryController'
import type Paged from '@/interfaces/models/Paged'
import { ElMessage } from 'element-plus'
import { reactive } from 'vue'

const getErrorMessage = error => error?.response?.data?.message || 'ERROR';

export const State = reactive({
    objects: {} as Paged<BatchImportHistoryDto>,
    currentPage: 1,
    pageSize: 10
});

export const getHistory = () => {
  return BatchImportHistoryController.fetchHistory(State.currentPage, State.pageSize).then(
    response => {
      State.objects = response.data as Paged<BatchImportHistoryDto>
      return Promise.resolve();
    },
    error => {
      ElMessage({
          message: getErrorMessage(error),
          showClose: true,
          grouping: true,
          type: 'error',
      });
      return Promise.reject();
    }
  );
};