import React from 'react';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Home from './components/Home.jsx';
import Login from './components/Login.jsx';
import Register from './components/Register.jsx';
import Conference from "./components/Conference.jsx";
import CreateConference from "./components/CreateConference.jsx";
import ConferenceList from "./components/ConferenceList.jsx";
import ConferenceDetails from "./components/ConferenceDetails.jsx";

const App = () => {
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
            <Router>
                <Routes>
                    <Route path="/" element={<Login/>}/>
                    <Route path="/login" element={<Login/>}/>
                    <Route path="/register" element={<Register/>}/>
                    <Route path="/home" element={<Home/>}/>
                    <Route path="/create-conference" element={<CreateConference />}/>
                    <Route path="/list-conference" element={<ConferenceList />}/>
                    <Route path="/conference/:roomName" element={<Conference />}/>
                    <Route path="/conference-details/:id" element={<ConferenceDetails />}/>
                </Routes>
            </Router>
        </div>
    );
};

export default App;
