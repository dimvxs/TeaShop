import React, { useState } from "react";
import axios from "axios";
import type { User } from "../../context/AuthContext"; // путь поправь, если другой

const API_BASE = "http://localhost:8080/api";

export type ReviewDto = {
    id: number;
    authorId: number;
    authorName: string;
    content: string;
    rating: number;
    productId: number;
};

interface ReviewFormProps {
    productId: number;
    user?: User; // ✅ теперь это объект, где есть id/login
    onAdd?: (review: ReviewDto) => void;
}

const ReviewForm: React.FC<ReviewFormProps> = ({ productId, user, onAdd }) => {
    const [content, setContent] = useState("");
    const [rating, setRating] = useState(5);
    const [message, setMessage] = useState("");

    const handleSubmit = async () => {
        if (!user) {
            setMessage("Только авторизованные пользователи могут оставить отзыв");
            return;
        }
        if (!content.trim()) {
            setMessage("Введите текст отзыва");
            return;
        }

        try {
            // ✅ ОДИН POST, и он отправляет authorId + productId
            const res = await axios.post<ReviewDto>(
                `${API_BASE}/reviews`,
                {
                    authorId: user.id,
                    content: content.trim(),
                    rating,
                    productId,
                },
                { withCredentials: true }
            );

            onAdd?.(res.data); // ✅ добавляем на страницу сразу

            setContent("");
            setRating(5);
            setMessage("Отзыв добавлен");
        } catch (err: any) {
            setMessage("Ошибка: " + (err.response?.data?.message || err.message));
        }
    };

    return (
        <div className="review-form">
            <h3>Оставьте отзыв</h3>

            {user ? (
                <>
          <textarea
              value={content}
              onChange={(e) => setContent(e.target.value)}
              placeholder="Ваш отзыв"
          />
                    <select value={rating} onChange={(e) => setRating(Number(e.target.value))}>
                        {[5, 4, 3, 2, 1].map((n) => (
                            <option key={n} value={n}>
                                {n}
                            </option>
                        ))}
                    </select>

                    <button onClick={handleSubmit}>Отправить</button>
                    {message && <p>{message}</p>}
                </>
            ) : (
                <p>Войдите, чтобы оставить отзыв</p>
            )}
        </div>
    );
};

export default ReviewForm;
