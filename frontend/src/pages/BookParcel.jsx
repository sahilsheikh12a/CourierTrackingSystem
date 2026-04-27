import { useState } from 'react'
import { bookParcel } from '../services/api'

const initialForm = {
  senderName: '',
  senderPhone: '',
  receiverName: '',
  receiverPhone: '',
  origin: '',
  destination: '',
  weightKg: '',
  serviceType: 'Standard',
}

export default function BookParcel() {
  const [form, setForm] = useState(initialForm)
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
      const payload = { ...form, weightKg: parseFloat(form.weightKg) }
      const { data } = await bookParcel(payload)
      setResult(data)
      setForm(initialForm)
    } catch (err) {
      setError(err.response?.data?.error || 'Something went wrong. Is the backend running?')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900">Book a Parcel</h1>
        <p className="text-gray-500 text-sm mt-1">Fill in the details below to create a booking.</p>
      </div>

      {result && (
        <div className="bg-green-50 border border-green-200 rounded-xl p-5 space-y-1">
          <p className="font-semibold text-green-700 text-lg">Booking Confirmed!</p>
          <p className="text-sm text-gray-700">
            Tracking ID:{' '}
            <span className="font-mono font-bold text-blue-700">{result.trackingId}</span>
          </p>
          <p className="text-sm text-gray-700">
            Status: <span className="font-medium">{result.currentStatus}</span>
          </p>
          <p className="text-sm text-gray-700">
            Estimated Cost:{' '}
            <span className="font-medium">Rs. {result.estimatedCost.toFixed(2)}</span>
          </p>
        </div>
      )}

      {error && (
        <div className="bg-red-50 border border-red-200 rounded-xl p-4 text-red-700 text-sm">
          {error}
        </div>
      )}

      <form onSubmit={handleSubmit} className="card space-y-5">
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          <fieldset className="space-y-3 border border-gray-100 rounded-xl p-4">
            <legend className="text-sm font-semibold text-gray-700 px-1">Sender</legend>
            <div>
              <label className="label">Name</label>
              <input
                name="senderName"
                value={form.senderName}
                onChange={handleChange}
                placeholder="First Name"
                className="input"
                required
              />
            </div>
            <div>
              <label className="label">Phone</label>
              <input
                name="senderPhone"
                value={form.senderPhone}
                onChange={handleChange}
                placeholder="Phone Number"
                className="input"
                required
              />
            </div>
          </fieldset>

          <fieldset className="space-y-3 border border-gray-100 rounded-xl p-4">
            <legend className="text-sm font-semibold text-gray-700 px-1">Receiver</legend>
            <div>
              <label className="label">Name</label>
              <input
                name="receiverName"
                value={form.receiverName}
                onChange={handleChange}
                placeholder="First Name"
                className="input"
                required
              />
            </div>
            <div>
              <label className="label">Phone</label>
              <input
                name="receiverPhone"
                value={form.receiverPhone}
                onChange={handleChange}
                placeholder="Phone Number"
                className="input"
                required
              />
            </div>
          </fieldset>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          <div>
            <label className="label">Origin City</label>
            <input
              name="origin"
              value={form.origin}
              onChange={handleChange}
              placeholder="City"
              className="input"
              required
            />
          </div>
          <div>
            <label className="label">Destination City</label>
            <input
              name="destination"
              value={form.destination}
              onChange={handleChange}
              placeholder="City"
              className="input"
              required
            />
          </div>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          <div>
            <label className="label">Weight (kg)</label>
            <input
              name="weightKg"
              type="number"
              step="0.1"
              min="0.1"
              value={form.weightKg}
              onChange={handleChange}
              placeholder="Weight in kg"
              className="input"
              required
            />
          </div>
          <div>
            <label className="label">Service Type</label>
            <select
              name="serviceType"
              value={form.serviceType}
              onChange={handleChange}
              className="input bg-white"
            >
              <option value="Standard">Standard</option>
              <option value="Express">Express (+Rs. 80)</option>
            </select>
          </div>
        </div>

        <div className="text-xs text-gray-400">
          Cost = Rs. 60 base + Rs. 35/kg {form.serviceType === 'Express' ? '+ Rs. 80 express' : ''}
          {form.weightKg
            ? ` = Rs. ${(60 + parseFloat(form.weightKg || 0) * 35 + (form.serviceType === 'Express' ? 80 : 0)).toFixed(2)}`
            : ''}
        </div>

        <button type="submit" disabled={loading} className="btn-primary w-full">
          {loading ? 'Booking...' : 'Book Parcel'}
        </button>
      </form>
    </div>
  )
}
