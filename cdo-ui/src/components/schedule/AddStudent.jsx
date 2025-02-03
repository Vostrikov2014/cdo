import React, { useState } from 'react';
import axiosInstance from '../../axiosConfig.js';
import { useNavigate } from 'react-router-dom';

const AddStudent = () => {
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        email: ''
    });
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axiosInstance.post('/api/students', formData);
            navigate('/students'); // Перенаправляем на страницу со списком студентов
        } catch (error) {
            console.error('Error creating student:', error);
        }
    };

    return (
        <div>
            <h1>Add Student</h1>
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label>First Name</label>
                    <input
                        type="text"
                        className="form-control"
                        value={formData.firstName}
                        onChange={(e) => setFormData({ ...formData, firstName: e.target.value })}
                    />
                </div>
                <div className="mb-3">
                    <label>Last Name</label>
                    <input
                        type="text"
                        className="form-control"
                        value={formData.lastName}
                        onChange={(e) => setFormData({ ...formData, lastName: e.target.value })}
                    />
                </div>
                <div className="mb-3">
                    <label>Email</label>
                    <input
                        type="email"
                        className="form-control"
                        value={formData.email}
                        onChange={(e) => setFormData({ ...formData, email: e.target.value })}
                    />
                </div>
                <button type="submit" className="btn btn-primary">Add Student</button>
            </form>
        </div>
    );
};

export default AddStudent;