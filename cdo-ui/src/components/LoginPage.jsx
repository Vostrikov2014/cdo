import React, {useState, useEffect} from 'react';
import {ReactKeycloakProvider} from "@react-keycloak/web";
import keycloakConfig from "./KeycloakConfig.jsx"
import {useKeycloak} from '@react-keycloak/web';
import {useNavigate} from 'react-router-dom';

const LoginPage = () => {

    const [username, setUsername] = useState('');
    const {keycloak, initialized} = useKeycloak();  //Используем хук useKeycloak для получения информации о Keycloak
    const navigate = useNavigate();                   // React RouterHook для навигации

    useEffect(() => {
        if (initialized && keycloak.authenticated) {
            setUsername(keycloak.tokenParsed?.preferred_username || 'Unknown');
            navigate('/home');   // Redirect to HomePage when authenticated
        }
    }, [initialized, keycloak, navigate]);

    // Логируем значения для отладки
    useEffect(() => {
        console.log("Current Username:", username);
    }, [username]);

    return (
        <div>
            <h1>Добро пожаловать на страницу входа!</h1>
            {username && <p>Загрузка данных пользователя...</p>}
        </div>
    )
};

const LoginPageWrapper = () => (
    <ReactKeycloakProvider authClient={keycloakConfig}
                           initOptions={{onLoad: 'login-required', checkLoginIframe: false}}>
        <LoginPage/>
    </ReactKeycloakProvider>
);

export default LoginPageWrapper;
