import React, {useState, useEffect} from 'react';
import {BrowserRouter, Link, Route, Routes, useLocation} from 'react-router-dom';
import Logo from './components/Logo.jsx';
import Index from './components/Index.jsx';
import HomeVideoConf from './components/videoconf/HomeVideoConf.jsx';
import Login from './components/auth/Login.jsx';
import LoginPage from './components/LoginPage.jsx';
import Register from './components/auth/Register.jsx';
import ConfStart from "./components/videoconf/ConfStart.jsx";
import ConfCreateUpdate from "./components/videoconf/ConfCreateUpdate.jsx";
import ConfList from "./components/videoconf/ConfList.jsx";
import ConfDetails from "./components/videoconf/ConfDetails.jsx";
import Layout from './components/Layout.jsx';
import ConfActive from "./components/videoconf/ConfActive.jsx";
import UnderConstruction from "./components/UnderConstruction.jsx";
import ConfDelete from "./components/videoconf/ConfDelete.jsx";
import IndexVideoConf from "./components/videoconf/IndexVideoConf.jsx";
import Cookies from "js-cookie";
import axiosInstance from "./axiosConfig.js";
import {BASE_URL} from "./config.js";
import CreateLesson from "./components/schedule/CreateLesson.jsx";
import EditLesson from "./components/schedule/EditLesson.jsx";
import Schedule from "./components/schedule/Schedule.jsx";
import AddStudent from "./components/schedule/AddStudent.jsx";
import AddClassroom from "./components/schedule/AddClassroom.jsx";
import StudentList from "./components/schedule/StudentList.jsx";
import ClassroomList from "./components/schedule/ClassroomList.jsx";


//import {ReactKeycloakProvider} from "@react-keycloak/web";
//import keycloakConfig from "./components/KeycloakConfig.jsx"
//import {useKeycloak} from '@react-keycloak/web';

// Основной компонент приложения
const App = () => {
    const location = useLocation();
    const [username, setUsername] = useState(null);

    // Используем хук useKeycloak для получения информации о Keycloak
    //const { keycloak, initialized } = useKeycloak();

    /*useEffect(() => {
        if (initialized && keycloak.authenticated) {
            setUsername(keycloak.tokenParsed?.preferred_username || "Гость");
        }
    }, [initialized, keycloak]);*/

    // Получаем имя пользователя из Local storage при загрузке компонента
    useEffect(() => {
        const localUsername = localStorage.getItem('username');
        if (localUsername) {
            console.log(localUsername);
            // Отправляем запрос к серверу для получения имени пользователя из сессии
            axiosInstance.get(`${BASE_URL}/username-str`, {params: {localUsername}})
                .then(response => {
                    setUsername(response.data); // Устанавливаем имя пользователя
                })
                .catch((error) => {
                    console.log("User Unknown", error);
                    setUsername('Unknown is App'); // Если запрос не удался
                });
        } else {
            setUsername(null);
        }
    }, [localStorage.getItem('username')]);

    /*// Получаем имя пользователя из cookie при загрузке компонента
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
        } else {
            setUsername(null);
        }
    }, []);*/

    // Отображение логотипа, имени пользователя, фона и пр. в зависимости от текущего пути
    const disableLogoLink = location.pathname === '/';
    const showLogo = location.pathname !== '/register' && location.pathname !== '/login';
    const showLogIn = location.pathname !== '/' && location.pathname !== '/login';
    const applyBackground = location.pathname === '/'
        || location.pathname === '/index-video-conf'
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
                        {showLogIn && username === null ? (
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
                            <div
                                style={{position: 'absolute', top: '35px', right: '50px'}}> {/* Позиционируем ссылку */}
                                <Link to="/home-video-conf"
                                      style={{
                                          color: 'white',
                                          textDecoration: 'none',
                                          fontSize: '1.5rem',
                                          fontWeight: 'bold'
                                      }}>{username}</Link>
                            </div>
                        )}
                        <Routes>
                            <Route path="/" element={<Index/>}/>
                            <Route path="/index-video-conf" element={<IndexVideoConf/>}/>
                            <Route path="/register" element={<Register/>}/>
                            <Route path="/login" element={<Login/>}/>
                            <Route path="/home-video-conf" element={<HomeVideoConf/>}/>
                            <Route path="/conference/:roomName" element={<ConfStart/>}/>
                            <Route path="/schedule" element={<Schedule />} />
                            <Route path="/callback" element={<Callback />} />
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
                                <Route path="/home-video-conf" element={<HomeVideoConf/>}/>
                                <Route path="/create-conference" element={<ConfCreateUpdate/>}/>
                                <Route path="/delete-conference" element={<ConfDelete/>}/>
                                <Route path="/list-conference" element={<ConfList/>}/>
                                <Route path="/conference-details/:id" element={<ConfDetails/>}/>
                                <Route path="/active-conf" element={<ConfActive/>}/>
                                <Route path="/create-lesson" element={<CreateLesson />} />
                                <Route path="/edit-lesson/:id" element={<EditLesson />} />
                                <Route path="/schedule" element={<Schedule />} />
                                <Route path="/add-student" element={<AddStudent />} />
                                <Route path="/add-classroom" element={<AddClassroom />} />
                                <Route path="/students" element={<StudentList />} />
                                <Route path="/classrooms" element={<ClassroomList />} />
                                <Route path="/callback" element={<Callback />} />
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