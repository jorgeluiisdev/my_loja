import React from 'react'
import './login.css'

const Form = () => {
  return (
    <div className='login-container'>
        <form>
            <div className='user-image'>
                <div className='box-img'>
                    <img src="/imgs/avatar.png" alt="default-profile-img" />
                </div>
                <h3>Lucas</h3>
            </div>

            <div className='composed'>
                <label>Email</label>
                <input type="text" placeholder='Email' />
                <label>Senha</label>
                <input type="password" placeholder='Senha' />
            </div>

            <div className='btn-enter'>
                <button type='submit'>Entrar</button>
            </div>    
        </form>
    </div>
  )
}

export default Form