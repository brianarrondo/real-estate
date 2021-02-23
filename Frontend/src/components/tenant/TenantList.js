import React, { useState, useEffect } from 'react';
import TenantService from "../../services/TenantService";
import DeleteConfirmModal from "../utils/DeleteConfirmModal";

const TenantList = () => {
	const [tenants, setTenants] = useState([]);
	const [tenantToDelete, setTenantToDelete] = useState(null);

	useEffect(() => {
		getAllTenants();
	}, []);

	const getAllTenants = () => {
		TenantService.getAllTenants((response) => setTenants(response.data));
	};

	const deleteTenant = (tenant) => {
		TenantService.deleteTenant(tenant, getAllTenants);
	};

	const tenantListComponents = tenants.map((tenant) => {
		return (
			<tr key={tenant.tenantId}>
				<th scope="row">{tenant.fullName}</th>
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
				<DeleteConfirmModal title="Borrar Inquilino" onClick={() => deleteTenant(tenantToDelete)}>
					¿Desea borrar el inquilino <span className="bold"> {tenantToDelete && tenantToDelete.fullName} </span>?
				</DeleteConfirmModal>
			</div>
		);
	};

	return (
		<div className="container">
			<table className="table table-bordered table-hover table-responsive">
				<thead>
					<tr>
						<th scope="col">NOMBRE</th>
						<th scope="col">DNI</th>
						<th scope="col">TELÉFONO</th>
						<th scope="col">DESCRIPCIÓN</th>
						<th scope="col"></th>
						<th scope="col"></th>
					</tr>
				</thead>

				<tbody>
				{tenantListComponents}
				</tbody>
			</table>

			{showDeleteConfirmModal()}

		</div>
	);
}

export default TenantList;