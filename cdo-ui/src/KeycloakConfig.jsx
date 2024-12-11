import Keycloak from 'keycloak-js';

const keycloakConfig = new Keycloak({
    url: 'http://localhost:8091',
    realm: 'cdo-realm',
    clientId: 'cdo-client',
});

export default keycloakConfig;
