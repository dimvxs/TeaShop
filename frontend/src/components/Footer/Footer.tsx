// src/components/Footer.jsx
import './Footer.scss';
import BodySection from '../BodySection/BodySection';
const Footer = () => {
    return (
        <footer className="site-footer">
        <BodySection noBorder>
            <div className="footer-container">
                {/* Верхняя часть — колонки */}
                <div className="footer-top">

                    {/* Колонка 1 — Логотип + описание */}
                    <div className="footer-column logo-column">
                        <a href="/" className="footer-logo">
                            <img src="/logo-white.png" alt="Golfen Leaf" /> {/* или твой logo */}
                        </a>
                        <p className="footer-description">
                            Golfen Leaf — твой чайный магазин<br />с быстрой доставкой по всей России
                        </p>
                    </div>

                    {/* Колонка 2 — Покупателям */}
                    <div className="footer-column">
                        <h4 className="footer-title">Покупателям</h4>
                        <ul>
                            <li><a href="#delivery">Доставка и оплата</a></li>
                            <li><a href="#return">Возврат товара</a></li>
                            <li><a href="#bonus">Бонусная программа</a></li>
                            <li><a href="#faq">Вопросы и ответы</a></li>
                            <li><a href="#reviews">Отзывы</a></li>
                        </ul>
                    </div>

                    {/* Колонка 3 — О компании */}
                    <div className="footer-column">
                        <h4 className="footer-title">О компании</h4>
                        <ul>
                            <li><a href="#about">О нас</a></li>
                            <li><a href="#vacancy">Вакансии</a></li>
                            <li><a href="#partners">Партнёрам</a></li>
                            <li><a href="#blog">Блог</a></li>
                            <li><a href="#contacts">Контакты</a></li>
                        </ul>
                    </div>

                    {/* Колонка 4 — Контакты и соцсети */}
                    <div className="footer-column">
                        <h4 className="footer-title">Связаться с нами</h4>
                        <ul className="contact-list">
                            <li>8 800 555-35-35</li>
                            <li>support@golfenleaf.ru</li>
                        </ul>

                        <div className="social-links">
                            <a href="#" aria-label="VK"><img src="/vk.svg" alt="VK" /></a>
                            <a href="#" aria-label="Telegram"><img src="/telegram.svg" alt="Telegram" /></a>
                            <a href="#" aria-label="YouTube"><img src="/youtube.svg" alt="YouTube" /></a>
                            <a href="#" aria-label="Instagram"><img src="/instagram.svg" alt="Instagram"/></a>
                        </div>
                    </div>
                </div>

                {/* Нижняя часть — копирайт и платежки */}
                <div className="footer-bottom">
                    <div className="copyright">
                        © 2025 Golfen Leaf. Все права защищены.
                    </div>

                    <div className="payment-methods">
                        <img src="/visa.svg" alt="Visa" />
                        <img src="/mastercard.svg" alt="Mastercard" />
                        <img src="/mir.svg" alt="Мир" />
                        <img src="/sbp.svg" alt="СБП" />
                    </div>
                </div>
            </div>
        </BodySection>

        </footer>
    );
};

export default Footer;