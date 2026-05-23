const mongoose = require('mongoose');

const playerProgressSchema = new mongoose.Schema({

    saveId: {
        type: String,
        required: true,
        unique: true
    },

    completedLevels: {
        type: [String],
        default: []
    },

    currentCoins: {
        type: Number,
        default: 0
    },

    currentScore: {
        type: Number,
        default: 0
    },

    lastLevel: {
        type: String,
        default: "NivelHUB"
    }

});

module.exports =
    mongoose.model(
        'PlayerProgress',
        playerProgressSchema
    );