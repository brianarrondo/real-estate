import React, {useContext, useEffect, useState, useRef} from "react";
import Modal from "react-bootstrap/Modal";
import {Button, Form} from "react-bootstrap";
import GenericSpinner from "../utils/GenericSpinner";
import {ServicesContext} from "../../services/Services";
import {AlertContext} from "../utils/GenericAlert";

const ExistingTenantAdditionModal = ({setModalShow, setExistingTenants, tenants}) => {
    const selectedExistingTenant = useRef();

    function addExistingTenant() {
        if (selectedExistingTenant.current.value.length > 0) {
            setExistingTenants(prevState => [...prevState, tenants.find(t => t.tenantId === selectedExistingTenant.current.value)]);
        }
        setModalShow(false);
    }

    const tenantsSelectOptions = tenants.map(t => {
        return <option key={t.tenantId} value={t.tenantId}>{t.fullName} (DNI {t.dni})</option>;
    });

    return (
        <>
            <Modal.Header closeButton>
                <Modal.Title>
                    Agregar inquilino existente
                </Modal.Title>
            </Modal.Header>

            <Modal.Body>
                <Form.Control as="select" ref={selectedExistingTenant}>
                    <option key="0" value="">Seleccione un inquilino</option>
                    {tenantsSelectOptions}
                </Form.Control>
            </Modal.Body>

            <Modal.Footer>
                <Button variant="secondary" onClick={() => setModalShow(false)}>Cerrar</Button>
                <Button variant="success" className="form-submit-button" onClick={addExistingTenant}>
                    Agregar
                </Button>
            </Modal.Footer>
        </>
    );
};

export default ExistingTenantAdditionModal;