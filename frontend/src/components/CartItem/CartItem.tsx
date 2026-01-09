import React, { useState } from "react";
import { useCart } from "../../context/CartContext";
import { useNavigate } from "react-router-dom"; // Добавляем для редиректа
import "./CartItem.scss";

interface CartItemProps {
    item: {
        id: number;
        quantity: number;
        product?: {
            id: number;
            name: string;
            price: number;
            imageUrls: string[];
        };
        productName?: string;
        pricePerUnit?: number;
        imageUrl?: string;
    };
}

const CartItem: React.FC<CartItemProps> = ({ item }) => {
    const { incrementItem, decrementItem, removeFromCart, items } = useCart();
    const navigate = useNavigate(); // Для программного редиректа

    const [isRemoved, setIsRemoved] = useState(false);

    const name = item.productName ?? item.product?.name ?? "Без названия";
    const price = item.pricePerUnit ?? item.product?.price ?? 0;
    const imageUrl =
        item.imageUrl ??
        item.product?.imageUrls?.[0] ??
        "/images/default-product.jpg";

    const handleRemove = () => {
        setIsRemoved(true);                  // Мгновенно скрываем карточку

        // Проверяем: это был последний товар в корзине?
        const isLastItem = items.length === 1;

        // Запускаем удаление на сервере
        removeFromCart(item.id);

        if (isLastItem) {
            // Через 1 секунду редиректим на главную
            setTimeout(() => {
                navigate("/");
            }, 1000);
        }
    };

    if (isRemoved) {
        return null;
    }

    return (
        <div className="card cart-item-card mb-4">
            <div className="row g-0">
                <div className="col-md-3 p-3">
                    <img
                        src={imageUrl.startsWith("http") ? imageUrl : `http://localhost:8080${imageUrl}`}
                        alt={name}
                        className="img-fluid rounded"
                    />
                </div>

                <div className="col-md-9">
                    <div className="card-body">
                        <h5>{name}</h5>
                        <p className="text-muted">Цена: {price.toLocaleString("ru-RU")} ₽</p>

                        <div className="d-flex align-items-center gap-3 mt-3">
                            <button
                                className="quantity-btn"
                                onClick={() => decrementItem(item.id)}
                                disabled={item.quantity <= 1}
                            >
                                −
                            </button>
                            <strong>{item.quantity}</strong>
                            <button
                                className="quantity-btn"
                                onClick={() => incrementItem(item.id)}
                            >
                                +
                            </button>
                        </div>

                        <div className="mt-3 d-flex justify-content-between align-items-center">
                            <button
                                className="delete-btn"
                                onClick={handleRemove}
                            >
                                Удалить
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default CartItem;