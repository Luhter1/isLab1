import { CrudDto } from '../CrudDto';

export interface LocationDto extends CrudDto {
    x: number;
    y?: number;
    z: number;
    name: String;
}