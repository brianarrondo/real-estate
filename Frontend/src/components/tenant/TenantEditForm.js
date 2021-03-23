import React from "react";
import Modal from "react-bootstrap/Modal";
import {Button, Col, Form, Row} from "react-bootstrap";
import TenantService from "../../services/TenantService";

const TenantEditForm = ({ show, onHide, tenant, callback }) => {
    let tenantFullName = React.createRef();
    let tenantDni = React.createRef();
    let tenantPhone = React.createRef();
    let tenantDescription = React.createRef();

    function handleSubmit(event) {
        event.preventDefault();
        TenantService.editTenant({
                tenantId: tenant.tenantId,
                fullName: tenantFullName.current.value,
                dni: tenantDni.current.value,
                phone: tenantPhone.current.value,
                description: tenantDescription.current.value
            },
            (response) => {
                onHide();
                if (callback) callback();
            },
            (error) => {
                //setErrorOnRequest(error);
                console.log("ERROR AL REALIZAR EL REQUEST");
            }
        );
    }

    return(
        <Modal
            size="lg"
            aria-labelledby="contained-modal-title-vcenter"
            centered
            show={show}
            onHide={onHide}
        >

            <Modal.Header closeButton>
                <Modal.Title>
                   Editar Inquilino
                </Modal.Title>
            </Modal.Header>

            <Form onSubmit={handleSubmit}>
                <Modal.Body>
                    <Form.Group as={Row}>
                        <Form.Label column sm={2}>
                            Email
                        </Form.Label>
                        <Col sm={9}>
                            <Form.Control type="text" ref={tenantFullName} defaultValue={tenant && tenant.fullName} placeholder="Nombre Completo" />
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
                            <Form.Control type="text" ref={tenantDescription} defaultValue={tenant && tenant.description} placeholder="Descripcion" />
                        </Col>
                    </Form.Group>
                </Modal.Body>

                <Modal.Footer>
                    <Button variant="secondary" onClick={onHide}>Cerrar</Button>
                    <Button variant="success" type="submit">Editar</Button>
                </Modal.Footer>
            </Form>

        </Modal>
    );
};

export default TenantEditForm;