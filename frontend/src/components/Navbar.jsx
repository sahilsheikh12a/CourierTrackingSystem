import { NavLink } from 'react-router-dom'

const links = [
  { to: '/', label: 'Home' },
  { to: '/book', label: 'Book Parcel' },
  { to: '/track', label: 'Track' },
  { to: '/status', label: 'Update Status' },
]

export default function Navbar() {
  return (
    <nav className="bg-white border-b border-gray-200 shadow-sm">
      <div className="max-w-5xl mx-auto px-4 flex items-center justify-between h-16">
        <NavLink to="/" className="flex items-center gap-2 text-blue-600 font-bold text-xl">
          <span className="text-2xl">📦</span>
          CourierTrack
        </NavLink>
        <div className="flex gap-1">
          {links.map(({ to, label }) => (
            <NavLink
              key={to}
              to={to}
              end={to === '/'}
              className={({ isActive }) =>
                `px-4 py-2 rounded-lg text-sm font-medium transition ${
                  isActive
                    ? 'bg-blue-50 text-blue-600'
                    : 'text-gray-600 hover:bg-gray-100'
                }`
              }
            >
              {label}
            </NavLink>
          ))}
        </div>
      </div>
    </nav>
  )
}
