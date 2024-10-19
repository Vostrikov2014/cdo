import React from 'react';
import { Link } from 'react-router-dom';
import Logo from './Logo';

const Index = () => {
    return (
        <div className="container">
            <h1>Enter conference ID</h1>
            <input type="text" placeholder="Conference ID" />
            <p>
                <Link to="/login">Log in</Link>
            </p>
        </div>
    );
};

export default Index;