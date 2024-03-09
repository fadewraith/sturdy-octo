import React, { useEffect, useState } from "react";
import shuffle from "./utilities/shuffle";
import Card from "./components/Card";
import Header from "./components/Header";
import useAppBadge from "./hooks/useAppBadge";

function App() {
  // cards array from assets
  const [cards, setCards] = useState(shuffle);
  // first selection
  const [pickOne, setPickOne] = useState(null);
  // second selection
  const [pickTwo, setPickTwo] = useState(null);
  // delay handler and disable the ui, so that player cant quickly click all the cards
  const [disabled, setDisabled] = useState(false);
  // win streak
  const [wins, setWins] = useState(0);
  // for pwa, handles app badge
  const [setBadge, clearBadge] = useAppBadge();

  // handle card selection
  const handleClick = (card) => {
    if (!disabled) {
      pickOne ? setPickTwo(card) : setPickOne(card);
    }
  };

  const handleTurn = () => {
    setPickOne(null);
    setPickTwo(null);
    setDisabled(false);
  };

  // start over
  const handleNewGame = () => {
    clearBadge();
    setWins(0);
    handleTurn();
    setCards(shuffle);
  }

  // used for selection and match handling
  useEffect(() => {
    let pickTimer;

    // 2 cards have been clicked
    if (pickOne && pickTwo) {
      // check if the cards are same
      if (pickOne.image === pickTwo.image) {
        setCards((prevCards) => {
          return prevCards.map((card) => {
            if (card.image === pickOne.image) {
              // update card property to reflect match
              return { ...card, matched: true };
            } else {
              // no match
              return card;
            }
          });
        });
        handleTurn();
      } else {
        // prevent new selections until after delay
        setDisabled(true);
        // add delay between selections
        pickTimer = setTimeout(() => {
          handleTurn();
        }, 1000);
      }
    }

    // to ensure, we dont have conflicting timeout between renders
    return () => {
      clearTimeout(pickTimer);
    }
  }, [cards, pickOne, pickTwo, setBadge, wins]);


  // if player has found all matches, handle accordingly
  useEffect(() => {
    // check for any remaining card matches
    const checkWin = cards.filter((card) => !card.matched);

    // all matches made, handle win/badge counters
    if(cards.length && checkWin.length < 1) {
      console.log('you win');
      setWins(wins + 1);
      handleTurn();
      setBadge();
      // reset and shuffle
      setCards(shuffle);
    }
  }, [cards, wins, setBadge]);
  

  return (
    <>
    <Header handleNewGame={handleNewGame} wins={wins}/>
    <div className="grid">
      {cards.map((card) => {
        const { image, id, matched } = card;

        return (
          <Card
            key={id}
            image={image}
            selected={card === pickOne || card === pickTwo || matched}
            onClick={() => handleClick(card)}
          />
        );
      })}
    </div>
    </>
  );
}

export default App;
