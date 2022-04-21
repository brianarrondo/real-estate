import React from 'react';
import TenantListTable from "./TenantListTable";

const TenantList = () => {
	return (
		<div className="container">
			<div className="basic-padding-10 shadow rounded-bottom border border-light">
				<div>
					<h3>Lista de Inquilinos</h3>
					<hr />
				</div>

				<div className="basic-padding-10 table-responsive">
					<TenantListTable />
				</div>
			</div>
		</div>
	);
}

export default TenantList;