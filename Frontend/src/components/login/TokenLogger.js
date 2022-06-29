import React, { useState } from 'react';

export const TokenLoggerContext = React.createContext({
    saveToken: null,
    deleteToken: null,
    token: null,
    userId: null,
    setUserId: null
});

const TokenLogger = ({ children }) => {
    const getToken = () => {
        return localStorage.getItem('token');
    };

    const getUserId = () => {
        return localStorage.getItem('userId');
    };

    const [token, setToken] = useState(getToken());
    const [userId, setUserId] = useState(getUserId());

    const saveToken = userToken => {
        localStorage.setItem('token', userToken);
        setToken(userToken);
    };

    const saveUserId = userId => {
        localStorage.setItem('userId', userId);
        setUserId(userId);
    }

    const deleteToken = () => {
        localStorage.removeItem('token');
        setToken(null);
    };

    const context = {
        saveToken,
        deleteToken,
        token,
        userId,
        saveUserId
    };

    return (
        <TokenLoggerContext.Provider value={context}>
            {children}
        </TokenLoggerContext.Provider>
    );
};

export default TokenLogger;