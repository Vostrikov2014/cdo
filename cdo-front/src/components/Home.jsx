import React, {useEffect, useState} from 'react';
import axios from "axios";
import {BASE_URL} from "../config.js";

const Home = () => {

    const [conferences, setConferences] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchConferences = async () => {
            try {
                const response = await axios.get(`${BASE_URL}/conference/list`); // Adjust the URL as needed
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

    return (
        <div
            style={{
                position: 'relative',   // Контейнер остается относительно страницы
                height: '100vh',        // Полная высота экрана
                width: '100vw',         // Полная ширина экрана
                padding: '20px',        // Отступы
                color: 'wight'
            }}>

            <h1
                style={{
                    color: 'orange',
                    position: 'absolute', // Абсолютное позиционирование
                    top: '20px',
                    right: '20px',        // Привязка к правому верхнему углу
                    fontWeight: 'bold'
                }}>
                CDO:ONLINE
            </h1>

            <div className="container mt-5">
                <h1>Welcom!</h1>
                <a href="/create-conference">Create new conference</a>
                <h2 className="text-center mb-4" style={{fontWeight: 'bold'}}>Your Conferences</h2>
                <div className="row">
                    {conferences.length > 0 ? (
                        conferences.map((conference) => (
                            <div className="col-md-4 mb-4" key={conference.id}>
                                <div className="card">
                                    <div className="card-body">
                                        <h5 className="card-title">{conference.conferenceName}</h5>
                                        <p className="card-text">Start Time: {new Date(conference.startTime).toLocaleString()}</p>
                                        <p className="card-text">End Time: {new Date(conference.endTime).toLocaleString()}</p>
                                        <a href={`/conference/${conference.conferenceName}`} className="btn btn-primary">
                                            View Conference
                                        </a>
                                    </div>
                                </div>
                            </div>
                        ))
                    ) : (
                        <div className="col-12 text-center">
                            <h5>No conferences found.</h5>
                        </div>
                    )}
                </div>
            </div>
        </div>

    )
};

export default Home;
