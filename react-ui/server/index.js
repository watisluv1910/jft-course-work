const express = require('express');
const bodyParser = require('body-parser');
const path = require('path');
const {createProxyMiddleware} = require('http-proxy-middleware');

require('dotenv').config({
    path: '../.env',
    override: false,
});

const app = express();

if (process.env.NODE_ENV === 'development') {
    console.log('in development.');
} else {
    console.log('in production.');
}

const frontendServer = process.env.FRONTEND_SERVER || 'localhost';
const frontendPort = process.env.FRONTEND_DEFAULT_PORT || 3000;
const backendServer = process.env.BACKEND_SERVER || 'localhost';
const backendPort = process.env.BACKEND_DEFAULT_PORT || 8080;

/* App Config */
app.use(express.static(path.join(__dirname, '../build')));
app.use(
    '/api',
    createProxyMiddleware({
        target: `http://${backendServer}:${backendPort}`,
        changeOrigin: true,
    }),
);
app.use(bodyParser.urlencoded({extended: false}));
app.use(bodyParser.json());

/* Server Initialization */
app.get('*',
    (_,
     res) =>
        res.sendFile(
            path.resolve(__dirname, '../build', 'index.html'),
        ),
);
app.listen(frontendPort, () => console.log(`Server initialized on: http://${frontendServer}:${frontendPort} // ${new Date()}`));
