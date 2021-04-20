import React from 'react';
import {BrowserRouter, Redirect, Route} from "react-router-dom";

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
import PrivateRoute from "./route/PrivateRoute";
import Services from "../services/Services";

const App = () => {
	return (
		<TokenLogger>
			<Services>

				<BrowserRouter>
					<Redirect to="/" />
					<GenericAlert>
						<GenericModal>
							<Header />
							<PrivateRoute path="/" exact component={Home} />
							<PrivateRoute path="/estate/all" exact component={EstateList} />
							<PrivateRoute path="/tenant/all" exact component={TenantList} />
							<PrivateRoute path="/tenant/creation" exact component={TenantCreation} />
							<PrivateRoute path="/logout" exact component={Logout} />
							<Route path="/login" exact component={Login} />
						</GenericModal>
					</GenericAlert>
				</BrowserRouter>

			</Services>
		</TokenLogger>
	);
};

export default App;