import logo from "../../assets/logo.png";
import "./Header.scss";
import { useState } from "react";
import { FiMenu, FiX } from "react-icons/fi"; // иконки от react-icons
import { useNavigate } from "react-router-dom";

const Header = () => {
  const [search, setSearch] = useState("");
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  const handleSearch = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (search.trim()) {
      setSearch("");
    }
  };

  const navigate = useNavigate();
  const goToLogin = () => {
    navigate("/login");
  };

  const toggleMenu = () => setIsMenuOpen(!isMenuOpen);

  return (
    <>
      <header className="header-site">
        <div className="header-container">
          {/* Логотип */}

          <a href="/public" className="logo">
            <img src={logo} alt="Golfen Leaf" />
          </a>

          {/* Кнопка гамбургера — только на мобильных */}
          <button
            className="menu-toggle"
            onClick={toggleMenu}
            aria-label="Открыть меню"
          >
            {isMenuOpen ? <FiX size={28} /> : <FiMenu size={28} />}
          </button>

          {/* Поисковая форма */}
          <form className="search-form" onSubmit={handleSearch}>
            <input
              type="text"
              placeholder="Найти на Golfen Leaf"
              value={search}
              onChange={(e) => setSearch(e.target.value)}
            />
            <button type="submit">Поиск</button>
          </form>

          {/* Навигация — скрывается на мобильных */}
          <nav className="nav-links">
            <a href="#login" onClick={goToLogin}>
              Вход
            </a>
            <a href="#cart">Корзина</a>
            <a href="#contact">Адреса</a>
          </nav>
        </div>
      </header>

      {/* Боковое мобильное меню */}
      <div
        className={`mobile-menu-overlay ${isMenuOpen ? "open" : ""}`}
        onClick={toggleMenu}
      />
      <aside className={`mobile-menu ${isMenuOpen ? "open" : ""}`}>
        <div className="mobile-menu-header">
          <a href="/public" className="logo mobile-logo">
            <img src={logo} alt="Golfen Leaf" />
          </a>
          <button onClick={toggleMenu} className="close-menu">
            <FiX size={32} />
          </button>
        </div>

        <nav className="mobile-nav">
          <a href="#login" onClick={goToLogin}>
            Вход
          </a>
          <a href="#cart" onClick={toggleMenu}>
            Корзина
          </a>
          <a href="#contact" onClick={toggleMenu}>
            Адреса
          </a>
          <a href="#catalog" onClick={toggleMenu}>
            Каталог
          </a>
          <a href="#sale" onClick={toggleMenu}>
            Акции
          </a>
          <a href="#delivery" onClick={toggleMenu}>
            Доставка
          </a>
        </nav>

        <div className="mobile-menu-footer">
          <p>© 2025 Golfen Leaf</p>
        </div>
      </aside>
    </>
  );
};

export default Header;
