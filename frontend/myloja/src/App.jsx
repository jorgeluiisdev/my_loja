import './App.css'
import { useState, useEffect, useRef } from 'react'
import { motion as _motion } from 'framer-motion';
import Nav from './componets/Nav'
import Banner from './componets/Banner'
import ProductCard from './componets/Productcard'
import Footer from './componets/Footer'
import {getAllProducts, getImageUrl} from "./services/RouteServices.jsx";

function App() {
    const carousel = useRef([]);
    const [width, setWidth] = useState(0)
    const [apiProducts, setProducts] = useState([]);

    useEffect(() => {
        setWidth(carousel.current?.scrollWidth - carousel.current?.offsetWidth)

        async function fetchProducts() {
            try {
                const data = await getAllProducts();
                console.log('Dados recebidos do backend:', data);
                setProducts(data);
            } catch (err) {
                console.error(err);
            }
        }

        fetchProducts();
    }, []);


    return (
        <div>
            <header className="top-header">
                <Banner />
            </header>
            <Nav />
            <main className="main-content">
                <div className="box-text">
                    <h3 className="text">Nossas ofertas para você</h3>
                </div>

                {apiProducts.map((category, catIndex) => (
                    <section key={catIndex} className="cart-i">
                        <h2 className="section-title">
                            {category.categoryName}
                        </h2>

                        <_motion.div
                            ref={carousel}
                            className="product-container"
                            whileTap={{ cursor: "grabbing" }}
                        >
                            <_motion.div
                                className="product-list"
                                drag="x"
                                dragConstraints={{ right: 0, left: -width }}
                            >
                                {category.products.map((product, index) => {
                                    const imageUrls = product.imagens.map(img => getImageUrl(img.id));
                                    return (
                                        <ProductCard
                                            key={index}
                                            images={imageUrls}
                                            title={product.title}
                                            sku={product.sku}
                                            price={new Intl.NumberFormat('pt-BR', {
                                                style: "currency",
                                                currency: "BRL",
                                            }).format(Number(product.price))}
                                        />
                                    );
                                })}
                            </_motion.div>
                        </_motion.div>
                    </section>
                ))}
            </main>
            <Footer />
        </div>
    );
}

export default App