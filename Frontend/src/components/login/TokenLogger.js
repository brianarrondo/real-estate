import { useState } from 'react';

const TokenLogger = () => {
    const getToken = () => {
        return sessionStorage.getItem('token');
    };

    const [token, setToken] = useState(getToken());

    const saveToken = userToken => {
        sessionStorage.setItem('token', userToken);
        setToken(userToken);
    };

    const deleteToken = () => {
        sessionStorage.removeItem('token');
        setToken(null);
    };

    return {
        saveToken: saveToken,
        deleteToken: deleteToken,
        token
    }
}

export default TokenLogger;