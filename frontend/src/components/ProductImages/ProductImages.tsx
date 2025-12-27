import type { FC } from "react";
import Slider from "react-slick";
import "./ProductImages.scss";

interface ProductImagesProps {
  images: string[];
  mainImage: string;
  setMainImage: (img: string) => void;
}

const ProductImages: FC<ProductImagesProps> = ({
  images,
  mainImage,
  setMainImage,
}) => {
  const sliderSettings = {
    dots: false,
    infinite: false,
    arrows: false,
    slidesToScroll: 1,
    slidesToShow: 4,
    vertical: true,
    verticalSwiping: true,
    responsive: [
      {
        breakpoint: 992,
        settings: {
          slidesToShow: 3,
          vertical: false,
          verticalSwiping: false,
          arrows: true,
          swipeToSlide: true,
          touchThreshold: 10,
        },
      },
      {
        breakpoint: 768,
        settings: {
          slidesToShow: 4,
          vertical: true,
          verticalSwiping: true,
          arrows: false,
        },
      },
      {
        breakpoint: 480,
        settings: {
          slidesToShow: 3,
          vertical: false,
          verticalSwiping: false,
          arrows: true,
          swipeToSlide: true,
          touchThreshold: 10,
        },
      },
    ],
  };

  return (
    <div className="image-layout">
      <div className="thumb-vertical">
        <Slider {...sliderSettings}>
          {images.map((img, i) => (
            <div
              key={i}
              className="thumb-wrapper"
              onClick={() => setMainImage(img)}
            >
              <img src={img} />
            </div>
          ))}
        </Slider>
      </div>

      <div className="main-image">
        <img src={mainImage} />
      </div>

      <div className="thumb-horizontal">
        <Slider {...sliderSettings}>
          {images.map((img, index) => (
            <div className="slide" key={index}>
              <img src={img} alt={`Product ${index}`} />
            </div>
          ))}
        </Slider>
      </div>
    </div>
  );
};

export default ProductImages;
