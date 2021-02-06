import React from 'react';
import Tenant from './Tenant';
import TenantService from "../../services/TenantService";

class TenantList extends React.Component {
	state = { tenants: [] };

	componentDidMount = () => {
		TenantService.getAllTenants((response) => this.setState({ tenants: response.data }));
	}

	render() {
		const tenantListComponents = this.state.tenants.map((tenant) => {
				return <Tenant key={tenant.tenantId} tenantResponse={tenant} />;
			});
		return (
			<div className="ui stackable four column grid container">
				{tenantListComponents}
			</div>
		);
	}
}

export default TenantList;