import express from "express";
import cors from "cors";
// intitialize the express app
const app = express();
app.use(cors());
// will parse any json i.e. sent to the server
app.use(express.json());

// make some animals
import Chance from "chance";
const chance = new Chance();

const animals = [...Array(250).keys()].map(id => {
    return {
        id,
        type: chance.animal(),
        age: chance.age(),
        name: chance.name(),
    }
});

// endpoint to search for animals
app.get('', (req, res) => {
    // filter results by query
    const q = req.query.q?.toLowerCase() || "";
    const results = animals.filter(animal => animal.type.toLowerCase().includes(q));

    res.send(results);
});

app.listen(8080, () => console.log("listening to port 8080"));