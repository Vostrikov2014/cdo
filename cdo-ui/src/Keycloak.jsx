import Keycloak from 'keycloak-js';

const keycloak = new Keycloak({
    url: 'http://localhost:8091',
    realm: 'cdo-realm',
    clientId: 'cdo-client',
});

export default keycloak;
