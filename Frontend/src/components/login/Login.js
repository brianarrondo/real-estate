import React, {useContext} from "react";
import {Col, Form} from "react-bootstrap";
import LoginService from "../../services/LoginService";
import {AlertContext} from "../utils/GenericAlert";

const Login = ({saveToken}) => {
    let userName = React.createRef();
    let password = React.createRef();
    const {setShowAlert, setAlertType, setAlertContent} = useContext(AlertContext);

    function handleSubmit(event) {
        event.preventDefault();
        LoginService.login(
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
                if (error.response && error.response.status === 401) {
                    setAlertType("warning");
                    const msg = error.response.data.msg;
                    setShowAlert(true);
                    setAlertContent(<div><i className="bi bi-exclamation-circle"></i> Acceso no autorizado: "{msg}"</div>);
                } else {
                    setAlertType("danger");
                    setShowAlert(true);
                    setAlertContent(<div><i className="bi bi-exclamation-circle"></i> Hubo un error al loguearse: "{error.message}"</div>);
                }
            }
        )
        ;
    }

    return (
            <div className="d-flex justify-content-center vertical-center login-background basic-padding-10">

                <Col xl={4} lg={7} md={7} sm={12} className="shadow rounded text-center login-box-background">
                    <div className="padding-top-30 bi-symmetry-vertical">
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