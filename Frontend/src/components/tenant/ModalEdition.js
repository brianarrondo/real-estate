import React, {useState} from "react";
import Modal from "react-bootstrap/Modal";
import {Button, Col, Form, Row} from "react-bootstrap";
import TenantService from "../../services/TenantService";

const ModalEdition = ({ setModalShow, tenant, successCallback, errorCallback }) => {
    const [validated, setValidated] = useState(false);
    let tenantFullName = React.createRef();
    let tenantDni = React.createRef();
    let tenantPhone = React.createRef();
    let tenantDescription = React.createRef();

    function onHide() {
        setValidated(false);
        setModalShow(false);
    }

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
            TenantService.editTenant({
                    tenantId: tenant.tenantId,
                    fullName: tenantFullName.current.value,
                    dni: tenantDni.current.value,
                    phone: tenantPhone.current.value,
                    description: tenantDescription.current.value
                },
                (response) => {
                    onHide();
                    if (successCallback) successCallback();
                },
                (error) => {
                    if (errorCallback) errorCallback(error);
                }
            );
        }
    }

    return(
        <>
            <Modal.Header closeButton>
                <Modal.Title>
                   Editar Inquilino
                </Modal.Title>
            </Modal.Header>

            <Form onSubmit={handleSubmit} noValidate validated={validated}>
                <Modal.Body>
                    <Form.Group as={Row}>
                        <Form.Label column sm={2}>
                            Nombre
                        </Form.Label>
                        <Col sm={9}>
                            <Form.Control type="text" ref={tenantFullName} defaultValue={tenant && tenant.fullName} placeholder="Nombre Completo" required />
                            <Form.Control.Feedback type="invalid">Por favor ingresar el nombre del inquilino</Form.Control.Feedback>
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row}>
                        <Form.Label column sm={2}>
                            DNI
                        </Form.Label>
                        <Col sm={9}>
                            <Form.Control type="text" ref={tenantDni} defaultValue={tenant && tenant.dni} placeholder="DNI" />
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row}>
                        <Form.Label column sm={2}>
                            Teléfono
                        </Form.Label>
                        <Col sm={9}>
                            <Form.Control type="text" ref={tenantPhone} defaultValue={tenant && tenant.phone} placeholder="Telefono" />
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row}>
                        <Form.Label column sm={2}>
                            Descripción
                        </Form.Label>
                        <Col sm={9}>
                            <Form.Control type="text" ref={tenantDescription} defaultValue={tenant && tenant.description} placeholder="Descripcion" maxLength="70" />
                        </Col>
                    </Form.Group>
                </Modal.Body>

                <Modal.Footer>
                    <Button variant="secondary" onClick={onHide}>Cerrar</Button>
                    <Button variant="success" type="submit">Editar</Button>
                </Modal.Footer>
            </Form>
        </>
    );
};

export default ModalEdition;