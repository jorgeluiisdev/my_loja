import { useState } from 'react';

const ProductCard = ({ images, title, price }) => {
  const [selectedImage, setSelectedImage] = useState(images?.[0] || '');

  const abrirWhatsApp = (title) => {
    const numero = '5587981028854';
    const mensagem = `Olá! Tenho interesse no produto "${title}"`;
    const url = `https://wa.me/${numero}?text=${encodeURIComponent(mensagem)}`;
    window.location.href = url;
  };

  return (
      <section className="product-card">
        <div className="product-content">
          <img className="product-image" src={selectedImage} alt={title} />

          {images.length > 1 && (
              <div className="image-thumbnail-list">
                {images.map((img, idx) => (
                    <img
                        key={idx}
                        src={img}
                        alt={`Miniatura ${idx + 1}`}
                        className={`thumbnail ${img === selectedImage ? 'active' : ''}`}
                        onClick={() => setSelectedImage(img)}
                    />
                ))}
              </div>
          )}

          <p className="product-title">{title}</p>
          <h3 className="product-price">{price}</h3>
          <button onClick={() => abrirWhatsApp(title)} className="contact-button">
            Entrar em contato
          </button>
        </div>
      </section>
  );
};

export default ProductCard;