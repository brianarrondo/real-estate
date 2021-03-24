import React, { useState, useEffect } from 'react';
import TenantService from "../../services/TenantService";
import Utils from "../../utils/Utils";

import DeleteConfirmModal from "../utils/DeleteConfirmModal";
import AlertCustom from "../utils/AlertCustom";
import TenantEdition from "./TenantEdition";
import TenantDeletion from "./TenantDeletion";

const TenantList = () => {
	const [tenants, setTenants] = useState([]);
	const [tenantToDelete, setTenantToDelete] = useState(null);
	const [showTenantDeletionAlert, setShowTenantDeletionAlert] = useState(null);

	const [showTenantEditionModal, setShowTenantEditionModal] = useState(false);
	const [showTenantDeletionModal, setShowTenantDeletionModal] = useState(false);

	const [tenantToEdit, setTenantToEdit] = useState(null);

	const [errorOnRequest, setErrorOnRequest] = useState(null);

	useEffect(() => {
		getAllTenants();
	}, []);

	const getAllTenants = () => {
		TenantService.getAllTenants((response) => setTenants(response.data));
	};

	function editOnClick(tenant) {
		setTenantToEdit(tenant);
		setShowTenantEditionModal(true);
	}

	function deleteOnClick(tenant) {
		setTenantToDelete(tenant);
		setShowTenantDeletionModal(true);
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

				<AlertCustom type="success" show={showTenantDeletionAlert} onClose={() => setShowTenantDeletionAlert(false)}> Borrando </AlertCustom>
				<TenantEdition
					show={showTenantEditionModal}
					onHide={() => setShowTenantEditionModal(false)}
					tenant={tenantToEdit}
					callback={() => getAllTenants()}
				/>
				<TenantDeletion
					show={showTenantDeletionModal}
					onHide={() => setShowTenantDeletionModal(false)}
					tenant={tenantToDelete}
					callback={() => {
							getAllTenants();
							setShowTenantDeletionAlert(true);
						}
					}
				/>

			</div>
		</div>
	);
}

export default TenantList;