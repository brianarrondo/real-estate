import React, {useContext} from "react";
import Modal from "react-bootstrap/Modal";
import {Button} from "react-bootstrap";
import {ServicesContext} from "../../services/Services";

const TenantModalDeletion = ({setModalShow, tenant, successCallback, errorCallback}) => {
    const { tenantService } = useContext(ServicesContext);
    function deleteTenant(tenant) {
        tenantService.deleteTenant(tenant,
                (response) => {
                    setModalShow(false);
                    if(successCallback) successCallback();
                },
                (error) => {
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
                <Button variant="success" onClick={() => deleteTenant(tenant)}>Borrar</Button>
            </Modal.Footer>
       </>
    );
};

export default TenantModalDeletion;