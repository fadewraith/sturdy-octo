import { useState } from "react";
import ListGroupType from ".";
// import styles from "./ListGroup.module.css";
// import styles from "./ListGroup.css";
import styled from "styled-components";
import { ListItemProps } from "../../interfaces/listItemProps";

const List = styled.ul`
  list-style: none !important;
  padding: 0;
`;

const ListItem = styled.li<ListItemProps>`
  padding: 5px 0;
  background: ${(props) => (props.active ? "blue" : "none")};
`;

export const ListGroup = ({ items, heading, onSelectItem }: ListGroupType) => {
  //   hook - fn allows to tap in the built in features of react
  //   -1 = none selected, 0 = default selected
  const [selectedIndex, setSelectedIndex] = useState(-1);

  //   const msg: React.ReactElement | null = countries.length === 0 ? <p>No countries found</p> : null;

  //   const getMsg = () => {
  //     return countries.length === 0 ? <p>No countries found</p> : null;
  //   };

  //   event handler
  // const handleClick = (event: React.MouseEvent) => console.log(event);

  return (
    <>
      <h1>{heading}</h1>
      {/* <ul className="list-group"> */}
      {/* <ul className={styles["list-group"]}> */}
      {/* <ul className={[styles.listGroup, styles.container].join(" ")}> */}
      <List>
        {/* {msg} */}
        {/* {getMsg()} */}
        {items.length === 0 && <p>No country found</p>}
        {items.map((country, index) => (
          // <li
          //   key={country}
          //   className={
          //     selectedIndex === index
          //       ? "list-group-item active"
          //       : "list-group-item"
          //   }
          //   onClick={() => {
          //     setSelectedIndex(index);
          //     onSelectItem(country);
          //   }}
          // >
          <ListItem
            active={index === selectedIndex ? "true" : ""}
            key={country}
            onClick={() => {
              setSelectedIndex(index);
              onSelectItem(country);
            }}
          >
            {country}
          </ListItem>
        ))}
      </List>
    </>
  );
};
