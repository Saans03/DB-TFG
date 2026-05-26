const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const Score = require('./models/Score'); 
const PlayerProgress =
    require('./models/PlayerProgress');

const app = express();

// CONEXIÓN A MONGO DB ATLAS
const MONGO_URI = "mongodb+srv://saizviecodavid_db_user:Salir@ttkj.ccpghia.mongodb.net/?appName=TTKJ";

mongoose.connect(MONGO_URI)
    .then(() => console.log('🟢 Conectado con éxito a MongoDB Atlas!'))
    .catch(err => console.error('🔴 Error al conectar a MongoDB:', err));

app.use(cors());
app.use(express.json());

// RUTA GET: Devuelve el Top 10 filtrado por el parámetro '?nivel='
app.get('/scores', async (req, res) => {
    try {
        const nivelSolicitado = req.query.nivel;
        let filtro = {};
        
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

// RUTA POST: Recibe las puntuaciones de Unity al finalizar un nivel
app.post('/scores', async (req, res) => {
    try {
        const newScore = new Score({
            playerName: req.body.playerName,
            score: req.body.score,
            monedas: req.body.monedas || 0,
            nivel: req.body.nivel || "Ninguno"
        });
        
        const savedScore = await newScore.save();
        console.log(`✨ Registro guardado: ${savedScore.playerName} | ${savedScore.score} PT | Nivel: ${savedScore.nivel}`);
        res.status(201).json(savedScore); 
    } catch (error) {
        console.error('🔴 Error al guardar la puntuación desde Unity:', error);
        res.status(500).json({ error: 'Error al guardar la puntuación' });
    }
});
// GUARDAR PROGRESO
app.post('/progress/save',
    async (req, res) => {

        try {

            const data = req.body;

            const updatedProgress =
                await PlayerProgress.findOneAndUpdate(

                    {
                        saveId: data.saveId
                    },

                    {
                        saveId: data.saveId,

                        completedLevels:
                            data.completedLevels,

                        currentCoins:
                            data.currentCoins,

                        currentScore:
                            data.currentScore,

                        lastLevel:
                            data.lastLevel
                    },

                    {
                        upsert: true,
                        new: true
                    }
                );

            console.log(
                `💾 Progress guardado: ${data.saveId}`
            );

            res.status(200).json(updatedProgress);

        }
        catch (error) {
            console.error(
                '🔴 Error guardando progreso:',
                error
            );

            res.status(500).json({
                error:
                    'Error al guardar progreso'
            });
        }
    });
// CARGAR PROGRESO
app.get('/progress/:saveId',
    async (req, res) => {

        try {

            const progress =
                await PlayerProgress.findOne({

                    saveId:
                        req.params.saveId

                });

            if (!progress) {
                return res.status(404).json({
                    error:
                        'Save no encontrado'
                });
            }

            res.status(200).json(progress);

        }
        catch (error) {
            console.error(
                '🔴 Error cargando progreso:',
                error
            );

            res.status(500).json({
                error:
                    'Error al cargar progreso'
            });
        }
    });

// BORRAR PROGRESO
app.delete('/progress/:saveId',
    async (req, res) => {

        try {

            const deleted =
                await PlayerProgress.findOneAndDelete({

                    saveId:
                        req.params.saveId

                });

            if (!deleted) {
                return res.status(404).json({
                    error:
                        'Save no encontrado'
                });
            }

            console.log(
                `🗑 Save borrado: ${req.params.saveId}`
            );

            res.status(200).json({
                message:
                    'Save eliminado'
            });

        }
        catch (error) {
            console.error(
                '🔴 Error borrando save:',
                error
            );

            res.status(500).json({
                error:
                    'Error eliminando save'
            });
        }
    });
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
    console.log(`🚀 Servidor backend escuchando en el puerto ${PORT}`);
});

