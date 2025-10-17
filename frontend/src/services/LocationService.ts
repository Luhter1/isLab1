import { LocationCreateDto } from '@/interfaces/dto/locations/LocationCreateDto'
import { LocationDto } from '@/interfaces/dto/locations/LocationDto'
import { LocationUpdateDto } from '@/interfaces/dto/locations/LocationUpdateDto'
import LocationController from '@/controllers/LocationController'
import CrudService from '@/interfaces/crud/CrudService';

class LocationService extends CrudService<LocationDto, LocationCreateDto, LocationUpdateDto> {
    constructor() {
        super("Location", LocationController);
    }

    getTable() {

    }
}

export default new LocationService()