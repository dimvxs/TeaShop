// src/components/ProductCard.jsx
import "./ProductCard.scss";

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
  return (
    <article className="product-card">
      <a href={`/product/${id}`} className="product-link">
        {/* Изображение + бейджик скидки */}
        <div className="product-image-wrapper">
          <img src={image} alt={title} className="product-image" />

          <button className="favorite-btn" aria-label="В избранное">
            ♥
          </button>
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
          <span className="reviews-count">{reviews}</span>
        </div>
      </a>

      {/* Кнопка "В корзину" появляется при ховере */}
      <button className="add-to-cart-btn">В корзину</button>
    </article>
  );
};

export default ProductCard;
