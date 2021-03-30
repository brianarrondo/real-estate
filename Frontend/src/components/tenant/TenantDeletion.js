import React from "react";
import Modal from "react-bootstrap/Modal";
import {Button} from "react-bootstrap";
import TenantService from "../../services/TenantService";

const TenantDeletion = ({show, onHide, tenant, successCallback, errorCallback}) => {

    function deleteTenant(tenant) {
        TenantService.deleteTenant(tenant,
                (response) => {
                    if(onHide) onHide();
                    if(successCallback) successCallback();
                },
                (error) => {
                    if (errorCallback) errorCallback(error);
                }
            );
    }

    return (
        <Modal
            size="lg"
            aria-labelledby="contained-modal-title-vcenter"
            centered
            show={show}
            onHide={onHide}
        >

            <Modal.Header closeButton>
                <Modal.Title>
                    Borrar Inquilino
                </Modal.Title>
            </Modal.Header>

            <Modal.Body>
                ¿Desea borrar el inquilino <span className="bold">{tenant && tenant.fullName}</span>?
            </Modal.Body>

            <Modal.Footer>
                <Button variant="secondary" onClick={onHide}>Cerrar</Button>
                <Button variant="success" onClick={() => deleteTenant(tenant)}>Borrar</Button>
            </Modal.Footer>

        </Modal>
    );
};

export default TenantDeletion;