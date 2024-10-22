import React from 'react';

const UnderConstruction = () => {
    return (
        <div className="d-flex justify-content-center align-items-center" style={{ height: 'center', textAlign: 'center' }}>
            <div>
                <h1 style={{ fontSize: '5rem', margin: 0 }}>🚧</h1>
                <h2 style={{ fontSize: '2rem', margin: '10px 0' }}>Страница в разработке</h2>
                <p style={{ fontSize: '1.5rem', margin: 0 }}>Приходите позже!</p>
            </div>
        </div>
    );
};

export default UnderConstruction;
