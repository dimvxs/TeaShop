// import logo from "../../assets/logo.png";
// import "./Header.scss";
// import { useState } from "react";
// import { FiMenu, FiX, FiSearch } from "react-icons/fi";
// import { useNavigate } from "react-router-dom";
// import { useAuth } from "../../context/AuthContext.tsx";
//
// interface HeaderProps {
//     sticky?: boolean;
// }
//
// const Header: React.FC<HeaderProps> = ({ sticky = true }) => {
//     const [search, setSearch] = useState("");
//     const [isMenuOpen, setIsMenuOpen] = useState(false);
//
//     const navigate = useNavigate();
//     const { user, loading, logout } = useAuth();
//
//     const goToCart = () => {
//         navigate("/cart");
//         setIsMenuOpen(false);
//     };
//
//     const handleSearch = (e: React.FormEvent<HTMLFormElement>) => {
//         e.preventDefault();
//         const query = search.trim();
//         if (!query) return;
//
//         navigate(`/search?query=${encodeURIComponent(query)}`);
//         setSearch("");
//         setIsMenuOpen(false);
//     };
//
//     const goToLoginOrProfile = () => navigate(user ? "/profile" : "/login");
//     const handleLogout = async () => {
//         await logout();
//         navigate("/");
//     };
//     const toggleMenu = () => setIsMenuOpen(!isMenuOpen);
//
//     if (loading) return null;
//
//     return (
//         <>
//             <header className={`header-site ${sticky ? "sticky" : "non-sticky"}`}>
//                 <div className="header-container">
//                     <a href="/" className="logo">
//                         <img src={logo} alt="Golfen Leaf" />
//                     </a>
//
//                     <button
//                         className="menu-toggle"
//                         onClick={toggleMenu}
//                         aria-label="Открыть меню"
//                     >
//                         {isMenuOpen ? <FiX size={28} /> : <FiMenu size={28} />}
//                     </button>
//
//                     <form className="search-form" onSubmit={handleSearch}>
//                         <input
//                             type="text"
//                             placeholder="Найти на Golfen Leaf"
//                             value={search}
//                             onChange={(e) => setSearch(e.target.value)}
//                         />
//                         <button type="submit" aria-label="Search">
//                             <FiSearch size={25} />
//                         </button>
//                     </form>
//
//                     <nav className="nav-links">
//                         <a href="#login" onClick={user ? handleLogout : goToLoginOrProfile}>
//                             {user ? "Выйти" : "Вход"}
//                         </a>
//                         <a href="#cart" onClick={goToCart}>Корзина</a>
//                         <a href="#contact">Адреса</a>
//                     </nav>
//                 </div>
//             </header>
//
//             <div
//                 className={`mobile-menu-overlay ${isMenuOpen ? "open" : ""}`}
//                 onClick={toggleMenu}
//             />
//             <aside className={`mobile-menu ${isMenuOpen ? "open" : ""}`}>
//                 <div className="mobile-menu-header">
//                     <a href="/" className="logo mobile-logo">
//                         <img src={logo} alt="Golfen Leaf" />
//                     </a>
//                     <button onClick={toggleMenu} className="close-menu">
//                         <FiX size={32} />
//                     </button>
//                 </div>
//
//                 <nav className="mobile-nav">
//                     <a href="#login" onClick={goToLoginOrProfile}>
//                         {user ? "Профиль" : "Вход"}
//                     </a>
//                     <a href="#cart" onClick={toggleMenu}>Корзина</a>
//                     <a href="#contact" onClick={toggleMenu}>Адреса</a>
//                     <a href="#catalog" onClick={toggleMenu}>Каталог</a>
//                 </nav>
//
//                 <div className="mobile-menu-footer">
//                     <p>© 2025 Golfen Leaf</p>
//                 </div>
//             </aside>
//         </>
//     );
// };
//
// export default Header;
import logo from "../../assets/logo.png";
import "./Header.scss";
import { useState } from "react";
import { FiMenu, FiX, FiSearch } from "react-icons/fi";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext.tsx";

interface HeaderProps {
    sticky?: boolean;
}

const Header: React.FC<HeaderProps> = ({ sticky = true }) => {
    const [search, setSearch] = useState("");
    const [isMenuOpen, setIsMenuOpen] = useState(false);

    const navigate = useNavigate();
    const { user, loading, logout } = useAuth();

    const goToCart = () => {
        navigate("/cart");
        setIsMenuOpen(false);
    };

    const handleSearch = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const query = search.trim();
        if (!query) return;

        navigate(`/search?query=${encodeURIComponent(query)}`);
        setSearch("");
        setIsMenuOpen(false);
    };

    const handleLogout = async () => {
        await logout();
        navigate("/");
    };

    const toggleMenu = () => setIsMenuOpen(!isMenuOpen);

    if (loading) return null;

    return (
        <>
            <header className={`header-site ${sticky ? "sticky" : "non-sticky"}`}>
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
                        {/* Показываем профиль, если пользователь авторизован */}
                        {user && (
                            <a href="/profile" onClick={() => navigate("/profile")}>
                                Профиль
                            </a>
                        )}

                        <a href="#login" onClick={user ? handleLogout : () => navigate("/login")}>
                            {user ? "Выйти" : "Вход"}
                        </a>
                        <a href="#cart" onClick={goToCart}>Корзина</a>
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
                    {user && (
                        <a href="/profile" onClick={() => navigate("/profile")}>
                            Профиль
                        </a>
                    )}
                    <a href="#login" onClick={user ? handleLogout : () => navigate("/login")}>
                        {user ? "Выйти" : "Вход"}
                    </a>
                    <a href="#cart" onClick={toggleMenu}>Корзина</a>
                    <a href="#contact" onClick={toggleMenu}>Адреса</a>
                    <a href="#catalog" onClick={toggleMenu}>Каталог</a>
                </nav>

                <div className="mobile-menu-footer">
                    <p>© 2025 Golfen Leaf</p>
                </div>
            </aside>
        </>
    );
};

export default Header;
