import React, {useState, useEffect} from 'react';
import {Link, useLocation, useNavigate} from 'react-router-dom';
import Cookies from 'js-cookie';
import {BASE_URL} from "../config.js";
import axiosInstance from "../axiosConfig.js";
import axios from "axios";
//import {useKeycloak} from "@react-keycloak/web";

const Layout = ({children}) => {
    const location = useLocation(); // Получаем текущее местоположение
    const [username, setUsername] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const sessionId = Cookies.get('JSESSIONID'); // Получаем идентификатор сессии из cookie
        if (sessionId) {
            // Отправляем запрос к серверу для получения имени пользователя из сессии
            axiosInstance.get(`${BASE_URL}/username`, {
                withCredentials: true // Включаем cookie в запросе
            })
                .then(response => {
                    setUsername(response.data); // Устанавливаем имя пользователя
                })
                .catch((error) => {
                    console.log("User Unknown", error);
                    setUsername('Unknown'); // Если запрос не удался
                });
        }
    }, []);

    const handleLogout = () => {
        sessionStorage.clear();
        Cookies.remove('JSESSIONID');
        setUsername('Unknown');
        navigate('/');
    };

    //const {keycloak, initialized} = useKeycloak();
    /*useEffect(() => {
        if (initialized && keycloak.authenticated) {
            setUsername(keycloak.tokenParsed?.preferred_username || 'Unknown');
        }
    }, [initialized, keycloak]);*/

    return (
        <div className="d-flex flex-column" style={{height: '100vh', width: '100vw'}}>
            {/* Информационная строка */}
            <div className="bg-dark text-light p-1" style={{height: '5vh', width: '100vw'}}>
                <h6>Это информационная строка.</h6>
            </div>

            {/* Панель управления
            <header className="bg-light text-dark p-2 w-100 d-flex justify-content-end"
                    style={{height: '10vh', width: '100vw'}}>
                {username && (
                    <h5>{username}</h5>
                )}
            </header>*/}

            {/* Панель управления */}
            <header className="bg-light text-dark p-2 w-100 d-flex justify-content-end align-items-center"
                    style={{ height: '10vh', width: '100vw' }}>
                <div style={{ marginRight: '15px' }}>
                    {username && <h5>{username}</h5>}
                </div>
                <button onClick={handleLogout} className="btn btn-danger">
                    Выйти
                </button>
            </header>


            <div className="d-flex flex-grow-1">
                {/* Боковое меню */}
                <nav className="flex-column p-3"
                     style={{width: '250px', height: '85vh', backgroundColor: '#ececec', flexShrink: 0}}>
                    <h5 className="text-dark">Личный</h5>
                    <ul className="nav flex-column">
                        <li className="nav-item">
                            <Link
                                className={`nav-link ${location.pathname === '/home-video-conf' ? 'text-white bg-primary' : 'text-dark'}`}
                                to="/home-video-conf">
                                Главная страница
                            </Link>
                        </li>
                        <li className="nav-item">
                            <Link
                                className={`nav-link ${location.pathname === '/list-conference' ||
                                location.pathname === '/create-conference' ? 'text-white bg-primary' : 'text-dark'}`}
                                to="/list-conference">
                                Конференции
                            </Link>
                        </li>
                    </ul>

                    {/* Отступ перед заголовком "Администратор" */}
                    <div style={{marginTop: '20px'}}/>
                    {/* Замените '20px' на нужный вам отступ */}

                    <h5 className="text-dark">Администратор</h5>
                    <ul className="nav flex-column">
                        <li className="nav-item">
                            <Link
                                className={`nav-link ${location.pathname === '/under-construction' ? 'text-white bg-primary' : 'text-dark'}`}
                                to="/under-construction">
                                Запущенные конференции
                            </Link>
                        </li>
                    </ul>
                </nav>

                <main className="flex-grow-1 p-3" style={{height: '85vh', overflowY: 'auto'}}>
                    {children} {/* Контент страницы */}
                </main>
            </div>
            <footer className="bg-dark text-white text-center p-3" style={{height: '5vh', width: '100vw'}}>
                © 2024 Ваша Компания. Все права защищены.
            </footer>
        </div>
    );
};

export default Layout;
