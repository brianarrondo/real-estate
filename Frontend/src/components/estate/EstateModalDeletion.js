import React, {useContext} from "react";
import Modal from "react-bootstrap/Modal";
import {Button} from "react-bootstrap";
import {ServicesContext} from "../../services/Services";

const EstateModalDeletion = ({setModalShow, estate, successCallback, errorCallback}) => {
    const { estateService } = useContext(ServicesContext);
    function deleteEstate(tenant) {
        estateService.deleteEstate(tenant,
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
                    Borrar Propiedad
                </Modal.Title>
            </Modal.Header>

            <Modal.Body>
                ¿Desea borrar la propiedad <span className="bold">{estate && estate.name}</span>?
            </Modal.Body>

            <Modal.Footer>
                <Button variant="secondary" onClick={() => setModalShow(false)}>Cerrar</Button>
                <Button variant="success" onClick={() => deleteEstate(estate)}>Borrar</Button>
            </Modal.Footer>
        </>
    );
};

export default EstateModalDeletion;