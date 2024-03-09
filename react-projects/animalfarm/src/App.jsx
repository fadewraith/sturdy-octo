import React, { useEffect, useState } from "react";

// custom hook
function useAnimalSearch() {
  const [animals, setAnimals] = useState([]);

  useEffect(() => {
    const lastQuery = localStorage.getItem("lastQuery");
    search(lastQuery);
  }, []);

  const search = async (q) => {
    try {
      const response = await fetch(
        `http://localhost:8080?${new URLSearchParams({ q }).toString()}`
      );

      if (!response.ok) {
        throw new Error("Network response was not ok");
      }

      const data = await response.json();
      setAnimals(data);
      localStorage.setItem("lastQuery", q);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  return { search, animals };
}

function App() {

  const { search, animals } = useAnimalSearch();
  return (
    <main>
      <h1>Animal Farm</h1>
      <input
        type="text"
        placeholder="Search"
        onChange={(e) => search(e.target.value)}
      />

      <ul>
        {animals.map((animal) => (
          <Animal key={animal.id} {...animal} />
        ))}
        {animals.length === 0 && <li>no animals found</li>}
      </ul>
    </main>
  );
}

function Animal({ type, name, age }) {
  return (
    <li>
      <strong>{type}</strong> {name} ({age} years old)
    </li>
  );
}

export default App;
