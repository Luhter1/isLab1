import { CoordinateDto } from '@/interfaces/dto/coordinates/CoordinateDto';
import { CoordinateCreateDto } from '@/interfaces/dto/coordinates/CoordinateCreateDto';
import { CoordinateUpdateDto } from '@/interfaces/dto/coordinates/CoordinateUpdateDto';
import CrudController from '@/interfaces/crud/CrudController';

export default new CrudController<CoordinateDto, CoordinateCreateDto, CoordinateUpdateDto>("coordinates")