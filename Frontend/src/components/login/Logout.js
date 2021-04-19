import React, {useContext, useEffect} from "react";
import {TokenLoggerContext} from "./TokenLogger";
import {Container} from "react-bootstrap";

const Logout = () => {
    const { deleteToken } = useContext(TokenLoggerContext);

    useEffect(() => {
        deleteToken();
    }, []);

    return (
        <Container>
            <div className="text-center">Deslogueandose...</div>
        </Container>
    );
}

export default Logout;