import React, {useContext, useEffect, useState} from "react";
import {ModalContext} from "../utils/GenericModal";
import {AlertContext} from "../utils/GenericAlert";
import TenantEditionModal from "./TenantEditionModal";
import TenantDeletionModal from "./TenantDeletionModal";
import {ServicesContext} from "../../services/Services";
import {Spinner} from "react-bootstrap";

const TenantListTable = () => {
    const { tenantService } = useContext(ServicesContext);
    const {setModalShow, setModalContent, setSize} = useContext(ModalContext);
    const {setShowAlert, setAlertType, setAlertContent} = useContext(AlertContext);
    const [tenants, setTenants] = useState([]);
    const [isLoading, setLoading] = useState(false);

    useEffect(() => {
        getAllTenants();
    }, []);

    const getAllTenants = () => {
        setLoading(true);
        tenantService.getAllTenants(
            (response) => {
                setLoading(false);
                setTenants(response.data);
            },
            (error) => {
                setAlertType("danger");
                setLoading(false);
                setShowAlert(true);
                setAlertContent(<div><i className="bi bi-exclamation-circle"/> Hubo un error al obtener el listado de inquilinos: "{error.message}"</div>);
            }
        );
    };

    function editOnClick(tenant) {
        setModalContent(
            <TenantEditionModal
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
            <TenantDeletionModal
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
        <>
            {isLoading ?
                <div className="spinner"><Spinner animation="border" variant="dark"/></div>
                :
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
            }
        </>
    );
};

export default TenantListTable;