import Paged from '@/interfaces/models/Paged';
import CrudController from '@/interfaces/crud/CrudController'
import { ElMessage } from 'element-plus'

abstract class CrudService<TDto, TCreateDto, TUpdateDto> {
    protected readonly name: string;
    protected readonly controller: CrudController<TDto, TCreateDto, TUpdateDto>;

    constructor(
        name: string,
        controller: CrudController<TDto, TCreateDto, TUpdateDto>
    ) {
        this.name = name;
        this.controller = controller;
    }

    protected getErrorMessage = (error: any): string => {
        return error?.response?.data?.message || 'ERROR';
    }

    getName = () => {
        return this.name
    }

    getAll = async (page: number, size: number, sort: string[]): Promise<Paged<TDto>> => {
        try {
            const response = await this.controller.getAll(page, size, sort);
            return response.data as Paged<TDto>;
        } catch (error) {
            ElMessage({
                message: this.getErrorMessage(error),
                showClose: true,
                grouping: true,
                type: 'error',
            });
            throw error;
        }
    }

    getById = async (id: number): Promise<TDto> => {
        try {
            const response = await this.controller.get(id);
            return response.data as TDto;
        } catch (error) {
            ElMessage({
                message: this.getErrorMessage(error),
                showClose: true,
                grouping: true,
                type: 'error',
            });
            throw error;
        }
    }

    create = async (data: TCreateDto): Promise<TDto> => {
        try {
            const response = await this.controller.create(data);
            ElMessage({
                message: `${this.name} created successfully!`,
                showClose: true,
                grouping: true,
                type: 'success',
            });
            return response.data as TDto;
        } catch (error) {
            ElMessage({
                message: this.getErrorMessage(error),
                showClose: true,
                grouping: true,
                type: 'error',
            });
            throw error;
        }
    }

    update = async (id: number, data: TUpdateDto): Promise<TDto> => {
        try {
            const response = await this.controller.update(id, data);
            ElMessage({
                message: `${this.name} updated successfully!`,
                showClose: true,
                grouping: true,
                type: 'success',
            });
            return response.data as TDto;
        } catch (error) {
            ElMessage({
                message: this.getErrorMessage(error),
                showClose: true,
                grouping: true,
                type: 'error',
            });
            throw error;
        }
    }

    delete = async (id: number): Promise<void> => {
        try {
            await this.controller.delete(id);
            ElMessage({
                message: `${this.name} deleted successfully!`,
                showClose: true,
                grouping: true,
                type: 'success',
            });
        } catch (error) {
            ElMessage({
                message: this.getErrorMessage(error),
                showClose: true,
                grouping: true,
                type: 'error',
            });
            throw error;
        }
    }

    abstract getTable(): any;
}

export default CrudService;