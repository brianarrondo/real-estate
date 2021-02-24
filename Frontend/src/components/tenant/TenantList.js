import React, { useState, useEffect } from 'react';
import TenantService from "../../services/TenantService";
import Utils from "../../utils/Utils";

import DeleteConfirmModal from "../utils/DeleteConfirmModal";
import Alert from "../utils/Alert";

const TenantList = () => {
	const [tenants, setTenants] = useState([]);
	const [tenantToDelete, setTenantToDelete] = useState(null);
	const [tenantDeleted, setTenantDeleted] = useState(null);

	useEffect(() => {
		getAllTenants();
	}, []);

	const getAllTenants = () => {
		TenantService.getAllTenants((response) => setTenants(response.data));
	};

	const deleteTenant = (tenant) => {
		TenantService.deleteTenant(tenant, () => {
			setTenantDeleted(tenant.fullName);
			getAllTenants();
		});
	};

	const tenantListComponents = tenants.map((tenant) => {
		return (
			<tr key={tenant.tenantId}>
				<td scope="row" className="bold">{tenant.fullName}</td>
				<td>{tenant.dni}</td>
				<td>{tenant.phone}</td>
				<td>{tenant.description}</td>
				<td><a href="#" className="btn btn-dark btn-sm"><i className="bi bi-pencil-fill"></i> Editar</a></td>
				<td><a href="#" className="btn btn-dark btn-sm" data-bs-toggle="modal" data-bs-target="#confirmDeleteModal" onClick={ () => setTenantToDelete(tenant) }><i className="bi bi-x-square-fill"></i> Borrar</a></td>
			</tr>
		);
	});

	const showDeleteConfirmModal = () => {
		return (
			<div>
				<DeleteConfirmModal title="Borrar Inquilino" onClick={() => deleteTenant(tenantToDelete)} id="confirmDeleteModal">
					¿Está seguro que desea borrar el inquilino <span className="bold"> {tenantToDelete && tenantToDelete.fullName} </span>?
				</DeleteConfirmModal>
			</div>
		);
	};

	const showTenantDeletionSuccessAlert = () => {
		if (tenantDeleted != null) {
			return (
				<div key={Utils.generateKey(tenantDeleted)}>
					<Alert type="success">
						El inquilino <strong>{tenantDeleted}</strong> ha sido borrado con éxito <i className="bi bi-check-square"></i>
					</Alert>
				</div>
			);
		}
	};

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
						{console.log("RENDERIZANDO")}
						{tenantListComponents}
						</tbody>
					</table>
				</div>

				{showDeleteConfirmModal()}
				{showTenantDeletionSuccessAlert()}

			</div>
		</div>
	);
}

export default TenantList;