import React, {useContext, useState} from "react";
import Modal from "react-bootstrap/Modal";
import {Button} from "react-bootstrap";
import {ServicesContext} from "../../../services/Services";
import GenericSpinner from "../../utils/GenericSpinner";

const TenantDeletionModal = ({setModalShow, tenant, successCallback, errorCallback}) => {
    const { tenantService } = useContext(ServicesContext);
    const [isLoading, setLoading] = useState(false);
    function deleteTenant(tenant) {
        setLoading(true);
        tenantService.deleteTenant(tenant,
                (response) => {
                    setLoading(false);
                    setModalShow(false);
                    if(successCallback) successCallback();
                },
                (error) => {
                    setLoading(false);
                    if (errorCallback) errorCallback(error);
                }
            );
    }

    return (
       <>
            <Modal.Header closeButton>
                <Modal.Title>
                    Borrar Inquilino
                </Modal.Title>
            </Modal.Header>

            <Modal.Body>
                ¿Desea borrar el inquilino <span className="bold">{tenant && tenant.fullName}</span>?
            </Modal.Body>

            <Modal.Footer>
                <Button variant="secondary" onClick={() => setModalShow(false)}>Cerrar</Button>
                <Button variant="success" onClick={() => deleteTenant(tenant)} className="form-submit-button" disabled={isLoading}>
                    <GenericSpinner show={isLoading}>Borrar</GenericSpinner>
                </Button>
            </Modal.Footer>
       </>
    );
};

export default TenantDeletionModal;