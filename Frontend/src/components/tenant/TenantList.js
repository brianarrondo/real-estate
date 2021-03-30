import React, { useState, useEffect } from 'react';
import TenantService from "../../services/TenantService";

import AlertCustom from "../utils/AlertCustom";
import TenantEdition from "./TenantEdition";
import TenantDeletion from "./TenantDeletion";

const TenantList = () => {
	const [tenants, setTenants] = useState([]);
	const [tenantSelected, setTenantSelected] = useState(null);
	const [tenantEdited, setTenantEdited] = useState(null);

	const [alertType, setAlertType] = useState(null);
	const [showAlert, setShowAlert] = useState(false);

	const [showTenantEditionModal, setShowTenantEditionModal] = useState(false);
	const [showTenantDeletionModal, setShowTenantDeletionModal] = useState(false);
	const [errorMsg, setErrorMsg] = useState(null);

	useEffect(() => {
		getAllTenants();
	}, []);

	const getAllTenants = () => {
		TenantService.getAllTenants(
			(response) => {
				setTenants(response.data);
			},
			(error) => {
				setAlertType("listError");
				setErrorMsg(error.message);
				setShowAlert(true);
			}
		);
	};

	function editOnClick(tenant) {
		setTenantSelected(tenant);
		setShowTenantEditionModal(true);
	}

	function deleteOnClick(tenant) {
		setTenantSelected(tenant);
		setShowTenantDeletionModal(true);
	}

	function getAlertChild() {
		if (alertType === "editSuccess") {
			return (<div><i className="bi bi-check-circle"></i> El inquilino <strong>{tenantEdited && tenantEdited.fullName}</strong> ha sido modificado con éxito</div>);
		} else if(alertType === "deleteSuccess") {
			return (<div><i className="bi bi-check-circle"></i> El inquilino <strong>{tenantSelected && tenantSelected.fullName}</strong> ha sido borrado con éxito</div>);
		} else if(alertType === "listError") {
			return (<div><i className="bi bi-exclamation-circle"></i> Hubo un error al obtener el listado de inquilinos: "{errorMsg}"</div>);
		} else if(alertType === "editionError") {
			return (<div><i className="bi bi-exclamation-circle"></i> Hubo un error al editar el  inquilino: "{errorMsg}"</div>);
		} else if(alertType === "deletionError") {
			return (<div><i className="bi bi-exclamation-circle"></i> Hubo un error al borrar el  inquilino: "{errorMsg}"</div>);
		}
	}

	function getAlertType() {
		if (alertType === "listError" || alertType === "deletionError" || alertType === "editionError") {
			return "danger";
		} else if (alertType === "deleteSuccess" || alertType === "editSuccess") {
			return "success";
		}
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
						<i className="bi bi-pencil-fill"></i> Editar
					</a>
				</td>
				<td>
					<a className="btn btn-dark btn-sm" onClick={() => deleteOnClick(tenant)}>
						<i className="bi bi-x-square-fill"></i> Borrar
					</a>
				</td>
			</tr>
		);
	});

	return (

		<div className="container">
			<div className="basic-padding-10 shadow rounded-bottom border border-light">
				<div>
					<h3>Lista de Inquilinos</h3>
					<hr />
				</div>

				<div className="basic-padding-10 table-responsive">
					<table className="table table-hover">
						<thead>
							<tr className="basic-padding">
								<th scope="col">Nombre</th>
								<th scope="col">Dni</th>
								<th scope="col">Teléfono</th>
								<th scope="col">Descripción</th>
								<th scope="col"></th>
								<th scope="col"></th>
							</tr>
						</thead>

						<tbody>
						{tenantListComponents}
						</tbody>
					</table>
				</div>

				<AlertCustom type={getAlertType()} show={showAlert} onClose={() => setShowAlert(false)}>
					{getAlertChild()}
				</AlertCustom>

				<TenantEdition
					show={showTenantEditionModal}
					onHideCallback={() => setShowTenantEditionModal(false)}
					tenant={tenantSelected}
					successCallback={() => {
							getAllTenants();
							setTenantEdited(tenantSelected);
							setAlertType("editSuccess");
							setShowAlert(true);
						}
					}
					errorCallback={(error) => {
							setAlertType("editionError");
							setErrorMsg(error.message);
							setShowAlert(true);
						}
					}
				/>
				<TenantDeletion
					show={showTenantDeletionModal}
					onHide={() => setShowTenantDeletionModal(false)}
					tenant={tenantSelected}
					successCallback={() => {
							getAllTenants();
							setAlertType("deleteSuccess");
							setShowAlert(true);
						}
					}
					errorCallback={(error) => {
							setAlertType("deletionError");
							setErrorMsg(error.message);
							setShowAlert(true);
						}
					}
				/>

			</div>
		</div>
	);
}

export default TenantList;