import React from 'react';

const Home = () => {
    return (
        <div
            style={{
                position: 'relative',   // Контейнер остается относительно страницы
                height: '100vh',        // Полная высота экрана
                width: '100vw',         // Полная ширина экрана
                padding: '20px',        // Отступы
                color: 'black'
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

            <h1>Welcom!</h1>
            <a href="/create-conference">Create new conference</a>
            <h2>Yours conference:</h2>
        </div>

    )
};

export default Home;
