import type { FC } from "react";
import { useEffect, useState } from "react";
import Rating from "../Rating/Rating";
import "./ReviewModal.scss";

type ReviewModalProps = {
  isOpen: boolean;
  onClose: () => void;
};

const ReviewModal: FC<ReviewModalProps> = ({
  isOpen,
  onClose,
}) => {
  const [show, setShow] = useState(false);
  const [shouldRender, setShouldRender] = useState(isOpen);
  const [rating, setRating] = useState(5);
  const [advantages, setAdvantages] = useState("");
  const [disadvantages, setDisadvantages] = useState("");
  const [text, setText] = useState("");
  
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

  if (!shouldRender) return null;

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    const reviewData = {
      rating,
      createdAt: new Date().toISOString(),
      advantages,
      disadvantages,
      text,
    };

    console.log("Review submitted:", reviewData);

    onClose();

    setAdvantages("");
    setDisadvantages("");
    setText("");
  };

  return (
    <div className={`review-modal ${show ? "review-modal--show" : ""}`}>
      <div className="review-modal__backdrop" onClick={onClose}></div>

      <div className="review-modal__content">
        <h4>Add your review: </h4>
        <div className="mb-4 text-center d-flex justify-content-center">
          <Rating
            value={rating}
            interactive
            showValue={false}
            onChange={(newRating) => setRating(newRating)}
          />
        </div>

        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label">
              <strong>Advantages</strong>
            </label>
            <input
              type="text"
              className="form-control"
              value={advantages}
              onChange={(e) => setAdvantages(e.target.value)}
              placeholder="What did you like?"
            />
          </div>

          <div className="mb-3">
            <label className="form-label">
              <strong>Disadvantages</strong>
            </label>
            <input
              type="text"
              className="form-control"
              value={disadvantages}
              onChange={(e) => setDisadvantages(e.target.value)}
              placeholder="What didn’t you like?"
            />
          </div>

          <div className="mb-3">
            <label className="form-label">
              <strong>Review</strong>
            </label>
            <textarea
              className="form-control"
              rows={4}
              value={text}
              onChange={(e) => setText(e.target.value)}
              placeholder="Write your review here..."
            />
          </div>

          <div className="d-flex justify-content-end gap-2">
            <button
              type="button"
              className="review-modal__btn btn-cancel"
              onClick={onClose}
            >
              Cancel
            </button>
            <button type="submit" className="review-modal__btn btn-submit">
              Submit
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default ReviewModal;
