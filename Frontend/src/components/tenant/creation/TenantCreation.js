import React, {useContext, useState} from 'react';
import {Button, Col, Form, Row} from "react-bootstrap";
import {AlertContext} from "../../utils/GenericAlert";
import {ServicesContext} from "../../../services/Services";
import Tenant from "../../../models/Tenant";
import GenericSpinner from "../../utils/GenericSpinner";

const TenantCreation = () => {
    let tenantFullName = React.createRef();
    let tenantDni = React.createRef();
    let tenantPhone = React.createRef();
    let tenantDescription = React.createRef();
    let form = React.createRef();

    const { tenantService } = useContext(ServicesContext);
    const {setShowAlert, setAlertType, setAlertContent} = useContext(AlertContext);
    const [validated, setValidated] = useState(false);
    const [isLoading, setLoading ] = useState(false);

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
            setLoading(true);
            let tenant = new Tenant(
                null,
                tenantFullName.current.value,
                tenantDni.current.value,
                tenantPhone.current.value,
                tenantDescription.current.value
            );

            tenantService.createTenant(tenant,
                () => {
                    setAlertType("success");
                    setShowAlert(true);
                    setAlertContent(<div><i className="bi bi-check-circle"></i> El inquilino <strong>{tenant && tenant.fullName}</strong> fue agregado con éxito</div>);
                    setValidated(false);
                    setLoading(false);
                    form.reset();
                },
                (error) => {
                    setAlertType("danger");
                    setShowAlert(true);
                    setLoading(false);
                    setAlertContent(<div><i className="bi bi-exclamation-circle"></i> Hubo un error al crear el inquilino: "{error.message}"</div>);
                }
            );
        }

    }

    return (
        <div className="container">
            <Form ref={form} onSubmit={handleSubmit} noValidate validated={validated} className="basic-padding-20 shadow justify-content-center rounded-bottom border border-light">
                <div>
                    <h3>Agregar inquilino</h3>
                    <hr />
                </div>

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

                <div className="align-center basic-padding-10">
                    <Button variant="dark" type="submit" className="form-submit-button" disabled={isLoading}>
                        <GenericSpinner show={isLoading}>Agregar</GenericSpinner>
                    </Button>
                </div>
            </Form>
        </div>

    );
};

export default TenantCreation;