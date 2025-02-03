import React, { useState, useEffect } from 'react';
import axiosInstance from '../../axiosConfig.js';
import { Link } from 'react-router-dom';

const ClassroomList = () => {
    const [classrooms, setClassrooms] = useState([]);

    useEffect(() => {
        fetchClassrooms();
    }, []);

    const fetchClassrooms = async () => {
        try {
            const response = await axiosInstance.get('/api/classrooms');
            setClassrooms(response.data);
        } catch (error) {
            console.error('Error fetching classrooms:', error);
        }
    };

    return (
        <div>
            <h1>Classrooms</h1>
            <Link to="/add-classroom" className="btn btn-primary mb-3">
                Add Classroom
            </Link>
            <table className="table">
                <thead>
                <tr>
                    <th>Room Number</th>
                    <th>Capacity</th>
                </tr>
                </thead>
                <tbody>
                {classrooms.map(classroom => (
                    <tr key={classroom.id}>
                        <td>{classroom.roomNumber}</td>
                        <td>{classroom.capacity}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default ClassroomList;