import React, { useContext, useEffect } from 'react';
import { AuthContext } from './AuthProvider.jsx';
import { useNavigate } from 'react-router-dom';

const Callback = () => {
  const { user } = useContext(AuthContext);
  const navigate = useNavigate();

  useEffect(() => {
    if (user) {
      navigate('/home');
    }
  }, [user, navigate]);

  return <div>Processing authentication...</div>;
};

export default Callback;