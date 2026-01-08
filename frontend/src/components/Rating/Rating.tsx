import { useState } from "react";
import "./Rating.scss";

type RatingProps = {
  value: number;
  max?: number;
  showValue?: boolean;
  interactive?: boolean;
  onChange?: (value: number) => void;
};

function Rating({
  value,
  max = 5,
  showValue = true,
  interactive = false,
  onChange,
}: RatingProps) {
  const [currentValue, setCurrentValue] = useState(value);

  const handleClick = (i: number) => {
    if (!interactive) return;
    setCurrentValue(i + 1);
    if (onChange) onChange(i + 1);
  };

  return (
    <div className={`rating ${interactive ? "rating--interactive" : ""}`}>
      <div className="stars">
        {Array.from({ length: max }).map((_, i) => (
          <span
            key={i}
            className={`star ${i < currentValue ? "filled" : ""}`}
            onClick={() => handleClick(i)}
            style={{ cursor: interactive ? "pointer" : "default" }}
          >
            ★
          </span>
        ))}
      </div>

      {showValue && <span className="rating-value">({currentValue}.0)</span>}
    </div>
  );
}

export default Rating;
