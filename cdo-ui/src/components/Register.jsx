import React, {useState} from 'react';
import axios from 'axios';
import {BASE_URL} from '../config';
import {useNavigate} from "react-router-dom";

const Register = () => {

    const [username, setUsername] = useState(null);
    const [password, setPassword] = useState(null);
    const [email, setEmail] = useState(null);
    const [firstname, setFirstname] = useState(null);
    const [lastname, setLastname] = useState(null);
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(false);
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);
        setSuccess(false);

        try {
            const response = await axios.post(`${BASE_URL}/register`, {
                userName: username,
                password,
                firstName: firstname,
                lastName: lastname,
                email,
            });

            if (response.status === 200 || response.status === 201) {
                setSuccess(true);
                // Доп логика, например, редирект на страницу логина
                navigate('/login');
            }
        } catch (err) {
            setError(err.response?.data?.message || 'Ошибка регистрации');
        }
    };

    return (
        <div className="card p-5"
             style={{
                 width: '450px',
                 backgroundColor: 'rgba(255, 255, 255, 0.5)',
                 border: '1px solid rgba(0, 0, 0, 0.1)',
             }}>
            <div className="text-center mb-3">
                <img src="/images/logocdo.svg" alt="Logo" style={{width: '100px'}}/>
            </div>
            <form onSubmit={handleSubmit}>
                <div className="form-group mb-3">
                    <label>User name:</label>
                    <input type="text" className="form-control" placeholder="Enter your username"
                           value={username}
                           onChange={(e) => setUsername(e.target.value)}
                           required
                    />
                </div>
                <div className="form-group mb-3">
                    <label>Password:</label>
                    <input type="password" className="form-control" placeholder="Enter your password"
                           value={password}
                           onChange={(e) => setPassword(e.target.value)}
                           required
                    />
                </div>
                <div className="form-group mb-3">
                    <label>First name:</label>
                    <input type="firstname" className="form-control" placeholder="Enter your firstname"
                           value={firstname}
                           onChange={(e) => setFirstname(e.target.value)}
                    />
                </div>
                <div className="form-group mb-3">
                    <label>Last name:</label>
                    <input type="lastname" className="form-control" placeholder="Enter your lastname"
                           value={lastname}
                           onChange={(e) => setLastname(e.target.value)}
                    />
                </div>
                <div className="form-group mb-3">
                    <label>Email:</label>
                    <input type="email" className="form-control" placeholder="Enter your lastname"
                           value={email}
                           onChange={(e) => setEmail(e.target.value)}
                    />
                </div>
                <button type="submit" className="btn btn-primary w-100" style={{backgroundColor: '#0f47ad'}}>Register
                </button>
                <div className="mt-3 text-center">
                    {error && <p className="error">{error}</p>}
                    {success && <p className="success">Register successfully!</p>}
                </div>
            </form>
        </div>
    );
};

export default Register;
