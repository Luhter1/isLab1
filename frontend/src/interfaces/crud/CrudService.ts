import Paged from '@/interfaces/models/Paged';
import CrudController from '@/interfaces/crud/CrudController'
import { ElMessage } from 'element-plus'

const getErrorMessage = error => error?.response?.data?.message || 'ERROR';

export const  CrudService = <TDto, TCreateDto, TUpdateDto>(
    name: string,
    Controller: CrudController<TDto, TCreateDto, TUpdateDto>
) => {
    const  getAll = (page: number, size: number, sort: string[]) => {
        return Controller.getAll(page, size, sort).then(
            response => {
                const { data } = response;
                return Promise.resolve(data as Paged<TDto>);
            },
            error => {
                ElMessage({
                    message: getErrorMessage(error),
                    showClose: true,
                    grouping: true,
                    type: 'error',
                });
                return Promise.reject(error);
            }
        );
    }

    const  getById = (id: number) => {
        return Controller.get(id).then(
            response => {
                const { data } = response;
                return Promise.resolve(data as TDto);
            },
            error => {
                ElMessage({
                    message: getErrorMessage(error),
                    showClose: true,
                    grouping: true,
                    type: 'error',
                });
                return Promise.reject(error);
            }
        );
    }

    const  create = (location: TCreateDto) => {
        return Controller.create(location).then(
            response => {
                const { data } = response;
                ElMessage({
                    message: name + ' created successfully!',
                    showClose: true,
                    grouping: true,
                    type: 'success',
                });
                return Promise.resolve(data as TDto);
            },
            error => {
                ElMessage({
                    message: getErrorMessage(error),
                    showClose: true,
                    grouping: true,
                    type: 'error',
                });
                return Promise.reject(error);
            }
        );
    }

    const  update = (id: number, location: TUpdateDto) => {
        return Controller.update(id, location).then(
            response => {
                const { data } = response;
                ElMessage({
                    message: name + ' updated successfully!',
                    showClose: true,
                    grouping: true,
                    type: 'success',
                });
                return Promise.resolve(data as TDto);
            },
            error => {
                ElMessage({
                    message: getErrorMessage(error),
                    showClose: true,
                    grouping: true,
                    type: 'error',
                });
                return Promise.reject(error);
            }
        );
    }

    const  Delete = (id: number) => {
        return Controller.delete(id).then(
            response => {
                ElMessage({
                    message: name + ' deleted successfully!',
                    showClose: true,
                    grouping: true,
                    type: 'success',
                });
                return Promise.resolve();
            },
            error => {
                ElMessage({
                    message: getErrorMessage(error),
                    showClose: true,
                    grouping: true,
                    type: 'error',
                });
                return Promise.reject(error);
            }
        );
    }

    return {
        getAll,
        getById,
        create,
        update,
        Delete
    }
}