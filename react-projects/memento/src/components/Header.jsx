import React, { useEffect } from "react";

const Header = ({ handleNewGame, wins }) => {
  // UPDATE PAGE TITLE WITH WIN COUNT
  useEffect(() => {
    document.title = `${wins} wins`;
    // return () => {};
  }, [wins]);
  return (
    <header className="header">
      <h4>{wins} wins</h4>
      <h3>Memory Game</h3>
      <button onClick={handleNewGame}>New Game</button>
    </header>
  );
};

export default Header;
