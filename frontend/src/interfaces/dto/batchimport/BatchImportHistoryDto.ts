import User from '../../models/User';
import { DateTime } from 'luxon';

export interface BatchImportHistoryDto {
    id: number;
    createdBy: User;
    createdAt: DateTime;
    status: 'SUCCESS' | 'FAILED';
    successfulOperations: number;
    filePath?: string;
}

