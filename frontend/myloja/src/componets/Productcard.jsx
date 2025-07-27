import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import 'swiper/css/navigation';
import { Navigation } from 'swiper/modules';

const ProductCard = ({ images, title, price }) => {
  const abrirWhatsApp = (title) => {
    const numero = '5587981028854';
    const mensagem = `Olá! Tenho interesse no produto "${title}"`;
    const url = `https://wa.me/${numero}?text=${encodeURIComponent(mensagem)}`;
    window.location.href = url;
  };

  const imagens = Array.isArray(images) ? images : [images];

  return (
      <section className="product-card">
        <div className="product-content">
          <Swiper
              modules={[Navigation]}
              navigation
              spaceBetween={34}
              slidesPerView={1}
              style={{ width: '100%', '--swiper-navigation-color': '#808080' }}
          >
            {imagens.map((imgUrl, index) => (
                <SwiperSlide key={index}>
                  <img
                      className="product-image"
                      src={imgUrl}
                      alt={`${title} - Imagem ${index + 1}`}
                  />
                </SwiperSlide>
            ))}
          </Swiper>

          <p className="product-title">{title}</p>
          <h3 className="product-price">{price}</h3>
          <button
              onClick={() => abrirWhatsApp(title)}
              className="contact-button"
          >
            Entrar em contato
          </button>
        </div>
      </section>
  );
};

export default ProductCard;