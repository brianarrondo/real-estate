import React, {useContext, useEffect, useState, useRef} from "react";
import {Button, ButtonGroup, Col, Form, Row, Table} from "react-bootstrap";
import GenericSpinner from "../utils/GenericSpinner";
import {ServicesContext} from "../../services/Services";
import {AlertContext} from "../utils/GenericAlert";
import Utils from "../../utils/Utils";
import {ModalContext} from "../utils/GenericModal";
import ExistingTenantAdditionModal from "./ExistingTenantAdditionModal";
import Tenant from "../../models/Tenant";

const LeaseCreation = () => {
    let form = React.createRef();
    let leaseName = React.createRef();
    let leaseDescription = React.createRef();

    const [estates, setEstates] = useState([]);
    const [apartments, setApartments] = useState([]);
    const [newTenants, setNewTenants] = useState([]);
    const [existingTenants, setExistingTenants] = useState([]);

    const [isLoading, setLoading ] = useState(false);
    const [validated, setValidated] = useState(false);

    const {setModalShow, setModalContent, setSize} = useContext(ModalContext);
    const {setShowAlert, setAlertType, setAlertContent} = useContext(AlertContext);
    const { tenantService, apartmentService, estateService } = useContext(ServicesContext);

    function handleSubmit(event) {
        let validationOk = true;

        event.preventDefault();
        console.log("submit");
    }

    useEffect(() => {
        setLoading(true);
        apartmentService.getAllApartment(
            (response) => {
                setApartments(response.data);
            },
            (error) => {
                setAlertType("danger");
                setShowAlert(true);
                setAlertContent(<div><i className="bi bi-exclamation-circle"></i> Hubo un error al cargar los departamentos: "{error.message}"</div>);
            }
        );

        estateService.getAllEstates(
            (response) => {
                setEstates(response.data);
                setLoading(false);
            },
            (error) => {
                setAlertType("danger");
                setShowAlert(true);
                setLoading(false);
                setAlertContent(<div><i className="bi bi-exclamation-circle"></i> Hubo un error al cargar las propiedades: "{error.message}"</div>);
            }
        );
    }, []);

    function addExistingTenant() {
        setModalContent(
            <ExistingTenantAdditionModal
                setModalShow={setModalShow}
                setExistingTenants={setExistingTenants}
            />
        )
        setSize("md");
        setModalShow(true);
    }

    function handleInputChange(e, i, name) {
        const newTenantsToAdd = [...newTenants];
        newTenantsToAdd[i][name] = e.target.value;
        setNewTenants(newTenantsToAdd);
    }

    function addNewTenant() {
        setValidated(false);
        setNewTenants(prevState => [...prevState, new Tenant("", "", "", "", "")]);
    }

    function removeNewTenantRow(index) {
        let newTenantsToAdd = [...newTenants];
        newTenantsToAdd.splice(index, 1);
        setNewTenants(newTenantsToAdd);
    }

    function removeExistingTenantRow(index) {
        let existingTenantsToAdd = [...existingTenants];
        existingTenantsToAdd.splice(index, 1);
        setExistingTenants(existingTenantsToAdd);
    }

    const existingTenantsRows = existingTenants.map((t, index) => {
        return (
            <tr key={index}>
                <td>{t.fullName}</td>
                <td>{t.dni}</td>
                <td>{t.phone}</td>
                <td>{t.description}</td>
                <td>
                    <Button className="delete-table-row-button" variant="danger" style={{display: "flex"}} onClick={() => removeExistingTenantRow(index)}>
                        <i className="bi bi-x x-button" />
                    </Button>
                </td>
            </tr>
        );
    });

    const newTenantsRows = newTenants.map((t, index) => {
        return (
            <tr key={index}>
                <td>
                    <Form.Control value={t.fullName} onChange={(e) => handleInputChange(e, index, "fullName")} required />
                    <Form.Control.Feedback type="invalid">Por favor ingresar el nombre del inquilino</Form.Control.Feedback>
                </td>
                <td>
                    <Form.Control value={t.dni} onChange={(e) => handleInputChange(e, index, "dni")} />
                </td>
                <td>
                    <Form.Control value={t.phone} onChange={(e) => handleInputChange(e, index, "phone")} />
                </td>
                <td>
                    <Form.Control value={t.description} onChange={(e) => handleInputChange(e, index, "description")} maxLength="70" />
                </td>
                <td>
                    <Button className="delete-table-row-button" variant="danger" style={{display: "flex"}} onClick={() => removeNewTenantRow(index)}>
                        <i className="bi bi-x x-button" />
                    </Button>
                </td>
            </tr>
        );
    });

    function getTenantsTable() {
        if (apartments.length > 0) {
            return(
                <div className="table-responsive">
                    <Table className="table-hover">
                        <thead>
                        <tr className="basic-padding">
                            <th scope="col">Nombre</th>
                            <th scope="col">DNI</th>
                            <th scope="col">Teléfono</th>
                            <th scope="col">Descripción</th>
                            <th scope="col"></th>
                        </tr>
                        </thead>

                        <tbody>
                        {existingTenantsRows}
                        {newTenantsRows}
                        </tbody>
                    </Table>
                </div>
            );
        } else {
            return (<div className="align-center padding-bottom-15">No hay departamentos para esta propiedad.</div>);
        }
    }

    const apartmentsSelectOptions = apartments.map((a) => {
        return (
            <option key={a.apartmentId} value={a.apartmentId}>
                {a.name} ({estates && estates.length && estates.find((e) => e.estateId === a.estateId).name})
            </option>
        );
    });

    return (
        <div className="container">
            <Form ref={form} onSubmit={handleSubmit} noValidate validated={validated} className="basic-padding-20 shadow justify-content-center rounded-bottom border border-light">
                <div>
                    <h3>Crear contrato</h3>
                    <hr />
                </div>

                <Form.Group as={Row} className="justify-content-center">
                    <Form.Label column sm={3}>Nombre</Form.Label>
                    <Col sm={8}>
                        <Form.Control type="text" ref={leaseName} placeholder="Nombre" required />
                        <Form.Control.Feedback type="invalid">Por favor ingresar el nombre del contrato</Form.Control.Feedback>
                    </Col>
                </Form.Group>

                <Form.Group as={Row} className="justify-content-center">
                    <Form.Label column sm={3}>Fecha de Inicio</Form.Label>
                    <Col sm={8}>
                        <Form.Control
                            type="date"
                            ref={leaseName}
                            placeholder="Fecha de caducidad"
                            defaultValue={Utils.getCurrentDate()}
                            required />
                        <Form.Control.Feedback type="invalid">Por favor seleccione una fecha de inicio</Form.Control.Feedback>
                    </Col>
                </Form.Group>

                <Form.Group as={Row} className="justify-content-center">
                    <Form.Label column sm={3}>Fecha de Caducidad</Form.Label>
                    <Col sm={8}>
                        <Form.Control
                            type="date"
                            ref={leaseName}
                            placeholder="Fecha de caducidad"
                            defaultValue={Utils.getCurrentDate()}
                            min={Utils.getCurrentDate()}
                            required />
                        <Form.Control.Feedback type="invalid">Por favor seleccione una fecha de caducidad</Form.Control.Feedback>
                    </Col>
                </Form.Group>

                <Form.Group as={Row} className="justify-content-center">
                    <Form.Label column sm={3}>Descripción</Form.Label>
                    <Col sm={8}>
                        <Form.Control type="textarea" ref={leaseDescription} placeholder="Descripcion" />
                    </Col>
                </Form.Group>

                <div className="padding-top-15">
                    <h5>Departamento</h5><hr />
                </div>

                <Form.Group as={Row} className="justify-content-center">
                    <Form.Label column sm={3}>Departamento</Form.Label>
                    <Col sm={8}>
                        <Form.Control as="select">
                            <option key="0">Seleccione un departamento</option>
                            {apartmentsSelectOptions}
                        </Form.Control>
                        <Form.Control.Feedback type="invalid">Por favor seleccione un departamento</Form.Control.Feedback>
                    </Col>
                </Form.Group>

                <div className="padding-top-15">
                    <h5>Inquilinos</h5><hr />
                </div>

                <div className="detail-table-padding">
                    <ButtonGroup size="sm" className="mb-2">
                        <Button style={{fontSize: "12px"}} variant="success" onClick={addNewTenant}>Agregar nuevo</Button>
                        <Button style={{fontSize: "12px", marginLeft: "1px"}} variant="success" onClick={addExistingTenant}>Agregar existente</Button>
                    </ButtonGroup>
                </div>

                <div className="table-responsive">
                    {getTenantsTable()}
                </div>

                <div className="align-center basic-padding-10">
                    <Button variant="dark" type="submit" className="form-submit-button" disabled={isLoading}>
                        <GenericSpinner show={isLoading}>Crear</GenericSpinner>
                    </Button>
                </div>
            </Form>
        </div>
    );
};

export default LeaseCreation;