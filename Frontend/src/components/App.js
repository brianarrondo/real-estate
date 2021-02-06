import React from 'react';
import { BrowserRouter, Route } from "react-router-dom";

import Header from "./Header";
import Home from "./Home";
import TenantList from "./tenant/TenantList";
import EstateList from "./estate/EstateList";
import TenantCreation from "./tenant/TenantCreation";

const App = () => {

	return (
		<div className="ui container">
			<BrowserRouter>
				<Header />
				<div>
					<Route path="/" exact component={Home} />
					<Route path="/estate/all" exact component={EstateList} />
					<Route path="/tenant/all" exact component={TenantList} />
					<Route path="/tenant/creation" exact component={TenantCreation} />
				</div>
			</BrowserRouter>
		</div>
	);
};

export default App;