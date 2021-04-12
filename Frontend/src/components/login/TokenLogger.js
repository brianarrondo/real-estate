import { useState } from 'react';

const TokenLogger = () => {
    const getToken = () => {
        return localStorage.getItem('token');
    };

    const [token, setToken] = useState(getToken());

    const saveToken = userToken => {
        localStorage.setItem('token', userToken);
        setToken(userToken);
    };

    const deleteToken = () => {
        localStorage.removeItem('token');
        setToken(null);
    };

    return {
        saveToken: saveToken,
        deleteToken: deleteToken,
        token
    }
}

export default TokenLogger;