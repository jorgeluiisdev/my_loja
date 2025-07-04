import { FaFacebookSquare } from "react-icons/fa";
import { FaWhatsapp } from "react-icons/fa";
import { FaInstagram } from "react-icons/fa";

const Footer = () => {
  return (
    <div className='footer w-full h-full bg-zinc-200 text-white p-4 mt-20
    md:h-100 md:mt-75'>
       <div className="container-footer flex justify-around" >
         <div className="about  md:mt-21">
             <ul>
               <li>Quem somos</li>
               <li>Contato</li>
               <li>Promoções</li>
               <li>Trabalhe conosco</li>
             </ul>
         </div>

         <div className="social-media flex flex-col justify-center items-center ml-23 md:mt-21 ">
            <h3>SIGA-NOS</h3>
           <ul>
              <li><FaFacebookSquare/></li>
              <li><FaWhatsapp/></li>
              <li><FaInstagram/></li> 
           </ul>
        </div>
       </div>
       <hr />
        <div className="right mt-10 text-center cursor-pointer md:mt-20">
            <p>© 2025 Veloz Motors. Todos os direitos reservados.</p>
        </div>
    </div>
  )
}

export default Footer