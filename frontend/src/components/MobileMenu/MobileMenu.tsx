import type { FC } from "react";
import { FiX } from "react-icons/fi";
import logo from "../../assets/logo.png";
import "./MobileMenu.scss";

type MobileMenuProps = {
  isOpen: boolean;
  onClose: () => void;
  goToLogin: () => void;
};

const MobileMenu: FC<MobileMenuProps> = ({ isOpen, onClose, goToLogin }) => {
  return (
    <>
      <div
        className={`mobile-menu-overlay ${isOpen ? "open" : ""}`}
        onClick={onClose}
      />

      <aside className={`mobile-menu ${isOpen ? "open" : ""}`}>
        <div className="mobile-menu-header">
          <a href="/public" className="logo mobile-logo">
            <img src={logo} alt="Golfen Leaf" />
          </a>
          <button onClick={onClose} className="close-menu">
            <FiX size={32} />
          </button>
        </div>

        <nav className="mobile-nav">
          <a href="#login" onClick={goToLogin}>Вход</a>
          <a href="#cart" onClick={onClose}>Корзина</a>
          <a href="#contact" onClick={onClose}>Адреса</a>
          <a href="#catalog" onClick={onClose}>Каталог</a>
          <a href="#sale" onClick={onClose}>Акции</a>
          <a href="#delivery" onClick={onClose}>Доставка</a>
        </nav>

        <div className="mobile-menu-footer">
          <p>© 2025 Golden Leaf</p>
        </div>
      </aside>
    </>
  );
};

export default MobileMenu;
