import React, {useContext, useEffect, useState, createRef} from 'react';
import {Button, Col, Form, Row, Table} from "react-bootstrap";
import {AlertContext} from "../utils/GenericAlert";
import {ServicesContext} from "../../services/Services";
import Estate from "../../models/Estate";
import Apartment from "../../models/Apartment";

const EstateCreation = () => {
    let estateName = createRef();
    let estateAddress = createRef();
    let estateDescription = createRef();
    let form = createRef();

    const { estateService } = useContext(ServicesContext);
    const {setShowAlert, setAlertType, setAlertContent} = useContext(AlertContext);
    const [validated, setValidated] = useState(false);
    const [apartments, setApartments] = useState([]);

    function handleSubmit(event) {
        let validationOk = true;

        event.preventDefault();

        // realizamos la validacion del form
        const form = event.currentTarget;
        if (form.checkValidity() === false) {
            validationOk = false;
        }
        setValidated(true);

        if (validationOk) {
            let estate = new Estate(
                null,
                estateName.current.value,
                estateAddress.current.value,
                estateDescription.current.value,
                apartments
            );

            estateService.createEstate(estate,
                () => {
                    setAlertType("success");
                    setShowAlert(true);
                    setAlertContent(<div><i className="bi bi-check-circle"></i> La propiedad <strong>{estate && estate.name}</strong> fue agregada con éxito</div>);
                    setValidated(false);
                    setApartments([]);
                    form.reset();
                },
                (error) => {
                    setAlertType("danger");
                    setShowAlert(true);
                    setAlertContent(<div><i className="bi bi-exclamation-circle"></i> Hubo un error al crear la propiedad: "{error.message}"</div>);
                }
            );
        }
    }

    useEffect(() => {
        setApartments([]);
    }, []);

    function getApartmentsTable() {
        if (apartments.length > 0) {
            return(
                <Table className="table-hover" id="apartment-table">
                    <thead>
                        <tr className="basic-padding">
                            <th scope="col">Nombre</th>
                            <th scope="col">Ambientes</th>
                            <th scope="col">Descripción</th>
                            <th scope="col"></th>
                        </tr>
                    </thead>

                    <tbody>
                        {apartmentsRows}
                    </tbody>
                </Table>
            );
        } else {
            return (<div className="align-center padding-bottom-15">No hay departamentos para esta propiedad.</div>);
        }
    }

    function handleInputChange(e, i, name) {
        const newApartments = [...apartments];
        newApartments[i][name] = e.target.value;
        setApartments(newApartments);
    }

    const apartmentsRows = apartments.map((apartment, index) => {
        return (
        <tr key={index}>
            <td>
                <Form.Control value={apartment.name} onChange={(e) => handleInputChange(e, index, "name")} required />
                <Form.Control.Feedback type="invalid">*Obligatorio</Form.Control.Feedback>
            </td>
            <td>
                <Form.Control value={apartment.rooms} onChange={(e) => handleInputChange(e, index, "rooms")} type="number" min="1" step="1" required />
                <Form.Control.Feedback type="invalid">*Obligatorio (entero mayor que 0)</Form.Control.Feedback>
            </td>
            <td>
                <Form.Control as="textarea" style={{height:"38px"}} value={apartment.description} onChange={(e) => handleInputChange(e, index, "description")} />
            </td>
            <td>
                <Button className="trash-button d-flex align-items-center" variant="danger" onClick={() => removeApartment(index)}><i className="bi bi-trash" style={{fontSize: "20px"}}/></Button>
            </td>
        </tr>);
    })

    function addApartment() {
        setValidated(false);
        setApartments(prevState => [...prevState, new Apartment(null, null, 1, "", "")]);
    }

    function removeApartment(index) {
        let newApartments = [...apartments];
        newApartments.splice(index, 1)
        setApartments(newApartments);
    }

    return (
        <div className="container">
            <Form ref={form} onSubmit={handleSubmit} noValidate validated={validated} className="basic-padding-20 shadow justify-content-center rounded-bottom border border-light">
                <div>
                    <h3>Agregar propiedad</h3>
                    <hr />
                </div>

                <Form.Group as={Row} className="justify-content-center">
                    <Form.Label column sm={3}>
                        Nombre
                    </Form.Label>
                    <Col sm={8}>
                        <Form.Control type="text" ref={estateName} placeholder="Nombre" required />
                        <Form.Control.Feedback type="invalid">*Obligatorio</Form.Control.Feedback>
                    </Col>
                </Form.Group>

                <Form.Group as={Row} className="justify-content-center">
                    <Form.Label column sm={3}>
                        Dirección
                    </Form.Label>
                    <Col sm={8}>
                        <Form.Control type="text" ref={estateAddress} placeholder="Dirección" required />
                        <Form.Control.Feedback type="invalid">*Obligatorio</Form.Control.Feedback>
                    </Col>
                </Form.Group>

                <Form.Group as={Row} className="justify-content-center">
                    <Form.Label column sm={3}>
                        Descripción
                    </Form.Label>
                    <Col sm={8}>
                        <Form.Control type="text" ref={estateDescription} placeholder="Descripción" />
                    </Col>
                </Form.Group>

                <div className="padding-top-15">
                    <h3>Departamentos</h3><hr />
                </div>

                <div className="detail-table-padding">
                    <Button variant="success" style={{fontSize: "12px"}} onClick={addApartment}>Agregar</Button>
                </div>

                <div className="table-responsive">
                    {getApartmentsTable()}
                </div>

                <div className="align-center"><Button variant="dark" type="submit">Agregar</Button></div>
            </Form>
        </div>

    );
};

export default EstateCreation;