import api from '@/controllers/api';
import { AxiosResponse } from 'axios';

import { CoordinateDto } from '@/interfaces/dto/coordinates/CoordinateDto';
import { CoordinateCreateDto } from '@/interfaces/dto/coordinates/CoordinateCreateDto';
import { CoordinateUpdateDto } from '@/interfaces/dto/coordinates/CoordinateUpdateDto';
import CrudController, { staticImplements } from '@/interfaces/crud/CrudController';
import Paged from '@/interfaces/models/Paged';
import { createCrudUri } from './utils/uri';

@staticImplements<CrudController<CoordinateDto, CoordinateCreateDto, CoordinateUpdateDto>>()
export default class CoordinatesService {
  static async getAll(page: number, size: number, sort: string[]): Promise<AxiosResponse<Paged<CoordinateDto>>> {
    return api.get<Paged<CoordinateDto>>(`/coordinates${createCrudUri(page, size, sort)}`);
  }

  static async get(id: number): Promise<AxiosResponse<CoordinateDto>> {
    return api.get<CoordinateDto>(`/coordinates/${id}`);
  }

  static async create(Coordinates: CoordinateCreateDto): Promise<AxiosResponse<CoordinateDto>> {
    return api.post<CoordinateDto>('/coordinates', Coordinates);
  }

  static async update(id: number, Coordinates: CoordinateUpdateDto): Promise<AxiosResponse<CoordinateDto>> {
    return api.patch<CoordinateDto>(`/coordinates/${id}`, Coordinates);
  }

  static async delete(id: number): Promise<AxiosResponse> {
    return api.delete(`/coordinates/${id}`);
  }
}