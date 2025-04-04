import React, { createContext, useEffect, useState } from 'react';
import axios from 'axios';
import { authConfig } from './config';
import { generatePKCE } from './pkce';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [accessToken, setAccessToken] = useState(null);

  const login = async () => {
    const { codeVerifier, codeChallenge } = await generatePKCE();

    localStorage.setItem('pkce_verifier', codeVerifier);

    const params = new URLSearchParams({
      response_type: 'code',
      client_id: authConfig.clientId,
      scope: authConfig.scopes.join(' '),
      redirect_uri: authConfig.redirectUri,
      code_challenge: codeChallenge,
      code_challenge_method: 'S256'
    });

    window.location = `${authConfig.issuer}${authConfig.authorizationEndpoint}?${params.toString()}`;
  };

  const handleCallback = async () => {
    const urlParams = new URLSearchParams(window.location.search);
    const code = urlParams.get('code');
    const codeVerifier = localStorage.getItem('pkce_verifier');

    if (code) {
      try {
        const response = await axios.post(
          `${authConfig.issuer}${authConfig.tokenEndpoint}`,
          new URLSearchParams({
            grant_type: 'authorization_code',
            code,
            redirect_uri: authConfig.redirectUri,
            client_id: authConfig.clientId,
            code_verifier: codeVerifier
          }),
          {
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded',
              Authorization: `Basic ${btoa(`${authConfig.clientId}:${authConfig.clientSecret}`)}`
            }
          }
        );

        setAccessToken(response.data.access_token);
        await fetchUserInfo(response.data.access_token);
        localStorage.removeItem('pkce_verifier');
      } catch (error) {
        console.error('Error during token exchange:', error);
      }
    }
  };

  const fetchUserInfo = async (token) => {
    try {
      const response = await axios.get(`${authConfig.issuer}/userinfo`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      setUser(response.data);
    } catch (error) {
      console.error('Error fetching user info:', error);
    }
  };

  const logout = () => {
    setUser(null);
    setAccessToken(null);
    window.location = `${authConfig.issuer}/logout?client_id=${authConfig.clientId}&post_logout_redirect_uri=${window.location.origin}`;
  };

  useEffect(() => {
    if (window.location.pathname === '/callback') {
      handleCallback();
    }
  }, []);

  return (
    <AuthContext.Provider value={{ user, accessToken, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
