import { DateTime } from 'luxon';

import User from '../../models/User';
import { Status } from '../../models/Status';

export interface AdminRequestDto {
    id: number;
    user: User;
    status: Status;
    approvedBy?: User;
    approvalDate?: Date;
    createdAt: DateTime;
}