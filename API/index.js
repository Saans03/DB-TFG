const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const Score = require('./models/Score'); // Asegúrate de que la ruta a tu modelo sea idéntica

const app = express();

// Middlewares esenciales
app.use(cors());
app.use(express.json());

// 📌 RUTA GET: Para leer las puntuaciones de MongoDB y enviárselas a la web
app.get('/scores', async (req, res) => {
    try {
        // Buscamos los 10 mejores registros ordenados de mayor a menor puntuación
        const topScores = await Score.find().sort({ score: -1 }).limit(10);
        res.status(200).json(topScores);
    } catch (error) {
        console.error('🔴 Error al obtener puntuaciones del ranking:', error);
        res.status(500).json({ error: 'Error al obtener las puntuaciones' });
    }
});

// 📌 RUTA POST: La entrada que usa Unity al cruzar la meta para guardar datos
app.post('/scores', async (req, res) => {
    try {
        // Construimos el documento con la estructura completa incluyendo monedas y nivel máximo
        const newScore = new Score({
            playerName: req.body.playerName,
            score: req.body.score,
            monedas: req.body.monedas || 0,
            nivelMaximo: req.body.nivelMaximo || "Ninguno" // Evita campos vacíos si viene de versiones viejas
        });
        
        // Guardamos de forma asíncrona en la base de datos de MongoDB Atlas
        const savedScore = await newScore.save();
        
        console.log(`✨ ¡Registro guardado!: ${savedScore.playerName} | ${savedScore.score} PT | ${savedScore.monedas} Monedas | Nivel: ${savedScore.nivelMaximo}`);
        res.status(201).json(savedScore); 
    } catch (error) {
        console.error('🔴 Error crítico al guardar la puntuación desde Unity:', error);
        res.status(500).json({ error: 'Error al guardar la puntuación' });
    }
});

// Configuración y encendido del puerto del servidor remoto
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
    console.log(`🚀 Servidor backend escuchando activamente en el puerto ${PORT}`);
});