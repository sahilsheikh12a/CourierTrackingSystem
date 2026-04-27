import { useState } from 'react'
import { updateStatus } from '../services/api'

const STATUS_OPTIONS = [
  'Picked Up',
  'In Transit',
  'Arrived At Hub',
  'Out For Delivery',
  'Delivered',
  'Delayed',
  'Returned',
  'Cancelled',
]

export default function UpdateStatus() {
  const [form, setForm] = useState({ trackingId: '', newStatus: '', remarks: '' })
  const [result, setResult] = useState(null)
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  const handleChange = (e) => {
    setForm((f) => ({ ...f, [e.target.name]: e.target.value }))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')
    setResult(null)
    setLoading(true)
    try {
      const { data } = await updateStatus(form)
      setResult(data)
      setForm((f) => ({ ...f, remarks: '' }))
    } catch (err) {
      setError(err.response?.data?.error || 'Update failed. Check the tracking ID and transition.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900">Update Shipment Status</h1>
        <p className="text-gray-500 text-sm mt-1">
          Operator panel — move a parcel to the next stage.
        </p>
      </div>

      {result && (
        <div className="bg-green-50 border border-green-200 rounded-xl p-5 space-y-1">
          <p className="font-semibold text-green-700">Status Updated Successfully</p>
          <p className="text-sm text-gray-700">
            Tracking ID:{' '}
            <span className="font-mono font-bold text-blue-700">{result.trackingId}</span>
          </p>
          <p className="text-sm text-gray-700">
            {result.previousStatus}{' '}
            <span className="text-gray-400 mx-1">→</span>
            <span className="font-semibold text-gray-900">{result.currentStatus}</span>
          </p>
        </div>
      )}

      {error && (
        <div className="bg-red-50 border border-red-200 rounded-xl p-4 text-red-700 text-sm">
          {error}
        </div>
      )}

      <form onSubmit={handleSubmit} className="card space-y-4">
        <div>
          <label className="label">Tracking ID</label>
          <input
            name="trackingId"
            value={form.trackingId}
            onChange={handleChange}
            placeholder="CTR-20260427-10001"
            className="input font-mono"
            required
          />
        </div>

        <div>
          <label className="label">New Status</label>
          <select
            name="newStatus"
            value={form.newStatus}
            onChange={handleChange}
            className="input bg-white"
            required
          >
            <option value="" disabled>
              Select status...
            </option>
            {STATUS_OPTIONS.map((s) => (
              <option key={s} value={s}>
                {s}
              </option>
            ))}
          </select>
        </div>

        <div>
          <label className="label">Remarks (optional)</label>
          <textarea
            name="remarks"
            value={form.remarks}
            onChange={handleChange}
            placeholder="e.g. Parcel picked up from Lahore hub"
            className="input resize-none"
            rows={3}
          />
        </div>

        <div className="bg-amber-50 border border-amber-200 rounded-lg p-3 text-xs text-amber-700">
          <strong>Valid transitions:</strong> Booked → Picked Up → In Transit → Arrived At Hub → Out
          For Delivery → Delivered. Delayed can go back to In Transit or Out For Delivery.
        </div>

        <button type="submit" disabled={loading} className="btn-primary w-full">
          {loading ? 'Updating...' : 'Update Status'}
        </button>
      </form>
    </div>
  )
}
