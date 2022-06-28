import React, {useRef, useState} from "react";
import Modal from "react-bootstrap/Modal";
import {Button, Col, Form, InputGroup, Row} from "react-bootstrap";
import RentalFee from "../../../models/RentalFee";
import DatePicker, {registerLocale} from "react-datepicker";
import es from "date-fns/locale/es";
import Utils from "../../../utils/Utils";
registerLocale("es", es);

const RentalFeesCreationModal = ({setModalShow, setRentalFees, payDay}) => {
    const fee = useRef();
    let form = React.createRef();

    const currentDate = new Date();
    currentDate.setDate(parseInt(payDay));
    currentDate.setHours(0,0,0);
    const [validated, setValidated] = useState(false);
    const [startDate, setStartDate] = useState(currentDate);
    const [endDate, setEndDate] = useState(currentDate);

    function addRentalFee() {
        setRentalFees(prevState => [...prevState, new RentalFee(parseInt(fee.current.value), startDate.toString(), endDate.toString())]);
        setModalShow(false);
    }

    function handleSubmit(e) {
        let validationOk = true;
        e.preventDefault();

        // realizamos la validacion del form
        const form = e.currentTarget;
        if (form.checkValidity() === false || startDate > endDate || (fee.current.value === null && fee.current.value.length === 0)) {
            validationOk = false;
        }
        setValidated(true);

        if (validationOk) {
            addRentalFee();
        }
    }

    return(<>
        <Modal.Header closeButton>
            <Modal.Title>
                Agregar incremento
            </Modal.Title>
        </Modal.Header>

        <Form ref={form} onSubmit={handleSubmit} noValidate validated={validated}>
            <Modal.Body>
                <Form.Group as={Row} className="justify-content-center">
                    <Form.Label column sm={3}>
                        Incremento
                    </Form.Label>

                    <Col sm={8}>
                        <InputGroup className="mb-2">
                            <InputGroup.Text>%</InputGroup.Text>
                            <Form.Control type="number" min="0.01" step="0.01" ref={fee} placeholder="Incremento" required />
                            <Form.Control.Feedback type="invalid">Por favor ingresar incremento mayor a 0</Form.Control.Feedback>
                        </InputGroup>
                    </Col>
                </Form.Group>

                <Form.Group as={Row} className="justify-content-center">
                    <Form.Label column sm={3}>Fecha Inicial (inclusive)</Form.Label>
                    <Col sm={8}>
                        <DatePicker
                            dateFormat="MMMM yyyy"
                            showMonthYearPicker
                            selected={startDate}
                            locale="es"
                            //onChange={date => setStartDate(Utils.generateUtcDate(date.getFullYear(), date.getMonth(), payDay))}
                            onChange={date => setStartDate(new Date(date.getFullYear(), date.getMonth(), payDay))}
                        />
                        <Form.Control.Feedback type="invalid">Por favor seleccione una fecha inicial</Form.Control.Feedback>
                    </Col>
                </Form.Group>

                <Form.Group as={Row} className="justify-content-center">
                    <Form.Label column sm={3}>Fecha Final (inclusive)</Form.Label>
                    <Col sm={8}>
                        <DatePicker
                        dateFormat="MMMM yyyy"
                        showMonthYearPicker
                        selected={endDate}
                        locale="es"
                        //onChange={date => setEndDate(Utils.generateUtcDate(date.getFullYear(), date.getMonth(), payDay))}
                        onChange={date => setEndDate(new Date(date.getFullYear(), date.getMonth(), payDay))}
                    />
                        <Form.Control.Feedback type="invalid">Por favor seleccione una fecha final</Form.Control.Feedback>
                    </Col>
                </Form.Group>
            </Modal.Body>

            <Modal.Footer>
                <Button variant="secondary" onClick={() => setModalShow(false)}>Cerrar</Button>
                <Button variant="success" className="form-submit-button" onClick={addRentalFee}>
                    Agregar
                </Button>
            </Modal.Footer>
        </Form>
    </>);
};

export default RentalFeesCreationModal;