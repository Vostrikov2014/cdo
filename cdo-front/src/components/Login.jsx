import React, {useState} from 'react';
import axios from 'axios';
import {BASE_URL} from '../config';

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = (e) => {
        e.preventDefault();
        axios.post(`${BASE_URL}/auth/login`, {username, password})
            .then(() => window.location = '/home')
            .catch((error) => {
                console.error(error);
                alert("Invalid credentials / Недействительные учетные данные")
            });
    };

    return (
        <div className="d-flex justify-content-center align-items-center"
             style={{
                 height: '100vh',
                 width: '100vw',
                 backgroundColor: 'lightgreen',
                 backgroundImage: `url(/images/MoscowStateUniversity.webp)`,
                 backgroundSize: 'cover',
                 backgroundPosition: 'center',
                 backgroundRepeat: 'no-repeat'
             }}
        >
            <h1
                style={{
                    color: 'orange',
                    position: 'absolute',
                    top: '20px',
                    right: '20px',
                    fontWeight: 'bold'
                }}
            >
                CDO:ONLINE
            </h1>
            <div className="card p-4" style={{width: '400px'}}>
                <h2 className="text-center">Login</h2>
                <form onSubmit={handleLogin}>
                    <div className="form-group mb-3">
                        <label>Username</label>
                        <input type="text" className="form-control" placeholder="Enter your username" value={username}
                               onChange={(e) => setUsername(e.target.value)}/>
                    </div>
                    <div className="form-group mb-3">
                        <label>Password</label>
                        <input type="password" className="form-control" placeholder="Enter your password"
                               value={password}
                               onChange={(e) => setPassword(e.target.value)}/>
                    </div>
                    <div className="mb-3 form-check">
                        <input type="checkbox" className="form-check-input" id="rememberMe"/>
                        <label className="form-check-label" htmlFor="rememberMe">Remember me</label>
                    </div>
                    <button type="submit" className="btn btn-primary w-100">Login</button>
                </form>
                <div className="mt-3 text-center">
                    <p>Don't have an account? <a href="/reg/register" className="btn btn-link">Register</a></p>
                </div>
            </div>
        </div>
    );
};

export default Login;
