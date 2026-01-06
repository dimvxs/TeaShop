// // import { useState } from "react";
// // import "./ProductPage.scss";
// // import QuantityControl from "../../components/QuantityControl/QuantityControl";
// // import ProductImages from "../../components/ProductImages/ProductImages";
// // import BodySection from "../../components/BodySection/BodySection";
// // import Rating from "../../components/Rating/Rating";
// //
// // function ProductPage() {
// //   const product = {
// //     name: "Green Tea",
// //     rating: 4,
// //     description:
// //       "Smooth, aromatic, and refreshing tea made from carefully selected leaves. Perfect for mornings or evenings.",
// //     price: 12.99,
// //     meta: {
// //       type: "Green Tea",
// //       weight: "100g",
// //       origin: "Japan",
// //     },
// //     images: [
// //       "/src/assets/img/prod1.jpg",
// //       "/src/assets/img/prod2.jpg",
// //       "/src/assets/img/prod3.jpg",
// //     ],
// //     perks: ["Free shipping over $30", "30-day return policy"],
// //   };
// //
// //   const [mainImage, setMainImage] = useState(product.images[0]);
// //   const [qty, setQty] = useState(1);
// //
// //   return (
// //     <div>
// //       <BodySection>
// //         <div className="product-page">
// //           <div className="product-container">
// //             <div className="row g-0">
// //               <div className="col-md-6 left-side">
// //                 <ProductImages
// //                   images={product.images}
// //                   mainImage={mainImage}
// //                   setMainImage={setMainImage}
// //                 />
// //               </div>
// //
// //               <div className="col-md-6 right-side">
// //                 <div className="product-header">
// //                   <h2 className="product-name">{product.name}</h2>
// //                   <Rating value={product.rating} />
// //                 </div>
// //
// //                 <p className="product-desc">{product.description}</p>
// //
// //                 <div className="product-meta">
// //                   <div className="meta-row">
// //                     <span>Type</span>
// //                     <span>{product.meta.type}</span>
// //                   </div>
// //                   <div className="meta-row">
// //                     <span>Weight</span>
// //                     <span>{product.meta.weight}</span>
// //                   </div>
// //                   <div className="meta-row">
// //                     <span>Origin</span>
// //                     <span>{product.meta.origin}</span>
// //                   </div>
// //                 </div>
// //
// //                 <div className="buy-section">
// //                   <div className="price">${product.price.toFixed(2)}</div>
// //
// //                   <div className="actions">
// //                     <div className="actions-top">
// //                       <QuantityControl value={qty} onChange={setQty} />
// //                       <button className="btn btn-primary add-cart">
// //                         Add to Cart
// //                       </button>
// //                     </div>
// //
// //                     <button className="btn btn-outline-primary buy-now">
// //                       Buy Now
// //                     </button>
// //                   </div>
// //                 </div>
// //
// //                 <div className="extra-info">
// //                   {product.perks.map((perk, i) => (
// //                     <p key={i}>✔ {perk}</p>
// //                   ))}
// //                 </div>
// //               </div>
// //             </div>
// //           </div>
// //         </div>
// //       </BodySection>
// //     </div>
// //   );
// // }
// //
// // export default ProductPage;
//
//
// // src/pages/ProductPage/ProductPage.tsx
// import React, { useState, useEffect } from 'react';
// import { useParams } from 'react-router-dom';
// import axios from 'axios';
// import "./ProductPage.scss";
// import QuantityControl from "../../components/QuantityControl/QuantityControl";
// import ProductImages from "../../components/ProductImages/ProductImages";
// import BodySection from "../../components/BodySection/BodySection";
// import Rating from "../../components/Rating/Rating";
//
// const API_BASE = 'http://localhost:8080/api';
// const BACKEND_URL = 'http://localhost:8080'; // для фото
//
// interface Product {
//     id: number;
//     name: string;
//     brand: string;
//     price: number;
//     description?: string; // добавь в модель, если нужно
//     imageUrls: string[];
//     rating?: number;
//     meta?: {
//         type?: string;
//         weight?: string;
//         origin?: string;
//     };
// }
//
// const ProductPage: React.FC = () => {
//     const { id } = useParams<{ id: string }>(); // берём ID из URL
//     const [product, setProduct] = useState<Product | null>(null);
//     const [mainImage, setMainImage] = useState<string>('');
//     const [qty, setQty] = useState(1);
//     const [loading, setLoading] = useState(true);
//     const [error, setError] = useState<string | null>(null);
//
//     useEffect(() => {
//         if (!id) {
//             setError('ID товара не указан');
//             setLoading(false);
//             return;
//         }
//
//         const fetchProduct = async () => {
//             try {
//                 const res = await axios.get(`${API_BASE}/products/${id}`);
//                 const data = res.data;
//
//                 // Подготавливаем данные
//                 setProduct({
//                     id: data.id,
//                     name: data.name,
//                     brand: data.brand,
//                     price: data.price,
//                     description: data.description || 'Прекрасный чай высшего качества. Ароматный и насыщенный.', // заглушка
//                     imageUrls: data.imageUrls || [],
//                     rating: data.rating || 4.8, // можно добавить в модель позже
//                     meta: {
//                         type: data.categories?.[0]?.name || 'Чай', // берём первую категорию
//                         weight: '100г', // пока заглушка, добавь поле в модель
//                         origin: 'Япония', // заглушка
//                     },
//                 });
//
//                 // Устанавливаем главное фото
//                 if (data.imageUrls?.length > 0) {
//                     setMainImage(`${BACKEND_URL}${data.imageUrls[0]}`);
//                 }
//
//                 setLoading(false);
//             } catch (err: any) {
//                 setError('Товар не найден или ошибка загрузки');
//                 setLoading(false);
//                 console.error(err);
//             }
//         };
//
//         fetchProduct();
//     }, [id]);
//
//     if (loading) {
//         return <div className="loading">Загрузка товара...</div>;
//     }
//
//     if (error || !product) {
//         return <div className="error">{error || 'Товар не найден'}</div>;
//     }
//
//     return (
//         <div>
//             <BodySection>
//                 <div className="product-page">
//                     <div className="product-container">
//                         <div className="row g-0">
//                             <div className="col-md-6 left-side">
//                                 <ProductImages
//                                     images={product.imageUrls.map(url => `${BACKEND_URL}${url}`)}
//                                     mainImage={mainImage}
//                                     setMainImage={setMainImage}
//                                 />
//                             </div>
//                             <div className="col-md-6 right-side">
//                                 <div className="product-header">
//                                     <h2 className="product-name">{product.brand} {product.name}</h2>
//                                     <Rating value={product.rating || 4.8} />
//                                 </div>
//                                 <p className="product-desc">{product.description}</p>
//                                 <div className="product-meta">
//                                     <div className="meta-row">
//                                         <span>Тип</span>
//                                         <span>{product.meta?.type}</span>
//                                     </div>
//                                     <div className="meta-row">
//                                         <span>Вес</span>
//                                         <span>{product.meta?.weight}</span>
//                                     </div>
//                                     <div className="meta-row">
//                                         <span>Происхождение</span>
//                                         <span>{product.meta?.origin}</span>
//                                     </div>
//                                 </div>
//                                 <div className="buy-section">
//                                     <div className="price">{product.price.toLocaleString('ru-RU')} ₽</div>
//                                     <div className="actions">
//                                         <div className="actions-top">
//                                             <QuantityControl value={qty} onChange={setQty} />
//                                             <button className="btn btn-primary add-cart">В корзину</button>
//                                         </div>
//                                         <button className="btn btn-outline-primary buy-now">Купить сразу</button>
//                                     </div>
//                                 </div>
//                                 <div className="extra-info">
//                                     {product.perks?.map((perk, i) => (
//                                         <p key={i}>✔ {perk}</p>
//                                     )) || (
//                                         <>
//                                             <p>✔ Бесплатная доставка от 3000 ₽</p>
//                                             <p>✔ Возврат в течение 30 дней</p>
//                                         </>
//                                     )}
//                                 </div>
//                             </div>
//                         </div>
//                     </div>
//                 </div>
//             </BodySection>
//         </div>
//     );
// };
//
// export default ProductPage;



