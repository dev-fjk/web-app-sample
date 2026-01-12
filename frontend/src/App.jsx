import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Header from './components/Header'
import UserList from './pages/UserList'
import UserDetail from './pages/UserDetail'

function App() {
  return (
    <BrowserRouter>
      <div className="min-h-screen bg-gray-50">
        <Header />
        <main className="container mx-auto px-4 py-8">
          <Routes>
            <Route path="/" element={<UserList />} />
            <Route path="/users" element={<UserList />} />
            <Route path="/users/:id" element={<UserDetail />} />
          </Routes>
        </main>
      </div>
    </BrowserRouter>
  )
}

export default App
