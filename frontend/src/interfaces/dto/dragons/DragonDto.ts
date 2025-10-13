import { CrudDto } from '../CrudDto';
import { Color } from '../../models/Colors';
import { DragonCharacter } from '../../models/DragonCharacter';
import { CoordinateDto } from '../coordinates/CoordinateDto';
import { DragonCaveDto } from '../dragoncaves/DragonCaveDto';
import { DragonHeadDto } from '../dragonheads/DragonHeadDto';
import { DragonType } from '../../models/DragonType';
import { PersonDto } from '../people/PersonDto';

export interface DragonDto extends CrudDto {
    name: string;
    coordinates: CoordinateDto;
    cave?: DragonCaveDto;
    killer?: PersonDto;
    age?: number;
    color?: Color;
    type?: DragonType;
    character: DragonCharacter;
    head?: DragonHeadDto;
}