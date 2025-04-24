import React, { useContext } from 'react';
import './styles.css';
import { AuthContext } from './auth/AuthContext';
import SearchBar from './components/SearchBar';

const Home = () => {
    const { token } = useContext(AuthContext);

    return (
        <div className="home-container">
            <h1 className="home-title">Welcome to ChatApp 🗨️</h1>

            <div className="container">
                {token ? (
                    <div className="search-container">
                        <h2>Search for a Room</h2>
                        <SearchBar />
                    </div>
                ) : (
                    <div className="login-prompt">
                        <p>Login or Register to start chatting with friends in real-time!</p>
                    </div>
                )}
            </div>
        </div>
    );
};

export default Home;
