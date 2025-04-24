import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api';

const Register = () => {
    const navigate = useNavigate();
    const [form, setForm] = useState({
        username: '',
        password: '',
    });
    const [error, setError] = useState('');

    const handleChange = e => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleSubmit = async e => {
        e.preventDefault();
        setError('');
        try {
            await api.post('http://localhost:8080/users', form);
            navigate('/login');
        } catch (err) {
            setError('Registration failed. Try again.');
        }
    };

    return (
        <div className="container">
            <h2>Register</h2>
            <form onSubmit={handleSubmit}>
                <input
                    name="username"
                    placeholder="Username"
                    value={form.username}
                    onChange={handleChange}
                    required
                    className="login-input"
                />
                <input
                    name="password"
                    type="password"
                    placeholder="Password"
                    value={form.password}
                    onChange={handleChange}
                    required
                    className="login-input"
                />
                <button type="submit" className="login-button">Register</button>
                {error && <p className="error-message">{error}</p>}
            </form>
        </div>
    );
};

export default Register;
