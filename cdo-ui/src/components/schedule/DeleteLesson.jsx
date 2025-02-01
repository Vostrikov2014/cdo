import React, { useState, useEffect } from 'react';
import axiosInstance from '../../axiosConfig.js';
import { Link } from 'react-router-dom';
import {BASE_URL} from '../../config';

const DeleteLesson = async (id) => {
    try {
        await axiosInstance.delete(`${BASE_URL}/api/lessons/${id}`);
        fetchLessons(); // Обновляем список после удаления
    } catch (error) {
        console.error('Error deleting lesson:', error);
    }
};