import React, {useContext, useState} from "react";
import Modal from "react-bootstrap/Modal";
import {Button} from "react-bootstrap";
import {ServicesContext} from "../../services/Services";
import GenericSpinner from "../utils/GenericSpinner";

const EstateDeletionModal = ({setModalShow, estate, successCallback, errorCallback}) => {
    const { estateService } = useContext(ServicesContext);
    const [isLoading, setLoading] = useState(false);
    function deleteEstate(tenant) {
        setLoading(true);
        estateService.deleteEstate(tenant,
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
                    Borrar Propiedad
                </Modal.Title>
            </Modal.Header>

            <Modal.Body>
                ¿Desea borrar la propiedad <span className="bold">{estate && estate.name}</span>?
            </Modal.Body>

            <Modal.Footer>
                <Button variant="secondary" onClick={() => setModalShow(false)}>Cerrar</Button>
                <Button variant="success" onClick={() => deleteEstate(estate)} className="form-submit-button" disabled={isLoading}>
                    <GenericSpinner show={isLoading}>Borrar</GenericSpinner>
                </Button>
            </Modal.Footer>
        </>
    );
};

export default EstateDeletionModal;