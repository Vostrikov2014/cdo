import React, { useState, useEffect } from 'react';
import axiosInstance from '../../axiosConfig.js';
import { Link } from 'react-router-dom';

const StudentList = () => {
    const [students, setStudents] = useState([]);

    useEffect(() => {
        fetchStudents();
    }, []);

    const fetchStudents = async () => {
        try {
            const response = await axiosInstance.get('/api/students');
            setStudents(response.data);
        } catch (error) {
            console.error('Error fetching students:', error);
        }
    };

    return (
        <div>
            <h1>Students</h1>
            <Link to="/add-student" className="btn btn-primary mb-3">
                Add Student
            </Link>
            <table className="table">
                <thead>
                <tr>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Email</th>
                </tr>
                </thead>
                <tbody>
                {students.map(student => (
                    <tr key={student.id}>
                        <td>{student.firstName}</td>
                        <td>{student.lastName}</td>
                        <td>{student.email}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default StudentList;