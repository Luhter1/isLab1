import { AxiosResponse } from 'axios';
import Paged from '@/interfaces/models/Paged';

export function staticImplements<T>() {
  return <U extends T>(constructor: U) => {constructor;};
}

export default interface CrudController<Dto, CreateDto, UpdateDto> {
  getAll(page: number, size: number, sort: string[]): Promise<AxiosResponse<Paged<Dto>>>;

  get(id: number): Promise<AxiosResponse<Dto>>;

  create(dto: CreateDto): Promise<AxiosResponse<Dto>>;

  update(id: number, dto: UpdateDto): Promise<AxiosResponse<Dto>>;

  delete(id: number): Promise<AxiosResponse>;
}