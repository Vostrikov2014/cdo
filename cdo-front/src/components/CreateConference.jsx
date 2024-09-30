import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import DateTimePicker from 'react-datetime-picker'; // Импортируем компонент DateTimePicker
import 'react-datetime-picker/dist/DateTimePicker.css'; // Импортируем стили для DateTimePicker
import axios from 'axios';
import {BASE_URL} from "../config.js";

const CreateConference = () => {
    const [conference, setConference] = useState({
        conferenceName: '',
        startTime: new Date(), // Инициализация с текущей датой и временем
        endTime: new Date()    // Инициализация с текущей датой и временем
    });

    const navigate = useNavigate();

    const handleTimeChange = (field, value) => {
        setConference({
            ...conference,
            [field]: value
        });
    };

    const handleChange = (e) => {
        setConference({
            ...conference,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const conferenceData = {
            conferenceName: conference.conferenceName,
            // Преобразование даты и времени в строку, которая совместима с DateTime
            //startTime: conference.startTime.toISOString().slice(0, 19).replace('T', ' '),
            //endTime: conference.endTime.toISOString().slice(0, 19).replace('T', ' ')
            startTime: conference.startTime,
            endTime: conference.endTime
        };

        try {
            const response = await axios.post(`${BASE_URL}/conference/create`, conferenceData);
            console.log('Conference created:', response.data);
            navigate('/home');
        } catch (error) {
            console.error('Error to сonference created:', error);
        }
    };

    return (
        <div className="card p-5"
             style={{
                 width: '450px',
                 backgroundColor: 'rgba(255, 255, 255, 0.5)',
                 border: '1px solid rgba(0, 0, 0, 0.1)',
             }}>
            <h2>Create conference</h2>
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label htmlFor="conferenceName" className="form-label">Name conference:</label>
                    <input
                        type="text"
                        name="conferenceName"
                        className="form-control"
                        value={conference.conferenceName}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div className="mb-3">
                    <label htmlFor="startTime" className="form-label">Start data-time</label>
                    <DateTimePicker
                        onChange={(value) => handleTimeChange('startTime', value)}
                        value={conference.startTime}
                        className="form-control"
                        required
                    />
                </div>
                <div className="mb-3">
                    <label htmlFor="endTime" className="form-label">End data-time</label>
                    <DateTimePicker
                        onChange={(value) => handleTimeChange('endTime', value)}
                        value={conference.endTime}
                        className="form-control"
                        required
                    />
                </div>
                <button type="submit" className="btn btn-primary w-100" style={{backgroundColor: '#0f47ad'}}>Create</button>
            </form>
        </div>
    );
};

export default CreateConference;
