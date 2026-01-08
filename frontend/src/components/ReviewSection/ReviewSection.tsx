// import { useState } from "react";
// import ReviewItem from "../ReviewItem/ReviewItem";
// import ReviewModal from "../ReviewModal/ReviewModal";
// import type { Review } from "../../types/Review";
// import "./ReviewSection.scss";
//
// type ReviewSectionProps = {
//   reviews: Review[];
// };
//
// const ReviewSection = ({ reviews }: ReviewSectionProps) => {
//   const INITIAL_VISIBLE = 5;
//   const LOAD_COUNT = 5;
//
//   const [visibleCount, setVisibleCount] = useState(INITIAL_VISIBLE);
//   const [isModalOpen, setIsModalOpen] = useState(false);
//
//   const handleButtonClick = () => {
//     if (visibleCount >= reviews.length) {
//       setVisibleCount(INITIAL_VISIBLE);
//     } else {
//       setVisibleCount((prev) => Math.min(prev + LOAD_COUNT, reviews.length));
//     }
//   };
//
//   const buttonLabel =
//     visibleCount >= reviews.length ? "Hide reviews" : "Load more reviews";
//
//   return (
//     <div className="review-section">
//       <div className="review-section__header d-flex justify-content-between align-items-center mb-3">
//         <h3 className="mb-0">
//           Reviews{" "}
//           <span className="review-section__count">({reviews.length})</span>
//         </h3>
//
//         <button
//           type="button"
//           className="review-section__add-btn"
//           onClick={() => setIsModalOpen(true)}
//         >
//           Add review
//         </button>
//       </div>
//
//       {reviews.length === 0 && <p>No reviews yet!</p>}
//
//       {reviews.slice(0, visibleCount).map((review, index) => (
//         <ReviewItem key={index} review={review} />
//       ))}
//
//       {reviews.length > INITIAL_VISIBLE && (
//         <div className="text-center mt-3">
//           <button
//             type="button"
//             className="review-section__add-btn"
//             onClick={handleButtonClick}
//           >
//             {buttonLabel}
//           </button>
//         </div>
//       )}
//       <ReviewModal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)} />
//     </div>
//   );
// };
//
// export default ReviewSection;



//
// import React, { useState, useEffect } from "react";
// import axios from "axios";
// import ReviewItem from "../ReviewItem/ReviewItem";
// import ReviewModal from "../ReviewModal/ReviewModal"; // или ReviewForm
// import { useAuth } from "../../context/AuthContext";
// import "./ReviewSection.scss";
//
// const API_BASE = "http://localhost:8080/api";
//
// type Review = {
//     id: number;
//     authorName: string;
//     content: string;
//     rating: number;
//     createdAt?: string;
//     advantages?: string;
//     disadvantages?: string;
// };
//
// interface ReviewSectionProps {
//     productId: number;
// }
//
// const ReviewSection: React.FC<ReviewSectionProps> = ({ productId }) => {
//     const INITIAL_VISIBLE = 5;
//     const LOAD_COUNT = 5;
//
//     const [reviews, setReviews] = useState<Review[]>([]);
//     const [visibleCount, setVisibleCount] = useState(INITIAL_VISIBLE);
//     const [isModalOpen, setIsModalOpen] = useState(false);
//     const [loading, setLoading] = useState(true);
//
//     const { user } = useAuth(); // авторизованный пользователь
//
//     // Загрузка отзывов
//     useEffect(() => {
//         const fetchReviews = async () => {
//             try {
//                 const res = await axios.get<Review[]>(`${API_BASE}/reviews/product/${productId}`);
//                 setReviews(res.data || []);
//             } catch (err) {
//                 console.error("Ошибка загрузки отзывов:", err);
//                 setReviews([]);
//             } finally {
//                 setLoading(false);
//             }
//         };
//
//         fetchReviews();
//     }, [productId]);
//
//     // Обработчик добавления нового отзыва
//     const handleReviewAdded = (newReview: Review) => {
//         setReviews((prev) => [newReview, ...prev]); // новый отзыв сверху
//         setIsModalOpen(false);
//         // Если было скрыто — показываем больше
//         setVisibleCount((prev) => Math.min(prev + 1, reviews.length + 1));
//     };
//
//     const handleLoadMore = () => {
//         if (visibleCount >= reviews.length) {
//             setVisibleCount(INITIAL_VISIBLE); // свернуть
//         } else {
//             setVisibleCount((prev) => Math.min(prev + LOAD_COUNT, reviews.length));
//         }
//     };
//
//     const buttonLabel =
//         visibleCount >= reviews.length ? "Скрыть отзывы" : "Показать ещё";
//
//     if (loading) {
//         return <div className="review-section">Загрузка отзывов...</div>;
//     }
//
//     return (
//         <div className="review-section">
//             <div className="review-section__header d-flex justify-content-between align-items-center mb-3">
//                 <h3 className="mb-0">
//                     Отзывы{" "}
//                     <span className="review-section__count">({reviews.length})</span>
//                 </h3>
//
//                 {user && (
//                     <button
//                         type="button"
//                         className="review-section__add-btn"
//                         onClick={() => setIsModalOpen(true)}
//                     >
//                         Добавить отзыв
//                     </button>
//                 )}
//             </div>
//
//             {reviews.length === 0 && <p>Пока нет отзывов. Будьте первым!</p>}
//
//             <div className="reviews-list">
//                 {reviews.slice(0, visibleCount).map((review, index) => (
//                     <ReviewItem key={review.id} review={review} />
//                 ))}
//             </div>
//
//             {reviews.length > INITIAL_VISIBLE && (
//                 <div className="text-center mt-4">
//                     <button
//                         type="button"
//                         className="review-section__load-more-btn"
//                         onClick={handleLoadMore}
//                     >
//                         {buttonLabel}
//                     </button>
//                 </div>
//             )}
//
//             {/* Модалка для добавления отзыва */}
//             <ReviewModal
//                 isOpen={isModalOpen}
//                 onClose={() => setIsModalOpen(false)}
//                 productId={productId}
//                 onReviewAdded={handleReviewAdded}
//             />
//         </div>
//     );
// };
//
// export default ReviewSection;

