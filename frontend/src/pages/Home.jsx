import { Link } from 'react-router-dom'

const features = [
  {
    icon: '📝',
    title: 'Book a Parcel',
    desc: 'Fill in sender & receiver details to generate a tracking ID instantly.',
    to: '/book',
    cta: 'Book Now',
  },
  {
    icon: '🔍',
    title: 'Track Shipment',
    desc: 'Enter your tracking ID to see real-time status and full history.',
    to: '/track',
    cta: 'Track',
  },
  {
    icon: '🔄',
    title: 'Update Status',
    desc: 'Operators can move parcels through each stage of the delivery pipeline.',
    to: '/status',
    cta: 'Update',
  },
]

export default function Home() {
  return (
    <div className="space-y-12">
      {/* Hero */}
      <div className="text-center space-y-4 py-8">
        <h1 className="text-4xl font-bold text-gray-900">
          Courier Tracking System
        </h1>
        <p className="text-gray-500 text-lg max-w-xl mx-auto">
          Book, track, and manage parcel deliveries — all in one place.
        </p>
        <div className="flex justify-center gap-4 pt-2">
          <Link to="/book" className="btn-primary">
            Book a Parcel
          </Link>
          <Link to="/track" className="btn-secondary">
            Track Shipment
          </Link>
        </div>
      </div>

      {/* Feature cards */}
      <div className="grid grid-cols-1 sm:grid-cols-3 gap-5">
        {features.map(({ icon, title, desc, to, cta }) => (
          <div key={to} className="card flex flex-col gap-3">
            <div className="text-3xl">{icon}</div>
            <h2 className="font-semibold text-gray-900">{title}</h2>
            <p className="text-sm text-gray-500 flex-1">{desc}</p>
            <Link to={to} className="btn-primary text-center text-sm">
              {cta}
            </Link>
          </div>
        ))}
      </div>

      {/* Status pipeline */}
      <div className="card">
        <h3 className="font-semibold text-gray-900 mb-4">Delivery Pipeline</h3>
        <div className="flex flex-wrap gap-2 items-center">
          {[
            'Booked',
            'Picked Up',
            'In Transit',
            'Arrived At Hub',
            'Out For Delivery',
            'Delivered',
          ].map((step, i, arr) => (
            <div key={step} className="flex items-center gap-2">
              <span className="bg-blue-50 text-blue-700 text-xs font-medium px-3 py-1 rounded-full">
                {step}
              </span>
              {i < arr.length - 1 && (
                <span className="text-gray-300 text-sm">→</span>
              )}
            </div>
          ))}
        </div>
      </div>
    </div>
  )
}
