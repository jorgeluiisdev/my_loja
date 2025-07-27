import { FaFacebookSquare } from "react-icons/fa";
import { FaWhatsapp } from "react-icons/fa";
import { FaInstagram } from "react-icons/fa";

const Footer = () => {
  return (
    <div className='footer'>
       <div className="container-footer" >
         <div className="about">
             <ul>
               <li>Quem somos</li>
               <li>Contato</li>
               <li>Promoções</li>
               <li>Trabalhe conosco</li>
             </ul>
         </div>

         <div className="social-media">
            <h3>SIGA-NOS</h3>
           <ul>
              <li><FaFacebookSquare/></li>
              <li><FaWhatsapp/></li>
              <li><FaInstagram/></li> 
           </ul>
        </div>
       </div>
       <hr />
        <div className="rights">
            <p>© 2025 Veloz Motors. Todos os direitos reservados.</p>
        </div>
    </div>
  )
}

export default Footer