//
//
// // src/pages/ProductPage/ProductPage.tsx
// import React, { useState, useEffect } from 'react';
// import { useParams } from 'react-router-dom';
// import axios from 'axios';
// import "./ProductPage.scss";
// import QuantityControl from "../../components/QuantityControl/QuantityControl";
// import ProductImages from "../../components/ProductImages/ProductImages";
// import BodySection from "../../components/BodySection/BodySection";
// import Rating from "../../components/Rating/Rating";
// import ReviewForm from "../AdminPanel/ReviewForm"; // default import
// import { useAuth } from '../../context/AuthContext';
//
// const API_BASE = 'http://localhost:8080/api';
// const BACKEND_URL = 'http://localhost:8080'; // для фото
//
// interface Review {
//     id: number;
//     user: string;
//     text: string;
//     rating: number;
//     createdAt: string;
// }
//
// interface Product {
//     id: number;
//     name: string;
//     brand: string;
//     price: number;
//     description?: string;
//     imageUrls: string[];
//     rating?: number;
//     perks?: string[];
//     reviews?: Review[];
//     meta?: {
//         type?: string;
//         weight?: string;
//         origin?: string;
//     };
// }
//
// const ProductPage: React.FC = () => {
//     const { id } = useParams<{ id: string }>();
//     const [product, setProduct] = useState<Product | null>(null);
//     const [mainImage, setMainImage] = useState<string>('');
//     const [qty, setQty] = useState(1);
//     const [loading, setLoading] = useState(true);
//     const [error, setError] = useState<string | null>(null);
//     const { currentUser } = useAuth(); // контекст авторизации
//
//     useEffect(() => {
//         if (!id) {
//             setError('ID товара не указан');
//             setLoading(false);
//             return;
//         }
//
//         const fetchProduct = async () => {
//             try {
//                 const res = await axios.get(`${API_BASE}/products/${id}`);
//                 const data = res.data;
//
//                 setProduct({
//                     id: data.id,
//                     name: data.name,
//                     brand: data.brand,
//                     price: data.price,
//                     description: data.description || 'Прекрасный чай высшего качества. Ароматный и насыщенный.',
//                     imageUrls: data.imageUrls || [],
//                     rating: data.rating || 4.8,
//                     perks: data.perks || ['Бесплатная доставка от 3000 ₽', 'Возврат в течение 30 дней'],
//                     reviews: data.reviews || [],
//                     meta: {
//                         type: data.categories?.[0]?.name || 'Чай',
//                         weight: '100г',
//                         origin: 'Япония',
//                     },
//                 });
//
//                 if (data.imageUrls?.length > 0) {
//                     setMainImage(`${BACKEND_URL}${data.imageUrls[0]}`);
//                 }
//
//                 setLoading(false);
//             } catch (err: any) {
//                 setError('Товар не найден или ошибка загрузки');
//                 setLoading(false);
//                 console.error(err);
//             }
//         };
//
//         fetchProduct();
//     }, [id]);
//
//     if (loading) return <div className="loading">Загрузка товара...</div>;
//     if (error || !product) return <div className="error">{error || 'Товар не найден'}</div>;
//
//     return (
//         <div>
//             <BodySection>
//                 <div className="product-page">
//                     <div className="product-container">
//                         <div className="row g-0">
//                             <div className="col-md-6 left-side">
//                                 <ProductImages
//                                     images={product.imageUrls.map(url => `${BACKEND_URL}${url}`)}
//                                     mainImage={mainImage}
//                                     setMainImage={setMainImage}
//                                 />
//                             </div>
//
//                             <div className="col-md-6 right-side">
//                                 <div className="product-header">
//                                     <h2 className="product-name">{product.brand} {product.name}</h2>
//                                     <Rating value={product.rating || 4.8} />
//                                 </div>
//
//                                 <p className="product-desc">{product.description}</p>
//
//                                 <div className="product-meta">
//                                     <div className="meta-row">
//                                         <span>Тип</span>
//                                         <span>{product.meta?.type}</span>
//                                     </div>
//                                     <div className="meta-row">
//                                         <span>Вес</span>
//                                         <span>{product.meta?.weight}</span>
//                                     </div>
//                                     <div className="meta-row">
//                                         <span>Происхождение</span>
//                                         <span>{product.meta?.origin}</span>
//                                     </div>
//                                 </div>
//
//                                 <div className="buy-section">
//                                     <div className="price">{product.price.toLocaleString('ru-RU')} ₽</div>
//                                     <div className="actions">
//                                         <div className="actions-top">
//                                             <QuantityControl value={qty} onChange={setQty} />
//                                             <button className="btn btn-primary add-cart">В корзину</button>
//                                         </div>
//                                         <button className="btn btn-outline-primary buy-now">Купить сразу</button>
//                                     </div>
//                                 </div>
//
//                                 <div className="extra-info">
//                                     {product.perks?.map((perk, i) => (
//                                         <p key={i}>✔ {perk}</p>
//                                     ))}
//                                 </div>
//
//                                 {/* ===================== */}
//                                 {/* Секция отзывов */}
//                                 {/* ===================== */}
//                                 <div className="reviews-section">
//                                     <h3>Отзывы</h3>
//
//                                     {product.reviews && product.reviews.length > 0 ? (
//                                         <ul className="reviews-list">
//                                             {product.reviews.map(r => (
//                                                 <li key={r.id}>
//                                                     <strong>{r.user}</strong> ({new Date(r.createdAt).toLocaleDateString()}):
//                                                     <Rating value={r.rating} />
//                                                     <p>{r.text}</p>
//                                                 </li>
//                                             ))}
//                                         </ul>
//                                     ) : (
//                                         <p>Пока нет отзывов.</p>
//                                     )}
//
//                                     {/* Форма отзыва для авторизованных */}
//                                     {currentUser && (
//                                         <div className="review-form-container">
//                                             <ReviewForm
//                                                 productId={product.id}
//                                                 user={currentUser.username}
//                                                 onAdd={(newReview: Review) => {
//                                                     setProduct(prev => prev ? { ...prev, reviews: [...(prev.reviews || []), newReview] } : prev);
//                                                 }}
//                                             />
//                                         </div>
//                                     )}
//                                 </div>
//                             </div>
//                         </div>
//                     </div>
//                 </div>
//             </BodySection>
//         </div>
//     );
// };
//
// export default ProductPage;



