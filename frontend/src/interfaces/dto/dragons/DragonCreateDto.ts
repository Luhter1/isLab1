import { DragonType } from '../../models/DragonType';
import { Color } from '../../models/Colors';
import { DragonCharacter } from '../../models/DragonCharacter';

export interface DragonCreateDto {
    name: string;
    coordinatesId: number;
    caveId?: number;
    killerId?: number;
    age?: number;
    color?: Color;
    type?: DragonType;
    character: DragonCharacter;
    headId?: number;
}