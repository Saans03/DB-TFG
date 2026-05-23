const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const Score = require('./models/Score'); 

const app = express();

// 🔌 CONEXIÓN A MONGO DB ATLAS
const MONGO_URI = "mongodb+srv://saizviecodavid_db_user:Salir@ttkj.ccpghia.mongodb.net/?appName=TTKJ";

mongoose.connect(MONGO_URI)
    .then(() => console.log('🟢 ¡Conectado con éxito a MongoDB Atlas!'))
    .catch(err => console.error('🔴 Error crítico al conectar a MongoDB:', err));

app.use(cors());
app.use(express.json());

// 📌 RUTA GET: Lee el filtro '?nivel=...' que le mande la web
app.get('/scores', async (req, res) => {
    try {
        const nivelSolicitado = req.query.nivel;
        let filtro = {};
        
        // Si la web nos pide un nivel concreto (ej: ?nivel=nivel1), filtramos en Mongo
        if (nivelSolicitado) {
            filtro = { nivel: nivelSolicitado };
        }

        const topScores = await Score.find(filtro).sort({ score: -1 }).limit(10);
        res.status(200).json(topScores);
    } catch (error) {
        console.error('🔴 Error al obtener puntuaciones del ranking:', error);
        res.status(500).json({ error: 'Error al obtener las puntuaciones' });
    }
});

// 📌 RUTA POST: Recibe los datos de Unity con la variable 'nivel'
app.post('/scores', async (req, res) => {
    try {
        const newScore = new Score({
            playerName: req.body.playerName,
            score: req.body.score,
            monedas: req.body.monedas || 0,
            nivel: req.body.nivel || "Ninguno" // 👈 Lee 'nivel' desde el JSON enviado por Unity
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