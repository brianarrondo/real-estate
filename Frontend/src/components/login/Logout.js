import React, {useEffect} from "react";
import TokenLogger from "./TokenLogger";
import {Container} from "react-bootstrap";
import {Redirect} from "react-router-dom";

const Logout = () => {
    const { deleteToken } = TokenLogger();

    const refreshPage = () => {
        window.location.reload();
    }

    useEffect(() => {
        deleteToken();
        refreshPage();
    }, []);

    return (
        <Container>
            <div className="text-center">Deslogueandose...</div>
            <Redirect to="/" />
        </Container>
    );
}

export default Logout;