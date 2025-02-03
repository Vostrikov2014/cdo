import React, { useState, useEffect } from 'react';
import axiosInstance from '../../axiosConfig.js';
import { Link } from 'react-router-dom';
import {BASE_URL} from '../../config';

const Schedule = () => {
    const [lessons, setLessons] = useState([]);

    useEffect(() => {
        fetchLessons();
    }, []);

    const fetchLessons = async () => {
        try {
            const response = await axiosInstance.get(`${BASE_URL}/api/lessons`);
            setLessons(response.data);
        } catch (error) {
            console.error('Error fetching lessons:', error);
        }
    };

    return (
        <div>
            <h1>School Schedule</h1>
            <Link to="/create-lesson" className="btn btn-primary mb-3">
                Create Lesson
            </Link>
            <table className="table">
                <thead>
                <tr>
                    <th>Day</th>
                    <th>Time</th>
                    <th>Student</th>
                    <th>Classroom</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {lessons.map(lesson => (
                    <tr key={lesson.id}>
                        <td>{lesson.dayOfWeek}</td>
                        <td>{lesson.startTime} - {lesson.endTime}</td>
                        <td>{lesson.student?.name || 'N/A'}</td>
                        <td>{lesson.classroom?.roomNumber || 'N/A'}</td>
                        <td>
                            <Link to={`/edit-lesson/${lesson.id}`} className="btn btn-sm btn-warning me-2">
                                Edit
                            </Link>
                            <button
                                className="btn btn-sm btn-danger"
                                onClick={() => deleteLesson(lesson.id)}
                            >
                                Delete
                            </button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );

    const deleteLesson = async (id) => {
        try {
            await axiosInstance.delete(`${BASE_URL}/api/lessons/${id}`);
            fetchLessons(); // Обновляем список после удаления
        } catch (error) {
            console.error('Error deleting lesson:', error);
        }
    };
};

export default Schedule;