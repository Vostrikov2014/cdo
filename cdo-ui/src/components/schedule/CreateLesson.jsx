import React, { useState } from 'react';
import axiosInstance from '../../axiosConfig.js';
import { useNavigate } from 'react-router-dom';
import {BASE_URL} from "../../config.js";

const CreateLesson = () => {
    const [formData, setFormData] = useState({
        studentId: '',
        classroomId: '',
        startTime: '',
        durationMinutes: 45,
        dayOfWeek: 'MONDAY'
    });
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axiosInstance.post(`${BASE_URL}/api/lessons`, formData);
            navigate('/schedule'); // Перенаправляем на страницу расписания
        } catch (error) {
            console.error('Error creating lesson:', error);
        }
    };

    return (
        <div>
            <h1>Create Lesson</h1>
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label>Student ID</label>
                    <input
                        type="text"
                        className="form-control"
                        value={formData.studentId}
                        onChange={(e) => setFormData({ ...formData, subjectId: e.target.value })}
                    />
                </div>
                <div className="mb-3">
                    <label>Classroom ID</label>
                    <input
                        type="text"
                        className="form-control"
                        value={formData.classroomId}
                        onChange={(e) => setFormData({ ...formData, classroomId: e.target.value })}
                    />
                </div>
                <div className="mb-3">
                    <label>Start Time</label>
                    <input
                        type="time"
                        className="form-control"
                        value={formData.startTime}
                        onChange={(e) => setFormData({ ...formData, startTime: e.target.value })}
                    />
                </div>
                <div className="mb-3">
                    <label>Duration (minutes)</label>
                    <input
                        type="number"
                        className="form-control"
                        value={formData.durationMinutes}
                        onChange={(e) => setFormData({ ...formData, durationMinutes: e.target.value })}
                    />
                </div>
                <div className="mb-3">
                    <label>Day of Week</label>
                    <select
                        className="form-control"
                        value={formData.dayOfWeek}
                        onChange={(e) => setFormData({ ...formData, dayOfWeek: e.target.value })}
                    >
                        <option value="MONDAY">Monday</option>
                        <option value="TUESDAY">Tuesday</option>
                        <option value="WEDNESDAY">Wednesday</option>
                        <option value="THURSDAY">Thursday</option>
                        <option value="FRIDAY">Friday</option>
                        <option value="SATURDAY">Saturday</option>
                        <option value="SUNDAY">Sunday</option>
                    </select>
                </div>
                <button type="submit" className="btn btn-primary">Create</button>
            </form>
        </div>
    );
};

export default CreateLesson;