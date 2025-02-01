import React, { useState, useEffect } from 'react';
import axiosInstance from '../../axiosConfig.js';
import { useParams, useNavigate } from 'react-router-dom';

const EditLesson = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        subjectId: '',
        classroomId: '',
        startTime: '',
        durationMinutes: 45,
        dayOfWeek: 'MONDAY'
    });

    useEffect(() => {
        fetchLesson();
    }, []);

    const fetchLesson = async () => {
        try {
            const response = await axiosInstance.get(`/api/lessons/${id}`);
            setFormData(response.data);
        } catch (error) {
            console.error('Error fetching lesson:', error);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axiosInstance.put(`/api/lessons/${id}`, formData);
            navigate('/schedule'); // Перенаправляем на страницу расписания
        } catch (error) {
            console.error('Error updating lesson:', error);
        }
    };

    return (
        <div>
            <h1>Edit Lesson</h1>
            <form onSubmit={handleSubmit}>
                {/* Форма аналогична CreateLesson.jsx */}
            </form>
        </div>
    );
};

export default EditLesson;