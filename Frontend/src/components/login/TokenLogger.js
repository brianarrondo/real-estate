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

    const [token, setToken] = useState(getToken());
    const [userId, setUserId] = useState();

    const saveToken = userToken => {
        localStorage.setItem('token', userToken);
        setToken(userToken);
    };

    const saveUserId = userId => {
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