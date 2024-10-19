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
                color: 'black',         // Цвет текста
            }}>
            <div className="container mt-5">
                <h1 className="text-center mb-4" style={{fontWeight: 'bold'}}>Your Conferences</h1>
                <div className="d-flex justify-content mb-4">
                    <a href="/create-conference"
                       className="btn btn-lg"
                       style={{
                           fontWeight: 'bold',
                           backgroundColor: '#e0956a',  // Light green color
                           color: 'white',              // Text color
                           border: '1px solid rgba(0, 0, 0, 0.1)'  // Light border for better visibility
                       }}>
                        Create New Conference
                    </a>
                </div>
                <div className="row">
                    {conferences.length > 0 ? (
                        conferences.map((conference) => (
                            <div className="col-md-4 mb-4" key={conference.id}>
                                <div className="card" style={{
                                    backgroundColor: 'rgba(255, 255, 255, 0.5)',
                                    border: '1px solid rgba(0, 0, 0, 0.1)',
                                }}>
                                    <div className="card-body">
                                        <h5 className="card-title">{conference.conferenceName}</h5>
                                        <p className="card-text">Start
                                            Time: {new Date(conference.startTime).toLocaleString()}</p>
                                        <p className="card-text">End
                                            Time: {new Date(conference.endTime).toLocaleString()}</p>
                                        <a href={`/conference/${conference.conferenceName}`}
                                           className="btn btn-primary" style={{backgroundColor: '#0f47ad'}}>
                                            Start conf
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
