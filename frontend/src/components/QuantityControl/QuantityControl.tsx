import type { FC } from 'react';
import './QuantityControl.scss'

interface QuantityControlProps {
  value: number;
  onChange: (newValue: number) => void;
  min?: number;
  max?: number;
}

const QuantityControl: FC<QuantityControlProps> = ({ value, onChange, min = 1, max = 99 }) => {

  const decrement = () => {
    if (value > min) onChange(value - 1);
  };

  const increment = () => {
    if (value < max) onChange(value + 1);
  };

  return (
    <div className="quantity-control">
      <button type="button" className="qty-btn" onClick={decrement}>−</button>
      <input type="number" value={value} readOnly />
      <button type="button" className="qty-btn" onClick={increment}>+</button>
    </div>
  );
};

export default QuantityControl;
