import api from '@/controllers/api';
import { AxiosResponse } from 'axios';
import { DragonDto } from '@/interfaces/dto/dragons/DragonDto';
import { DragonCreateDto } from '@/interfaces/dto/dragons/DragonCreateDto';
import { DragonUpdateDto } from '@/interfaces/dto/dragons/DragonUpdateDto';
import CrudController, { staticImplements } from '@/interfaces/crud/CrudController';
import Paged from '@/interfaces/models/Paged';
import { createCrudUri } from './utils/uri';

@staticImplements<CrudController<DragonDto, DragonCreateDto, DragonUpdateDto>>()
export default class DragonService {
  static async getAll(page: number, size: number, sort: string[]): Promise<AxiosResponse<Paged<DragonDto>>> {
    return api.get<Paged<DragonDto>>(`/dragons${createCrudUri(page, size, sort)}`);
  }

  static async get(id: number): Promise<AxiosResponse<DragonDto>> {
    return api.get<DragonDto>(`/dragons/${id}`);
  }

  static async create(dragon: DragonCreateDto): Promise<AxiosResponse<DragonDto>> {
    return api.post<DragonDto>('/dragons', dragon);
  }

  static async update(id: number, dragon: DragonUpdateDto): Promise<AxiosResponse<DragonDto>> {
    return api.patch<DragonDto>(`/dragons/${id}`, dragon);
  }

  static async delete(id: number): Promise<AxiosResponse> {
    return api.delete(`/dragons/${id}`);
  }
}