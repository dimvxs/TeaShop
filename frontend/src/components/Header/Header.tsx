import logo from "../../assets/logo.png";
import "./Header.scss";
import { useState } from "react";
import { FiMenu, FiX } from "react-icons/fi";
import { FiSearch } from "react-icons/fi";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext.tsx";

const Header = () => {
    const [search, setSearch] = useState("");
    const [isMenuOpen, setIsMenuOpen] = useState(false);

    const navigate = useNavigate();
    const { user, loading, logout } = useAuth();

    const handleSearch = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        if (search.trim()) setSearch("");
    };

    const goToLoginOrProfile = () => navigate(user ? "/profile" : "/login");
    const handleLogout = async () => {
        await logout();
        navigate("/login");
    };
    const toggleMenu = () => setIsMenuOpen(!isMenuOpen);

    // Пока идёт проверка cookie, не показываем Header
    if (loading) return null;

    return (
        <>
            <header className="header-site">
                <div className="header-container">
                    <a href="/" className="logo">
                        <img src={logo} alt="Golfen Leaf" />
                    </a>

                    <button
                        className="menu-toggle"
                        onClick={toggleMenu}
                        aria-label="Открыть меню"
                    >
                        {isMenuOpen ? <FiX size={28} /> : <FiMenu size={28} />}
                    </button>

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

                    <nav className="nav-links">
                        <a href="#login" onClick={user ? handleLogout : goToLoginOrProfile}>
                            {user ? "Выйти" : "Вход"}
                        </a>

                        <a href="#cart">Корзина</a>
                        <a href="#contact">Адреса</a>
                    </nav>
                </div>
            </header>

            <div
                className={`mobile-menu-overlay ${isMenuOpen ? "open" : ""}`}
                onClick={toggleMenu}
            />
            <aside className={`mobile-menu ${isMenuOpen ? "open" : ""}`}>
                <div className="mobile-menu-header">
                    <a href="/" className="logo mobile-logo">
                        <img src={logo} alt="Golfen Leaf" />
                    </a>
                    <button onClick={toggleMenu} className="close-menu">
                        <FiX size={32} />
                    </button>
                </div>

                <nav className="mobile-nav">
                    <a href="#login" onClick={goToLoginOrProfile}>
                        {user ? "Профиль" : "Вход"}
                    </a>
                    <a href="#cart" onClick={toggleMenu}>Корзина</a>
                    <a href="#contact" onClick={toggleMenu}>Адреса</a>
                    <a href="#catalog" onClick={toggleMenu}>Каталог</a>
                    <a href="#sale" onClick={toggleMenu}>Акции</a>
                    <a href="#delivery" onClick={toggleMenu}>Доставка</a>
                </nav>

                <div className="mobile-menu-footer">
                    <p>© 2025 Golfen Leaf</p>
                </div>
            </aside>
        </>
    );
};

export default Header;
