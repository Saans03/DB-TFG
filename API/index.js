const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const Score = require('./models/Score'); 

const app = express();

// 🔌 CONEXIÓN A MONGO DB ATLAS (La URI que tenías originalmente)
const MONGO_URI = "mongodb+srv://saizviecodavid_db_user:Salir@ttkj.ccpghia.mongodb.net/?appName=TTKJ";

mongoose.connect(MONGO_URI)
    .then(() => console.log('🟢 ¡Conectado con éxito a MongoDB Atlas!'))
    .catch(err => console.error('🔴 Error crítico al conectar a MongoDB:', err));

// Middlewares esenciales
app.use(cors());
app.use(express.json());

// 📌 RUTA GET: Ahora filtra por nivel
app.get('/scores', async (req, res) => {
    try {
        const nivelSolicitado = req.query.nivel; 
        let filtro = {}; // Por defecto, si no hay filtro, buscamos todo (o puedes poner filtro = {nivel: "nivel1"} para que sea el default)

        // Si recibimos el parámetro "nivel" desde la web/unity, aplicamos el filtro
        if (nivelSolicitado) {
            filtro = { nivel: nivelSolicitado };
        }

        // Aquí es donde ocurre la magia: buscamos usando el filtro
        const topScores = await Score.find(filtro).sort({ score: -1 }).limit(10);
        
        res.status(200).json(topScores);
    } catch (error) {
        console.error('🔴 Error al obtener puntuaciones del ranking:', error);
        res.status(500).json({ error: 'Error al obtener las puntuaciones' });
    }
})

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

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
    console.log(`🚀 Servidor backend escuchando activamente en el puerto ${PORT}`);
});