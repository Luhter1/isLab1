import User from '../models/User';
import { DateTime } from 'luxon';

export interface CrudDto {
    id: number;
    createdBy?: User;
    createdAt?: DateTime;
    updatedBy?: User;
    updatedAt?: DateTime;
}