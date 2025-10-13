import { Color } from '../../models/Colors'; 
import { JsonNullable } from '../../models/JsonNullable';
import { DateTime } from 'luxon';


export interface PersonUpdateDto {
    name: JsonNullable<string>;
    eyeColor?: JsonNullable<Color>;
    hairColor: JsonNullable<Color>;
    locationId?: JsonNullable<number>;
    birthday?: JsonNullable<DateTime>;
    height: JsonNullable<number>;
    weight?: JsonNullable<number>;
    passportId: JsonNullable<string>;
}