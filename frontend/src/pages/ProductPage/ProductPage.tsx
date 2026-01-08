// import { useState } from "react";
// import "./ProductPage.scss";
// import QuantityControl from "../../components/QuantityControl/QuantityControl";
// import ProductImages from "../../components/ProductImages/ProductImages";
// import BodySection from "../../components/BodySection/BodySection";
// import Rating from "../../components/Rating/Rating";
// import ReviewSection from "../../components/ReviewSection/ReviewSection";
//
// function ProductPage() {
//   const product = {
//     name: "Green Tea",
//     rating: 4,
//     description:
//       "Smooth, aromatic, and refreshing tea made from carefully selected leaves. Perfect for mornings or evenings.",
//     price: 12.99,
//     meta: {
//       type: "Green Tea",
//       weight: "100g",
//       origin: "Japan",
//     },
//     images: [
//       "/src/assets/img/prod1.jpg",
//       "/src/assets/img/prod2.jpg",
//       "/src/assets/img/prod3.jpg",
//     ],
//     perks: ["Free shipping over $30", "30-day return policy"],
//   };
//
//   const baseReviews = [
//     {
//       author: "John",
//       avatar: "/src/assets/img/prod1.jpg",
//       rating: 5,
//       createdAt: "2026-01-02T10:30:00Z",
//       advantages: "Great quality, fast delivery",
//       disadvantages: "None",
//       text: "Great product, very high quality!",
//     },
//     {
//       author: "Jane",
//       avatar: "/src/assets/img/prod1.jpg",
//       rating: 4,
//       createdAt: "2026-01-01T18:15:00Z",
//       advantages: "Nice taste",
//       disadvantages: "A bit expensive",
//       text: "Nice taste, but a bit expensive.",
//     },
//   ];
//
//   const reviews: typeof baseReviews = Array.from({ length: 5 }).flatMap(() =>
//     baseReviews.map((r) => ({ ...r }))
//   );
//
//   const [mainImage, setMainImage] = useState(product.images[0]);
//   const [qty, setQty] = useState(1);
//
//   return (
//     <div>
//       <BodySection>
//         <div className="product-page">
//           <div className="product-container">
//             <div className="row g-0">
//               <div className="col-md-6 left-side">
//                 <ProductImages
//                   images={product.images}
//                   mainImage={mainImage}
//                   setMainImage={setMainImage}
//                 />
//               </div>
//
//               <div className="col-md-6 right-side">
//                 <div className="product-header">
//                   <h2 className="product-name">{product.name}</h2>
//                   <Rating value={product.rating} />
//                 </div>
//
//                 <p className="product-desc">{product.description}</p>
//
//                 <div className="product-meta">
//                   <div className="meta-row">
//                     <span>Type</span>
//                     <span>{product.meta.type}</span>
//                   </div>
//                   <div className="meta-row">
//                     <span>Weight</span>
//                     <span>{product.meta.weight}</span>
//                   </div>
//                   <div className="meta-row">
//                     <span>Origin</span>
//                     <span>{product.meta.origin}</span>
//                   </div>
//                 </div>
//
//                 <div className="buy-section">
//                   <div className="price">${product.price.toFixed(2)}</div>
//
//                   <div className="actions">
//                     <div className="actions-top">
//                       <QuantityControl value={qty} onChange={setQty} />
//                       <button className="btn btn-primary add-cart">
//                         Add to Cart
//                       </button>
//                     </div>
//
//                     <button className="btn btn-outline-primary buy-now">
//                       Buy Now
//                     </button>
//                   </div>
//                 </div>
//
//                 <div className="extra-info">
//                   {product.perks.map((perk, i) => (
//                     <p key={i}>✔ {perk}</p>
//                   ))}
//                 </div>
//               </div>
//             </div>
//           </div>
//         </div>
//         <ReviewSection reviews={reviews} />
//       </BodySection>
//     </div>
//   );
// }
//
// export default ProductPage;
import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import "./ProductPage.scss";

import QuantityControl from "../../components/QuantityControl/QuantityControl";
import ProductImages from "../../components/ProductImages/ProductImages";
import BodySection from "../../components/BodySection/BodySection";
import Rating from "../../components/Rating/Rating";
import ReviewSection from "../../components/ReviewSection/ReviewSection";

