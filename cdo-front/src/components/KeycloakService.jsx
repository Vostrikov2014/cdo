import Keycloak from 'keycloak-js';

const keycloak = new Keycloak({
    url: 'http://localhost:8091',
    realm: 'cdo-realm',
    clientId: 'cdo-client',
});

export const initKeycloak = (onAuthenticatedCallback) => {
    keycloak.init({ onLoad: 'login-required' }).then((authenticated) => {
        if (authenticated) {
            onAuthenticatedCallback();
        } else {
            keycloak.login();
        }
    });
};

export const doLogout = keycloak.logout;
export const getToken = () => keycloak.token;
export const isLoggedIn = () => !!keycloak.token;
export const updateToken = (successCallback) =>
    keycloak.updateToken(5).then(successCallback).catch(doLogout);

export default keycloak;
