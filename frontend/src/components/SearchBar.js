import React, {useState} from 'react';
import api from '../api';
import RoomList from './RoomList';
import CreateRoomForm from './CreateRoom';

const SearchRoom = () => {
    const [searchQuery, setSearchQuery] = useState('');
    const [rooms, setRooms] = useState([]);
    const [error, setError] = useState('');

    const handleSearchChange = (e) => {
        setSearchQuery(e.target.value);
    };

    const handleRoomCreated = (newRoom) => {
        setRooms((prevRooms) => [...prevRooms, newRoom]);
    };

    const handleSearchSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await api.get(`/rooms/name/${searchQuery}`);
            setRooms(response.data);
            setError('');
        } catch (err) {
            setError('Room not found or an error occurred.');
            setRooms([]);
        }
    };

    return (
        <div className="search-room-container">


            <div className="search-bar">
                <form onSubmit={handleSearchSubmit}>
                    <input
                        type="text"
                        placeholder="Search for a room by name"
                        value={searchQuery}
                        onChange={handleSearchChange}
                        className="search-input"
                    />
                    <button type="submit" className="search-button">Search</button>
                </form>
            </div>

            {error && <div className="error-message">{error}</div>}

            <div className="room-list-container">
                {rooms.length > 0 ? (
                    <RoomList rooms={rooms}/>
                ) : (
                    <p className="no-rooms-message">No rooms found. Try searching with a different name.</p>
                )}
            </div>

            <CreateRoomForm></CreateRoomForm>
        </div>
    );
};

export default SearchRoom;
