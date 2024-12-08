import React from 'react';
import { createRoot } from 'react-dom/client'
import App from './App.jsx'
import './index.css'
import 'bootstrap/dist/css/bootstrap.min.css'

import { ReactKeycloakProvider } from '@react-keycloak/web';
import Keycloak from 'keycloak-js';
import keycloakConfig from './keycloakConfig.jsx';

// Инициализация Keycloak
const keycloak = new Keycloak(keycloakConfig);

createRoot(document.getElementById('root'))
    .render(
        <ReactKeycloakProvider authClient = { keycloak }
                               initOptions = {{ onLoad: 'login-required', checkLoginIframe: false }}>
                <App/>
        </ReactKeycloakProvider>
    );