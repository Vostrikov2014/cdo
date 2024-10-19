import React from 'react';
import {Link} from 'react-router-dom';

const Layout = ({children}) => {
    return (
        <div className="d-flex flex-column" style={{height: '100vh'}}>
            {/* Информационная строка */}
            <div className="bg-dark text-light p-1" style={{width: '100%'}}>
                <h6>Это информационная строка.</h6>
            </div>

            {/* Панель управления */}
            <header className="bg-light text-dark p-2 w-100 d-flex justify-content-end" style={{width: '100%'}}>
                <h2>Панель управления</h2>
            </header>

            <div className="d-flex flex-grow-1">
                {/* Боковое меню */}
                <nav className="flex-column p-3" style={{width: '250px', flexShrink: 0, backgroundColor: '#ececec'}}>
                    <h5 className="text-dark">Меню</h5>
                    <ul className="nav flex-column">
                        <li className="nav-item">
                            <Link className="nav-link text-dark" to="/home">Главная страница</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link text-dark" to="/list-conference">Конференции</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link text-dark" to="/register">Регистрация</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link text-dark" to="/create-conference">Создать конференцию</Link>
                        </li>
                    </ul>
                </nav>

                <main className="flex-grow-1 p-3">
                    {children} {/* Контент страницы */}
                </main>
            </div>
            <footer className="bg-dark text-white text-center p-3 mt-auto">
                © 2024 Ваша Компания. Все права защищены.
            </footer>
        </div>
    );
};

export default Layout;
