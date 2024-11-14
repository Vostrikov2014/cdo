
import React, { useEffect } from 'react';
import { ReactKeycloakProvider } from '@react-keycloak/web';
import keycloak from './keycloak.jsx';

const KeycloakProvider = ({ children }) => {
    useEffect(() => {
        keycloak.init({ onLoad: 'login-required' }).then(authenticated => {
            console.log(authenticated ? 'Authenticated' : 'Not authenticated');
        });
    }, []);

    return (
        <ReactKeycloakProvider authClient={keycloak}>
            {children}
        </ReactKeycloakProvider>
    );
};

export default KeycloakProvider;
