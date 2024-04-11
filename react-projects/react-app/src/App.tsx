import { useState } from "react";
import "./App.css";
import Button from "./components/Button/Button";
import { Color } from "./enums/colors";
import Alert from "./components/Alert";
import { ListGroup } from "./components/ListGroup/ListGroup";
import { BsFillCalendarFill } from "react-icons/bs";
import { Like } from "./components/Like";
import Form from "./components/Form";

export const categories = ["Groceries", "Utilities", "Entertainment"];

function App() {
  const [alertVisible, setAlertVisible] = useState(false);
  const [selectCategory, setSelectedCategory] = useState("");
  const [expenses, setExpenses] = useState([
    { id: 1, description: "aaa", amount: 10, category: "Utilities" },
    { id: 1, description: "aaa", amount: 10, category: "Utilities" },
    { id: 1, description: "aaa", amount: 10, category: "Utilities" },
    { id: 1, description: "aaa", amount: 10, category: "Utilities" },
  ]);

  const visibleExpenses = selectCategory
    ? expenses.filter((e) => e.category === selectCategory)
    : expenses;

  const countries: string[] = [
    "New York",
    "San Francisco",
    "Tokyo",
    "London",
    "Peru",
  ];

  const handleSelectItem = (item: string) => {
    console.log(item);
  };

  const handleClick = () => {
    setAlertVisible(true);
    console.warn(alertVisible);
  };

  return (
    <>
      {/* <div>
        <button onClick={handleClick}>Click</button>
      </div>
      <div>
        <Like onClick={() => console.warn("clicked")} />
      </div>
      <div>
        <BsFillCalendarFill color="red" size="40" />
      </div>
      <ListGroup
        items={countries}
        heading="Cities"
        onSelectItem={handleSelectItem}
      /> */}
      {/* <Alert text="Hello World" /> */}
      {/* <Alert>
        Hello <span>World</span>
      </Alert> */}
      {/* {alertVisible && (
        <Alert onClose={() => setAlertVisible(false)}>My Alert</Alert>
      )}
      <Button color={Color.Success} onClick={() => setAlertVisible(true)}>
        Custom Button
      </Button> */}
      {/* <Button onClick={() => {}}>My Button</Button> */}
      {/* form */}
      {/* <Form /> */}
      {/* Expense Form */}
      <div>
        <div className="mb-3"></div>
      </div>
    </>
  );
}

export default App;
