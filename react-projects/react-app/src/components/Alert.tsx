import { AlertType } from "../interfaces/alertType";

const Alert = ({ children, onClose }: AlertType) => {
  return (
    <>
      {" "}
      <div className="alert alert-primary alert-dismissible">
        {children}
        <button
          type="button"
          className="btn-close"
          data-bs-dismiss="alert"
          aria-label="Close"
          onClick={onClose}
        ></button>
      </div>
    </>
  );
};

export default Alert;
