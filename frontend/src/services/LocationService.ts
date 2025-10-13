import { LocationCreateDto } from '@/interfaces/dto/locations/LocationCreateDto'
import { LocationDto } from '@/interfaces/dto/locations/LocationDto'
import { CrudService } from '@/interfaces/crud/CrudService';
import { LocationUpdateDto } from '@/interfaces/dto/locations/LocationUpdateDto'
import LocationController from '@/controllers/LocationController'

const Location = CrudService<LocationDto, LocationCreateDto, LocationUpdateDto>(
  'location',
  LocationController
);

export const {
  getAll: getAllLocation,
  getById: getLocation,
  create: createLocation,
  update: updateLocation,
  Delete: deleteLocation,
} = Location