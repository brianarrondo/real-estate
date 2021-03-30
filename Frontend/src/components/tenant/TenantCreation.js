import React, { useState } from 'react';
import TenantService from "../../services/TenantService";
import AlertCustom from "../utils/AlertCustom";
import {Button, Col, Form, Row} from "react-bootstrap";

const TenantCreation = () => {
    let tenantFullName = React.createRef();
    let tenantDni = React.createRef();
    let tenantPhone = React.createRef();
    let tenantDescription = React.createRef();
    let form = React.createRef();

    const [validated, setValidated] = useState(false);
    const [tenantCreated, setTenantCreated] = useState(null);
    const [showAlert, setShowAlert] = useState(false);
    const [errorMsg, setErrorMsg] = useState(null);
    let tenant;

    function handleSubmit(event) {
        let validationOk = true;

        event.preventDefault();

        // realizamos la validacion del form
        const form = event.currentTarget;
        if (form.checkValidity() === false) {
            validationOk = false;
        }
        setValidated(true);


        if (validationOk) {
            tenant = {
                fullName: tenantFullName.current.value,
                dni: tenantDni.current.value,
                phone: tenantPhone.current.value,
                description: tenantDescription.current.value
            };

            TenantService.createTenant(tenant,
                () => {
                    setTenantCreated(tenant);
                    setValidated(false);
                    setErrorMsg(null);
                    setShowAlert(true);
                    form.reset();
                },
                (error) => {
                    setErrorMsg(error.message);
                    setShowAlert(true);
                }
            );
        }

    }

    function getAlertType() {
        if (errorMsg !== null) {
            return "danger";
        } else {
            return "success";
        }
    }

    function getAlertChild() {
        if (errorMsg !== null) {
            return (<div><i className="bi bi-exclamation-circle"></i> Hubo un error al crear el inquilino: "{errorMsg}"</div>);
        } else {
            return (<div><i className="bi bi-check-circle"></i> El inquilino <strong>{tenantCreated && tenantCreated.fullName}</strong> fue agregado con éxito</div>)
        }
    }

    return (
        <div className="container">

            <Form ref={form} onSubmit={handleSubmit} noValidate validated={validated} className="basic-padding-20 shadow justify-content-center rounded-bottom border border-light">
                    <Form.Group as={Row} className="justify-content-center">
                        <Form.Label column sm={3}>
                            Nombre
                        </Form.Label>
                        <Col sm={8}>
                            <Form.Control type="text" ref={tenantFullName} placeholder="Nombre Completo" required />
                            <Form.Control.Feedback type="invalid">Por favor ingresar el nombre del inquilino</Form.Control.Feedback>
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row} className="justify-content-center">
                        <Form.Label column sm={3}>
                            DNI
                        </Form.Label>
                        <Col sm={8}>
                            <Form.Control type="text" ref={tenantDni} placeholder="DNI" />
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row} className="justify-content-center">
                        <Form.Label column sm={3}>
                            Teléfono
                        </Form.Label>
                        <Col sm={8}>
                            <Form.Control type="text" ref={tenantPhone} placeholder="Telefono" />
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row} className="justify-content-center">
                        <Form.Label column sm={3}>
                            Descripción
                        </Form.Label>
                        <Col sm={8}>
                            <Form.Control type="text" ref={tenantDescription} placeholder="Descripcion" maxLength="70" />
                        </Col>
                    </Form.Group>

                    <div className="align-center basic-padding-10"><Button variant="dark" type="submit">Agregar</Button></div>
            </Form>

            <AlertCustom show={showAlert} onClose={() => setShowAlert(false)} type={getAlertType()}>
                {getAlertChild()}
            </AlertCustom>
        </div>

    );
};

export default TenantCreation;