import React, {useEffect, useState} from 'react';
import axios from 'axios';
import {BASE_URL} from "../../config.js";
import {Link} from 'react-router-dom';
import ConfDelete from './ConfDelete.jsx';
import axiosInstance from "../../axiosConfig.js";

const ConfList = () => {
    const [conferences, setConferences] = useState([]);
    const [error, setError] = useState(null);
    const [activeTab, setActiveTab] = useState('all');


    /*useEffect(() => {
        axiosInstance.get(`${BASE_URL}/conferences`).then(response => {
            setConferences(response.data)
        }).catch((error) => {
            setError("There was an error fetching the Conference list. " + error);
        })
    }, []);*/

    useEffect(() => {
        const fetchConferences = async () => {
            try {
                const response = await axiosInstance.get(`${BASE_URL}/conferences`);
                setConferences(response.data);
            } catch (error) {
                setError("'There was an error fetching the Conference list. " + error);
            }
        };

        fetchConferences();
    }, []);

    if (error) return <div>{error}</div>;

    const now = new Date();
    const allConferences = conferences;
    const upcomingConferences = conferences.filter(conference => new Date(conference.startTime) > now);
    const pastConferences = conferences.filter(conference => new Date(conference.startTime) <= now);

    const handleDelete = async (id) => {
        if (window.confirm('Вы уверены, что хотите удалить эту конференцию?')) {
            try {
                await axiosInstance.delete(`${BASE_URL}/conference/${id}`);
                setConferences(conferences.filter(conference => conference.id !== id)); // Обновляем список конференций
                console.log(`Конференция с id: ${id} удалена`);
            } catch (error) {
                console.error('Ошибка при удалении конференции:', error);
            }
        }
    };

    return (
        <div className="container" style={{minHeight: '100vh', paddingTop: '0px'}}>
            <div className="container-fluid p-2">
                <h3 className="text mb-3" style={{fontWeight: 'bold'}}>Конференции</h3>
                <Link to="/create-conference" className="btn btn-primary mb-3" style={{backgroundColor: '#0f47ad'}}>
                    + Запланировать конференцию
                </Link>

                <div className="d-flex justify-content mb-4">
                    <button
                        className={`btn btn-link ${activeTab === 'all' ? 'text-primary' : ''}`}
                        onClick={() => setActiveTab('all')}
                    >
                        Все
                    </button>
                    <button
                        className={`btn btn-link ${activeTab === 'upcoming' ? 'text-primary' : ''}`}
                        onClick={() => setActiveTab('upcoming')}
                    >
                        Предстоящие
                    </button>
                    <button
                        className={`btn btn-link ${activeTab === 'past' ? 'text-primary' : ''}`}
                        onClick={() => setActiveTab('past')}
                    >
                        Прошедшие
                    </button>
                </div>

                {activeTab === 'all' && (
                    allConferences.length > 0 ? (
                        <table className="table table-striped">
                            <thead>
                            <tr>
                                <th>Conference Name</th>
                                <th>Start Time</th>
                                <th>End Time</th>
                                <th>Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            {allConferences.map((conference) => (
                                <tr key={conference.id}>
                                    <td>{conference.conferenceName}</td>
                                    <td>{new Date(conference.startTime).toLocaleString()}</td>
                                    <td>{new Date(conference.endTime).toLocaleString()}</td>
                                    <td>
                                        <a href={`/conference/${conference.id}`} className="btn btn-primary me-2"
                                           style={{backgroundColor: '#0f47ad'}}>
                                            Начать
                                        </a>
                                        <Link
                                            to="/create-conference"
                                            state={{conferenceData: conference}} // передаем данные конференции
                                            className="btn btn-secondary me-2"
                                        >
                                            Редактировать
                                        </Link>
                                        <button onClick={() => handleDelete(conference.id)} className="btn btn-danger">
                                            Удалить
                                        </button>
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    ) : (
                        <div className="text-center">
                            <h5>No upcoming conferences found.</h5>
                        </div>
                    )
                )}

                {activeTab === 'upcoming' && (
                    upcomingConferences.length > 0 ? (
                        <table className="table table-striped">
                            <thead>
                            <tr>
                            <th>Conference Name</th>
                                <th>Start Time</th>
                                <th>End Time</th>
                                <th>Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            {upcomingConferences.map((conference) => (
                                <tr key={conference.id}>
                                    <td>{conference.conferenceName}</td>
                                    <td>{new Date(conference.startTime).toLocaleString()}</td>
                                    <td>{new Date(conference.endTime).toLocaleString()}</td>
                                    <td>
                                        <a href={`/conference/${conference.id}`} className="btn btn-primary me-2"
                                           style={{backgroundColor: '#0f47ad'}}>
                                            Начать
                                        </a>
                                        <Link
                                            to="/create-conference"
                                            state={{conferenceData: conference}} // передаем данные конференции
                                            className="btn btn-secondary me-2"
                                        >
                                            Редактировать
                                        </Link>
                                        <button onClick={() => handleDelete(conference.id)} className="btn btn-danger">
                                            Удалить
                                        </button>
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    ) : (
                        <div className="text-center">
                            <h5>No upcoming conferences found.</h5>
                        </div>
                    )
                )}

                {activeTab === 'past' && (
                    pastConferences.length > 0 ? (
                        <table className="table table-striped">
                            <thead>
                            <tr>
                                <th>Conference Name</th>
                                <th>Start Time</th>
                                <th>End Time</th>
                                <th>Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            {pastConferences.map((conference) => (
                                <tr key={conference.id}>
                                    <td>{conference.conferenceName}</td>
                                    <td>{new Date(conference.startTime).toLocaleString()}</td>
                                    <td>{new Date(conference.endTime).toLocaleString()}</td>
                                    <td>
                                        <a href={`/conference/${conference.id}`} className="btn btn-primary me-2"
                                           style={{backgroundColor: '#0f47ad'}}>
                                            Начать
                                        </a>
                                        <Link
                                            to="/create-conference"
                                            state={{conferenceData: conference}} // передаем данные конференции
                                            className="btn btn-secondary me-2"
                                        >
                                            Редактировать
                                        </Link>
                                        <button onClick={() => handleDelete(conference.id)} className="btn btn-danger">
                                            Удалить
                                        </button>
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    ) : (
                        <div className="text-center">
                            <h5>No past conferences found.</h5>
                        </div>
                    )
                )}
            </div>
        </div>
    );
};

export default ConfList;
