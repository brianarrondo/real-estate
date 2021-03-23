import React, { useState, useEffect } from 'react';
import TenantService from "../../services/TenantService";
import Utils from "../../utils/Utils";

import DeleteConfirmModal from "../utils/DeleteConfirmModal";
import Alert from "../utils/Alert";
import TenantEditForm from "./TenantEditForm";

const TenantList = () => {
	const [tenants, setTenants] = useState([]);
	const [tenantToDelete, setTenantToDelete] = useState(null);
	const [showTenantDeletionAlert, setShowTenantDeletionAlert] = useState(null);

	const [showTenantEditionModal, setShowTenantEditionModal] = useState(false);
	const [tenantToEdit, setTenantToEdit] = useState(null);
	const [showTenantEditionAlert, setShowTenantEditionAlert] = useState(null);

	const [errorOnRequest, setErrorOnRequest] = useState(null);

	useEffect(() => {
		getAllTenants();
	}, []);

	const getAllTenants = () => {
		TenantService.getAllTenants((response) => setTenants(response.data));
	};

	const deleteTenant = (tenant) => {
		TenantService.deleteTenant(tenant,
			(response) => {
				setShowTenantDeletionAlert(true);
				getAllTenants();
			},
			(error) => {
				setErrorOnRequest(error);
			}
		);
	};

	/* Muestra el modal flotante para confirmar la eliminacion del objeto */
	const deleteConfirmModal = () => {
		return (
			<DeleteConfirmModal title="Borrar Inquilino" onClick={() => deleteTenant(tenantToDelete)} id="deleteConfirmModal">
				¿Está seguro que desea borrar el inquilino <span className="bold"> {tenantToDelete && tenantToDelete.fullName} </span>?
			</DeleteConfirmModal>
		);
	};

	/* Muestra la alerta flotante al eliminar correctamente un objeto */
	const displayTenantDeletionSuccessAlert = () => {
		let tenantName = tenantToDelete ? tenantToDelete.fullName : "";
		return (
			<div key={Utils.generateKey(tenantToDelete)}>
				<Alert type="success" show={showTenantDeletionAlert} onClose={() => setShowTenantDeletionAlert(false)}>
					<i className="bi bi-check-circle"></i> El inquilino <strong>{tenantName}</strong> ha sido borrado con éxito
				</Alert>
			</div>
		);
	};
	
	/* Alerta para errores al realizar el request */
	const displayTenantErrorCreationAlert = () => {
		let errorMsg = errorOnRequest ? errorOnRequest.message : "";
		return (
			<div key={Utils.generateKey("error")}>
				<Alert type="danger" show={errorOnRequest} onClose={() => setErrorOnRequest(null)}>
					<i className="bi bi-exclamation-circle"></i> Hubo un error: "{errorMsg}"
				</Alert>
			</div>
		);
	};

	function editOnClick(tenant) {
		setTenantToEdit(tenant);
		setShowTenantEditionModal(true);
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
					<a className="btn btn-dark btn-sm" data-bs-toggle="modal" onClick={() => setTenantToDelete(tenant)}>
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

				{deleteConfirmModal()}
				{displayTenantDeletionSuccessAlert()}

				<TenantEditForm show={showTenantEditionModal} onHide={() => setShowTenantEditionModal(false)} tenant={tenantToEdit} callback={() => getAllTenants()} />

				{displayTenantErrorCreationAlert()}

			</div>
		</div>
	);
}

export default TenantList;