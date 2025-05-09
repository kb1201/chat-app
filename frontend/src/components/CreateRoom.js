import React, { useState } from 'react';
import api from '../api';
import { useNavigate } from 'react-router-dom';

const CreateRoomForm = () => {
    const [roomName, setRoomName] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await api.post('/rooms', { name: roomName });
            const data = response.data
            //onRoomCreated(response.data);
            setRoomName('');
            navigate(`/rooms/${data.id}`, {
                state: { roomName: roomName }
            });
        } catch (error) {
            console.error('Failed to create room:', error);
        }
    };

    return (
        <div className="search-bar">
            <p>Or create a room</p>
                <form onSubmit={handleSubmit}>
                    <input
                        type="text"
                        value={roomName}
                        onChange={(e) => setRoomName(e.target.value)}
                        placeholder="Enter room name"
                        required
                        className="search-input"
                    />
                    <button type="submit" className="search-button">Create</button>
                </form>
        </div>
    );
};

export default CreateRoomForm;
