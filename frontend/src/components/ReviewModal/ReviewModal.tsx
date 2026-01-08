// import type { FC } from "react";
// import { useEffect, useState } from "react";
// import Rating from "../Rating/Rating";
// import "./ReviewModal.scss";
//
// type ReviewModalProps = {
//   isOpen: boolean;
//   onClose: () => void;
// };
//
// const ReviewModal: FC<ReviewModalProps> = ({
//   isOpen,
//   onClose,
// }) => {
//   const [show, setShow] = useState(false);
//   const [shouldRender, setShouldRender] = useState(isOpen);
//   const [rating, setRating] = useState(5);
//   const [advantages, setAdvantages] = useState("");
//   const [disadvantages, setDisadvantages] = useState("");
//   const [text, setText] = useState("");
//
//   useEffect(() => {
//     if (isOpen) {
//       setShouldRender(true);
//       setTimeout(() => setShow(true), 10);
//       document.body.style.overflow = "hidden";
//     } else {
//       setShow(false);
//       document.body.style.overflow = "";
//
//       const timer = setTimeout(() => setShouldRender(false), 300);
//       return () => clearTimeout(timer);
//     }
//   }, [isOpen]);
//
//   if (!shouldRender) return null;
//
//   const handleSubmit = (e: React.FormEvent) => {
//     e.preventDefault();
//
//     const reviewData = {
//       rating,
//       createdAt: new Date().toISOString(),
//       advantages,
//       disadvantages,
//       text,
//     };
//
//     console.log("Review submitted:", reviewData);
//
//     onClose();
//
//     setAdvantages("");
//     setDisadvantages("");
//     setText("");
//   };
//
//   return (
//     <div className={`review-modal ${show ? "review-modal--show" : ""}`}>
//       <div className="review-modal__backdrop" onClick={onClose}></div>
//
//       <div className="review-modal__content">
//         <h4>Add your review: </h4>
//         <div className="mb-4 text-center d-flex justify-content-center">
//           <Rating
//             value={rating}
//             interactive
//             showValue={false}
//             onChange={(newRating) => setRating(newRating)}
//           />
//         </div>
//
//         <form onSubmit={handleSubmit}>
//           <div className="mb-3">
//             <label className="form-label">
//               <strong>Advantages</strong>
//             </label>
//             <input
//               type="text"
//               className="form-control"
//               value={advantages}
//               onChange={(e) => setAdvantages(e.target.value)}
//               placeholder="What did you like?"
//             />
//           </div>
//
//           <div className="mb-3">
//             <label className="form-label">
//               <strong>Disadvantages</strong>
//             </label>
//             <input
//               type="text"
//               className="form-control"
//               value={disadvantages}
//               onChange={(e) => setDisadvantages(e.target.value)}
//               placeholder="What didn’t you like?"
//             />
//           </div>
//
//           <div className="mb-3">
//             <label className="form-label">
//               <strong>Review</strong>
//             </label>
//             <textarea
//               className="form-control"
//               rows={4}
//               value={text}
//               onChange={(e) => setText(e.target.value)}
//               placeholder="Write your review here..."
//             />
//           </div>
//
//           <div className="d-flex justify-content-end gap-2">
//             <button
//               type="button"
//               className="review-modal__btn btn-cancel"
//               onClick={onClose}
//             >
//               Cancel
//             </button>
//             <button type="submit" className="review-modal__btn btn-submit">
//               Submit
//             </button>
//           </div>
//         </form>
//       </div>
//     </div>
//   );
// };
//
// export default ReviewModal;

