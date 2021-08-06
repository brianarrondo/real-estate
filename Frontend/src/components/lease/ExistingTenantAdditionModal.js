import React, {useContext, useEffect, useState, useRef} from "react";
import Modal from "react-bootstrap/Modal";
import {Button, Form} from "react-bootstrap";
import GenericSpinner from "../utils/GenericSpinner";
import {ServicesContext} from "../../services/Services";
import {AlertContext} from "../utils/GenericAlert";

const ExistingTenantAdditionModal = ({setModalShow, setExistingTenants}) => {
    const selectedExistingTenant = useRef();
    const [isLoading, setLoading] = useState(false);
    const [tenants, setTenants] = useState([]);

    const { tenantService } = useContext(ServicesContext);
    const {setShowAlert, setAlertType, setAlertContent} = useContext(AlertContext);

    useEffect(() => {
        tenantService.getAllTenants(
            (response) => {
                setTenants(response.data);
            },
            (error) => {
                setAlertType("danger");
                setShowAlert(true);
                setLoading(false);
                setAlertContent(<div><i className="bi bi-exclamation-circle"></i> Hubo un error al cargar los inquilinos: "{error.message}"</div>);
            })
    }, []);

    function addExistingTenant() {
        setExistingTenants(prevState => [...prevState, tenants.find(t => t.tenantId === selectedExistingTenant.current.value)]);
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
                    <option key="0">Seleccione un inquilino</option>
                    {tenantsSelectOptions}
                </Form.Control>
            </Modal.Body>

            <Modal.Footer>
                <Button variant="secondary" onClick={() => setModalShow(false)}>Cerrar</Button>
                <Button variant="success" className="form-submit-button" disabled={isLoading} onClick={addExistingTenant}>
                    <GenericSpinner show={isLoading}>Agregar</GenericSpinner>
                </Button>
            </Modal.Footer>
        </>
    );
};

export default ExistingTenantAdditionModal;