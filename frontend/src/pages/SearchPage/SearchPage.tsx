import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import "./SearchPage.scss";

const API_BASE = "http://localhost:8080/api";
const BACKEND_URL = "http://localhost:8080";

interface Product {
    id: number;
    name: string;
    brand: string;
    price: number;
    imageUrls: string[];
}

const useQuery = () => {
    return new URLSearchParams(useLocation().search);
};

const SearchPage: React.FC = () => {
    const queryParam = useQuery().get("query") || "";
    const [products, setProducts] = useState<Product[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

    useEffect(() => {
        if (!queryParam.trim()) {
            setProducts([]);
            setLoading(false);
            return;
        }

        const fetchProducts = async () => {
            try {
                setLoading(true);
                const res = await axios.get(`${API_BASE}/products/search?query=${encodeURIComponent(queryParam)}`);
                setProducts(res.data);
            } catch (e) {
                console.error(e);
                setError("Ошибка при загрузке товаров");
            } finally {
                setLoading(false);
            }
        };

        fetchProducts();
    }, [queryParam]);

    const goToProduct = (id: number) => {
        navigate(`/product/${id}`);
    };

    if (loading) return <div className="loading">Загрузка товаров...</div>;
    if (error) return <div className="error">{error}</div>;
    if (products.length === 0) return <div className="no-results">Товары не найдены</div>;

    return (
        <div className="search-page">
            <h2>Результаты поиска: "{queryParam}"</h2>
            <div className="product-grid">
                {products.map((product) => (
                    <div
                        key={product.id}
                        className="product-card"
                        onClick={() => goToProduct(product.id)}
                    >
                        <img
                            src={product.imageUrls[0] ? `${BACKEND_URL}${product.imageUrls[0]}` : "/images/default-product.jpg"}
                            alt={product.name}
                        />
                        <div className="product-info">
                            <h3>{product.brand} {product.name}</h3>
                            <p>{product.price.toLocaleString("ru-RU")} ₽</p>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default SearchPage;
