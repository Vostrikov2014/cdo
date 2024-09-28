import React, { useEffect } from 'react';

const Conference = ({ message, roomName }) => {
    useEffect(() => {
        const domain = "online3.spa.msu.ru";
        const options = {
            roomName: roomName, // Имя комнаты, переданное через пропсы
            width: '100%',
            height: 700,
            parentNode: document.querySelector('#meet'),
        };
        const api = new window.JitsiMeetExternalAPI(domain, options);

        // Установка отображаемого имени пользователя
        api.executeCommand('displayName', 'My Display Name');

        // Обработчик события при присоединении нового участника
        api.addEventListener('participantJoined', (event) => {
            console.log("Новый участник присоединился:", event.id);
        });

        // Обработчик события при выходе из конференции
        api.addEventListener('videoConferenceLeft', (event) => {
            console.log("Конференция завершена:", event.roomName);
        });

        // Очистка при размонтировании компонента
        return () => {
            api.dispose();
        };
    }, [roomName]);

    return (
        <div>
            <h1>{message || 'Присоединиться к конференции'}</h1>
            <div id="meet"></div>
        </div>
    );
};

export default Conference;
