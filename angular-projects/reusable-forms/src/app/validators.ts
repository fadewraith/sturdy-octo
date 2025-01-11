import { Validator } from './types';

const isNotEmpty = (str: string) => str.length !== 0;

const createMinLengthCheck = (minLength: number) => (str: string) =>
  str.length >= minLength;

const createMinValueCheck = (minValue: number) => (value: number) =>
  value >= minValue;

export const emailRegex =
  /^[a-zA-Z0-9._-]+@(gmail\.com|outlook\.com|ymail\.com|rediffmail\.com|hotmail\.com|mail\.com|test\.com)$/;

export const timeRegex = /^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/;

export const dateRegex = /^(0[1-9]|[12][0-9]|3[01])\/(0[1-9]|1[0-2])\/\d{4}$/;

const createRegexCheck = (regex: RegExp) => (str: string) => regex.test(str);

export const isNotEmptyValidator: Validator = {
  checkFn: isNotEmpty,
  errorMessage: 'Field cannot be empty',
};

export const createMinLengthValidator = (minLength: number): Validator => ({
  checkFn: createMinLengthCheck(minLength),
  errorMessage: `Field must be ${minLength} characters or longer`,
});

export const createMinValueValidator = (minValue: number): Validator => ({
  checkFn: createMinValueCheck(minValue),
  errorMessage: `Field must be ${minValue} or greater`,
});

export const createRegexValidator = (regex: RegExp, pattern: string) => ({
  checkFn: createRegexCheck(regex),
  errorMessage: `Field must be a valid ${pattern}`,
});
