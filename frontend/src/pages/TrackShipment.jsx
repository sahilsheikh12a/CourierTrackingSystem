import { useState } from 'react'
import { trackShipment } from '../services/api'

const STATUS_COLORS = {
  Booked: 'bg-blue-100 text-blue-700',
  'Picked Up': 'bg-yellow-100 text-yellow-700',
  'In Transit': 'bg-orange-100 text-orange-700',
  'Arrived At Hub': 'bg-purple-100 text-purple-700',
  'Out For Delivery': 'bg-indigo-100 text-indigo-700',
  Delivered: 'bg-green-100 text-green-700',
  Delayed: 'bg-red-100 text-red-700',
  Returned: 'bg-gray-100 text-gray-700',
  Cancelled: 'bg-red-100 text-red-700',
}

function formatStatus(raw) {
  return raw
    .toLowerCase()
    .split('_')
    .map((w) => w.charAt(0).toUpperCase() + w.slice(1))
    .join(' ')
}

function formatDate(iso) {
  return new Date(iso).toLocaleString('en-PK', {
    day: '2-digit',
    month: 'short',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
  })
}

export default function TrackShipment() {
  const [trackingId, setTrackingId] = useState('')
  const [result, setResult] = useState(null)
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  const handleSearch = async (e) => {
    e.preventDefault()
    setError('')
    setResult(null)
    setLoading(true)
    try {
      const { data } = await trackShipment(trackingId.trim())
      setResult(data)
    } catch (err) {
      setError(err.response?.data?.error || 'Tracking ID not found or backend is offline.')
    } finally {
      setLoading(false)
    }
  }

  const statusLabel = result?.currentStatus
  const statusClass = STATUS_COLORS[statusLabel] || 'bg-gray-100 text-gray-700'

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900">Track Shipment</h1>
        <p className="text-gray-500 text-sm mt-1">Enter your tracking ID to see live status.</p>
      </div>

      <form onSubmit={handleSearch} className="flex gap-3">
        <input
          value={trackingId}
          onChange={(e) => setTrackingId(e.target.value)}
          placeholder="e.g. CTR-20260427-10001"
          className="input flex-1"
          required
        />
        <button type="submit" disabled={loading} className="btn-primary whitespace-nowrap">
          {loading ? 'Searching...' : 'Track'}
        </button>
      </form>

      {error && (
        <div className="bg-red-50 border border-red-200 rounded-xl p-4 text-red-700 text-sm">
          {error}
        </div>
      )}

      {result && (
        <div className="space-y-4">
          {/* Summary card */}
          <div className="card space-y-3">
            <div className="flex items-start justify-between flex-wrap gap-2">
              <div>
                <p className="text-xs text-gray-400 uppercase tracking-wide">Tracking ID</p>
                <p className="font-mono font-bold text-blue-700 text-lg">{result.trackingId}</p>
              </div>
              <span className={`text-sm font-semibold px-3 py-1 rounded-full ${statusClass}`}>
                {result.currentStatus}
              </span>
            </div>

            <div className="grid grid-cols-2 sm:grid-cols-3 gap-3 text-sm">
              <div>
                <p className="text-gray-400 text-xs">Route</p>
                <p className="font-medium">
                  {result.origin} → {result.destination}
                </p>
              </div>
              <div>
                <p className="text-gray-400 text-xs">Receiver</p>
                <p className="font-medium">{result.receiverName}</p>
              </div>
              <div>
                <p className="text-gray-400 text-xs">Booked On</p>
                <p className="font-medium">{formatDate(result.bookedAt)}</p>
              </div>
            </div>
          </div>

          {/* History timeline */}
          <div className="card">
            <h3 className="font-semibold text-gray-900 mb-4">Tracking History</h3>
            <ol className="relative border-l border-gray-200 space-y-5 pl-4">
              {[...result.history].reverse().map((item, i) => {
                const label = formatStatus(item.status)
                const color = STATUS_COLORS[label] || 'bg-gray-100 text-gray-700'
                return (
                  <li key={i} className="ml-3">
                    <span className="absolute -left-1.5 mt-1.5 w-3 h-3 bg-blue-500 rounded-full border-2 border-white" />
                    <p className="text-xs text-gray-400">{formatDate(item.updatedAt)}</p>
                    <span className={`text-xs font-semibold px-2 py-0.5 rounded-full ${color}`}>
                      {label}
                    </span>
                    <p className="text-sm text-gray-600 mt-0.5">{item.remarks}</p>
                  </li>
                )
              })}
            </ol>
          </div>
        </div>
      )}
    </div>
  )
}
