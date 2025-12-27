import './App.css'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import DefaultLayout from './layouts/DefaultLayout/DefaultLayout'

import MainPage from './pages/MainPage/MainPage'
import LoginPage from './pages/LoginPage/LoginPage'
import RegisterPage from './pages/RegisterPage/RegisterPage'
import ProductPage from './pages/ProductPage/ProductPage'
import AdminPanel from './pages/AdminPanel/AdminPanel'
function App() {
  return (
    <BrowserRouter>
      <Routes>
      <Route
          path="/"
          element={
            <DefaultLayout>
              <MainPage />
            </DefaultLayout>
          }
        />
        <Route
          path="/product/:id"
          element={
            <DefaultLayout>
              <ProductPage />
            </DefaultLayout>
          }
        />
        
        <Route path="/admin" element={<AdminPanel />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App