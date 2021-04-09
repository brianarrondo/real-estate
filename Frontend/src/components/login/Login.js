import React from "react";
import {Col, Form} from "react-bootstrap";

const Login = () => {
    let userName = React.createRef();
    let password = React.createRef();

    function handleSubmit(event) {
        event.preventDefault();
        console.log("user: " + userName.current.value);
        console.log("password: " + password.current.value);

        // TODO: enviar request al servicio de login
    }

    return (
            <div className="d-flex justify-content-center vertical-center login-background basic-padding-10">

                <Col xl={4} lg={7} md={7} sm={12} className="shadow rounded text-center bi-symmetry-vertical login-box-background">
                    <div>
                        <h2>Login RealEstate</h2>
                        <hr />
                    </div>
                    <div className="basic-padding-20 ">
                        <Form onSubmit={handleSubmit}>
                            <Form.Group>
                                <Form.Control ref={userName} type="text" placeholder="Usuario" maxLength="20" required />
                                <Form.Control.Feedback type="invalid">Por favor ingresar el nombre de usuario</Form.Control.Feedback>
                            </Form.Group>
                            <Form.Group>
                                <Form.Control ref={password} type="password" placeholder="Password" maxLength="20" required />
                                <Form.Control.Feedback type="invalid">Por favor ingresar la password del usuario</Form.Control.Feedback>
                            </Form.Group>
                            <Form.Group>
                                <button className="btn btn-dark text-white ">Acceder</button>
                            </Form.Group>
                        </Form>
                    </div>
                </Col>

            </div>
    );
};

export default Login;