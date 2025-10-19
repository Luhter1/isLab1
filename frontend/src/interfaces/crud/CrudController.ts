import { AxiosResponse } from 'axios';
import Paged from '@/interfaces/models/Paged';
import api from '@/controllers/api';
import { createCrudUri, createFilterUri } from '@/utils/uri';

export default class CrudController<TDto, TCreateDto, TUpdateDto> {
  protected readonly path: string;

  constructor(path: string) {
    this.path = path;
  }

  async getAll(page: number, size: number, sort: string[], filter: string[]): Promise<AxiosResponse<Paged<TDto>>> {
    return api.get<Paged<TDto>>(`/${this.path}${createCrudUri(page, size, sort)}${createFilterUri(filter)}`);
  }

  async get(id: number): Promise<AxiosResponse<TDto>> {
    return api.get<TDto>(`/${this.path}/${id}`);
  }

  async create(Coordinates: TCreateDto): Promise<AxiosResponse<TDto>> {
    return api.post<TDto>(`/${this.path}`, Coordinates);
  }

  async update(id: number, Coordinates: TUpdateDto): Promise<AxiosResponse<TDto>> {
    return api.patch<TDto>(`/${this.path}/${id}`, Coordinates);
  }

  async delete(id: number): Promise<AxiosResponse> {
    return api.delete(`/${this.path}/${id}`);
  }
}