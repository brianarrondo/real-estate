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

        console.log(apartments);

        event.preventDefault();

        // realizamos la validacion del form
        const form = event.currentTarget;
        if (form.checkValidity() === false) {
            validationOk = false;
        }
        setValidated(true);

        if (validationOk) {
            // TODO: realizar el request con los datos correspondientes
            /*
            let apartmentsToSet = [];
            if (estateApartments && estateApartments.length) {
                const apartmentsIds = estateApartments.map(a => { return a.value });
                apartmentsToSet = apartments
                    .filter(a => { return apartmentsIds.includes(a.apartmentId) })
                    .map(a => new Apartment(a.apartmentId, a.estate, a.rooms, a.name, a.description));
            }*/

            let estate = new Estate(
                null,
                estateName.current.value,
                estateAddress.current.value,
                estateDescription.current.value,
                apartments.current
            );

            estateService.createEstate(estate,
                () => {
                    setAlertType("success");
                    setShowAlert(true);
                    setAlertContent(<div><i className="bi bi-check-circle"></i> La propiedad <strong>{estate && estate.name}</strong> fue agregada con éxito</div>);
                    setValidated(false);
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
            return (<div className="align-center">No hay departamentos para esta propiedad.</div>);
        }
    }

    function handleInputChange(e, i, name) {
        const newApartments = [...apartments];
        newApartments[i][name] = e.target.value;
        setApartments(newApartments);
    }

    const apartmentsRows = apartments.map((apartment, index) => {
        // TODO: realizar validaciones en los inputs
        return (
        <tr key={index}>
            <td>
                <Form.Control value={apartment.name} onChange={(e) => handleInputChange(e, index, "name")} />
            </td>
            <td>
                <Form.Control value={apartment.rooms} onChange={(e) => handleInputChange(e, index, "rooms")} />
            </td>
            <td>
                <Form.Control as="textarea" style={{height:"38px"}} value={apartment.description} onChange={(e) => handleInputChange(e, index, "description")} />
            </td>
            <td>
                <Button variant="danger" onClick={() => removeApartment(index)}><i className="bi bi-x-square-fill" style={{fontSize: "20px"}}/></Button>
            </td>
        </tr>);
    });

    function addApartment() {
        setApartments(prevState => [...prevState, new Apartment(null, null, "", "", "")]);
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
                        <Form.Control.Feedback type="invalid">Por favor ingresar el nombre de la propiedad</Form.Control.Feedback>
                    </Col>
                </Form.Group>

                <Form.Group as={Row} className="justify-content-center">
                    <Form.Label column sm={3}>
                        Dirección
                    </Form.Label>
                    <Col sm={8}>
                        <Form.Control type="text" ref={estateAddress} placeholder="Dirección" required />
                        <Form.Control.Feedback type="invalid">Por favor ingresar la dirección de la propiedad</Form.Control.Feedback>
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

                <div>
                    <hr />
                    <h4>Detalle de departmantos</h4>
                </div>

                <div className="detail-table-padding">
                    <Button variant="success" style={{fontSize: "12px"}} onClick={addApartment}>Agregar</Button>
                </div>

                <div className="table-responsive">
                    {getApartmentsTable()}
                </div>

                <div className="align-center basic-padding-10"><Button variant="dark" type="submit">Agregar</Button></div>
            </Form>
        </div>

    );
};

export default EstateCreation;