import React, {useState, useEffect} from 'react';
import {BrowserRouter, Link, Route, Routes, useLocation} from 'react-router-dom';
import Logo from './components/Logo.jsx';
import Index from './components/Index.jsx';
import Home from './components/Home.jsx';
import Login from './components/Login.jsx';
import LoginPage from './components/LoginPage.jsx';
import Register from './components/Register.jsx';
import ConfStart from "./components/ConfStart.jsx";
import ConfCreateUpdate from "./components/ConfCreateUpdate.jsx";
import ConfList from "./components/ConfList.jsx";
import ConfDetails from "./components/ConfDetails.jsx";
import Layout from './components/Layout.jsx';
import ConfActive from "./components/ConfActive.jsx";
import UnderConstruction from "./components/UnderConstruction.jsx";
import ConfDelete from "./components/ConfDelete.jsx";

import {ReactKeycloakProvider} from "@react-keycloak/web";
import keycloakConfig from "./components/KeycloakConfig.jsx"
import {useKeycloak} from '@react-keycloak/web';

// Основной компонент приложения
const App = () => {
    const location = useLocation();
    const [username, setUsername] = useState('');

    // Используем хук useKeycloak для получения информации о Keycloak
    //const { keycloak, initialized } = useKeycloak();

    /*useEffect(() => {
        if (initialized && keycloak.authenticated) {
            setUsername(keycloak.tokenParsed?.preferred_username || "Гость");
        }
    }, [initialized, keycloak]);*/

    // Отображение логотипа, имени пользователя, фона и пр. в зависимости от текущего пути
    const disableLogoLink = location.pathname === '/';
    const showLogo = location.pathname !== '/register' && location.pathname !== '/login';
    const showLogIn = location.pathname !== '/login';
    const applyBackground = location.pathname === '/'
        || location.pathname === '/register'
        || location.pathname === '/login'
        || location.pathname.startsWith('/conference');

    return (
        <div>
            <div className="App"/>
            {applyBackground ? (
                    <div
                        className={`d-flex justify-content-center align-items-center ${applyBackground ? '' : 'no-bg'}`}
                        style={{
                            height: '100vh',
                            width: '100vw',
                            backgroundImage: `url(/images/welcome-background.jpg)`,
                            backgroundSize: 'cover',
                            backgroundPosition: 'center',
                            backgroundRepeat: 'no-repeat'
                        }}>
                        {showLogo && (
                            <div className="position-absolute" style={{top: '34px', left: '30px', padding: '5px'}}>
                                {/* <Logo disableLink={disableLogoLink}/> */} {/* Это коммент */}
                                <Logo disableLink={disableLogoLink}/>
                            </div>
                        )}
                        {showLogIn && username === '' ? (
                            <div style={{position: 'absolute', top: '35px', right: '50px'}}> {/* Позиционируем ссылку */}
                                <Link to="/login"
                                      style={{
                                          color: 'white',
                                          textDecoration: 'none',
                                          fontSize: '1.5rem',
                                          fontWeight: 'bold'
                                      }}>LOGIN</Link>
                            </div>
                        ) : showLogIn && (
                            <h4 style={{
                                color: '#e0956a',
                                position: 'absolute',
                                top: '35px',
                                right: '50px',
                                fontWeight: 'bold',
                                zIndex: 9999
                            }}>
                                Welcome, {username}
                            </h4>
                        )}
                        <Routes>
                            <Route path="/" element={<Index/>}/>
                            <Route path="/register" element={<Register/>}/>
                            <Route path="/login" element={<Login/>}/>
                            <Route path="/conference/:roomName" element={<ConfStart/>}/>
                        </Routes>
                    </div>
                )
                :
                (
                    <Layout>
                        <div className="d-flex flex-grow-1">
                            {showLogo && (
                                <div className="position-absolute" style={{top: '34px', left: '30px', padding: '5px'}}>
                                    <Logo disableLink={disableLogoLink}/>
                                </div>
                            )}
                            <Routes>
                                <Route path="/under-construction" element={<UnderConstruction/>}/>
                                <Route path="/home" element={<Home/>}/>
                                <Route path="/create-conference" element={<ConfCreateUpdate/>}/>
                                <Route path="/delete-conference" element={<ConfDelete/>}/>
                                <Route path="/list-conference" element={<ConfList/>}/>
                                <Route path="/conference-details/:id" element={<ConfDetails/>}/>
                                <Route path="/active-conf" element={<ConfActive/>}/>
                            </Routes>
                        </div>
                    </Layout>
                )}
        </div>
    );
};

const AppWrapper = () => (
    //<ReactKeycloakProvider authClient={keycloakConfig} initOptions={{onLoad: 'login-required', checkLoginIframe: false}}>
    <BrowserRouter>
        <App/>
    </BrowserRouter>
    //</ReactKeycloakProvider>
);

export default AppWrapper;