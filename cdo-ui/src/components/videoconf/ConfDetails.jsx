import React, { useState, useEffect } from 'react';
import axios from 'axios';
import {BASE_URL} from "../../config.js";
import axiosInstance from "../../axiosConfig.js";

const ConfDetails = ({ id }) => {
    const [conference, setConference] = useState(null);

    useEffect(() => {
        const fetchConference = async () => {
            try {
                const response = await axiosInstance.get(`${BASE_URL}/conference/${id}`);
                setConference(response.data);
            } catch (error) {
                console.error('Error fetching conference', error);
            }
        };

        fetchConference();
    }, [id]);

    if (!conference) {
        return <p>Loading conference details...</p>;
    }

    return (
        <div>
            <h2>{conference.conferenceName}</h2>
            <p>URL: <a href={conference.conferenceUrl} target="_blank" rel="noopener noreferrer">{conference.conferenceUrl}</a></p>
        </div>
    );
};

export default ConfDetails;
