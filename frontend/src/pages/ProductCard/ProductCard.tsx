import "./ProductCard.scss";
import { useCart } from "../../context/CartContext";

interface ProductCardProps {
    id: number | string;
    image: string;
    title: string;
    price: number;
    rating?: number;
    reviews?: number;
}

const ProductCard: React.FC<ProductCardProps> = ({
                                                     id,
                                                     image,
                                                     title,
                                                     price,
                                                     rating = 4.8,
                                                     reviews = 124,
                                                 }) => {
    const { addToCart } = useCart();

    const handleAddToCart = (e: React.MouseEvent) => {
        e.preventDefault();    // не переходим по ссылке
        e.stopPropagation();  // не срабатывает клик по карточке
        addToCart(Number(id)); // id может быть string, приводим к number
    };

    return (
        <article className="product-card">
            <a href={`/product/${id}`} className="product-link">
                {/* Изображение */}
                <div className="product-image-wrapper">
                    <img src={image} alt={title} className="product-image" />
                </div>

                {/* Цена */}
                <div className="product-price">
                    <span className="current-price">{price} ₽</span>
                </div>

                {/* Название */}
                <h3 className="product-title">{title}</h3>

                {/* Рейтинг и отзывы */}
                <div className="product-rating">
          <span className="stars">
            {"★".repeat(Math.floor(rating))}
              {"☆".repeat(5 - Math.floor(rating))}
          </span>
                    <span className="reviews-count">({reviews})</span>
                </div>
            </a>

            {/* Кнопка "В корзину" при ховере */}
            <button className="add-to-cart-btn" onClick={handleAddToCart}>
                В корзину
            </button>
        </article>
    );
};

export default ProductCard;