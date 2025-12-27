import AdminRequestController from '@/controllers/AdminRequestController'
import { AdminRequestDto } from '@/interfaces/dto/adminrequests/AdminRequestDto'
import Paged from '@/interfaces/models/Paged'

class AdminRequestService {
  async getAll(page: number, size: number, sort: string[]): Promise<Paged<AdminRequestDto>> {
    const response = await AdminRequestController.getAll(page, size, sort)
    return response.data
  }

  async getPending(page: number, size: number, sort: string[]): Promise<Paged<AdminRequestDto>> {
    const response = await AdminRequestController.getPending(page, size, sort)
    return response.data
  }

  async getById(id: number): Promise<AdminRequestDto> {
    const response = await AdminRequestController.get(id)
    return response.data
  }

  async create(): Promise<AdminRequestDto> {
    const response = await AdminRequestController.create()
    return response.data
  }

  async process(id: number, approved: boolean): Promise<AdminRequestDto> {
    const response = await AdminRequestController.process(id, approved)
    return response.data
  }
}

export default new AdminRequestService()
