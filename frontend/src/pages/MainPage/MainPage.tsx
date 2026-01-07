import { Link } from "react-router-dom";
import ProductCard from "../ProductCard/ProductCard.tsx";

function MainPage() {
  return (
    <div>
      <h1>Main Page</h1>
      <ProductCard
        id={1}
        image="/src/assets/img/prod1.jpg"
        title="Tea"
        price={300}
      />
      <Link to="/admin" style={{ color: "white", fontSize: "18px" }}>
        Админ-панель
      </Link>
      <div style={{ padding: "20px", textAlign: "center" }}>
        <Link
          to="/admin"
          style={{
            display: "inline-block",
            padding: "12px 24px",
            background: "#2c3e50",
            color: "white",
            textDecoration: "none",
            borderRadius: "8px",
            fontSize: "18px",
            fontWeight: "bold",
          }}
        >
          Перейти в админ-панель
        </Link>
      </div>
    </div>
  );
}

export default MainPage;
