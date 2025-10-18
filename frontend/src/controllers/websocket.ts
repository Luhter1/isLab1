import DragonService from '@/services/DragonService'
import CoordinatesService from '@/services/CoordinatesService'
import DragonCaveService from '@/services/DragonCaveService'
import DragonHeadService from '@/services/DragonHeadService'
import LocationService from '@/services/LocationService'
import PersonService from '@/services/PeopleService'
import type { Event } from '@/interfaces/events/Event'
import { ResourceType } from '@/interfaces/models/ResourceType';
class WebSocketService {
  socket: WebSocket | null

  connect() {
    this.socket = new WebSocket('ws://localhost:8080/ws')

    this.socket.onopen = () => {
      console.log('WebSocket connected')
    }

    this.socket.onmessage = (rawEvent) => {
      try {
        const event: Event<any> = JSON.parse(rawEvent.data);
        
        switch(event.resourceType) {
          case ResourceType.ADMIN_REQUESTS:
            return console.log('admin-requests received')
          case ResourceType.COORDINATES:
            return CoordinatesService.handleObjectEvent(event)
          case ResourceType.DRAGON_CAVES:
            return DragonCaveService.handleObjectEvent(event)
          case ResourceType.DRAGON_HEADS:
            return DragonHeadService.handleObjectEvent(event)
          case ResourceType.DRAGONS:
            return DragonService.handleObjectEvent(event)
          case ResourceType.LOCATIONS:
            return LocationService.handleObjectEvent(event)
          case ResourceType.PEOPLE:
            return PersonService.handleObjectEvent(event)
          default:
            return console.log('Invalid event received')
        }
      } catch (error) {
        console.error('Ошибка при парсинге данных WebSocket:', error);
        console.log('Сырые данные:', rawEvent.data);
      }
    }

    this.socket.onerror = (error) => {
      console.error('WebSocket error:', error)
    }

    this.socket.onclose = () => {
      console.log('WebSocket disconnected')
    }
  }

  disconnect() {
    if (this.socket) {
      this.socket.close()
    }
  }

  send(data: any) {
    if (this.socket && this.socket.readyState === WebSocket.OPEN) {
      this.socket.send(JSON.stringify(data))
    }
  }
}

export default new WebSocketService()