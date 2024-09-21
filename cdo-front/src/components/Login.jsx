import React, {useState} from 'react';
import axios from 'axios';
import API_BASE_URL from '../config.js';

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = (e) => {
        e.preventDefault();
        axios.post(`${API_BASE_URL}/login`, {username, password})
            .then(() => window.location = '/home')
            .catch((error) => alert("Invalid credentials"));
    };

    return (
        <div className="d-flex justify-content-center align-items-center vh-100">
            <div className="card p-4" style={{width: '400px'}}>
                <h2 className="text-center">Login</h2>
                <form onSubmit={handleLogin}>
                    <div className="form-group mb-3">
                        <label>Username</label>
                        <input type="text" className="form-control" value={username}
                               onChange={(e) => setUsername(e.target.value)}/>
                    </div>
                    <div className="form-group mb-3">
                        <label>Password</label>
                        <input type="password" className="form-control" value={password}
                               onChange={(e) => setPassword(e.target.value)}/>
                    </div>
                    <button type="submit" className="btn btn-primary w-100">Login</button>
                </form>
                <div className="mt-3 text-center">
                    <p>Don't have an account? <a href="/register" className="btn btn-link">Register</a></p>
                </div>
            </div>
        </div>
    );
};

export default Login;
