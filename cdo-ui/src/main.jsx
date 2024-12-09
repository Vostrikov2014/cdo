import React from 'react';
import { createRoot } from 'react-dom/client'
import App from './App.jsx'
import './index.css'
import 'bootstrap/dist/css/bootstrap.min.css'

import { ReactKeycloakProvider } from '@react-keycloak/web';
import keycloak from './keycloakConfig.jsx';

// Keycloak event and token logging (optional)
const onKeycloakEvent = (event, error) => {
    console.log('Keycloak Event:', event, error);
};

const onKeycloakTokens = (tokens) => {
    console.log('Keycloak Tokens:', tokens);
};

createRoot(document.getElementById('root'))
    .render(
        <ReactKeycloakProvider authClient={keycloak} onEvent={onKeycloakEvent} onTokens={onKeycloakTokens}
                               initOptions = {{ onLoad: 'login-required', checkLoginIframe: false }}>
            <App/>
        </ReactKeycloakProvider>
    );