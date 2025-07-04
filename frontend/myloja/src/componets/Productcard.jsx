import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import 'swiper/css/navigation';
import { Navigation } from 'swiper/modules';

const ProductCard = ({ image, title, price }) => {

  // TODO | Verificar como vai ficar no lado admin
  const abrirWhatsApp = (title) => {
    const numero = '5587981028854';
    const mensagem = `Olá! Tenho interesse no produto "${title}"`;
    const url = `https://wa.me/${numero}?text=${encodeURIComponent(mensagem)}`;
    window.location.href = url;
  };

  const imagens = Array.isArray(image) ? image : [image];

  return (
    <section className="product-card w-48 h-107 border-1 border-amber-400-500 rounded-md ml-6
     md:w-70 md:h-110 md:ml-7 md:border-0 ">
      <div className="product-content  w-48 flex justify-between flex-col 
      md:w-full   ">
        
        <Swiper
          modules={[Navigation]}
          navigation
          spaceBetween={34}
          slidesPerView={1}
          style={{ width: '100%', '--swiper-navigation-color': '# #808080' }}
        >
          {imagens.map((imgUrl, index) => (
            <SwiperSlide key={index}>
              <img
                className="product-image w-90 rounded-md cursor-pointer transition-transform duration-300 hover:scale-102 
                md:w-86 md:h-100 md:mt-5 md:ml-8"
                src={imgUrl}
                alt={`${title} - Imagem ${index + 1}`}
              />
            </SwiperSlide>
          ))}
        </Swiper>

        <p className="product-title ml-4 mt-3 text-lg md:text-xl md:mt-1  md:text-center">{title}</p>
        <h3 className="product-price ml-12 mt-5 text-lg md:text-xl md:mt-1 md:ml-0 md:text-center">{price}</h3>
        <button
          onClick={() => abrirWhatsApp(title)}
          className="contact-button w-38 h-9 bg-emerald-500 ml-4 mt-10 rounded-xl cursor-pointer
          md:w-57 md:ml-11 md:mt-3"
        >
          Entrar em contato
        </button>
      </div>
    </section>
  );
};

export default ProductCard;
