import React, {useContext, useEffect, useState} from "react";
import {ModalContext} from "../utils/GenericModal";
import {AlertContext} from "../utils/GenericAlert";
import {ServicesContext} from "../../services/Services";
import EstateModalDetail from "./EstateModalDetail";

const EstateListTable = () => {
    const { estateService } = useContext(ServicesContext);
    const {setModalShow, setModalContent, setSize} = useContext(ModalContext);
    const {setShowAlert, setAlertType, setAlertContent} = useContext(AlertContext);
    const [estates, setEstates] = useState([]);

    useEffect(() => {
        getAllEstates();
    }, []);

    const getAllEstates = () => {
        estateService.getAllEstates(
            (response) => {
                setEstates(response.data);
            },
            (error) => {
                setAlertType("danger");
                setShowAlert(true);
                setAlertContent(<div><i className="bi bi-exclamation-circle"/> Hubo un error al obtener el listado de propiedades: "{error.message}"</div>);
            }
        );
    };

    function editOnClick() {
        //TODO
    }

    function deleteOnClick() {
        //TODO
    }

    function seeDetailOnClick(estate) {
        setModalContent(
            <EstateModalDetail
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
            <tr key={estate.estateId}>
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
        <table className="table table-hover">
            <thead>
                <tr className="basic-padding">
                    <th scope="col">Nombre</th>
                    <th scope="col">Dirección</th>
                    <th scope="col" />
                    <th scope="col" />
                    <th scope="col" />
                </tr>
            </thead>

            <tbody>
            {estateListComponents}
            </tbody>
        </table>
    );
};

export default EstateListTable;