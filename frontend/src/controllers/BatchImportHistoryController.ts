import api from './api';

import { AxiosResponse } from 'axios';
import { BatchImportHistoryDto } from '../interfaces/dto/batchimport/BatchImportHistoryDto';
import type Paged from '@/interfaces/models/Paged'

export default class BatchImportHistoryController {
  static async fetchHistory(currentPage, pageSize): Promise<AxiosResponse<Paged<BatchImportHistoryDto>>> {
    return api.get<Paged<BatchImportHistoryDto>>('/batch-import/history', {
      params: {
        page: currentPage - 1,
        size: pageSize,
        sort: 'createdAt,desc'
      }
    });
    
  }
}