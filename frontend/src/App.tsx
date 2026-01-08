import './App.css'
import { Routes, Route } from 'react-router-dom'
import DefaultLayout from './layouts/DefaultLayout/DefaultLayout'

import MainPage from './pages/MainPage/MainPage'
import LoginPage from './pages/LoginPage/LoginPage'
import RegisterPage from './pages/RegisterPage/RegisterPage'
import ProductPage from './pages/ProductPage/ProductPage'
import AdminPanel from './pages/AdminPanel/AdminPanel'
import ProfilePage from './pages/ProfilePage/ProfilePage'
import ProductAdminForm from "./pages/AdminPanel/ProductAdminForm.tsx";
import CategoryAdminForm from "./pages/AdminPanel/CategoryAdminForm.tsx";
import CartPage from "./pages/CartPage/CartPage.tsx";

function App() {
    return (
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
            <Route
                path="/profile"
                element={
                    <DefaultLayout>
                        <ProfilePage />
                    </DefaultLayout>
                }
            />
            <Route path="/admin" element={<AdminPanel />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route path="/adminProduct" element={<ProductAdminForm />} />
            <Route path="/adminCategory" element={<CategoryAdminForm />} />

            <Route
                path="/cart"
                element={
                    <DefaultLayout headerSticky={false}>
                        <CartPage />
                    </DefaultLayout>
                }
            />

        </Routes>
    )
}

export default App
