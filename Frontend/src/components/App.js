import React, {useState} from 'react';
import { BrowserRouter, Route } from "react-router-dom";

import Header from "./Header";
import Home from "./Home";
import TenantList from "./tenant/TenantList";
import EstateList from "./estate/EstateList";
import TenantCreation from "./tenant/TenantCreation";
import Login from "./login/Login";
import GenericModal from "./utils/GenericModal";
import GenericAlert from "./utils/GenericAlert";

const App = () => {

	const [token, setToken] = useState(false);

	if(!token) {
		return <Login setToken={setToken} />
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
					</GenericModal>
				</GenericAlert>
			</BrowserRouter>
		</div>
	);
};

export default App;