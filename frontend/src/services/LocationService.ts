import { LocationCreateDto } from '@/interfaces/dto/locations/LocationCreateDto'
import { LocationDto } from '@/interfaces/dto/locations/LocationDto'
import Paged from '@/interfaces/models/Paged';
import { LocationUpdateDto } from '@/interfaces/dto/locations/LocationUpdateDto'
import LocationController from '@/controllers/LocationController'
import { ElMessage } from 'element-plus'

const getErrorMessage = error => error?.response?.data?.message || 'ERROR';

export const getAllLocations = (page: number, size: number, sort: string[]) => {
  return LocationController.getAll(page, size, sort).then(
    response => {
      const { data } = response;
      return Promise.resolve(data as Paged<LocationDto>);
    },
    error => {
      ElMessage.error(getErrorMessage(error));
      return Promise.reject(error);
    }
  );
};

export const getLocation = (id: number) => {
  return LocationController.get(id).then(
    response => {
      const { data } = response;
      return Promise.resolve(data as LocationDto);
    },
    error => {
      ElMessage.error(getErrorMessage(error));
      return Promise.reject(error);
    }
  );
};

export const createLocation = (location: LocationCreateDto) => {
  return LocationController.create(location).then(
    response => {
      const { data } = response;
      ElMessage.success('Location created successfully!');
      return Promise.resolve(data as LocationDto);
    },
    error => {
      ElMessage.error(getErrorMessage(error));
      return Promise.reject(error);
    }
  );
};

export const updateLocation = (id: number, location: LocationUpdateDto) => {
  return LocationController.update(id, location).then(
    response => {
      const { data } = response;
      ElMessage.success('Location updated successfully!');
      return Promise.resolve(data as LocationDto);
    },
    error => {
      ElMessage.error(getErrorMessage(error));
      return Promise.reject(error);
    }
  );
};

export const deleteLocation = (id: number) => {
  return LocationController.delete(id).then(
    response => {
      ElMessage.success('Location deleted successfully!');
      return Promise.resolve();
    },
    error => {
      ElMessage.error(getErrorMessage(error));
      return Promise.reject(error);
    }
  );
};