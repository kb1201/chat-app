import React, {useState} from 'react';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import NavBar from './components/Navbar';
import Login from './auth/Login';
import Register from './auth/Register';
import Home from './Home';
import './styles.css';
import Chat from "./components/Chat";
import SearchRoom from "./components/SearchBar";
import SearchBar from "./components/SearchBar";

function App() {
    const [user, setUser] = useState(null);

    const handleLogout = () => {
        localStorage.removeItem('token');
        setUser(null);
    };

    return (
        <Router>
            <NavBar user={user} onLogout={handleLogout}/>
            <Routes>
                <Route path="/" element={<Home/>}/>
                <Route path="/login" element={<Login setUser={setUser}/>}/>
                <Route path="/register" element={<Register/>}/>
                <Route path="/rooms/:roomId" element={<Chat/>}/>
            </Routes>
        </Router>
    );
}

export default App;
