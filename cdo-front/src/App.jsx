import React from 'react';
import {BrowserRouter as Router, Route, Routes, useLocation} from 'react-router-dom';
import Home from './components/Home.jsx';
import Login from './components/Login.jsx';
import Register from './components/Register.jsx';
import Conference from "./components/Conference.jsx";
import CreateConference from "./components/CreateConference.jsx";
import ConferenceList from "./components/ConferenceList.jsx";
import ConferenceDetails from "./components/ConferenceDetails.jsx";
import Logo from './components/Logo.jsx';

const App = () => {
    const location = useLocation();

    // Conditionally render the Logo component based on the current path
    const showLogo = location.pathname !== '/' && location.pathname !== '/login';

    return (
        <div className="d-flex justify-content-center align-items-center"
             style={{
                 height: '100vh',
                 width: '100vw',
                 backgroundImage: `url(/images/welcome-background.jpg)`,
                 backgroundSize: 'cover',
                 backgroundPosition: 'center',
                 backgroundRepeat: 'no-repeat'
             }}>
            {showLogo && (
                <div className="position-absolute top-0 start-0 p-5">
                    <Logo/>
                </div>
            )}
            <h1
                style={{
                    color: '#e0956a',
                    position: 'absolute', // Абсолютное позиционирование
                    top: '50px',
                    right: '40px',        // Привязка к правому верхнему углу
                    fontWeight: 'bold'
                }}>
                CDO: ONLINE
            </h1>
            <Routes>
                <Route path="/" element={<Login/>}/>
                <Route path="/login" element={<Login/>}/>
                <Route path="/register" element={<Register/>}/>
                <Route path="/home" element={<Home/>}/>
                <Route path="/create-conference" element={<CreateConference/>}/>
                <Route path="/list-conference" element={<ConferenceList/>}/>
                <Route path="/conference/:roomName" element={<Conference/>}/>
                <Route path="/conference-details/:id" element={<ConferenceDetails/>}/>
            </Routes>
        </div>
    );
};

const AppWrapper = () => (
    <Router>
        <App/>
    </Router>
);

export default AppWrapper;
