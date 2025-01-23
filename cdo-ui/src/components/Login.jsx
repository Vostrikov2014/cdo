import React, {useState, useEffect} from 'react';
import axios from 'axios';
import {BASE_URL} from '../config';

//import {ReactKeycloakProvider} from "@react-keycloak/web";
//import keycloakConfig from "./KeycloakConfig.jsx";
//import { useKeycloak } from '@react-keycloak/web';

const Login = () => {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    // Используем хук useKeycloak для получения информации о Keycloak
    /*const { keycloak, initialized } = useKeycloak();

    useEffect(() => {
        if (initialized && keycloak.authenticated) {
            setUsername(keycloak.tokenParsed?.preferred_username || "fix");
        }
    }, [initialized, keycloak]);*/

    const handleLogin = (e) => {
        e.preventDefault();
        axios.post(`${BASE_URL}/auth/login`, {username, password})
            .then(() => window.location = '/home')
            .catch((err) => {
                console.error(err);
                alert("Invalid credentials / Недействительные учетные данные")
            });
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
