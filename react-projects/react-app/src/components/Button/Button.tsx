import { Color } from "../../enums/colors";
import { ButtonType } from "../../interfaces/buttonType";
import styles from "./Button.module.css";

const Button = ({ children, color = Color.Primary, onClick }: ButtonType) => {
  return (
    // <button onClick={onClick} className={`btn btn-${color}`}>
    //   {children}
    // </button>
    <button
      onClick={onClick}
      className={[styles.btn, styles["btn-" + color]].join(" ")}
    >
      {children}
    </button>
  );
};

export default Button;
