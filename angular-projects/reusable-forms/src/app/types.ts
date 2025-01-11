export interface FieldConfig {
  name: string;
  displayName?: string;
  type?: string; // text, number, date, etc.
  placeholder?: string;
  validators?: Validator[];
}

export interface Validator {
  checkFn: (value: any) => boolean;
  errorMessage: string;
}
