import { DragonType } from '../../models/DragonType';
import { Color } from '../../models/Colors';
import { DragonCharacter } from '../../models/DragonCharacter';
import { JsonNullable } from '../../models/JsonNullable';

export interface DragonUpdateDto  {
    name: JsonNullable<string>;
    coordinatesId: JsonNullable<number>;
    caveId?: JsonNullable<number>;
    killerId?: JsonNullable<number>;
    age?: JsonNullable<number>;
    color?: JsonNullable<Color>;
    type?: JsonNullable<DragonType>;
    character: JsonNullable<DragonCharacter>;
    headId?: JsonNullable<number>;
}