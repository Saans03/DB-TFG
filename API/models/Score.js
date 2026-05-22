const mongoose = require('mongoose');

const scoreSchema = new mongoose.Schema({
    playerName: { type: String, required: true },
    score: { type: Number, required: true },
    monedas: { type: Number, default: 0 },
    nivel: { type: String, required: true } // 👈 Cambiado: Ahora guarda el nivel de esta partida
});

module.exports = mongoose.model('Score', scoreSchema);