import React, {useContext, useEffect, useState} from "react";
import {ModalContext} from "../utils/GenericModal";
import {AlertContext} from "../utils/GenericAlert";
import TenantModalEdition from "./TenantModalEdition";
import TenantModalDeletion from "./TenantModalDeletion";
import {ServicesContext} from "../../services/Services";

const TenantListTable = () => {
    const { tenantService } = useContext(ServicesContext);
    const {setModalShow, setModalContent, setSize} = useContext(ModalContext);
    const {setShowAlert, setAlertType, setAlertContent} = useContext(AlertContext);
    const [tenants, setTenants] = useState([]);

    useEffect(() => {
        getAllTenants();
    }, []);

    const getAllTenants = () => {
        tenantService.getAllTenants(
            (response) => {
                setTenants(response.data);
            },
            (error) => {
                setAlertType("danger");
                setShowAlert(true);
                setAlertContent(<div><i className="bi bi-exclamation-circle"/> Hubo un error al obtener el listado de inquilinos: "{error.message}"</div>);
            }
        );
    };

    function editOnClick(tenant) {
        setModalContent(
            <TenantModalEdition
                setModalShow={setModalShow}
                tenant={tenant}
                successCallback={() => {
                    setAlertType("success");
                    setShowAlert(true);
                    setAlertContent(<div><i className="bi bi-check-circle"/> El inquilino <strong>{tenant && tenant.fullName}</strong> ha sido modificado con éxito</div>);
                    getAllTenants();
                }}
                errorCallback={(error) => {
                    setAlertType("danger");
                    setShowAlert(true);
                    setAlertContent(<div><i className="bi bi-exclamation-circle"/> Hubo un error al editar el inquilino: "{error.message}"</div>);
                }}
            />
        )
        setSize("lg");
        setModalShow(true)
    }

    function deleteOnClick(tenant) {
        setModalContent(
            <TenantModalDeletion
                setModalShow={setModalShow}
                tenant={tenant}
                successCallback={() => {
                    setAlertType("success");
                    setShowAlert(true);
                    setAlertContent(<div><i className="bi bi-check-circle"/> El inquilino <strong>{tenant && tenant.fullName}</strong> ha sido borrado con éxito</div>);
                    getAllTenants();
                }}
                errorCallback={(error) => {
                    setAlertType("danger");
                    setShowAlert(true);
                    setAlertContent(<div><i className="bi bi-exclamation-circle"/> Hubo un error al borrar el inquilino: "{error.message}"</div>);
                }}
            />
        )
        setSize("lg");
        setModalShow(true);
    }

    /* Llenamos la tabla con los objetos correspondientes */
    const tenantListComponents = tenants.map((tenant) => {
        return (
            <tr key={tenant.tenantId}>
                <td className="bold">{tenant.fullName}</td>
                <td>{tenant.dni}</td>
                <td>{tenant.phone}</td>
                <td>{tenant.description}</td>
                <td>
                    <a className="btn btn-dark btn-sm" onClick={() => editOnClick(tenant)}>
                        <i className="bi bi-pencil-fill"/> Editar
                    </a>
                </td>
                <td>
                    <a className="btn btn-dark btn-sm" onClick={() => deleteOnClick(tenant)}>
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
                <th scope="col">Dni</th>
                <th scope="col">Teléfono</th>
                <th scope="col">Descripción</th>
                <th scope="col"/>
                <th scope="col"/>
            </tr>
            </thead>

            <tbody>
            {tenantListComponents}
            </tbody>
        </table>
    );
};

export default TenantListTable;