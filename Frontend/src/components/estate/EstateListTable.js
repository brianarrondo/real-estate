import React, {useContext, useEffect, useState} from "react";
import {ModalContext} from "../utils/GenericModal";
import {AlertContext} from "../utils/GenericAlert";
import {ServicesContext} from "../../services/Services";
import EstateDetailModal from "./EstateDetailModal";
import EstateDeletionModal from "./EstateDeletionModal";
import EstateEditionModal from "./EstateEditionModal";
import {Spinner} from "react-bootstrap";
import GenericEmptyTable from "../utils/GenericEmptyTable";

const EstateListTable = () => {
    const { estateService } = useContext(ServicesContext);
    const {setModalShow, setModalContent, setSize} = useContext(ModalContext);
    const {setShowAlert, setAlertType, setAlertContent} = useContext(AlertContext);
    const [estates, setEstates] = useState([]);
    const [isLoading, setLoading] = useState(false);

    useEffect(() => {
        getAllEstates();
    }, []);

    const getAllEstates = () => {
        setLoading(true);
        estateService.getAllEstates(
            (response) => {
                setLoading(false);
                setEstates(response.data);
            },
            (error) => {
                setLoading(false);
                setAlertType("danger");
                setShowAlert(true);
                setAlertContent(<div><i className="bi bi-exclamation-circle"/> Hubo un error al obtener el listado de propiedades: "{error.message}"</div>);
            }
        );
    };

    function editOnClick(estate) {
        setModalContent(
            <EstateEditionModal
                setModalShow={setModalShow}
                estate={estate}
                successCallback={() => {
                    setAlertType("success");
                    setShowAlert(true);
                    setAlertContent(<div><i className="bi bi-check-circle"/> La propiedad <strong>{estate && estate.name}</strong> ha sido modificada con éxito</div>);
                    getAllEstates();
                }}
                errorCallback={(error) => {
                    setAlertType("danger");
                    setShowAlert(true);
                    setAlertContent(<div><i className="bi bi-exclamation-circle"/> Hubo un error al editar la propiedad: "{error.message}"</div>);
                }}
            />
        )
        setSize("xl");
        setModalShow(true);
    }

    function deleteOnClick(estate) {
        setModalContent(
            <EstateDeletionModal
                setModalShow={setModalShow}
                estate={estate}
                successCallback={() => {
                    setAlertType("success");
                    setShowAlert(true);
                    setAlertContent(<div><i className="bi bi-check-circle"/> La propiedad <strong>{estate && estate.name}</strong> ha sido borrada con éxito</div>);
                    getAllEstates();
                }}
                errorCallback={(error) => {
                    setAlertType("danger");
                    setShowAlert(true);
                    setAlertContent(<div><i className="bi bi-exclamation-circle"/> Hubo un error al borrar la propiedad: "{error.message}"</div>);
                }}
            />
        )
        setSize("lg");
        setModalShow(true);
    }

    function seeDetailOnClick(estate) {
        setModalContent(
            <EstateDetailModal
                setModalShow={setModalShow}
                estate={estate}
            />
        );
        setSize("xl");
        setModalShow(true);
    }

    /* Llenamos la tabla con los objetos correspondientes */
    const estateListComponents = estates.map((estate) => {
        return (
            <tr key={estate.id}>
                <td className="bold">{estate.name}</td>
                <td>{estate.address}</td>
                <td>
                    <a className="btn btn-dark btn-sm" onClick={() => seeDetailOnClick(estate)}>
                        <i className="bi bi-card-text"/> Detalle
                    </a>
                </td>
                <td>
                    <a className="btn btn-dark btn-sm" onClick={() => editOnClick(estate)}>
                        <i className="bi bi-pencil-fill"/> Editar
                    </a>
                </td>
                <td>
                    <a className="btn btn-dark btn-sm" onClick={() => deleteOnClick(estate)}>
                        <i className="bi bi-x-square-fill"/> Borrar
                    </a>
                </td>
            </tr>
        );
    });

    return (
        <GenericEmptyTable show={estateListComponents.length > 0} emptyTableText="No hay propiedades para mostrar.">
            {isLoading ?
                <div className="spinner"><Spinner animation="border" variant="dark"/></div>
                :
                <table className="table table-hover">
                    <thead>
                    <tr className="basic-padding">
                        <th scope="col">Nombre</th>
                        <th scope="col">Dirección</th>
                        <th scope="col"/>
                        <th scope="col"/>
                        <th scope="col"/>
                    </tr>
                    </thead>

                    <tbody>
                    {estateListComponents}
                    </tbody>
                </table>
            }
        </GenericEmptyTable>

    );
};

export default EstateListTable;