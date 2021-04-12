import React from 'react';
import { BrowserRouter, Route } from "react-router-dom";

import Header from "./Header";

import Home from "./Home";
import TenantList from "./tenant/TenantList";
import EstateList from "./estate/EstateList";
import TenantCreation from "./tenant/TenantCreation";

import Logout from "./login/Logout";
import Login from "./login/Login";

import TokenLogger from "./login/TokenLogger";
import GenericModal from "./utils/GenericModal";
import GenericAlert from "./utils/GenericAlert";

import Styles from "../css/styles.css";

const App = () => {
	const { token, saveToken } = TokenLogger();

	if(!token) {
		return <Login saveToken={saveToken} />
	}

	return (
		<div className="container">
			<BrowserRouter>

				<GenericAlert>
					<GenericModal>
						<Header />
						<Route path="/" exact component={Home} />
						<Route path="/estate/all" exact component={EstateList} />
						<Route path="/tenant/all" exact component={TenantList} />
						<Route path="/tenant/creation" exact component={TenantCreation} />
						<Route path="/logout" exact component={Logout} />
					</GenericModal>
				</GenericAlert>

			</BrowserRouter>
		</div>
	);
};

export default App;