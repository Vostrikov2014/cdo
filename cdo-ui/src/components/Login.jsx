import React, {useState, useEffect} from 'react';
import axios from 'axios';
import Cookies from 'js-cookie';
import {BASE_URL, AUTH_URL} from '../config';
import {Link, useNavigate} from "react-router-dom";

//import {ReactKeycloakProvider} from "@react-keycloak/web";
//import keycloakConfig from "./KeycloakConfig.jsx";
//import { useKeycloak } from '@react-keycloak/web';

const Login = () => {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    // Используем хук useKeycloak для получения информации о Keycloak
    /*const { keycloak, initialized } = useKeycloak();

    useEffect(() => {
        if (initialized && keycloak.authenticated) {
            setUsername(keycloak.tokenParsed?.preferred_username || "fix");
        }
    }, [initialized, keycloak]);*/

    // Проверка сессии при загрузке компонента
    useEffect(() => {
        const sessionId = Cookies.get('JSESSIONID'); // Проверяем наличие сессии в куках
        if (sessionId) {
            navigate('/home-video-conf'); // Если сессия есть, переходим на страницу home
        }
    }, [navigate]);

    const handleLogin = async (e) => {
        e.preventDefault(); // Предотвратим отравку по умолчанию
        setError('');
        const loginData = {username, password}

        try {
            const response =
                await axios.post(`${AUTH_URL}/login`, loginData, { withCredentials: true}); // Включает cookie
                //console.log(response)

            if (response.status === 200 || response.status === 201) {
                // Сохраняем данные в sessionStorage
                localStorage.setItem('username', username);
                localStorage.setItem('password', password);
                // Перенаправляем на страницу home
                navigate('/home-video-conf')
            } else {
                const errorData = await response.json()
                setError(errorData.message || "Loin faild for user. Please retry!");
            }
        } catch (error) {
            setError("And error occured. pleas retry")
        }

        /*axios.post(`${AUTH_URL}/login`, loinData)
            .then(() => window.location = '/home-video-conf')
            .catch((err) => {
                console.error(err);
                alert("Invalid credentials / Недействительные учетные данные")
            });*/
    };

    return (
        <div className="card p-5"
             style={{
                 width: '450px',
                 backgroundColor: 'rgba(255, 255, 255, 0.5)',
                 border: '1px solid rgba(0, 0, 0, 0.1)',
             }}>
            <div className="text-center mb-3">
                <img src="/images/logocdo.svg" alt="Logo" style={{width: '100px'}}/>
            </div>
            <form onSubmit={handleLogin}>
                <div className="form-group mb-3">
                    <label>Username</label>
                    <input type="text" className="form-control" placeholder="Enter your username" value={username}
                           onChange={(e) => setUsername(e.target.value)}/>
                </div>
                <div className="form-group mb-3">
                    <label>Password</label>
                    <input type="password" className="form-control" placeholder="Enter your password"
                           value={password}
                           onChange={(e) => setPassword(e.target.value)}/>
                </div>
                <div className="mb-3 form-check">
                    <input type="checkbox" className="form-check-input" id="rememberMe"/>
                    <label className="form-check-label" htmlFor="rememberMe">Remember me</label>
                </div>
                <button type="submit" className="btn btn-primary w-100" style={{backgroundColor: '#0f47ad'}}>Login
                </button>
            </form>
            <div className="mt-3 text-center">
                <p>Don't have an account? <a href="/register" className="btn btn-link">Register</a></p>
            </div>

            {/* Ссылка CDO в верхнем правом углу */}
            <div style={{
                position: 'fixed', // Фиксированное позиционирование
                top: '35px',
                right: '50px',
                zIndex: 1000 // Увеличиваем z-index для перекрытия других элементов
            }}>
                <Link to="/"
                      style={{
                          color: 'white',
                          textDecoration: 'none',
                          fontSize: '1.5rem',
                          fontWeight: 'bold'
                      }}>CDO</Link>
            </div>
        </div>
    );
};

const LoginWrapper = () => (
    //<ReactKeycloakProvider authClient={keycloakConfig}
    //                       initOptions={{onLoad: 'login-required', checkLoginIframe: false}}>
    <Login/>
    //</ReactKeycloakProvider>
);

export default LoginWrapper;
