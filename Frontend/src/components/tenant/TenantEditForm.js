import React from "react";

const TenantEditForm = ({ register, errors, tenant }) => {

    return(
        <div>

            <div className="modal-body">
                <div className="form-group row justify-content-center">
                    <label htmlFor="name" className="col-sm-3 col-md-3 col-lg-2 col-form-label form-label">Nombre</label>
                    <div className="col-sm-7 col-md-5">
                        <input
                            type="text"
                            className={"form-control" + (errors.fullName ? " is-invalid" : "")}
                            name="fullName"
                            defaultValue={tenant && tenant.fullName}
                            ref={register({required: true})}
                        />
                        <div className="invalid-feedback">Debe completar el nombre</div>
                    </div>
                </div>
                <br />
                <div className="form-group row justify-content-center">
                    <label htmlFor="dni" className="col-sm-3 col-md-3 col-lg-2 col-form-label form-label">DNI</label>
                    <div className="col-sm-7 col-md-5"><input type="text" className="form-control" name="dni" defaultValue={tenant && tenant.dni} ref={register} /></div>
                </div>
                <br />
                <div className="form-group row justify-content-center">
                    <label htmlFor="phone" className="col-sm-3 col-md-3 col-lg-2 col-form-label form-label">Teléfono</label>
                    <div className="col-sm-7 col-md-5"><input type="text" className="form-control" name="phone" defaultValue={tenant && tenant.phone} ref={register} /></div>
                </div>
                <br />
                <div className="form-group row justify-content-center">
                    <label htmlFor="desc" className="col-sm-3 col-md-3 col-lg-2 col-form-label form-label">Descripción</label>
                    <div className="col-sm-7 col-md-5">
                        <textarea
                            type="text"
                            className={"form-control" + (errors.description ? " is-invalid" : " ")}
                            name="description"
                            defaultValue={tenant && tenant.description}
                            ref={register({maxLength: 10})}
                        />
                        <div className="invalid-feedback">Se superó el máximo de carácteres permitidos (50)</div>
                    </div>
                </div>
            </div>

        </div>
    );
};

export default TenantEditForm;