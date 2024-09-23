import React from 'react';

const Home = () => {
    return (
        <div className="d-flex justify-content-center align-items-center"
             style={{
                 height: '100vh',
                 width: '100vw',
                 backgroundColor: 'lightgreen',
                 backgroundImage: `url(/images/MoscowStateUniversity.webp)`,
                 backgroundSize: 'cover',
                 backgroundPosition: 'center',
                 backgroundRepeat: 'no-repeat'
             }}
        >
            <h1
                style={{
                    color: 'orange',
                    position: 'absolute',
                    top: '20px',
                    right: '20px',
                    fontWeight: 'bold'
                }}
            >
                CDO:ONLINE
            </h1>
        </div>
    );
};

export default Home;
