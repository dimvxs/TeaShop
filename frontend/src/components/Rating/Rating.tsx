import "./Rating.scss";

type RatingProps = {
  value: number; 
  max?: number; 
  showValue?: boolean; 
};

function Rating({ value, max = 5, showValue = true }: RatingProps) {
  const filledStars = Math.max(0, Math.min(value, max));

  return (
    <div className="rating">
      <div className="stars">
        {Array.from({ length: max }).map((_, i) => (
          <span
            key={i}
            className={`star ${i < filledStars ? "filled" : ""}`}
          >
            ★
          </span>
        ))}
      </div>

      {showValue && <span className="rating-value">({value}.0)</span>}
    </div>
  );
}

export default Rating;
