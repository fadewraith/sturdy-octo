const express = require('express');
const nedb = require('nedb');
const rest = require("express-nedb-rest");
const cors = require("cors");

const app = express();
// its a no sql db
const datastore = new nedb({
    filename: "coffeelog.db",
    autoload: true
});

const restAPI = rest();
restAPI.addDatastore('coffees', datastore);

app.use(cors());
app.use("/", restAPI);

app.listen(3000, () => {
    console.log("API ready at http:localhost:3000");
});