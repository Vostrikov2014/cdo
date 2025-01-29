import React from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { BASE_URL } from "../../config.js";
import axiosInstance from "../../axiosConfig.js";

const ConfDelete = ({ conferenceId }) => {
    const navigate = useNavigate();

    const handleDelete = async () => {
        if (window.confirm('Вы уверены, что хотите удалить эту конференцию?')) {
            try {
                await axiosInstance.delete(`${BASE_URL}/conference/${conferenceId}`);
                console.log('Конференция удалена');
                navigate('/list-conference'); // Перенаправление на главную страницу
            } catch (error) {
                console.error('Ошибка при удалении конференции:', error);
            }
        }
    };

    return (
        <div>
            <button type="button" className="btn btn-danger" onClick={handleDelete}>
                Удалить
            </button>
        </div>
    );
};

export default ConfDelete;
