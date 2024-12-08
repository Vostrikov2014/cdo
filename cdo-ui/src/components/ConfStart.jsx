import React, { useEffect, useRef } from 'react';

const confStart = ({roomName}) => {
    const jitsiContainerRef = useRef(null);

    useEffect(() => {
        if (window.JitsiMeetExternalAPI) {
            const domain = "online3.spa.msu.ru";
            const options = {
                roomName: roomName,
                width: '100%',
                height: '100%',
                parentNode: jitsiContainerRef.current
            };

            const api = new window.JitsiMeetExternalAPI(domain, options);

            // Set the display name
            api.executeCommand('displayName', 'My Display Name');

            // Add event listeners
            api.addEventListener('participantJoined', (event) => {
                console.log("New participant joined:", event.id);
            });

            api.addEventListener('videoConferenceLeft', (event) => {
                console.log("ConfStart left:", event.roomName);
            });

            // Clean up on component unmount
            return () => api.dispose();
        } else {
            console.error('Jitsi Meet API script not loaded');
        }
    }, [roomName]);

    return (
        <div style={{ width: '100vw', height: '100vh' }}>
            <div
                ref={jitsiContainerRef}
                style={{width: '100%', height: '100%'}}
            />
        </div>
    );
};

export default confStart;
