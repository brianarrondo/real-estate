import React from "react";
import { useForm } from 'react-hook-form';

const TenantEdition = ({ title, id, confirmButtonText, submitFunction, oldTenant, childrenBuilder }) => {
    const { register, handleSubmit, errors } = useForm();

    const closeModal = () => {
        let modal = window.bootstrap.Modal.getInstance(document.getElementById(id));
        modal.toggle();
    };

    const submit = (formData) => {
        formData.tenantId = oldTenant.tenantId;
        submitFunction(formData);
        closeModal();
    };

    return(
        <div className="modal fade" id={id} tabIndex="-1" aria-labelledby="formModalLabel"
             aria-hidden="true">
            <div className="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
                <div className="modal-content">

                    <div className="modal-header">
                        <h5 className="modal-title" id="formModalLabel">{title}</h5>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>

                    <form onSubmit={handleSubmit((formData) => {submit(formData)} )} id="modalForm" noValidate>
                        {/* Notar que el children (inputs del form) los construimos a partir de una funcion y parametros customs */}
                        {childrenBuilder(register, errors, oldTenant)}

                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                            <button type="submit" className="btn btn-success">{confirmButtonText}</button>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    );
};

export default TenantEdition;