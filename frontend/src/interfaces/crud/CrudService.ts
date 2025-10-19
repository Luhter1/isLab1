import { reactive } from 'vue';
import Paged from '@/interfaces/models/Paged';
import CrudController from '@/interfaces/crud/CrudController'
import type { Event } from '@/interfaces/events/Event'
import { EventType } from '@/interfaces/models/EventType';
import { ElMessage } from 'element-plus'

abstract class CrudService<TDto extends { id: any }, TCreateDto, TUpdateDto> {
    protected readonly name: string;
    protected readonly controller: CrudController<TDto, TCreateDto, TUpdateDto>;

    state: {
        objects: TDto[];
        totalObjects: number;
        loading: boolean;
        isLast: boolean;
        isFirst: boolean;
        isEmpty: boolean;
        currentPage: number;
        pageSize: number;
        sorts: Map<string, string>;
        filters: Record<string, string>;
    };

    constructor(
        name: string,
        controller: CrudController<TDto, TCreateDto, TUpdateDto>
    ) {
        this.name = name;
        this.controller = controller;

        this.state = reactive({
            objects: [],
            totalObjects: 0,
            loading: false,
            isLast: false,
            isFirst: true,
            isEmpty: true,
            currentPage: 1,
            pageSize: 10,
            sorts: new Map<string, string>(),
            filters: {}
        });
    }

    protected getErrorMessage = (error: any): string => {
        return error?.response?.data?.message || 'ERROR';
    }

    getName = () => {
        return this.name
    }

    getAll = async (page: number, size: number, sort: string[], filter: string[]): Promise<Paged<TDto>> => {
        try {
            const response = await this.controller.getAll(page, size, sort, filter);
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

    addSort(field: string, direction = 'asc') {
        this.state.sorts.set(field, direction);
        return this;
    }

    resetSort() {
        this.state.sorts.clear()
    }

    setFilters(filters: Record<string, string>) {
        this.state.filters = filters
    }

    clearFilters() {
        this.state.filters = {}
    }

    async fetchObjects() {
        try {
            this.state.loading = true

            // Добавляем сортировку
            const prepSort = []
            this.state.sorts.forEach((value, key) => {
                if(value === 'asc' || value === 'desc'){
                    prepSort.push(`${key},${value}`)
                }
            });

            // Добавляем фильтры
            const filterParams: string[] = []
            Object.entries(this.state.filters).forEach(([field, value]) => {
                if (value) {
                filterParams.push(`${field}:${value}`)
                }
            })
            const response = await this.getAll(
                this.state.currentPage - 1,
                this.state.pageSize,
                prepSort,
                filterParams
            )
            this.state.objects = response.content
            this.state.totalObjects = response.totalElements
            this.state.isLast = response.last
            this.state.isFirst = response.first
            this.state.isEmpty = response.empty
        } catch (error) {
            console.error('Error fetching objects:', error)
            this.state.objects = []
            this.state.totalObjects = 0
        } finally {
            this.state.loading = false  // Гарантируем, что loading станет false
        }
    }

    async updatePage(page) {
        this.state.currentPage = page
        await this.fetchObjects()
    }

    async updatePageSize(size) {
        this.state.pageSize = size
        this.state.currentPage = 1
        await this.fetchObjects()
    }

    handleObjectEvent(event: Event<TDto>) {
        switch(event.eventType){
            case EventType.CREATE:
                return this.handleObjectCreated(event.entity)
            case EventType.UPDATE:
                return this.handleObjectUpdated(event.entity)
            case EventType.DELETE:
        }
    }

    handleObjectCreated(object: TDto) {
        this.state.objects.push(object)
        this.state.totalObjects++
    }

    handleObjectUpdated(object: TDto) {
        const index = this.state.objects.findIndex(d => d.id === object.id)
        if (index !== -1) {
            this.state.objects[index] = object
        }
    }

    handleObjectDeleted(id) {
        this.state.objects = this.state.objects.filter(d => d.id !== id)
        this.state.totalObjects--
    }

    abstract getTable(): any;
}

export default CrudService;