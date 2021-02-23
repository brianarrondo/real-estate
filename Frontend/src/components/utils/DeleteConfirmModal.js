import React from "react";

const DeleteConfirmModal = ({ title, onClick, children }) => {

    return(
        <div className="modal fade" id="confirmDeleteModal" tabIndex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div className="modal-dialog modal-dialog-centered">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title" id="exampleModalLabel">{title}</h5>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div className="modal-body">
                        {children}
                    </div>
                    <div className="modal-footer">
                        <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                        <button type="button" className="btn btn-danger" data-bs-dismiss="modal" onClick={onClick}>Borrar</button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default DeleteConfirmModal;