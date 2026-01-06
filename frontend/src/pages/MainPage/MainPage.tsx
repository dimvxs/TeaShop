import React, { useState, useEffect } from 'react';
import axios from 'axios';
import ProductCard from "../ProductCard/ProductCard.tsx"; // проверь путь

const BACKEND_URL = 'http://localhost:8080';
const API_BASE = 'http://localhost:8080/api';

interface Product {
    id: number;
    name: string;
    brand: string;
    price: number;
    imageUrls: string[];
}

const MainPage: React.FC = () => {
    const [products, setProducts] = useState<Product[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        console.log('useEffect сработал — начинаю загрузку товаров');

        const fetchProducts = async () => {
            try {
                const res = await axios.get(`${API_BASE}/products`);

                console.log('API RESPONSE RAW:', res.data);
                console.log('Content-Type:', res.headers['content-type']);

                // Правильно: логируем то, что пришло с сервера
                const data = Array.isArray(res.data) ? res.data : [];
                console.log('Данные с сервера (будут в products):', data);

                // Обновляем состояние
                setProducts(data);
                setLoading(false); // ← Это завершит загрузку

                console.log('Состояния обновлены: loading = false, products =', data.length);
            } catch (err: any) {
                console.error('Ошибка при загрузке товаров:', err);
                if (err.response) {
                    console.error('Статус:', err.response.status);
                    console.error('Тело ошибки:', err.response.data);
                }

                setProducts([]);
                setError('Не удалось загрузить товары');
                setLoading(false); // ← Обязательно и здесь!
            }
        };

        fetchProducts();
    }, []);

    if (loading) {
        return <div style={{ padding: '40px', textAlign: 'center' }}>Загрузка товаров...</div>;
    }

    if (error) {
        return <div style={{ padding: '40px', textAlign: 'center', color: 'red' }}>{error}</div>;
    }

    return (
        <div style={{ padding: '20px' }}>
            <h1 style={{ textAlign: 'center', marginBottom: '40px' }}>
                GoldenLeaf Shop — Каталог чая
            </h1>

            {products.length === 0 ? (
                <p style={{ textAlign: 'center', fontSize: '18px', color: '#777' }}>
                    Товаров пока нет. Добавьте первый в админ-панели!
                </p>
            ) : (
                <div
                    style={{
                        display: 'grid',
                        gridTemplateColumns: 'repeat(auto-fill, minmax(280px, 1fr))',
                        gap: '30px',
                        maxWidth: '1400px',
                        margin: '0 auto',
                    }}
                >
                    {products.map((product) => (
                        <ProductCard
                            key={product.id}
                            id={product.id}
                            image={
                                product.imageUrls?.[0]
                                    ? `${BACKEND_URL}${product.imageUrls[0]}`
                                    : `https://via.placeholder.com/300x300?text=${encodeURIComponent('Чай')}`
                            }
                            title={`${product.brand} ${product.name}`}
                            price={product.price}
                        />
                    ))}
                </div>
            )}
        </div>
    );
};

export default MainPage;