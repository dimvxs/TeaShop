import React from "react";
import { useCart } from "../../context/CartContext.tsx";
import { Link } from "react-router-dom";
import { MdOutlineShoppingCart } from "react-icons/md";
import CartItem from "../../components/CartItem/CartItem";
import "./CartPage.scss";

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
                <div className="go-back-wrapper card ">
                    <MdOutlineShoppingCart className="empty-cart-icon" />
                    <h2>Корзина пуста</h2>
                    <p>Загляните на главную — мы собрали там товары, которые могут вам понравиться!</p>
                    <Link to="/" className="btn go-back-btn">
                        Перейти к покупкам
                    </Link>
                </div>

            </div>
        );
    }

    return (
        <div className="container py-5">
            <h2 className="mb-5">Корзина</h2>

            <div className="row">
                <div className="col-lg-8">
                    {items.map((item) => (
                        <CartItem key={item.id} item={item} />
                    ))}
                </div>

                <div className="col-lg-4">
                    <div className="summary-card-wrapper">
                        <div className="cart-page-card card p-4">
                            <h4>Итог</h4>
                            <hr />
                            <div className="d-flex justify-content-between mb-3">
                                <span>Товаров:</span>
                                <strong>{items.reduce((s, i) => s + i.quantity, 0)}</strong>
                            </div>
                            <div className="d-flex justify-content-between total-row">
                                <h5>К оплате:</h5>
                                <h5 className="total-price">{totalPrice.toLocaleString("ru-RU")} ₽</h5>
                            </div>

                            <button className="order-btn w-100 mt-4">
                                Оформить заказ
                            </button>

                        </div>
                    </div>
                </div>

            </div>
        </div>
    );
};

export default CartPage;