import React, { useContext } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { AuthContext } from '../auth/AuthContext';

const NavBar = () => {
    const { token, userId, logout } = useContext(AuthContext);
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate('/');
    };

    return (
        <nav className="navbar">
            <div className="navbar-content">
                <Link to="/" className="navbar-title">🗨️ ChatApp</Link>

                <div className="navbar-buttons">
                    {token ? (
                        <>
                            <button onClick={handleLogout} className="navbar-logout">Logout</button>
                        </>
                    ) : (
                        <>
                            <Link to="/login">
                                <button className="navbar-login">Login</button>
                            </Link>
                            <Link to="/register">
                                <button className="navbar-register">Register</button>
                            </Link>
                        </>
                    )}
                </div>
            </div>
        </nav>
    );
};

export default NavBar;
