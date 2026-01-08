import React from "react";
import { useCart } from "../../context/CartContext.tsx";
import "./CartItem.scss";

interface CartItemProps {
    item: {
        id: number;
        quantity: number;
        product: {
            id: number;
            name: string;
            brand?: string;
            price: number;
            imageUrls: string[];
        };
    };
}

const CartItem: React.FC<CartItemProps> = ({ item }) => {
    const { updateQuantity, removeFromCart } = useCart();
    return (
        <div className="card cart-item-card mb-4">
            <div className="row g-0">
                <div className="col-md-3 p-3">
                    {item.product.imageUrls[0] ? (
                        <img
                            src={`http://localhost:8080${item.product.imageUrls[0]}`}
                            alt={item.product.name}
                            className="img-fluid rounded"
                            style={{ maxHeight: "180px", objectFit: "cover" }}
                        />
                    ) : (
                        <div
                            className="bg-light rounded d-flex align-items-center justify-content-center"
                            style={{ height: "180px" }}
                        >
                            Нет фото
                        </div>
                    )}
                </div>

                <div className="col-md-9">
                    <div className="card-body">
                        <h5>{item.product.brand} {item.product.name}</h5>
                        <p className="text-muted">Цена: {item.product.price} ₽</p>

                        <div className="d-flex align-items-center gap-3 mt-3">
                            <button
                                className="quantity-btn"
                                onClick={() => updateQuantity(item.id, item.quantity - 1)}
                            >
                                −
                            </button>
                            <strong>{item.quantity}</strong>
                            <button
                                className="quantity-btn"
                                onClick={() => updateQuantity(item.id, item.quantity + 1)}
                            >
                                +
                            </button>
                        </div>


                        <p className="mt-3 d-flex justify-content-between align-items-center">
                            <strong>
                                Итого: {(item.product.price * item.quantity).toLocaleString("ru-RU")} ₽
                            </strong>
                            <button
                                className="delete-btn"
                                onClick={() => removeFromCart(item.id)}
                            >
                                Удалить
                            </button>

                        </p>

                    </div>
                </div>
            </div>
        </div>
    );
};

export default CartItem;
