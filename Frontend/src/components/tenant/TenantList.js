import React, { useState, useEffect } from 'react';
import TenantService from "../../services/TenantService";
import Utils from "../../utils/Utils";

import DeleteConfirmModal from "../utils/DeleteConfirmModal";
import Alert from "../utils/Alert";
import TenantEdition from "./TenantEdition";
import TenantEditForm from "./TenantEditForm";

const TenantList = () => {
	const [tenants, setTenants] = useState([]);
	const [tenantToDelete, setTenantToDelete] = useState(null);
	const [showTenantDeletionAlert, setShowTenantDeletionAlert] = useState(null);

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

	const editTenant = (tenant) => {
		TenantService.editTenant(tenant,
			(response) => {
				setShowTenantEditionAlert(true);
				getAllTenants();
			},
			(error) => {
				setErrorOnRequest(error);
			}
		);
	};

	/* Function que construye el componente que incluye los inputs del form con parametros customs */
	const TenantEditFormBuilder = (register, errors, tenant) => {
		return (
			<TenantEditForm register={register} errors={errors} tenant={tenant} />
		);
	};

	/* Muestra el modal flotante para confirmar la eliminacion del objeto */
	const displayDeleteConfirmModal = () => {
		return (
			<DeleteConfirmModal title="Borrar Inquilino" onClick={() => deleteTenant(tenantToDelete)} id="confirmDeleteModal">
				¿Está seguro que desea borrar el inquilino <span className="bold"> {tenantToDelete && tenantToDelete.fullName} </span>?
			</DeleteConfirmModal>
		);
	};

	/* Muestra el modal flotante para editar el objeto */
	const displayEditModal = () => {
		return(
			<TenantEdition
				title="Editar Inquilino"
				confirmButtonText="Guardar"
				id="editModal"
				submitFunction={editTenant}
				oldTenant={tenantToEdit}
				childrenBuilder={TenantEditFormBuilder}
			>
			</TenantEdition>
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

	/* Muestra la alerta flotante al editar correctamente un objeto */
	const displayTenantEditionSuccessAlert = () => {
		return (
			<div key={Utils.generateKey("")}>
				<Alert type="success" show={showTenantEditionAlert} onClose={() => setShowTenantEditionAlert(false)}>
					<i className="bi bi-check-circle"></i> El inquilino ha sido modificado exitosamente
				</Alert>
			</div>
		);
	};

	/* Alerta para errores al realizar el request */
	const displayErrorTenantCreationAlert = () => {
		let errorMsg = errorOnRequest ? errorOnRequest.message : "";
		return (
			<div key={Utils.generateKey("error")}>
				<Alert type="danger" show={errorOnRequest} onClose={() => setErrorOnRequest(null)}>
					<i className="bi bi-exclamation-circle"></i> Hubo un error: "{errorMsg}"
				</Alert>
			</div>
		);
	};

	/* Llenamos la tabla con los objetos correspondientes */
	const tenantListComponents = tenants.map((tenant) => {
		return (
			<tr key={tenant.tenantId}>
				<td className="bold">{tenant.fullName}</td>
				<td>{tenant.dni}</td>
				<td>{tenant.phone}</td>
				<td>{tenant.description}</td>
				<td>
					<a className="btn btn-dark btn-sm" data-bs-toggle="modal" data-bs-target="#editModal" onClick={() =>  setTenantToEdit(tenant) }>
						<i className="bi bi-pencil-fill"></i> Editar
					</a>
				</td>
				<td>
					<a className="btn btn-dark btn-sm" data-bs-toggle="modal" data-bs-target="#confirmDeleteModal" onClick={ () => setTenantToDelete(tenant) }>
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

				{displayDeleteConfirmModal()}
				{displayEditModal()}

				{displayTenantDeletionSuccessAlert()}
				{displayTenantEditionSuccessAlert()}
				{displayErrorTenantCreationAlert()}

			</div>
		</div>
	);
}

export default TenantList;