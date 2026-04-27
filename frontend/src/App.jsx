import { Routes, Route } from 'react-router-dom'
import Navbar from './components/Navbar'
import Home from './pages/Home'
import BookParcel from './pages/BookParcel'
import TrackShipment from './pages/TrackShipment'
import UpdateStatus from './pages/UpdateStatus'

export default function App() {
  return (
    <div className="min-h-screen bg-gray-50">
      <Navbar />
      <main className="max-w-3xl mx-auto px-4 py-10">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/book" element={<BookParcel />} />
          <Route path="/track" element={<TrackShipment />} />
          <Route path="/status" element={<UpdateStatus />} />
        </Routes>
      </main>
    </div>
  )
}
