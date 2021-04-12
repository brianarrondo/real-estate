import React from 'react';
import {Nav, Navbar, NavDropdown} from "react-bootstrap";
import {Link} from "react-router-dom";

const Header = () => {
    return (
        <div className="container header">
            <Navbar bg="dark" variant="dark" expand="lg">
                <Navbar.Brand href="#home">Inmobiliaria</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="mr-auto">
                        <Nav.Link as={Link} to="/">Inicio</Nav.Link>
                        <NavDropdown title="Inquilinos" id="basic-nav-dropdown">
                            <NavDropdown.Item as={Link} to="/tenant/all">Listar</NavDropdown.Item>
                            <NavDropdown.Item as={Link} to="/tenant/creation">Agregar</NavDropdown.Item>
                        </NavDropdown>
                    </Nav>
                    <Navbar.Collapse className="justify-content-end">
                        <Nav className="justify-content-end"><Nav.Link as={Link} to="/logout">Cerrar Sesión</Nav.Link></Nav>
                    </Navbar.Collapse>
                </Navbar.Collapse>
            </Navbar>
        </div>
    );
};

export default Header;