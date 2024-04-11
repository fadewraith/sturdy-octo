import { Color } from "../enums/colors";

export interface ButtonType {
  children: string;
  //   color?:
  //     | "primary"
  //     | "secondary"
  //     | "success"
  //     | "danger"
  //     | "warning"
  //     | "info"
  //     | "light"
  //     | "dark";
  color?: Color;
  onClick: () => void;
}