// src/pages/ProductPage/ProductPage.tsx
import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import "./ProductPage.scss";
import QuantityControl from "../../components/QuantityControl/QuantityControl";
import ProductImages from "../../components/ProductImages/ProductImages";
import BodySection from "../../components/BodySection/BodySection";
import Rating from "../../components/Rating/Rating";
import ReviewForm from '../AdminPanel/ReviewForm.tsx';
import { useAuth } from '../../context/AuthContext';

const API_BASE = 'http://localhost:8080/api';
const BACKEND_URL = 'http://localhost:8080'; // для фото

// interface Product {
//     id: number;
//     name: string;
//     brand: string;
//     price: number;
//     description?: string;
//     imageUrls: string[];
//     rating?: number;
//     meta?: {
//         type?: string;
//         weight?: string;
//         origin?: string;
//     };
//     reviews?: { id: number; user: string; comment: string; createdAt: string }[];
// }

interface Product {
    id: number;
    name: string;
    brand: string;
    price: number;
    description?: string;
    imageUrls: string[];
    rating?: number;
    meta?: { type?: string; weight?: string; origin?: string };
    reviews?: ReviewDto[];
}


type ReviewDto = {
    id: number;
    authorId: number;
    authorName: string;
    content: string;
    rating: number;
    productId: number;
};