//
//
// import type { FC } from "react";
// import { useEffect, useState } from "react";
// import axios from "axios";
// import Rating from "../Rating/Rating";
// import { useAuth } from "../../context/AuthContext";
// import "./ReviewModal.scss";
//
// const API_BASE = "http://localhost:8080/api";
//
// type ReviewModalProps = {
//     isOpen: boolean;
//     onClose: () => void;
//     productId: number;
//     onReviewAdded: (newReview: any) => void; // вызывается после успешной отправки
// };
//
// const ReviewModal: FC<ReviewModalProps> = ({
//                                                isOpen,
//                                                onClose,
//                                                productId,
//                                                onReviewAdded,
//                                            }) => {
//     const [show, setShow] = useState(false);
//     const [shouldRender, setShouldRender] = useState(isOpen);
//     const [rating, setRating] = useState(5);
//     const [advantages, setAdvantages] = useState("");
//     const [disadvantages, setDisadvantages] = useState("");
//     const [text, setText] = useState("");
//     const [isSubmitting, setIsSubmitting] = useState(false);
//     const [error, setError] = useState<string | null>(null);
//
//     const { user } = useAuth(); // получаем авторизованного пользователя
//
//     useEffect(() => {
//         if (isOpen) {
//             setShouldRender(true);
//             setTimeout(() => setShow(true), 10);
//             document.body.style.overflow = "hidden";
//         } else {
//             setShow(false);
//             document.body.style.overflow = "";
//             const timer = setTimeout(() => setShouldRender(false), 300);
//             return () => clearTimeout(timer);
//         }
//     }, [isOpen]);
//
//     const resetForm = () => {
//         setRating(5);
//         setAdvantages("");
//         setDisadvantages("");
//         setText("");
//         setError(null);
//     };
//
//     const handleSubmit = async (e: React.FormEvent) => {
//         e.preventDefault();
//
//         if (!user) {
//             setError("Вы должны быть авторизованы");
//             return;
//         }
//
//         if (!text.trim()) {
//             setError("Пожалуйста, напишите текст отзыва");
//             return;
//         }
//
//         setIsSubmitting(true);
//         setError(null);
//
//         try {
//             const response = await axios.post(`${API_BASE}/reviews`, {
//                 productId,
//                 rating,
//                 content: text.trim(),
//                 advantages: advantages.trim() || null,
//                 disadvantages: disadvantages.trim() || null,
//             });
//
//             // Предполагаем, что бэкенд возвращает созданный отзыв
//             const newReview = response.data;
//
//             // Оптимистично добавляем в список на фронтенде
//             onReviewAdded({
//                 ...newReview,
//                 authorName: user.username || user.name || "Аноним",
//                 createdAt: new Date().toISOString(),
//             });
//
//             onClose();
//             resetForm();
//         } catch (err: any) {
//             console.error("Ошибка при отправке отзыва:", err);
//             setError(
//                 err.response?.data?.message ||
//                 "Не удалось отправить отзыв. Попробуйте позже."
//             );
//         } finally {
//             setIsSubmitting(false);
//         }
//     };
//
//     if (!shouldRender) return null;
//
//     return (
//         <div className={`review-modal ${show ? "review-modal--show" : ""}`}>
//             <div className="review-modal__backdrop" onClick={onClose} />
//
//             <div className="review-modal__content">
//                 <h4>Оставить отзыв</h4>
//
//                 <div className="mb-4 text-center d-flex justify-content-center">
//                     <Rating
//                         value={rating}
//                         interactive
//                         showValue={false}
//                         onChange={(newRating) => setRating(newRating)}
//                     />
//                 </div>
//
//                 {error && <div className="alert alert-danger mb-3">{error}</div>}
//
//                 <form onSubmit={handleSubmit}>
//                     <div className="mb-3">
//                         <label className="form-label">
//                             <strong>Достоинства</strong>
//                         </label>
//                         <input
//                             type="text"
//                             className="form-control"
//                             value={advantages}
//                             onChange={(e) => setAdvantages(e.target.value)}
//                             placeholder="Что вам понравилось?"
//                             disabled={isSubmitting}
//                         />
//                     </div>
//
//                     <div className="mb-3">
//                         <label className="form-label">
//                             <strong>Недостатки</strong>
//                         </label>
//                         <input
//                             type="text"
//                             className="form-control"
//                             value={disadvantages}
//                             onChange={(e) => setDisadvantages(e.target.value)}
//                             placeholder="Что можно улучшить?"
//                             disabled={isSubmitting}
//                         />
//                     </div>
//
//                     <div className="mb-4">
//                         <label className="form-label">
//                             <strong>Ваш отзыв</strong>
//                         </label>
//                         <textarea
//                             className="form-control"
//                             rows={5}
//                             value={text}
//                             onChange={(e) => setText(e.target.value)}
//                             placeholder="Поделитесь впечатлениями..."
//                             required
//                             disabled={isSubmitting}
//                         />
//                     </div>
//
//                     <div className="d-flex justify-content-end gap-2">
//                         <button
//                             type="button"
//                             className="btn btn-secondary"
//                             onClick={onClose}
//                             disabled={isSubmitting}
//                         >
//                             Отмена
//                         </button>
//                         <button
//                             type="submit"
//                             className="btn btn-primary"
//                             disabled={isSubmitting}
//                         >
//                             {isSubmitting ? "Отправка..." : "Отправить отзыв"}
//                         </button>
//                     </div>
//                 </form>
//             </div>
//         </div>
//     );
// };
//
// export default ReviewModal;

