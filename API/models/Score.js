const mongoose = require('mongoose');

const scoreSchema = new mongoose.Schema({
    playerName: { type: String, required: true },
    score: { type: Number, required: true },
    // La nueva variable para guardar las monedas del jugador
    monedas: { type: Number, default: 0 } ,
    nivelMaximo: { type: String, default: "Ninguno" } // <-- NUEVO: Guarda el último nivel superado
});

module.exports = mongoose.model('Score', scoreSchema);