import { useCart } from "../../context/CartContext";
import { useAuth } from "../../context/AuthContext";

const API_BASE = "http://localhost:8080/api";
const BACKEND_URL = "http://localhost:8080";

interface Product {
    id: number;
    name: string;
    brand: string;
    price: number;
    description?: string;
    imageUrls: string[];
    rating?: number;
    meta?: {
        type?: string;
        weight?: string;
        origin?: string;
    };
}

const ProductPage: React.FC = () => {
    const { id } = useParams<{ id: string }>();

    const { addToCart } = useCart();
    const { isAuthenticated } = useAuth();

    const [product, setProduct] = useState<Product | null>(null);
    const [mainImage, setMainImage] = useState("");
    const [qty, setQty] = useState(1);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (!id) {
            setError("ID товара не указан");
            setLoading(false);
            return;
        }

        const fetchProduct = async () => {
            try {
                const res = await axios.get(`${API_BASE}/products/${id}`);
                const data = res.data;

                const mappedProduct: Product = {
                    id: data.id,
                    name: data.name,
                    brand: data.brand,
                    price: data.price,
                    description:
                        data.description ||
                        "Прекрасный чай высшего качества. Ароматный и насыщенный.",
                    imageUrls: data.imageUrls || [],
                    rating: data.rating || 4.8,
                    meta: {
                        type: data.categories?.[0]?.name || "Чай",
                        weight: "100г",
                        origin: "Япония",
                    },
                };

                setProduct(mappedProduct);

                if (mappedProduct.imageUrls.length > 0) {
                    setMainImage(`${BACKEND_URL}${mappedProduct.imageUrls[0]}`);
                }
            } catch (e) {
                console.error(e);
                setError("Товар не найден или ошибка загрузки");
            } finally {
                setLoading(false);
            }
        };

        fetchProduct();
    }, [id]);

    if (loading) return <div className="loading">Загрузка товара...</div>;
    if (error || !product)
        return <div className="error">{error || "Товар не найден"}</div>;

    return (
        <BodySection>
            <div className="product-page">
                <div className="product-container">
                    <div className="row g-0">
                        <div className="col-md-6 left-side">
                            <ProductImages
                                images={product.imageUrls.map(
                                    (url) => `${BACKEND_URL}${url}`
                                )}
                                mainImage={mainImage}
                                setMainImage={setMainImage}
                            />
                        </div>

                        <div className="col-md-6 right-side">
                            <div className="product-header">
                                <h2 className="product-name">
                                    {product.brand} {product.name}
                                </h2>
                                <Rating value={product.rating || 4.8} />
                            </div>

                            <p className="product-desc">{product.description}</p>

                            <div className="product-meta">
                                <div className="meta-row">
                                    <span>Тип</span>
                                    <span>{product.meta?.type}</span>
                                </div>
                                <div className="meta-row">
                                    <span>Вес</span>
                                    <span>{product.meta?.weight}</span>
                                </div>
                                <div className="meta-row">
                                    <span>Происхождение</span>
                                    <span>{product.meta?.origin}</span>
                                </div>
                            </div>

                            <div className="buy-section">
                                <div className="price">{product.price.toLocaleString('ru-RU')} ₽</div>
                                <div className="actions">
                                    <div className="actions-top">
                                        <QuantityControl value={qty} onChange={setQty} />
                                        <button
                                            className="btn btn-primary add-cart"
                                            onClick={() => addToCart(product.id, qty)}
                                        >
                                            В корзину
                                        </button>

                                    </div>
                                    <button className="btn btn-outline-primary buy-now">Купить сразу</button>
                                </div>
                            </div>


                            {!isAuthenticated && (
                                <p className="text-muted mt-2">
                                    Войдите, чтобы добавить товар в корзину
                                </p>
                            )}

                            <div className="extra-info">
                                <p>✔ Бесплатная доставка от 3000 ₽</p>
                                <p>✔ Возврат в течение 30 дней</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <ReviewSection productId={product.id} />
        </BodySection>
    );
};

export default ProductPage;
