import React, {useEffect} from "react";
import TokenLogger from "./TokenLogger";
import {Container} from "react-bootstrap";
import {useHistory} from "react-router-dom";

const Logout = () => {
    const { deleteToken } = TokenLogger();
    const history = useHistory();

    const refreshPage = () => {
        window.location.reload();
    }

    useEffect(() => {
        deleteToken();
        history.push("/login");
        refreshPage();
    }, []);

    return (
        <Container>
            <div className="text-center">Deslogueandose...</div>
        </Container>
    );
}

export default Logout;