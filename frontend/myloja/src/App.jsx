import './App.css'
import { useState, useEffect, useRef } from 'react'
import { motion as _motion } from 'framer-motion';
import Nav from './componets/Nav'
import Banner from './componets/Banner'
import Productcard from './componets/Productcard'
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
                const data = await getAllProducts(); // Sem paginação ainda
                console.log('Dados recebidos do backend:', data);  // <-- Aqui
                setProducts(data);
            } catch (err) {
                console.error(err);
            }
        }

        fetchProducts();
    }, []);


    // TODO : Arrumar a posição dos menus de categoria
    return (
        <div>
            <header className='top-header w-full h-40'>
                <Banner />
            </header>
            <Nav />
            <main className='main-content w-full h-full'>
                <div className='box-text h-10 mt-10 text-center md:mt-25'>
                    <h3 className='text text-xl md:text-3xl'>Nossas ofertas para você</h3>
                </div>

                {apiProducts.map((category, catIndex) => (
                    <section key={catIndex} className="mb-10">
                        <h2 className="section-title text-xl md:text-3xl text-center mb-4">
                            {category.categoryName}
                        </h2>

                        <_motion.div
                            ref={carousel}
                            className="product-container w-full h-110 border-b-blue-700 mt-7 md:w-full md:mt-20"
                            whileTap={{ cursor: "grabbing" }}
                        >
                            <_motion.div
                                className="product-list w-full h-full flex md:justify-start md:w-680 md:h-100 md:mt-10 md:ml-4"
                                drag="x"
                                dragConstraints={{ right: 0, left: -width }}
                            >
                                {category.products.map((product, index) => {
                                    const imageUrls = product.imagens.map(img => getImageUrl(img.id));
                                    return (
                                        <Productcard
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