import logo from "../../assets/logo.png";
import "./Header.scss";
import { useState } from "react";
import { FiMenu, FiX } from "react-icons/fi";
import { FiSearch } from "react-icons/fi";
import { useNavigate } from "react-router-dom";
import BodySection from "../BodySection/BodySection";

import MobileMenu from "../MobileMenu/MobileMenu";

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
        <BodySection noBorder>
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
              <button type="submit" aria-label="Search">
                <FiSearch size={25} />
              </button>
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
        </BodySection>
      </header>

      {/* Боковое мобильное меню */}
      <MobileMenu
        isOpen={isMenuOpen}
        onClose={toggleMenu}
        goToLogin={goToLogin}
      />
    </>
  );
};

export default Header;
