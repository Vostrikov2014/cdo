import React, { useState } from 'react';
import axiosInstance from '../../axiosConfig.js';
import { useNavigate } from 'react-router-dom';

const AddClassroom = () => {
    const [formData, setFormData] = useState({
        roomNumber: '',
        capacity: 0
    });
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axiosInstance.post('/api/classrooms', formData);
            navigate('/classrooms'); // Перенаправляем на страницу со списком аудиторий
        } catch (error) {
            console.error('Error creating classroom:', error);
        }
    };

    return (
        <div>
            <h1>Add Classroom</h1>
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label>Room Number</label>
                    <input
                        type="text"
                        className="form-control"
                        value={formData.roomNumber}
                        onChange={(e) => setFormData({ ...formData, roomNumber: e.target.value })}
                    />
                </div>
                <div className="mb-3">
                    <label>Capacity</label>
                    <input
                        type="number"
                        className="form-control"
                        value={formData.capacity}
                        onChange={(e) => setFormData({ ...formData, capacity: e.target.value })}
                    />
                </div>
                <button type="submit" className="btn btn-primary">Add Classroom</button>
            </form>
        </div>
    );
};

export default AddClassroom;