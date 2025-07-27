import React from 'react'
import './edit.css'

const Page = () => {
  return (
    <div className='container-crm'>
      <header className="crm-header">
        <a href="#">CRM da Loja</a>
        <nav>
          <ul>
            <li>Produtos</li>
            <li>avatar</li>
          </ul>
        </nav>
      </header>

      <main>
        <div className='edit-box'>
          <div className='btn-add-products'>
            <button>+</button>
            <p>adicionar produto</p>
          </div>
          <div className='edit'>
            <div className='image'>
              <img src="/imgs/img-loja1.png" alt="" />
              <div className='edit-group'>
                <input className='description' type="text" placeholder='Descricão do produto' />
                <input className='submit' type="text" placeholder='Preço do produto' />
              </div> 
            </div>

            <div className='image'>
              <img src="/imgs/img-loja.png" alt="" />
              <div className='edit-group'>
                <input className='description' type="text" placeholder='Descricão do produto' />
                <input className='submit' type="text" placeholder='Preço do produto' />
              </div>
            </div>

            
            <div className='image'>
              <img src="/imgs/img-loja8.png" alt="" />
              <div className='edit-group'>
                <input className='description' type="text" placeholder='Descricão do produto' />
                <input className='submit' type="text" placeholder='Preço do produto' />
              </div>
            </div>
          </div>
        </div>
      </main>
        
    </div>
  )
}

export default Page; 
