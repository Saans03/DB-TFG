const mongoose = require('mongoose');

const scoreSchema = new mongoose.Schema({
    playerName: { type: String, required: true },
    score: { type: Number, required: true },
    monedas: { type: Number, default: 0 },
    nivel: { type: String, default: "Ninguno" } // Sincronizado exactamente como 'nivel'
});

module.exports = mongoose.model('Score', scoreSchema);