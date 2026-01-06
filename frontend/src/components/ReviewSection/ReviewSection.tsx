import { useState } from "react";
import ReviewItem from "../ReviewItem/ReviewItem";
import ReviewModal from "../ReviewModal/ReviewModal";
import type { Review } from "../../types/Review";
import "./ReviewSection.scss";

type ReviewSectionProps = {
  reviews: Review[];
};

const ReviewSection = ({ reviews }: ReviewSectionProps) => {
  const INITIAL_VISIBLE = 5;
  const LOAD_COUNT = 5;

  const [visibleCount, setVisibleCount] = useState(INITIAL_VISIBLE);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleButtonClick = () => {
    if (visibleCount >= reviews.length) {
      setVisibleCount(INITIAL_VISIBLE);
    } else {
      setVisibleCount((prev) => Math.min(prev + LOAD_COUNT, reviews.length));
    }
  };

  const buttonLabel =
    visibleCount >= reviews.length ? "Hide reviews" : "Load more reviews";

  return (
    <div className="review-section">
      <div className="review-section__header d-flex justify-content-between align-items-center mb-3">
        <h3 className="mb-0">
          Reviews{" "}
          <span className="review-section__count">({reviews.length})</span>
        </h3>

        <button
          type="button"
          className="review-section__add-btn"
          onClick={() => setIsModalOpen(true)}
        >
          Add review
        </button>
      </div>

      {reviews.length === 0 && <p>No reviews yet!</p>}

      {reviews.slice(0, visibleCount).map((review, index) => (
        <ReviewItem key={index} review={review} />
      ))}

      {reviews.length > INITIAL_VISIBLE && (
        <div className="text-center mt-3">
          <button
            type="button"
            className="review-section__add-btn"
            onClick={handleButtonClick}
          >
            {buttonLabel}
          </button>
        </div>
      )}
      <ReviewModal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)} />
    </div>
  );
};

export default ReviewSection;
