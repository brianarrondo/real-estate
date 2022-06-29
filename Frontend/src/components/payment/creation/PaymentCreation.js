import React, {useContext, useEffect, useState} from "react";
import {Button, Col, Form, InputGroup, Row} from "react-bootstrap";
import GenericSpinner from "../../utils/GenericSpinner";
import {ServicesContext} from "../../../services/Services";
import Utils from "../../../utils/Utils";
import {AlertContext} from "../../utils/GenericAlert";
import {TokenLoggerContext} from "../../login/TokenLogger";
import Payment from "../../../models/Payment";

const PaymentCreation = () => {
    let paymentAmount = React.createRef();
    //let rentalBillId = React.createRef();
    let date = React.createRef();
    let form = React.createRef();

    const { leaseService, paymentService } = useContext(ServicesContext);
    const { setShowAlert, setAlertType, setAlertContent } = useContext(AlertContext);
    const { userId } = useContext(TokenLoggerContext);

    const [isLoading, setLoading] = useState(false);
    const [rentalBillId, setRentalBillId] = useState(0);
    const [leaseIdSelected, setLeaseIdSelected] = useState(0);
    const [validated, setValidated] = useState(false);
    const [activeLeases, setActiveLeases] = useState([]);

    useEffect(() => {
        setLoading(true);
        getAllLease();
    }, []);

    function getAllLease() {
        leaseService.getAllLease((response) => {
            setActiveLeases(response.data);
            setLoading(false);
        },
        (error) => {
            console.log(error);
            setLoading(false);
        });
    }

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
            setLoading(true);
            let payment = new Payment(
                parseInt(userId),
                parseInt(rentalBillId),
                new Date(),
                parseInt(paymentAmount.current.value),
            );

            paymentService.create(payment,
                () => {
                    setAlertType("success");
                    setShowAlert(true);
                    setAlertContent(<div><i className="bi bi-check-circle"></i> El pago por <strong>{payment && payment.amount}</strong> fue registrado con éxito</div>);
                    setValidated(false);
                    setLoading(false);
                    form.reset();
                    getAllLease();
                    resetForm();
                },
                (error) => {
                    setAlertType("danger");
                    setShowAlert(true);
                    setLoading(false);
                    setAlertContent(<div><i className="bi bi-exclamation-circle"></i> Hubo un error al registrar el pago: "{error.response.data}"</div>);
                }
            );
        }
    }

    function resetForm() {
        setRentalBillId(0);
        setLeaseIdSelected(0);
    }

    let leaseSelectOptions = activeLeases.map(lease => {
        return <option key={lease.id} value={lease.id}>{lease.name}</option>
    });

    function getUnpaidAmount() {
        if (rentalBillId !== 0) {
            let unpaidAmount = getLeaseRentalBills().find(s => s.id === rentalBillId).unpaidAmount;
            let txt = unpaidAmount === 0 ? <label className="valid">*Factura paga</label> : <label className="invalid">*Resta pagar {unpaidAmount}$</label>;

            return (
                <Form.Group as={Row} className="justify-content-center">
                    <Form.Label column sm={3}></Form.Label>
                    <Col sm={8}>
                        {txt}
                    </Col>
                </Form.Group>
            );
        } else {
            return "";
        }
    }

    function getLeaseRentalBills() {
        let lease = activeLeases.find(s => s.id === leaseIdSelected);
        return lease ? lease.rentalBills : [];
    }

    let rentalBillSelectOptions = getLeaseRentalBills().map((rentalBill, index) => {
        return <option key={index} value={rentalBill.id}>{rentalBill.amount + "$ [" + Utils.getFormattedDate(rentalBill.startDate) + " - " + Utils.getFormattedDate(rentalBill.endDate) + "]"}</option>
    });

    return (
        <div className="container">
            <Form ref={form} onSubmit={handleSubmit} noValidate validated={validated} className="basic-padding-20 shadow justify-content-center rounded-bottom border border-light">
                <div>
                    <h3>Registrar Pago</h3>
                    <hr />
                </div>

                <Form.Group as={Row} className="justify-content-center">
                    <Form.Label column sm={3}>Contrato</Form.Label>
                    <Col sm={8}>
                        <Form.Control as="select" value={leaseIdSelected} defaultValue={0} onChange={e => {setLeaseIdSelected(parseInt(e.target.value)); setRentalBillId(0);}}  required>
                            <option key="0" value="">Seleccione un contrato</option>
                            {leaseSelectOptions}
                        </Form.Control>
                        <Form.Control.Feedback type="invalid">Por favor seleccione un contrato</Form.Control.Feedback>
                    </Col>
                </Form.Group>

                <Form.Group as={Row} className="justify-content-center">
                    <Form.Label column sm={3}>Factura</Form.Label>
                    <Col sm={8}>
                        <Form.Control as="select" value={rentalBillId} defaultValue={rentalBillId} onChange={e => setRentalBillId(parseInt(e.target.value))} required>
                            <option key="0" value="">Seleccione una factura</option>
                            {rentalBillSelectOptions}
                        </Form.Control>
                        <Form.Control.Feedback type="invalid">Por favor seleccione una factura</Form.Control.Feedback>
                    </Col>
                </Form.Group>

                {getUnpaidAmount()}

                <Form.Group as={Row} className="justify-content-center">
                    <Form.Label column sm={3}>
                        Monto
                    </Form.Label>

                    <Col sm={8}>
                        <InputGroup className="mb-2">
                            <InputGroup.Text>$</InputGroup.Text>
                            <Form.Control type="number" min="0" step="0.01" ref={paymentAmount} placeholder="Monto" required />
                            <Form.Control.Feedback type="invalid">Por favor ingresar monto mayor a 0</Form.Control.Feedback>
                        </InputGroup>
                    </Col>
                </Form.Group>

                <div className="align-center basic-padding-10">
                    <Button variant="dark" type="submit" className="form-submit-button" disabled={isLoading}>
                        <GenericSpinner show={isLoading}>Registrar</GenericSpinner>
                    </Button>
                </div>
            </Form>
        </div>
    );
};

export default  PaymentCreation;