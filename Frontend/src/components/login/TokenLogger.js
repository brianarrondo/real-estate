import React, { useState } from 'react';

export const TokenLoggerContext = React.createContext({
    saveToken: null,
    deleteToken: null,
    token: null
});

const TokenLogger = ({ children }) => {
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

    const context = {
        saveToken,
        deleteToken,
        token
    };

    return (
        <TokenLoggerContext.Provider value={context}>
            {children}
        </TokenLoggerContext.Provider>
    );
};

export default TokenLogger;