import Keycloak from 'keycloak-js';

const keycloakConfig = {
    url: 'http://localhost:8091',
    realm: 'cdo-realm',
    clientId: 'cdo-client',
};

// Initialize the Keycloak instance
const keycloak = new Keycloak(keycloakConfig);

export default keycloak;
