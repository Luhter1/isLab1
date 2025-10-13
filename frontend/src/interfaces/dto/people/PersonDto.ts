import { Color } from '../../models/Colors';
import { LocationDto } from '../locations/LocationDto';
import { CrudDto } from '../CrudDto';
import { DateTime } from 'luxon';


export interface PersonDto extends CrudDto {
    name: string;
    eyeColor?: Color;
    hairColor: Color;
    location?: LocationDto;
    birthday?: DateTime;
    height: number;
    weight?: number;
    passportId: string;
}