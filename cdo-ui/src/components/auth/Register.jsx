import React, {useState} from 'react';
import axios from 'axios';
import {BASE_URL, AUTH_URL} from '../../config.js';
import {useNavigate} from "react-router-dom";

const Register = () => {

    const [formData, setFormData] = useState({
        username: null, email: null, password: null
    })
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(false);
    const navigate = useNavigate();

    const handleChange = (e) => {
        setFormData({...formData, [e.target.name]: e.target.value});
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);
        setSuccess(false);
        try {
            const response = await axios.post(`${AUTH_URL}/api/auth/register`, formData);
            /*const response = await axios.post(`${BASE_URL}/register`, {
                userName: formData.username, password: formData.password,
            const response = await axios.post("http://l
                firstName: formData.firstname, lastName: formData.lastname,
                email: formData.email,
            });*/

            if (response.status === 200 || response.status === 201) {
                setSuccess(true);
                // Доп логика, например, редирект на страницу логина
                navigate('/login')
            } else {
                setError(response.data.message);
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
                    <label>Email:</label>
                    <input type="email" className="form-control" placeholder="Enter your e-mail *"
                           name="username" value={formData.username} onChange={handleChange}
                    />
                </div>
                <div className="form-group mb-3">
                    <label>Password:</label>
                    <input type="password" className="form-control" placeholder="Enter your password *"
                           name="password" value={formData.password} onChange={handleChange} required
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
