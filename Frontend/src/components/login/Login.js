import React, {useContext, useState} from "react";
import {Col, Form, Spinner} from "react-bootstrap";
import {TokenLoggerContext} from "./TokenLogger";
import {Redirect} from "react-router-dom";
import {ServicesContext} from "../../services/Services";

const Login = () => {
    let userName = React.createRef();
    let password = React.createRef();
    const { loginService } = useContext(ServicesContext);
    const { token, saveToken } = useContext(TokenLoggerContext);
    const [isLoading, setLoading ] = useState(false);
    const [errorLoginMsg, setErrorLoginMsg] = useState(null);

    function handleSubmit(event) {
        event.preventDefault();
        setLoading(true);
        setErrorLoginMsg(null);
        loginService.login(
            {
                "userName": userName.current.value,
                "userPassword": password.current.value
            },
            (response) => {
                if (response.data) {
                    saveToken(response.data.token);
                }
            },
            (error) => {
                setLoading(false);
                if (error.response && error.response.status === 401) {
                    const msg = error.response.data.msg;
                    setErrorLoginMsg("Acceso no autorizado: " + msg);
                } else {
                    setErrorLoginMsg("Hubo un error al loguearse: " + error.message);
                }
            }
        )
    }

    function showSpinner() {
        if (isLoading) {
            return <div className="spinner"><Spinner animation="border" variant="dark" size="sm" /></div>
        }
    }

    function showErrorMsg() {
        if (errorLoginMsg) {
            return <div className="error-login-msg">{errorLoginMsg}</div>
        }
    }

    if (token == null) return (
        <div className="d-flex justify-content-center vertical-center login-background basic-padding-10">

            <Col xl={4} lg={7} md={7} sm={12} className="shadow rounded text-center login-box-background">
                <div className="padding-top-30 bi-symmetry-vertical">
                    <h2>Login RealEstate</h2>
                    <hr />
                </div>
                <div className="basic-padding-20 ">
                    <Form onSubmit={handleSubmit}>
                        <Form.Group>
                            <Form.Control ref={userName} type="text" autoComplete="username" placeholder="Usuario" maxLength="20" required />
                            <Form.Control.Feedback type="invalid">Por favor ingresar el nombre de usuario</Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group>
                            <Form.Control ref={password} type="password" autoComplete="current-password" placeholder="Password" maxLength="20" required />
                            <Form.Control.Feedback type="invalid">Por favor ingresar la password del usuario</Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group>
                            {showSpinner()}
                            {showErrorMsg()}
                            <button className="btn btn-dark text-white ">Acceder</button>
                        </Form.Group>
                    </Form>
                </div>
            </Col>
        </div>
    );
    return <Redirect to="/" />
};

export default Login;