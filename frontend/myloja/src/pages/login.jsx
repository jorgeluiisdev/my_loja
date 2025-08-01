import React, {useState} from 'react'
import './login.css'
import {loginUser} from "../auth/AuthGuard.jsx";
import {useNavigate} from "react-router-dom";

const Form = () => {

    const navigate = useNavigate();
    const [login, setLogin] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await loginUser(login, password);
            navigate('/edit');
        } catch (err) {
            setError(err.message);
        }
    };

  return (
    <div className='login-container'>
        <form onSubmit={handleSubmit}>
            <div className='user-image'>
                <div className='box-img'>
                    <img src="/imgs/avatar.png" alt="default-profile-img" />
                </div>
                <h3>Bem-vindo(a)</h3>
            </div>

            <div className='composed'>
                <label>Login</label>
                <input
                    type="text"
                    placeholder='Login'
                    value={login}
                    onChange={(e) => setLogin(e.target.value)}
                />
                <label>Senha</label>
                <input
                    type="password"
                    placeholder='Senha'
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
            </div>

            {error && <p className="error">{error}</p>}

            <div className='btn-enter'>
                <button type='submit'>Entrar</button>
            </div>
        </form>
    </div>
  )
}

export default Form