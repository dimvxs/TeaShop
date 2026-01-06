import { useState } from "react";
import "./ProductPage.scss";
import QuantityControl from "../../components/QuantityControl/QuantityControl";
import ProductImages from "../../components/ProductImages/ProductImages";
import BodySection from "../../components/BodySection/BodySection";
import Rating from "../../components/Rating/Rating";
import ReviewSection from "../../components/ReviewSection/ReviewSection";

function ProductPage() {
  const product = {
    name: "Green Tea",
    rating: 4,
    description:
      "Smooth, aromatic, and refreshing tea made from carefully selected leaves. Perfect for mornings or evenings.",
    price: 12.99,
    meta: {
      type: "Green Tea",
      weight: "100g",
      origin: "Japan",
    },
    images: [
      "/src/assets/img/prod1.jpg",
      "/src/assets/img/prod2.jpg",
      "/src/assets/img/prod3.jpg",
    ],
    perks: ["Free shipping over $30", "30-day return policy"],
  };

  const baseReviews = [
    {
      author: "John",
      avatar: "/src/assets/img/prod1.jpg",
      rating: 5,
      createdAt: "2026-01-02T10:30:00Z",
      advantages: "Great quality, fast delivery",
      disadvantages: "None",
      text: "Great product, very high quality!",
    },
    {
      author: "Jane",
      avatar: "/src/assets/img/prod1.jpg",
      rating: 4,
      createdAt: "2026-01-01T18:15:00Z",
      advantages: "Nice taste",
      disadvantages: "A bit expensive",
      text: "Nice taste, but a bit expensive.",
    },
  ];

  const reviews: typeof baseReviews = Array.from({ length: 5 }).flatMap(() =>
    baseReviews.map((r) => ({ ...r }))
  );

  const [mainImage, setMainImage] = useState(product.images[0]);
  const [qty, setQty] = useState(1);

  return (
    <div>
      <BodySection>
        <div className="product-page">
          <div className="product-container">
            <div className="row g-0">
              <div className="col-md-6 left-side">
                <ProductImages
                  images={product.images}
                  mainImage={mainImage}
                  setMainImage={setMainImage}
                />
              </div>

              <div className="col-md-6 right-side">
                <div className="product-header">
                  <h2 className="product-name">{product.name}</h2>
                  <Rating value={product.rating} />
                </div>

                <p className="product-desc">{product.description}</p>

                <div className="product-meta">
                  <div className="meta-row">
                    <span>Type</span>
                    <span>{product.meta.type}</span>
                  </div>
                  <div className="meta-row">
                    <span>Weight</span>
                    <span>{product.meta.weight}</span>
                  </div>
                  <div className="meta-row">
                    <span>Origin</span>
                    <span>{product.meta.origin}</span>
                  </div>
                </div>

                <div className="buy-section">
                  <div className="price">${product.price.toFixed(2)}</div>

                  <div className="actions">
                    <div className="actions-top">
                      <QuantityControl value={qty} onChange={setQty} />
                      <button className="btn btn-primary add-cart">
                        Add to Cart
                      </button>
                    </div>

                    <button className="btn btn-outline-primary buy-now">
                      Buy Now
                    </button>
                  </div>
                </div>

                <div className="extra-info">
                  {product.perks.map((perk, i) => (
                    <p key={i}>✔ {perk}</p>
                  ))}
                </div>
              </div>
            </div>
          </div>
        </div>
        <ReviewSection reviews={reviews} />
      </BodySection>
    </div>
  );
}

export default ProductPage;
