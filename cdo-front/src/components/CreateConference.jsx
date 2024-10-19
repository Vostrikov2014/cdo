import React, {useState} from 'react';
import {useNavigate} from 'react-router-dom';
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
        <div className="container-fluid d-flex flex-column" style={{ minHeight: '100vh' }}>
            <div className="container-fluid p-3">
                <h3 className="text mb-3" style={{fontWeight: 'bold'}}>Create conferences</h3>
                <form onSubmit={handleSubmit} >
                    <div className="mb-3 d-flex">
                        <label htmlFor="conferenceName" className="form-label">Name conference:</label>
                        <input
                            type="text"
                            name="conferenceName"
                            className="form-control"
                            value={conference.conferenceName}
                            onChange={handleChange}
                            required
                            style={{ width: '100%' }}
                        />
                    </div>
                    <div className="mb-3 d-flex">
                        <label htmlFor="startTime" className="form-label">Start data-time</label>
                        <DateTimePicker
                            onChange={(value) => handleTimeChange('startTime', value)}
                            value={conference.startTime}
                            className="form-control"
                            required
                            style={{ width: '100%' }}
                        />
                    </div>
                    <div className="mb-3 d-flex">
                        <label htmlFor="endTime" className="form-label">End data-time</label>
                        <DateTimePicker
                            onChange={(value) => handleTimeChange('endTime', value)}
                            value={conference.endTime}
                            className="form-control"
                            required
                            style={{ width: '100%' }}
                        />
                    </div>
                    <button type="submit" className="btn btn-primary"
                            style={{backgroundColor: '#0f47ad'}}>Create
                    </button>
                </form>
            </div>
        </div>
    );
};

export default CreateConference;
