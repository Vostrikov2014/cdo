import React, {useState, useEffect} from 'react';
import {BrowserRouter as Router, Route, Routes, useLocation} from 'react-router-dom';
import Logo from './components/Logo.jsx';
import Index from './components/Index.jsx';
import Home from './components/Home.jsx';
import Login from './components/Login.jsx';
import Register from './components/Register.jsx';
import ConfStart from "./components/ConfStart.jsx";
import ConfCreateUpdate from "./components/ConfCreateUpdate.jsx";
import ConfList from "./components/ConfList.jsx";
import ConfDetails from "./components/ConfDetails.jsx";
import Layout from './components/Layout.jsx';
import ConfActive from "./components/ConfActive.jsx";
import UnderConstruction from "./components/UnderConstruction.jsx";
import ConfDelete from "./components/ConfDelete.jsx";

const App = () => {
    const location = useLocation();
    const [username, setUsername] = useState(null);

    // Check for user authentication and set the username
    useEffect(() => {
        // Assume you store the username in localStorage or sessionStorage after login
        const storedUsername = localStorage.getItem('username'); // Replace with sessionStorage if necessary
        setUsername(storedUsername);
    }, []);

    // Conditionally render the Logo component based on the current path
    const disableLogoLink = location.pathname === '/';
    const showLogo = location.pathname !== '/login';
    const showUsername = location.pathname !== '/login';
    const applyBackground = location.pathname === '/' ||
        location.pathname === '/login' ||
        location.pathname.startsWith('/conference');

    return (
        <div>
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
                    <div
                        className="position-absolute"
                        style={{
                            top: '34px',   // Изменить отступ сверху
                            left: '30px',  // Изменить отступ слева
                            padding: '5px'
                        }}>
                        <Logo disableLink={disableLogoLink}/>
                    </div>
                )}
                {showUsername && username && (
                    <h4
                        style={{
                            color: '#e0956a',
                            position: 'absolute',
                            top: '50px',
                            right: '40px',
                            fontWeight: 'bold',
                            zIndex: 9999 // Ensure it stays on top of other elements
                        }}>
                        Welcome, {username}
                    </h4>
                )}
                <Routes>
                    <Route path="/" element={<Index/>}/>
                    <Route path="/login" element={<Login/>}/>
                    <Route path="/conference/:roomName" element={<ConfStart/>}/>
                </Routes>
            </div>
        ) : (
            <Layout>
                <div className="d-flex flex-grow-1">
                    {showLogo && (
                        <div
                            className="position-absolute"
                            style={{
                                top: '34px',   // Изменить отступ сверху
                                left: '30px',  // Изменить отступ слева
                                padding: '5px'
                            }}>
                            <Logo disableLink={disableLogoLink}/>
                        </div>
                    )}
                    <Routes>
                        <Route path="/under-construction" element={<UnderConstruction />} />
                        <Route path="/register" element={<Register/>}/>
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
    <Router>
        <App/>
    </Router>
);

export default AppWrapper;