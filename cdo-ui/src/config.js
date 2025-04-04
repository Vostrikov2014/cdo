export const BASE_URL = 'http://localhost:8090';

export const AUTH_URL = 'http://localhost:9000';

export const authConfig = {
  issuer: 'http://localhost:9000', // URL вашего Authorization Server
  clientId: 'react-client',
  clientSecret: 'secret', // Для демо, в production используйте защищенное хранение
  redirectUri: window.location.origin + '/callback',
  authorizationEndpoint: '/oauth2/authorize',
  tokenEndpoint: '/oauth2/token',
  scopes: ['openid', 'profile', 'email'],
  responseType: 'code',
  pkce: true // Рекомендуется для безопасности
};