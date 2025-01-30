import React, {useEffect, useState} from 'react';
import axios from "axios";
import {BASE_URL} from "../../config.js";
import axiosInstance from "../../axiosConfig.js";

const HomeVideoConf = () => {

    const [conferences, setConferences] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        axios.get(`${BASE_URL}/conferences`, { withCredentials: true,
        //axiosInstance.get(`${BASE_URL}/conferences`
            /*, {
            //headers: {
            //    'Content-Type': 'application/json',
            //    'Authorization': 'Bearer ' + localStorage.getItem('token')*/
            auth: {
                username: 'vd',
                password: '111'
            }
        }
        ).then(response => {
            setConferences(response.data)
        }).catch((error) => {
            console.log("Получение листа конференций из HomeVideoConf", error);
            setError("Получение листа конференций из HomeVideoConf " + error);
        })
    }, []);

    /*useEffect(() => {
        const fetchConferences = async () => {
            try {
                const response = await axiosInstance.get(`${BASE_URL}/conference/list`); // Adjust the URL as needed
                setConferences(response.data);
                setLoading(false);
            } catch (err) {
                setError(err.response?.data?.message || ' Error fetching conferences');
            }
        };

        fetchConferences().catch(console.error); // Return the promise and handle any errors

    }, []);*/

    if (loading) return <div>Loading...</div>;
    if (error) return <div>{error}</div>;

    return (
        <div className="container"
             style={{minHeight: '100vh', paddingTop: '0px'}}>
            <div className="container-fluid p-2">
                <h3 className="text mb-3" style={{fontWeight: 'bold'}}>Конференции</h3>
                <div className="d-flex justify-content-end mb-4">
                    <a href="/create-conference" className="btn"
                       style={{
                           height: '33px',
                           backgroundColor: '#0f47ad',  // Light green color
                           color: 'white',              // Text color
                           border: '1px solid rgba(0, 0, 0, 0.1)',  // Light border for better visibility
                           padding: '0 20px'
                       }}>
                        + Create new conference
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
                                        <a href={`/conference/${conference.conferenceName}`} className="btn btn-primary"
                                           style={{backgroundColor: '#0f47ad'}}>
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

export default HomeVideoConf;
