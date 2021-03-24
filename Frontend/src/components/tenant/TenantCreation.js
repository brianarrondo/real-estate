import React, { useState, useEffect } from 'react';
import TenantService from "../../services/TenantService";
import Utils from "../../utils/Utils";

import { useForm } from 'react-hook-form';
import AlertCustom from "../utils/AlertCustom";
import Modal from "react-bootstrap/Modal";
import {Button, Col, Form, Row} from "react-bootstrap";

const TenantCreation = () => {
    let tenantFullName = React.createRef();
    let tenantDni = React.createRef();
    let tenantPhone = React.createRef();
    let tenantDescription = React.createRef();

    const [tenantCreated, setTenantCreated] = useState(null);
    const [showTenantCreationAlert, setShowTenantCreationAlert] = useState(false);
    const [errorOnTenantCreationRequest, setErrorOnTenantCreationRequest] = useState(false);

    /* Alerta para la creacion exitosa del objeto */
    const displaySuccessTenantCreationAlert = () => {
        let tenantName = tenantCreated ? tenantCreated.fullName : "";
        return (
            <div key={Utils.generateKey(tenantName)}>
                <AlertCustom type="success" show={showTenantCreationAlert} onClose={() => setShowTenantCreationAlert(false)}>
                    <i className="bi bi-check-circle"></i> El inquilino <strong>{tenantName}</strong> fue agregado con éxito
                </AlertCustom>
            </div>
        );
    };

    /* Alerta para errores al realizar el request de creacion del objeto */
    const displayErrorTenantCreationAlert = () => {
        let errorMsg = errorOnTenantCreationRequest ? errorOnTenantCreationRequest.message : "";
        return (
            <div key={Utils.generateKey("error")}>
                <AlertCustom type="danger" show={errorOnTenantCreationRequest} onClose={() => setErrorOnTenantCreationRequest(null)}>
                    <i className="bi bi-exclamation-circle"></i> Hubo un error en la creación del Inquilino: "{errorMsg}"
                </AlertCustom>
            </div>
        );
    };

    function handleSubmit(event) {
        event.preventDefault();
        console.log("hola");
        const tenant = {
            fullName: tenantFullName.current.value,
            dni: tenantDni.current.value,
            phone: tenantPhone.current.value,
            description: tenantDescription.current.value
        };
        console.log(tenant);
        TenantService.createTenant(tenant,
            () => {
                setTenantCreated(tenant);
                setShowTenantCreationAlert(true);
            },
            (error) => {
                setErrorOnTenantCreationRequest(error);
            }
        );
    }

    return (
        <div className="container">

            <Form onSubmit={handleSubmit} className="basic-padding-20 shadow justify-content-center rounded-bottom border border-light">
                    <Form.Group as={Row} className="justify-content-center">
                        <Form.Label column sm={3}>
                            Nombre
                        </Form.Label>
                        <Col sm={8}>
                            <Form.Control type="text" ref={tenantFullName} placeholder="Nombre Completo" />
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
                            <Form.Control type="text" ref={tenantDescription} placeholder="Descripcion" />
                        </Col>
                    </Form.Group>

                    <div className="align-center basic-padding-10"><Button variant="dark" type="submit">Agregar</Button></div>
            </Form>
        </div>
    );
};

export default TenantCreation;