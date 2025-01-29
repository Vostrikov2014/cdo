import React, {useEffect, useState} from 'react';
import {useNavigate, useLocation} from 'react-router-dom';
import DateTimePicker from 'react-datetime-picker';
import 'react-datetime-picker/dist/DateTimePicker.css';
import axios from 'axios';
import {BASE_URL} from "../../config.js";
import axiosInstance from "../../axiosConfig.js";

const ConfCreateUpdate = () => {
    //это заполнение поумолчанию
    const [conference, setConference] = useState({
        conferenceName: '',
        startTime: new Date(),
        endTime: new Date(),
        conferenceUrl: '',
        hostUsername: ''
    });

    const navigate = useNavigate();
    const location = useLocation();
    const {conferenceData} = location.state || {};

    useEffect(() => {
        // Это заполнялка для редактирования
        if (conferenceData) {
            setConference({
                id: conferenceData.id,
                conferenceName: conferenceData.conferenceName,
                startTime: conferenceData.startTime,
                endTime: conferenceData.endTime,
                conferenceUrl: conferenceData.conferenceUrl,
                hostUsername: conferenceData.hostUsername
            });
        }
    }, [conferenceData]);

    const handleTimeChange = (field, value) => {
        setConference({...conference, [field]: value});
    };

    const handleChange = (e) => {
        setConference({...conference, [e.target.name]: e.target.value});
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const conferenceDataToSend = {
            id: conference.id,
            conferenceName: conference.conferenceName,
            startTime: conference.startTime,
            endTime: conference.endTime,
            conferenceUrl: conference.conferenceUrl,
            hostUsername: conference.hostUsername
        };

        try {
            if (conferenceDataToSend.id) {
                const response = await axiosInstance.put(`${BASE_URL}/conferences`, conferenceDataToSend);
                console.log('ConfStart updated:', response.data);
            } else {
                const response = await axiosInstance.post(`${BASE_URL}/conferences`, conferenceDataToSend);
                console.log('ConfStart created:', response.data);
            }
            navigate('/list-conference');
        } catch (error) {
            console.error('Error to create or update conference:', error);
        }
    };

    return (
        <div className="container-fluid d-flex flex-column" style={{minHeight: '100vh'}}>
            <div className="container-fluid p-3">
                <h3 className="text mb-3" style={{fontWeight: 'bold'}}>
                    {conferenceData ? `Редактировать "${conference.conferenceName}"` : "Запланировать конференцию"}
                </h3>
                <form onSubmit={handleSubmit}>
                    <div className="mb-3 d-flex align-items-center">
                        <label htmlFor="conferenceName" className="form-label" style={{width: '150px'}}>Тема:</label>
                        <input
                            type="text"
                            name="conferenceName"
                            className="form-control"
                            value={conference.conferenceName}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div className="mb-3 d-flex align-items-center">
                        <label htmlFor="startTime" className="form-label" style={{width: '150px'}}>Дата начала:</label>
                        <DateTimePicker
                            onChange={(value) => handleTimeChange('startTime', value)}
                            value={conference.startTime}
                            className="custom-date-time-picker"
                        />
                    </div>
                    <div className="mb-3 d-flex align-items-center">
                        <label htmlFor="endTime" className="form-label" style={{width: '150px'}}>Дата окончания:</label>
                        <DateTimePicker
                            onChange={(value) => handleTimeChange('endTime', value)}
                            value={conference.endTime}
                            className="custom-date-time-picker"
                        />
                    </div>
                    <button type="submit" className="btn btn-primary" style={{backgroundColor: '#0f47ad'}}>
                        Сохранить
                    </button>
                </form>
            </div>
        </div>
    );
};

export default ConfCreateUpdate;
