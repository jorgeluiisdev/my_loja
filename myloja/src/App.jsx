
import './App.css'
import { useState, useEffect, useRef } from 'react'
import { motion } from 'framer-motion'
import Nav from './componets/Nav'
import Banner from './componets/Banner'
import Productcard from './componets/Productcard'
import Footer from './componets/Footer'

function App() {
  
const products = [
    {
  
      image: ['src/imgs/img-loja.png',
              'src/imgs/img-loja1.png'
            ],
      title: 'Conjuto Prezada',
      price: 'R$ 189,00'
    },
    {
  
      image: 'src/imgs/img-loja1.png',
      title: 'Short Lud',
      price: 'R$ 69,00'
    },
    {
  
      image: 'src/imgs/img-loja3.png',      
      title: 'Macaquinho Niurka' ,
      price: 'R$ 89,90'
    },
    {
  
      image: 'src/imgs/img-loja4.png',
      title: 'Vestido  Simay',
      price: 'R$ 59,00'
    },
    {
  
      image: 'src/imgs/img-loja5.png',
      title: 'Macaquinho Eva',
      price: 'R$ 79,00'
    },
    {
  
      image: 'src/imgs/img-loja6.png',
      title: 'Conjunto Vita',
      price: 'R$ 199,00'
    },
    {
  
      image: 'src/imgs/img-loja7.png',
      title: 'Vestido Sila',
      price: 'R$ 119,00'
    },
    {
  
      image: 'src/imgs/img-loja8.png',
      title: 'Vestido Tania',
      price: 'R$ 79,00'
    },
    
  ]

    
const sectionShorts = [
    {
  
      image: 'src/imgs/img-loja.png',
      title: 'Conjuto Prezada',
      price: 'R$ 189,00'
    },
    {
  
      image: 'src/imgs/img-loja1.png',
      title: 'Short Lud',
      price: 'R$ 69,00'
    },
    {
  
      image: 'src/imgs/img-loja3.png',      
      title: 'Macaquinho Niurka' ,
      price: 'R$ 89,90'
    },
    {
  
      image: 'src/imgs/img-loja4.png',
      title: 'Vestido  Simay',
      price: 'R$ 59,00'
    },
    {
  
      image: 'src/imgs/img-loja5.png',
      title: 'Macaquinho Eva',
      price: 'R$ 79,00'
    },
    {
  
      image: 'src/imgs/img-loja6.png',
      title: 'Conjunto Vita',
      price: 'R$ 199,00'
    },
    {
  
      image: 'src/imgs/img-loja7.png',
      title: 'Vestido Sila',
      price: 'R$ 119,00'
    },
    {
  
      image: 'src/imgs/img-loja8.png',
      title: 'Vestido Tania',
      price: 'R$ 79,00'
    },
    
  ];


  const sectionSet = [
    {
  
      image: 'src/imgs/img-loja.png',
      title: 'Conjuto Prezada',
      price: 'R$ 189,00'
    },
    {
  
      image: 'src/imgs/img-loja1.png',
      title: 'Short Lud',
      price: 'R$ 69,00'
    },
    {
  
      image: 'src/imgs/img-loja3.png',      
      title: 'Macaquinho Niurka' ,
      price: 'R$ 89,90'
    },
    {
  
      image: 'src/imgs/img-loja4.png',
      title: 'Vestido  Simay',
      price: 'R$ 59,00'
    },
    {
  
      image: 'src/imgs/img-loja5.png',
      title: 'Macaquinho Eva',
      price: 'R$ 79,00'
    },
    {
  
      image: 'src/imgs/img-loja6.png',
      title: 'Conjunto Vita',
      price: 'R$ 199,00'
    },
    {
  
      image: 'src/imgs/img-loja7.png',
      title: 'Vestido Sila',
      price: 'R$ 119,00'
    },
    {
  
      image: 'src/imgs/img-loja8.png',
      title: 'Vestido Tania',
      price: 'R$ 79,00'
    },
    
  ];


  const carousel = useRef();
  const [width, setWidth] = useState(0)
   
  useEffect(() => {
    console.log(carousel.current?.scrollWidth, carousel.current?.offsetWidth)
    setWidth(carousel.current?.scrollWidth - carousel.current?.offsetWidth)
  }, [])

  return (
    <div>
      <header className='top-header w-full h-40  '>
         <Banner />
      </header>
      <Nav />
      <main className='main-content w-full h-full '>
         <div className='box-text h-10 mt-10  text-center md:mt-25' >
            <h3 className='text text-xl md:text-3xl'>Nossas ofertas para você</h3>
         </div>
         
            <motion.div ref={carousel} className="product-container w-full h-110 border-b-blue-700 mt-7
              md:w-full md:mt-20"  whileTap={{cursor: "grabbing"}} >
              <motion.div className="product-list w-full h-full flex 
                 md:justify-start md:w-680 md:h-100 md:mt-10 md:ml-4"  drag="x" dragConstraints={{right: 0, left: -width}} >
                {products.map((produto, index) => (
                  <Productcard
                  key={index}
                  image={produto.image}
                  title={produto.title}
                  price={produto.price}
                  
                  />

                ))}
                
              </motion.div>
           
            </motion.div>
            
            <div className="section-header  text-center mt-5 md:mt-45">
              <h2 className="section-title text-xl md:text-3xl">Mais ofertas para você</h2>
            </div>

              <motion.div className="product-container w-97 h-100 
               mt-13 md:mt-30"  whileTap={{cursor: "grabbing"}}>
                <motion.div className="product-list w-full h-full flex 
                 md:justify-start md:w-440  md:h-100 md:mt-10 md:ml-4" drag="x" dragConstraints={{right: 0, left: -width}} >
                  {sectionShorts.map((produto, index) => (
                    <Productcard
                      key={index}
                      image={produto.image}
                      title={produto.title}
                      price={produto.price}
                    />
                  ))}
              </motion.div>
          </motion.div>

            
            <div className="section-header  text-center mt-10 md:mt-55">
              <h2 className="section-title text-xl md:text-3xl">Mais ofertas para você</h2>
            </div>

              <motion.div className="product-container w-97 h-100 
               mt-13 md:mt-35"  whileTap={{cursor: "grabbing"}}>
                <motion.div className="product-list w-full h-full flex 
                 md:justify-start md:w-440  md:h-100 md:mt-10 md:ml-4" drag="x" dragConstraints={{right: 0, left: -width}} >
                  {sectionSet.map((produto, index) => (
                    <Productcard
                      key={index}
                      image={produto.image}
                      title={produto.title}
                      price={produto.price}
                    />
                  ))}
              </motion.div>
          </motion.div>
      </main>
      <Footer />
    </div>
  )
}

export default App
