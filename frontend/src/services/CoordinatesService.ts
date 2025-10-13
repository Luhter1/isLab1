import { CoordinateCreateDto } from '@/interfaces/dto/coordinates/CoordinateCreateDto'
import { CoordinateDto } from '@/interfaces/dto/coordinates/CoordinateDto'
import { CrudService } from '@/interfaces/crud/CrudService';
import { CoordinateUpdateDto } from '@/interfaces/dto/coordinates/CoordinateUpdateDto'
import CoordinateController from '@/controllers/CoordinatesController'

const Coordinate = CrudService<CoordinateDto, CoordinateCreateDto, CoordinateUpdateDto>(
  'Coordinate',
  CoordinateController
);

export const {
  getAll: getAllCoordinate,
  getById: getCoordinate,
  create: createCoordinate,
  update: updateCoordinate,
  Delete: deleteCoordinate,
} = Coordinate