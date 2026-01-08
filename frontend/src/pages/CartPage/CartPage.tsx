import React from "react";
import { useCart } from "../../context/CartContext.tsx";
import { Link } from "react-router-dom";

const CartPage: React.FC = () => {
    const {
        items,
        updateQuantity,
        removeFromCart,
        totalPrice,
        loading,
    } = useCart();

    if (loading) return <div>Загрузка корзины...</div>;

    if (items.length === 0) {
        return (
            <div className="container py-5 text-center">
                <h2>Корзина пуста</h2>
                <Link to="/" className="btn btn-primary mt-4">
                    Перейти к покупкам
                </Link>
            </div>
        );
    }

    return (
        <div className="container py-5">
            <h2 className="mb-5">Корзина</h2>

            <div className="row">
                <div className="col-lg-8">
                    {items.map((item) => (
                        <div key={item.id} className="card mb-4">
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
                                        <div className="bg-light rounded d-flex align-items-center justify-content-center" style={{ height: "180px" }}>
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
                                                className="btn btn-outline-secondary btn-sm"
                                                onClick={() => updateQuantity(item.id, item.quantity - 1)}
                                            >
                                                −
                                            </button>
                                            <strong>{item.quantity}</strong>
                                            <button
                                                className="btn btn-outline-secondary btn-sm"
                                                onClick={() => updateQuantity(item.id, item.quantity + 1)}
                                            >
                                                +
                                            </button>

                                            <button
                                                className="btn btn-danger btn-sm ms-auto"
                                                onClick={() => removeFromCart(item.id)}
                                            >
                                                Удалить
                                            </button>
                                        </div>

                                        <p className="mt-3">
                                            <strong>Итого: {(item.product.price * item.quantity).toLocaleString("ru-RU")} ₽</strong>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>

                <div className="col-lg-4">
                    <div className="card p-4">
                        <h4>Итог</h4>
                        <hr />
                        <div className="d-flex justify-content-between mb-3">
                            <span>Товаров:</span>
                            <strong>{items.reduce((s, i) => s + i.quantity, 0)}</strong>
                        </div>
                        <div className="d-flex justify-content-between">
                            <h5>К оплате:</h5>
                            <h5>{totalPrice.toLocaleString("ru-RU")} ₽</h5>
                        </div>
                        <button className="btn btn-success w-100 mt-4">
                            Оформить заказ
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default CartPage;