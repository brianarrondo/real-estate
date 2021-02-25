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

	useEffect(() => {
		getAllTenants();
	}, []);

	const getAllTenants = () => {
		TenantService.getAllTenants((response) => setTenants(response.data));
	};

	const deleteTenant = (tenant) => {
		TenantService.deleteTenant(tenant, () => {
			setShowTenantDeletionAlert(true);
			getAllTenants();
		});
	};

	const editTenant = (tenant) => {
		TenantService.editTenant(tenant, () => {
			setShowTenantEditionAlert(true);
			getAllTenants();
		});
	};

	/* Function que construye el componente que incluye los inputs del form con parametros customs */
	const TenantEditFormBuilder = (register, tenant) => {
		return (
			<TenantEditForm register={register} tenant={tenant} />
		);
	};

	/* Muestra el modal flotante para confirmar la eliminacion del objeto */
	const showDeleteConfirmModal = () => {
		return (
			<DeleteConfirmModal title="Borrar Inquilino" onClick={() => deleteTenant(tenantToDelete)} id="confirmDeleteModal">
				¿Está seguro que desea borrar el inquilino <span className="bold"> {tenantToDelete && tenantToDelete.fullName} </span>?
			</DeleteConfirmModal>
		);
	};

	/* Muestra el modal flotante para editar el objeto */
	const showEditModal = () => {
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
				<Alert type="success" show={showTenantDeletionAlert} setShow={setShowTenantDeletionAlert}>
					<i className="bi bi-check-circle"></i> El inquilino <strong>{tenantName}</strong> ha sido borrado con éxito
				</Alert>
			</div>
		);
	};

	/* Muestra la alerta flotante al editar correctamente un objeto */
	const displayTenantEditionSuccessAlert = () => {
		return (
			<div key={Utils.generateKey("")}>
				<Alert type="success" show={showTenantEditionAlert} setShow={setShowTenantEditionAlert}>
					<i className="bi bi-check-circle"></i> El inquilino ha sido modificado exitosamente
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

				{showDeleteConfirmModal()}
				{showEditModal()}

				{displayTenantDeletionSuccessAlert()}
				{displayTenantEditionSuccessAlert()}

			</div>
		</div>
	);
}

export default TenantList;