import Rating from "../Rating/Rating";
import type { Review } from "../../types/Review";
import "./ReviewItem.scss";

type ReviewItemProps = {
  review: Review;
};

const ReviewItem = ({ review }: ReviewItemProps) => {
  const { author, avatar, rating, createdAt, advantages, disadvantages, text } =
    review;

  return (
    <div className="review-item card mb-3">
      <div className="card-body">
        <div className="d-flex justify-content-between align-items-start mb-2">
          <div className="d-flex align-items-center gap-2">
            <img src={avatar} alt={author} className="review-item__avatar" />
            <strong>{author}</strong>
          </div>

          <div className="text-end">
            <Rating value={rating} showValue={false} />
            <small className="text-muted d-block">
              {new Date(createdAt).toLocaleDateString()}
            </small>
          </div>
        </div>

        <p className="review-item__text">{text}</p>

        <div className="review-item__pros-cons">
          <p className="mb-1">
            <strong>Advantages:</strong> {advantages}
          </p>
          <p className="mb-0">
            <strong>Disadvantages:</strong> {disadvantages}
          </p>
        </div>
      </div>
    </div>
  );
};

export default ReviewItem;
