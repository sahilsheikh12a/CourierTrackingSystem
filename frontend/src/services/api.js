import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: { 'Content-Type': 'application/json' },
})

export const bookParcel = (data) => api.post('/bookings', data)
export const trackShipment = (trackingId) => api.get(`/tracking/${trackingId}`)
export const updateStatus = (data) => api.put('/status', data)
