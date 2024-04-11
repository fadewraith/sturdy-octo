import { ReactNode } from "react";

export interface AlertType {
  //   text: string;
  // special prop that all components support
  //   children: string;
  //    with this we can pass the HTML content also
  children: ReactNode;
  onClose: () => void;
}
