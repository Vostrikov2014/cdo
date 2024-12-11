import React, { useState } from 'react';
import { Link } from 'react-router-dom';

const Index = () => {

    const [conferenceId, setConferenceId] = useState(''); // Состояние для хранения ID конференции

    const handleStartConference = () => {
        // Логика для запуска конференции
        if (conferenceId) {
            console.log(`Starting conference with ID: ${conferenceId}`);
            // Здесь можно добавить логику для перехода на страницу конференции
            // Например, можно использовать navigate для перехода на страницу конференции
            // navigate(`/conference/${conferenceId}`);
        }
    };

    return (
        <div
            style={{
                //position: 'relative',         // Устанавливаем относительное позиционирование для корневого контейнера
                height: '100vh',              // Полная высота экрана
                display: 'flex',              // Используем Flexbox
                flexDirection: 'column',      // Вертикальное расположение элементов
                justifyContent: 'flex-start', // Выравнивание по верхнему краю
                alignItems: 'center',         // Центрирование по горизонтали
                color: 'white',               // Цвет текста
                padding: '145px',             // Отступы для контейнера
            }}
        >
            <div className="text-center">
                <h1 className="mb-2" style={{fontWeight: 'bold', fontSize: '3.5rem'}}>CDO:ONLINE</h1>
                <h5 className="mb-0" style={{fontWeight: 'bold'}}>
                    Distance learning linter SPA Lomonosov Moscow State University
                </h5>
            </div>
            {/* Контейнер для input и кнопки */}
            <div
                style={{
                    position: 'relative',      // Позиционирование для кнопки внутри контейнера
                    marginTop: '10px'          // Отступ сверху для визуального разделения
                }}>
                <input
                    type = "text"
                    placeholder = "ConfStart ID"
                    className = "form-control mb-3"
                    style = {{ width: '470px', height: '65px', paddingRight: '70px' }} // Отступ для кнопки
                    value = {conferenceId} // Устанавливаем значение из состояния
                    onChange = {(e) => setConferenceId(e.target.value)} // Обновляем состояние при изменении
                />
                <button
                    onClick={handleStartConference}
                    className="btn btn-danger"  // Класс Bootstrap для кнопки
                    style={{
                        position: 'absolute',
                        right: '5px',
                        top: '39%',
                        transform: 'translateY(-50%)',
                        display: 'flex',          // Используем flexbox для центровки
                        justifyContent: 'center', // Центрируем по горизонтали
                        alignItems: 'center',     // Центрируем по вертикали
                        backgroundColor: 'red',   // Красный цвет для кнопки
                        width: '50px',
                        height: '55px',
                        fontSize: '2rem',    // Размер шрифта
                        border: 'none',      // Убираем рамку
                        padding: '0',        // Убираем отступы
                        borderRadius: '5px', // Закругленные углы
                        cursor: 'pointer',   // Курсор в виде указателя

                    }}>
                    &#9675;
                </button>
            </div>
        </div>
    );
};

export default Index;
