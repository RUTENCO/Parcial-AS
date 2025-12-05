import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080', 
    headers: {
        'Content-Type': 'application/json',
        // REQUISITO 6 DEL PARCIAL:
        'X-API-VERSION': '1.0.0' 
    }
});

export default api;