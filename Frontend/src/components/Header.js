import React, {useContext} from 'react';
import {Nav, Navbar, NavDropdown} from "react-bootstrap";
import {Link} from "react-router-dom";
import {TokenLoggerContext} from "./login/TokenLogger";

const Header = () => {
    const { token } = useContext(TokenLoggerContext);

    if (token) return (
        <div className="container header">
            <Navbar bg="dark" variant="dark" expand="lg">
                <Navbar.Brand href="#home">Inmobiliaria</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="mr-auto">
                        <Nav.Link as={Link} to="/">Inicio</Nav.Link>

                        <NavDropdown title="Inquilinos" id="tenant">
                            <NavDropdown.Item as={Link} to="/tenant/all">Listar</NavDropdown.Item>
                            <NavDropdown.Item as={Link} to="/tenant/creation">Agregar</NavDropdown.Item>
                        </NavDropdown>

                        <NavDropdown title="Propiedad" id="estate">
                            <NavDropdown.Item as={Link} to="/estate/all">Listar</NavDropdown.Item>
                            <NavDropdown.Item as={Link} to="/estate/creation">Agregar</NavDropdown.Item>
                        </NavDropdown>

                        <NavDropdown title="Contratos" id="lease">
                            <NavDropdown.Item as={Link} to="/lease/all">Listar</NavDropdown.Item>
                            <NavDropdown.Item as={Link} to="/lease/creation">Agregar</NavDropdown.Item>
                        </NavDropdown>

                        <NavDropdown title="Pagos" id="payment">
                            <NavDropdown.Item as={Link} to="/payment/all">Listar</NavDropdown.Item>
                            <NavDropdown.Item as={Link} to="/payment/creation">Agregar</NavDropdown.Item>
                        </NavDropdown>
                    </Nav>

                    <Navbar.Collapse className="justify-content-end">
                        <Nav className="justify-content-end"><Nav.Link as={Link} to="/logout">Cerrar Sesión</Nav.Link></Nav>
                    </Navbar.Collapse>
                </Navbar.Collapse>
            </Navbar>
        </div>
    );
    return null;
};

export default Header;