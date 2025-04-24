import React, { useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from "../auth/AuthContext";
import api from '../api';

const RoomList = ({ rooms }) => {
    const navigate = useNavigate();
    const { userId } = useContext(AuthContext);

    const handleJoinRoom = async (room) => {
        try {
            await api.post(`/users/${userId}/rooms`, { roomId: room.id });
            navigate(`/rooms/${room.id}`, { state: { roomName: room.name } });
        } catch (error) {
            console.error('Failed to join room:', error);
            alert('Failed to join room');
        }
    };

    return (
        <div className="room-list">
            <ul>
                {rooms.map((room) => (
                    <li key={room.id}>
                        <span className="room-name">{room.name}</span>
                        <button className="join-button" onClick={() => handleJoinRoom(room)}>
                            Join Room
                        </button>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default RoomList;
