import React, {useEffect, useState, useContext} from 'react';
import {useParams, useLocation, useNavigate} from 'react-router-dom';
import {Client} from '@stomp/stompjs';
import {AuthContext} from '../auth/AuthContext';
import api from "../api";

let stompClient = null;

const Chat = () => {
    const {roomId} = useParams();
    const location = useLocation();
    const {userId, token} = useContext(AuthContext);
    const roomName = location.state?.roomName || roomId;
    const [message, setMessage] = useState('');
    const [messages, setMessages] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchMessages = async () => {
            try {
                const response = await api.get(`/rooms/${roomId}/messages`);
                const data = await response.data;
                setMessages(data);
            } catch (err) {
                console.error("Failed to fetch messages:", err);
            }
        };

        const connectWebSocket = () => {
            stompClient = new Client({
                brokerURL: 'ws://localhost:8080/ws-chat',
                connectHeaders: {
                    Authorization: `Bearer ${token}`,
                },
                onConnect: () => {
                    stompClient.subscribe(`/topic/room.${roomId}`, (msg) => {
                        const body = JSON.parse(msg.body);
                        setMessages((prev) => [...prev, body]); // live updates
                    });
                },
                onWebSocketError: (error) => {
                    console.error('WebSocket Error:', error);
                },
            });
            stompClient.activate();
        };

        void fetchMessages();
        connectWebSocket();

        return () => {
            if (stompClient) stompClient.deactivate();
        };
    }, [roomId, token]);

    const leaveChat = () => {
        if (stompClient) stompClient.deactivate();
        navigate('/');
    };

    const sendMessage = () => {
        if (stompClient && stompClient.connected && message.trim() !== '') {
            stompClient.publish({
                destination: `/rooms/${roomId}/messages`,
                body: message,
            });
            setMessage('');
        }
    };

    return (
        <div className="container">
            <div className="chat-container">
                <div className="chat-header">
                    <div className="chat-header-top">
                        <h2 className="chat-room-title">Chat Room: {roomName}</h2>
                        <button className="leave-button" onClick={leaveChat}>
                            Leave
                        </button>
                    </div>
                </div>

                <div className="chat-messages">
                    {messages.map((msg, index) => (
                        <div
                            key={index}
                            className={`chat-message ${msg.user.id === userId ? 'me' : 'them'}`}
                        >
                            <div className="chat-sender">{msg.user.username}</div>
                            <div className="chat-bubble">{msg.content}</div>
                        </div>
                    ))}
                </div>

                <div className="chat-input-area">
                    <input
                        value={message}
                        onChange={(e) => setMessage(e.target.value)}
                        placeholder="Type a message"
                        className="chat-input"
                    />
                    <button onClick={sendMessage} className="chat-send-button">
                        Send
                    </button>
                </div>
            </div>
        </div>
    );
};

export default Chat;
