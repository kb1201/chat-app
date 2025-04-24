import React, {useContext, useState} from 'react';
import {AuthContext} from './AuthContext';
import {useNavigate} from 'react-router-dom';
import api from "../api";

const Login = () => {
    const navigate = useNavigate();
    const {login} = useContext(AuthContext);
    const [formData, setFormData] = useState({
        username: '',
        password: ''
    });
    const [error, setError] = useState('');

    const handleChange = (e) => {
        const {name, value} = e.target;
        setFormData({
            ...formData,
            [name]: value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        try {
            const response = await api.post('/users/login', formData);
            const {token, id: userId} = response.data;

            if (token && userId !== undefined) {
                login({token, userId});
                navigate('/');
            } else {
                setError('Login failed. Please check your credentials.');
            }
        } catch (err) {
            setError('Invalid username or password');
        }
    };

    return (

        <div className="container">
            <h2>Login</h2>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    placeholder="Username"
                    name="username"
                    value={formData.username}
                    onChange={handleChange}
                    required
                    className="login-input"
                />
                <input
                    type="password"
                    placeholder="Password"
                    name="password"
                    value={formData.password}
                    onChange={handleChange}
                    required
                    className="login-input"
                />
                <button type="submit" className="login-button">Log In</button>
            </form>
            {error && <p className="error-message">{error}</p>}
        </div>
    );
};

export default Login;
