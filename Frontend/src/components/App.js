import React from 'react';
import {BrowserRouter, Redirect, Route} from "react-router-dom";

import Header from "./Header";

import Home from "./Home";
import TenantList from "./tenant/list/TenantList";
import EstateList from "./estate/EstateList";
import TenantCreation from "./tenant/creation/TenantCreation";

import Logout from "./login/Logout";
import Login from "./login/Login";

import TokenLogger from "./login/TokenLogger";
import GenericModal from "./utils/GenericModal";
import GenericAlert from "./utils/GenericAlert";

import Styles from "../css/styles.css";
import PrivateRoute from "./route/PrivateRoute";
import Services from "../services/Services";
import EstateCreation from "./estate/EstateCreation";
import LeaseCreation from "./lease/creation/LeaseCreation";
import LeaseList from "./lease/list/LeaseList";

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
							<PrivateRoute path="/lease/all" exact component={LeaseList} />
							<PrivateRoute path="/lease/creation" exact component={LeaseCreation} />
							<PrivateRoute path="/estate/all" exact component={EstateList} />
							<PrivateRoute path="/estate/creation" exact component={EstateCreation} />
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