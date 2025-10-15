class WebSocketService {
  socket: WebSocket | null

  connect() {
    this.socket = new WebSocket('ws://localhost:8080/ws')

    this.socket.onopen = () => {
      console.log('WebSocket connected')
    }

    this.socket.onmessage = (event) => {
      console.log('Message received:', event.data)
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