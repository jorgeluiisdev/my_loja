import './App.css'
import { useState, useEffect } from 'react'
import Nav from './componets/Nav'
import Banner from './componets/Banner'
import ProductCard from './componets/Productcard'
import Footer from './componets/Footer'
import { getAllProducts, getImageUrl } from './services/RouteServices.jsx'

function App() {
    const [apiProducts, setProducts] = useState([]);

    useEffect(() => {
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
                        <div className="product-list">
                            {category.products.map((product, index) => {
                                const imageUrls = product.imagens?.map(img => getImageUrl(img.id)) || [];
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
                        </div>
                    </section>
                ))}
            </main>
            <Footer />
        </div>
    );
}

export default App