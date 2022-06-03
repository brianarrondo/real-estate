import React, {useContext, useState} from "react";
import {Button, ButtonGroup, Col, Form, Row, Table} from "react-bootstrap";
import GenericEmptyTable from "../../utils/GenericEmptyTable";
import {ModalContext} from "../../utils/GenericModal";
import RentalFeesCreationModal from "./RentalFeesCreationModal";

const RentalFeesCreationList = ({rentalFees, setRentalFees, payDay}) => {
    const {setModalShow, setModalContent, setSize} = useContext(ModalContext);

    function addRentalFee() {
        if (payDay != null && payDay.length > 0) {
            setModalContent(
                <RentalFeesCreationModal
                    setModalShow={setModalShow}
                    setRentalFees={setRentalFees}
                    payDay={payDay}
                />
            );
            setSize("lg");
            setModalShow(true);
        }
    }

    function removeRentalFeeRow(index) {
        let newRentalFeesToAdd = [...rentalFees];
        newRentalFeesToAdd.splice(index, 1);
        setRentalFees(newRentalFeesToAdd);
    }

    const rentalFeesRows = rentalFees.map((t, index) => {
        return (
            <tr key={index}>
                <td>{t.fee}</td>
                <td>{new Date(t.startDate).toLocaleDateString()}</td>
                <td>{new Date(t.endDate).toLocaleDateString()}</td>
                <td>
                    <Button className="delete-table-row-button" variant="danger" style={{display: "flex"}} onClick={() => removeRentalFeeRow(index)}>
                        <i className="bi bi-x x-button" />
                    </Button>
                </td>
            </tr>
        );
    });

    return(
        <>
            <div className="detail-table-padding">
                <ButtonGroup size="sm" className="mb-2">
                    <Button style={{fontSize: "12px"}} variant="success" onClick={addRentalFee}>Agregar</Button>

                </ButtonGroup>
            </div>

            <div className="table-responsive">
                {payDay == null || payDay.length == 0 ?
                        <div className="align-center red-font">  *Debe elegir un día de pago para agregar incrementos</div>
                    :
                        <GenericEmptyTable show={rentalFees.length > 0} emptyTableText="No hay incrementos agregados.">
                                <Table className="table-hover">
                                    <thead>
                                    <tr className="basic-padding">
                                        <th scope="col">Incremento</th>
                                        <th scope="col">Fecha Inicial</th>
                                        <th scope="col">Fecha Final</th>
                                        <th scope="col"></th>
                                    </tr>
                                    </thead>

                                    <tbody>
                                    {rentalFeesRows}
                                    </tbody>
                                </Table>
                        </GenericEmptyTable>
                }
            </div>
        </>
    );
};

export default RentalFeesCreationList;