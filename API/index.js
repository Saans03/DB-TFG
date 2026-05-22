const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const Score = require('./models/Score'); 

const app = express();

// 🔌 CONEXIÓN A MONGO DB ATLAS 
const MONGO_URI = "TU_ENLACE_MONGO"; // ⚠️ Pega aquí tu URI original con la contraseña

mongoose.connect(MONGO_URI)
    .then(() => console.log('🟢 ¡Conectado con éxito a MongoDB Atlas!'))
    .catch(err => console.error('🔴 Error crítico al conectar a MongoDB:', err));

app.use(cors());
app.use(express.json());

// 📌 RUTA GET: Ahora filtra por nivel si se lo pedimos
app.get('/scores', async (req, res) => {
    try {
        const nivelSolicitado = req.query.nivel;
        let filtro = {};
        
        // Si la web nos pide un nivel (ej: /scores?nivel=nivel1), aplicamos el filtro
        if (nivelSolicitado) {
            filtro = { nivel: nivelSolicitado };
        }

        // Buscamos los 10 mejores de ese nivel concreto
        const topScores = await Score.find(filtro).sort({ score: -1 }).limit(10);
        res.status(200).json(topScores);
    } catch (error) {
        console.error('🔴 Error al obtener puntuaciones del ranking:', error);
        res.status(500).json({ error: 'Error al obtener las puntuaciones' });
    }
});

// 📌 RUTA POST: Guardamos el nivel de la partida
app.post('/scores', async (req, res) => {
    try {
        const newScore = new Score({
            playerName: req.body.playerName,
            score: req.body.score,
            monedas: req.body.monedas || 0,
            nivel: req.body.nivel // 👈 Actualizado al nuevo nombre
        });
        
        const savedScore = await newScore.save();
        console.log(`✨ ¡Registro guardado!: ${savedScore.playerName} | ${savedScore.score} PT | Nivel: ${savedScore.nivel}`);
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