# React + TypeScript + Vite

This template provides a minimal setup to get React working in Vite with HMR and some ESLint rules.

Currently, two official plugins are available:

- [@vitejs/plugin-react](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react/README.md) uses [Babel](https://babeljs.io/) for Fast Refresh
- [@vitejs/plugin-react-swc](https://github.com/vitejs/vite-plugin-react-swc) uses [SWC](https://swc.rs/) for Fast Refresh

## Expanding the ESLint configuration

If you are developing a production application, we recommend updating the configuration to enable type aware lint rules:

- Configure the top-level `parserOptions` property like this:

```js
export default {
  // other rules...
  parserOptions: {
    ecmaVersion: "latest",
    sourceType: "module",
    project: ["./tsconfig.json", "./tsconfig.node.json"],
    tsconfigRootDir: __dirname,
  },
};
```

- Replace `plugin:@typescript-eslint/recommended` to `plugin:@typescript-eslint/recommended-type-checked` or `plugin:@typescript-eslint/strict-type-checked`
- Optionally add `plugin:@typescript-eslint/stylistic-type-checked`
- Install [eslint-plugin-react](https://github.com/jsx-eslint/eslint-plugin-react) and add `plugin:react/recommended` & `plugin:react/jsx-runtime` to the `extends` list

Fragment -
<Fragment></Fragment> - similar to <></> - will not create extra DOM element
props and state changes cause a re render
things that are related, should be next to each other

=> suppose we have .list-group in App.css and ListGroup.css files, then App.css will override the same class. Soln, is rename the components css file with Component.module.\* and import sytle from "";

to add multiple class -
className={[styles.listGroup, styles.container].join(" ")}>

css in js -
a. scoped styles
b. all the css and js/ts code in one place
c. easier to delete a component
d. easier to style based on props/states
libraries -

styled components/emotion/polished/etc.
npm i @types/styled-components

npm i react-icons@4.7.1

State Hook -

1. React updates state asynchronously not immediately
2. state is stored outside of components
3. use hooks at the top level of the component

