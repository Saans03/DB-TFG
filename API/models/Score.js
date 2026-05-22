const mongoose = require('mongoose');

const scoreSchema = new mongoose.Schema({
    playerName: { type: String, required: true },
    score: { type: Number, required: true },
    monedas: { type: Number, default: 0 },
    nivelMaximo: { type: String, default: "Ninguno" } // 👈 Asegúrate de que esta línea exista
});

module.exports = mongoose.model('Score', scoreSchema);