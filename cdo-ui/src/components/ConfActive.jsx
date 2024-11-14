import React, {useEffect, useState} from 'react';
import axios from 'axios';
import {BASE_URL} from "../config.js";
import {Link} from 'react-router-dom';

const ConfActive = () => {
    const [conferences, setConferences] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [activeTab, setActiveTab] = useState('upcoming');

    useEffect(() => {
        const fetchConferences = async () => {
            try {
                const response = await axios.get(`${BASE_URL}/conference/list`);
                setConferences(response.data);
                setLoading(false);
            } catch (err) {
                setError('Error fetching conferences');
                setLoading(false);
            }
        };

        fetchConferences();
    }, []);

    if (loading) return <div>Loading...</div>;
    if (error) return <div>{error}</div>;

    const now = new Date();
    const upcomingConferences = conferences.filter(conference => new Date(conference.startTime) > now);
    const pastConferences = conferences.filter(conference => new Date(conference.startTime) <= now);

    const handleDelete = (id) => {
        console.log(`Удалить конференцию с id: ${id}`);
    };

    return (
        <div className="container" style={{minHeight: '100vh', paddingTop: '0px'}}>
            <div className="container-fluid p-2">
                <h3 className="text mb-3" style={{fontWeight: 'bold'}}>Conferences</h3>
                <Link to="/create-conference" className="btn btn-primary mb-3" style={{backgroundColor: '#0f47ad'}}>
                    + Запланировать конференцию
                </Link>

                <div className="d-flex justify-content mb-4">
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

export default ConfActive;
