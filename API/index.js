const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const Score = require('./models/Score'); 

const app = express();

// 🔌 CONEXIÓN A MONGO DB ATLAS (Recuperada de tu historial de chat)
const MONGO_URI = "mongodb+srv://saizviecodavid_db_user:Salir@ttkj.ccpghia.mongodb.net/?appName=TTKJ";

mongoose.connect(MONGO_URI)
    .then(() => console.log('🟢 ¡Conectado con éxito a MongoDB Atlas!'))
    .catch(err => console.error('🔴 Error crítico al conectar a MongoDB:', err));

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
        const newScore = new Score({
            playerName: req.body.playerName,
            score: req.body.score,
            monedas: req.body.monedas || 0,
            nivelMaximo: req.body.nivelMaximo || "Ninguno" 
        });
        
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