import type { FC } from "react";
import { useEffect, useState } from "react";
import axios from "axios";
import Rating from "../Rating/Rating";
import { useAuth } from "../../context/AuthContext";
import "./ReviewModal.scss";

const API_BASE = "http://localhost:8080/api";

type ReviewModalProps = {
    isOpen: boolean;
    onClose: () => void;
    productId: number;
    onReviewAdded: (newReview: ReviewDto) => void;
};

export type ReviewDto = {
    id: number;
    authorId: number;
    authorName: string;
    content: string;
    rating: number;
    productId: number;
};

const ReviewModal: FC<ReviewModalProps> = ({
                                               isOpen,
                                               onClose,
                                               productId,
                                               onReviewAdded,
                                           }) => {
    const [show, setShow] = useState(false);
    const [shouldRender, setShouldRender] = useState(isOpen);
    const [rating, setRating] = useState(5);
    const [text, setText] = useState("");
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const { user } = useAuth();

    useEffect(() => {
        if (isOpen) {
            setShouldRender(true);
            setTimeout(() => setShow(true), 10);
            document.body.style.overflow = "hidden";
        } else {
            setShow(false);
            document.body.style.overflow = "";
            const timer = setTimeout(() => setShouldRender(false), 300);
            return () => clearTimeout(timer);
        }
    }, [isOpen]);

    const resetForm = () => {
        setRating(5);
        setText("");
        setError(null);
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        if (!user) {
            setError("Необходимо авторизоваться");
            return;
        }

        if (!text.trim()) {
            setError("Напишите ваш отзыв");
            return;
        }

        setIsSubmitting(true);
        setError(null);

        try {
            const response = await axios.post<ReviewDto>(
                `${API_BASE}/reviews`,
                {
                    productId,
                    authorId: user.id,
                    content: text.trim(),
                    rating,
                },
                { withCredentials: true }
            );

            const savedReview = response.data;

            // Добавляем отзыв в список — бэкенд уже возвращает authorName
            onReviewAdded(savedReview);

            onClose();
            resetForm();
        } catch (err: any) {
            console.error("Ошибка отправки отзыва:", err);
            setError("Не удалось отправить отзыв");
        } finally {
            setIsSubmitting(false);
        }
    };

    if (!shouldRender) return null;

    return (
        <div className={`review-modal ${show ? "review-modal--show" : ""}`}>
            <div className="review-modal__backdrop" onClick={onClose} />

            <div className="review-modal__content">
                <h4>Оставить отзыв</h4>

                <div className="mb-4 text-center">
                    <Rating
                        value={rating}
                        interactive
                        onChange={setRating}
                    />
                </div>

                {error && <div className="alert alert-danger mb-3">{error}</div>}

                <form onSubmit={handleSubmit}>
                    <div className="mb-4">
                        <label className="form-label">
                            <strong>Ваш отзыв</strong>
                        </label>
                        <textarea
                            className="form-control"
                            rows={6}
                            value={text}
                            onChange={(e) => setText(e.target.value)}
                            placeholder="Поделитесь впечатлениями..."
                            required
                            disabled={isSubmitting}
                        />
                    </div>

                    <div className="d-flex justify-content-end gap-2">
                        <button
                            type="button"
                            className="btn btn-secondary"
                            onClick={onClose}
                            disabled={isSubmitting}
                        >
                            Отмена
                        </button>
                        <button
                            type="submit"
                            className="btn btn-primary"
                            disabled={isSubmitting}
                        >
                            {isSubmitting ? "Отправка..." : "Отправить"}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default ReviewModal;