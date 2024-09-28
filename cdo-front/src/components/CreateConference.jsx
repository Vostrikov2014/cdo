import React, { useState } from 'react';

const CreateConference = () => {
    const [conference, setConference] = useState({
        conferenceName: '',
        startTime: '',
        endTime: ''
    });

    const handleChange = (e) => {
        setConference({
            ...conference,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        // You would send the data to the backend here, e.g., using fetch or axios
        console.log('Conference created:', conference);
    };

    return (
        <div className="container">
            <h1>Создать новую конференцию</h1>
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label htmlFor="conferenceName" className="form-label">Название конференции:</label>
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
                    <label htmlFor="startTime" className="form-label">Start Time</label>
                    <input
                        type="datetime-local"
                        name="startTime"
                        className="form-control"
                        value={conference.startTime}
                        onChange={handleChange}
                    />
                </div>

                <div className="mb-3">
                    <label htmlFor="endTime" className="form-label">End Time</label>
                    <input
                        type="datetime-local"
                        name="endTime"
                        className="form-control"
                        value={conference.endTime}
                        onChange={handleChange}
                    />
                </div>

                <button type="submit" className="btn btn-primary">Create</button>
            </form>
        </div>
    );
};

export default CreateConference;
