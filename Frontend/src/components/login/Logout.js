import React, {useEffect} from "react";
import TokenLogger from "./TokenLogger";
import {Container} from "react-bootstrap";

const Logout = () => {
    const { deleteToken } = TokenLogger();

    useEffect(() => deleteToken(), []);

    return (
        <Container>
            Deslogueandose...
        </Container>
    );
}

export default Logout;