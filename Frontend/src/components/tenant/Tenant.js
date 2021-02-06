import React from 'react';
import '../../css/styles.css';

class Tenant extends React.Component {

	render() {
		const tenantData = this.props.tenantResponse;
		return (
			<div className="column">
				<div className="ui card">
					<div className="content">
						<div className="header">{tenantData.fullName}</div>
					</div>
					<div className="content">
						<span className="ui sub">
							{/* <span className="obj-attr-title">ID</span> <span>{tenantData.tenantId}</span><br /> */}
							<span className="obj-attr-title">DNI</span> <span>{tenantData.dni}</span><br />
							<span className="obj-attr-title">TELÉFONO</span> <span>{tenantData.phone}</span><br />
							<span className="obj-attr-title">DESCRIPCIÓN</span> <span>{tenantData.description}</span><br />
						</span>
					</div>
					<div className="extra content">
						<button className="ui button">Editar</button>
					</div>
				</div>
			</div>

		);
	}
}

export default Tenant;