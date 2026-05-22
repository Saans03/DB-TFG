// 1. IMPORTACIONES
require('dotenv').config(); 
const express = require('express'); 
const mongoose = require('mongoose'); 
const cors = require('cors'); 
const dns = require('node:dns'); 

// 📦 IMPORTAMOS TU MOLDE (Asegúrate de tener la carpeta models y Score.js dentro)
const Score = require('./models/Score');

// 2. EL PARCHE PARA EL BUG DE WINDOWS (Para saltarnos el bloqueo del router)
dns.setServers(['1.1.1.1', '8.8.8.8']); 

// 3. CONFIGURACIÓN INICIAL
const app = express();
const PORT = process.env.PORT || 3000;

// 4. MIDDLEWARES (Los porteros)
app.use(cors()); 
app.use(express.json()); 

// 5. CONEXIÓN A LA BASE DE DATOS
mongoose.connect(process.env.MONGODB_URI)
    .then(() => {
        console.log('🟢 ¡Conectado a la Base de Datos (MongoDB)!');
    })
    .catch((error) => {
        console.log('🔴 Error al conectar a MongoDB:', error.message);
    });

// ==========================================
// 6. RUTAS DE LA API (El corazón de tu servidor)
// ==========================================

// RUTA GET (Leer): Es la que usa tu página HTML para mostrar el Top 10
app.get('/scores', async (req, res) => {
    try {
        // Busca en la BD, ordena por puntuación de mayor a menor (-1) y coge las 10 primeras
        const topScores = await Score.find().sort({ score: -1 }).limit(10);
        res.json(topScores);
    } catch (error) {
        console.error('🔴 Error al enviar el ranking:', error);
        res.status(500).json({ error: 'Error al obtener las puntuaciones' });
    }
});

// RUTA POST (Escribir): Es la puerta que usa Unity para guardar una nueva partida
app.post('/scores', async (req, res) => {
    try {
        // Construimos el nuevo dato (AHORA CON MONEDAS Y NIVEL MÁXIMO)
        const newScore = new Score({
            playerName: req.body.playerName,
            score: req.body.score,
            monedas: req.body.monedas || 0,
            nivelMaximo: req.body.nivelMaximo || "Ninguno" // <-- NUEVO: Recogemos el nivel
        });
        
        // Lo guardamos definitivamente en MongoDB
        const savedScore = await newScore.save();
        
        // Respondemos a Unity con un código 201 (Creado con éxito)
        res.status(201).json(savedScore); 
        console.log(`✨ ¡Guardado!: ${savedScore.playerName} - ${savedScore.score} PT - ${savedScore.monedas} Monedas - ${savedScore.nivelMaximo}`);
    } catch (error) {
        console.error('🔴 Error al guardar desde Unity:', error);
        res.status(500).json({ error: 'Error al guardar la puntuación' });
    }
});

// 7. ENCENDER EL SERVIDOR
app.listen(PORT, () => {
    console.log(`📡 Servidor escuchando en el puerto ${PORT}`);
});