import React, { useState, useEffect } from "react";
import axios from "axios";
import ReviewModal from "../ReviewModal/ReviewModal";
import { useAuth } from "../../context/AuthContext";
import "./ReviewSection.scss";
import Rating from "../Rating/Rating.tsx"

const API_BASE = "http://localhost:8080/api";

export type ReviewDto = {
    id: number;
    authorId: number;
    authorName: string;
    content: string;
    rating: number;
    productId: number;
};

interface ReviewSectionProps {
    productId: number;
}

const ReviewSection: React.FC<ReviewSectionProps> = ({ productId }) => {
    const INITIAL_VISIBLE = 5;

    const [reviews, setReviews] = useState<ReviewDto[]>([]);
    const [visibleCount, setVisibleCount] = useState(INITIAL_VISIBLE);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [loading, setLoading] = useState(true);

    const { user } = useAuth();

    useEffect(() => {
        const fetchReviews = async () => {
            try {
                const res = await axios.get<ReviewDto[]>(
                    `${API_BASE}/reviews/product/${productId}`
                );
                setReviews(res.data || []);
            } catch (err) {
                console.error("Ошибка загрузки отзывов:", err);
                setReviews([]);
            } finally {
                setLoading(false);
            }
        };

        fetchReviews();
    }, [productId]);

    const handleReviewAdded = (newReview: ReviewDto) => {
        setReviews((prev) => [newReview, ...prev]);
    };

    const handleLoadMore = () => {
        setVisibleCount((prev) =>
            prev >= reviews.length ? INITIAL_VISIBLE : Math.min(prev + 5, reviews.length)
        );
    };

    if (loading) {
        return <div className="review-section">Загрузка отзывов...</div>;
    }

    return (
        <section className="review-section mt-5">
            <div className="d-flex justify-content-between align-items-center mb-4">
                <h3>
                    Отзывы <span className="text-muted">({reviews.length})</span>
                </h3>

                {user && (
                    <button
                        className="btn btn-outline-primary"
                        onClick={() => setIsModalOpen(true)}
                    >
                        Добавить отзыв
                    </button>
                )}
            </div>

            {reviews.length === 0 ? (
                <p className="text-center text-muted">Пока нет отзывов. Будьте первым!</p>
            ) : (
                <>
                    <div className="reviews-list">
                        {reviews.slice(0, visibleCount).map((review) => (
                            <div key={review.id} className="review-card p-4 border rounded mb-3 bg-white">
                                <div className="d-flex justify-content-between mb-2">
                                    <strong>{review.authorName}</strong>
                                </div>

                                <div className="mb-2">
                                    <Rating value={review.rating} />
                                </div>

                                <p className="mb-0">{review.content}</p>
                            </div>
                        ))}
                    </div>

                    {reviews.length > INITIAL_VISIBLE && (
                        <div className="text-center mt-4">
                            <button className="btn btn-link" onClick={handleLoadMore}>
                                {visibleCount >= reviews.length ? "Скрыть" : "Показать ещё"}
                            </button>
                        </div>
                    )}
                </>
            )}

            <ReviewModal
                isOpen={isModalOpen}
                onClose={() => setIsModalOpen(false)}
                productId={productId}
                onReviewAdded={handleReviewAdded}
            />
        </section>
    );
};

export default ReviewSection;