import React from "react";
import Modal from "react-bootstrap/Modal";
import {Button} from "react-bootstrap";
import TenantService from "../../services/TenantService";

const ModalDeletion = ({setModalShow, tenant, successCallback, errorCallback}) => {

    function deleteTenant(tenant) {
        TenantService.deleteTenant(tenant,
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

export default ModalDeletion;