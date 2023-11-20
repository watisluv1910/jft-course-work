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

/* App Config */
app.use(express.static(path.join(__dirname, '../build')));
app.use(
    '/api',
    createProxyMiddleware({
        target: `http://${process.env.BACKEND_SERVER}:8080`,
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
            path.resolve(__dirname, 'client', 'build', '../public/index.html'),
        ),
    );
const port = process.env.FRONTEND_LOCAL_PORT || 3000;
app.listen(port, () => console.log(`Server initialized on: http://${process.env.FRONTEND_SERVER}:${port} // ${new Date()}`));
