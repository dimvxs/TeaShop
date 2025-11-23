import logo from '../../../assets/logo.png';
import "./Header.css"
import {useState} from "react";
const Header = () => {

    const [isOpen, setIsOpen] = useState(false);

    const toggleMenu = () => setIsOpen(!isOpen);
    const [search, setSearch] = useState("");

    const handleSearch = (e) => {
        e.preventDefault();
        alert(`Вы ищете: ${search}`);
        setSearch(""); // очищаем поле после поиска
    };

    return (
        <>
        <header className="header-site">
            <div className="header-container">
                <a href="/" className="logo">
                    <img src={logo} alt="Логотип сайта"/>
                </a>

                <div className="hamburger" onClick={toggleMenu}>
                    <span></span>
                    <span></span>
                    <span></span>
                </div>

                <form className="search-form" onSubmit={handleSearch}>
                    <input
                        type="text"
                        placeholder="Найти на Golfen Leaf"
                        value={search}
                        onChange={(e) => setSearch(e.target.value)}
                    />
                    <button type="submit">🔍</button>
                </form>

                <nav className="nav-links">
                    <a href="#home">Home</a>
                    <a href="#about">About</a>
                    <a href="#contact">Contact</a>
                </nav>

            </div>
        </header>

    <div className={`side-menu ${isOpen ? "open" : ""}`}>
        <a href="#home" onClick={toggleMenu}>Home</a>
        <a href="#about" onClick={toggleMenu}>About</a>
        <a href="#contact" onClick={toggleMenu}>Contact</a>
    </div>
    </>
    );
};

export default Header;
