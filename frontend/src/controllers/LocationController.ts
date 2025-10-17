import { LocationDto } from '@/interfaces/dto/locations/LocationDto';
import { LocationCreateDto } from '@/interfaces/dto/locations/LocationCreateDto';
import { LocationUpdateDto } from '@/interfaces/dto/locations/LocationUpdateDto';
import CrudController from '@/interfaces/crud/CrudController';


export default new CrudController<LocationDto, LocationCreateDto, LocationUpdateDto>("locations")
