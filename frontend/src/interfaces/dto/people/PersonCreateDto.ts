import { Color } from '../../models/Colors'; 
import { DateTime } from 'luxon';

export interface PersonCreateDto {
    name: string;
    eyeColor?: Color;
    hairColor: Color;
    locationId?: number;
    birthday?: DateTime;
    height: number;
    weight?: number;
    passportId: string;
}