const ProductPage: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const [product, setProduct] = useState<Product | null>(null);
    const [mainImage, setMainImage] = useState<string>('');
    const [qty, setQty] = useState(1);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    const { user } = useAuth(); // теперь user вместо currentUser

    useEffect(() => {
        if (!id) {
            setError('ID товара не указан');
            setLoading(false);
            return;
        }

        const fetchProduct = async () => {
            try {
                const res = await axios.get(`${API_BASE}/products/${id}`);
                const data = res.data;

                const reviewsRes = await axios.get<ReviewDto[]>(`${API_BASE}/reviews/product/${id}`);
                const reviewsData = reviewsRes.data;


                setProduct({
                    id: data.id,
                    name: data.name,
                    brand: data.brand,
                    price: data.price,
                    description: data.description || 'Прекрасный чай высшего качества. Ароматный и насыщенный.',
                    imageUrls: data.imageUrls || [],
                    rating: data.rating || 4.8,
                    meta: {
                        type: data.categories?.[0]?.name || 'Чай',
                        weight: '100г',
                        origin: 'Япония',
                    },
                    reviews: reviewsData || [],
                });

                if (data.imageUrls?.length > 0) {
                    setMainImage(`${BACKEND_URL}${data.imageUrls[0]}`);
                }

                setLoading(false);
            } catch (err: any) {
                setError('Товар не найден или ошибка загрузки');
                setLoading(false);
                console.error(err);
            }
        };

        fetchProduct();
    }, [id]);

    if (loading) return <div className="loading">Загрузка товара...</div>;
    if (error || !product) return <div className="error">{error || 'Товар не найден'}</div>;

    return (
        <div>
            <BodySection>
                <div className="product-page">
                    <div className="product-container">
                        <div className="row g-0">
                            <div className="col-md-6 left-side">
                                <ProductImages
                                    images={product.imageUrls.map(url => `${BACKEND_URL}${url}`)}
                                    mainImage={mainImage}
                                    setMainImage={setMainImage}
                                />
                            </div>
                            <div className="col-md-6 right-side">
                                <div className="product-header">
                                    <h2 className="product-name">{product.brand} {product.name}</h2>
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
                                            <button className="btn btn-primary add-cart">В корзину</button>
                                        </div>
                                        <button className="btn btn-outline-primary buy-now">Купить сразу</button>
                                    </div>
                                </div>

                                <div className="extra-info">
                                    {product.perks?.map((perk, i) => (
                                        <p key={i}>✔ {perk}</p>
                                    )) || (
                                        <>
                                            <p>✔ Бесплатная доставка от 3000 ₽</p>
                                            <p>✔ Возврат в течение 30 дней</p>
                                        </>
                                    )}
                                </div>

                                {/* Отзывы */}
                                <div className="reviews-section">
                                    <h3>Отзывы</h3>
                                    {product.reviews?.length ? (
                                        product.reviews.map(r => (
                                            <div key={r.id} className="review">
                                                <strong>{r.authorName}</strong>
                                                <Rating value={r.rating} />
                                                <p>{r.content}</p>
                                            </div>
                                        ))
                                    ) : (
                                        <p>Пока нет отзывов</p>
                                    )}


                                    {/* Форма добавления отзыва для авторизованных */}
                                    {user && (
                                        <ReviewForm
                                            productId={product.id}
                                            user={user}
                                            onAdd={(newReview: any) => {
                                                setProduct(prev => prev ? { ...prev, reviews: [...(prev.reviews || []), newReview] } : prev);
                                            }}
                                        />
                                    )}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </BodySection>
        </div>
    );
};

export default ProductPage;
