import React from 'react';
import { Link } from 'react-router-dom';

const Header = () => {
    return (
        <div className="container">
            <nav className="navbar navbar-expand-lg navbar-light bg-light header">
                <div className="container-fluid">
                    <a className="navbar-brand" href="#">RealEstate</a>
                    {/* Barra responsive */}
                    <button className="navbar-toggler" type="button" data-bs-toggle="collapse"
                            data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false"
                            aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>

                    <div className="collapse navbar-collapse" id="navbarNavAltMarkup">
                        <ul className="navbar-nav">
                            <li className="nav-item"><Link to="/" className="nav-link" aria-current="page">Inicio</Link></li>
                            <li className="nav-item"><Link to="/estate/all" className="nav-link">Contratos</Link></li>
                            <li className="nav-item dropdown">
                                <a className="nav-link dropdown-toggle" id="navbarDropdownMenuLink"
                                   role="button" data-bs-toggle="dropdown" aria-expanded="false" href="#">Inquilinos</a>
                                <ul className="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                                    <li><Link to="/tenant/all" className="dropdown-item">Listar</Link></li>
                                    <li><Link to="/tenant/creation" className="dropdown-item">Crear</Link></li>
                                    <li><Link to="/" className="dropdown-item">Borrar</Link></li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </div>


    );
};

export default Header;