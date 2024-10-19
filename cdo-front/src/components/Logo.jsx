// src/components/Logo.jsx
import React from 'react';
import { Link } from 'react-router-dom';

const Logo = () => {
    return (
        <Link to="/">
            <img src="/images/logocdo.svg" alt="Logo" style={{ width: '55px', height: 'auto' }} />
        </Link>
    );
};

export default Logo;
