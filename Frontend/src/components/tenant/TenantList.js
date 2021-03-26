import React, { useState, useEffect } from 'react';
import TenantService from "../../services/TenantService";

import AlertCustom from "../utils/AlertCustom";
import TenantEdition from "./TenantEdition";
import TenantDeletion from "./TenantDeletion";

const TenantList = () => {
	const [tenants, setTenants] = useState([]);
	const [tenantToEdit, setTenantToEdit] = useState(null);
	const [tenantToDelete, setTenantToDelete] = useState(null);
	const [tenantEdited, setTenantEdited] = useState(null);

	const [alertAction, setAlertAction] = useState(null);
	const [showAlert, setShowAlert] = useState(false);

	const [showTenantEditionModal, setShowTenantEditionModal] = useState(false);
	const [showTenantDeletionModal, setShowTenantDeletionModal] = useState(false);

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

	const tenantEditionAlertChildren = () => {
		return (<div><i className="bi bi-check-circle"></i> El inquilino <strong>{tenantEdited && tenantEdited.fullName}</strong> ha sido modificado con éxito</div>);
	};

	const tenantDeletionAlertChildren = () => {
		return (<div><i className="bi bi-check-circle"></i> El inquilino <strong>{tenantToDelete && tenantToDelete.fullName}</strong> ha sido borrado con éxito</div>);
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
						{tenantListComponents}
						</tbody>
					</table>
				</div>

				<AlertCustom type="success" show={showAlert} onClose={() => setShowAlert(false)}>
					{alertAction === "edit"? tenantEditionAlertChildren() : (alertAction === "delete" ? tenantDeletionAlertChildren() : null)}
				</AlertCustom>

				<TenantEdition
					show={showTenantEditionModal}
					onHideCallback={() => setShowTenantEditionModal(false)}
					tenant={tenantToEdit}
					callback={() => {
							getAllTenants();
							setTenantEdited(tenantToEdit);
							setAlertAction("edit");
							setShowAlert(true);
						}
					}
				/>
				<TenantDeletion
					show={showTenantDeletionModal}
					onHide={() => setShowTenantDeletionModal(false)}
					tenant={tenantToDelete}
					callback={() => {
							getAllTenants();
							setAlertAction("delete");
							setShowAlert(true);
						}
					}
				/>

			</div>
		</div>
	);
}

export default TenantList;