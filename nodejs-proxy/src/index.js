const express = require('express');
const morgan = require("morgan");
const { createProxyMiddleware } = require('http-proxy-middleware');

// Create Express Server
const app = express();

// Configuration
const PORT = 3000;
const HOST = "10.77.3.117";

const KEYCLOAK_SERVER_URL = "http://192.168.49.2:31606";
const NG_URL = "http://10.77.3.117:4200"
const API_URL = "http://localhost:8888"

app.use(morgan('dev'));

app.get('/info', (req, res, next) => {
    res.send('This is a proxy service which proxies requests for this project.');
});

app.use('/iam', createProxyMiddleware({
    target: KEYCLOAK_SERVER_URL,
    changeOrigin: true,
    pathRewrite: {
        [`^/iam`]: '/iam',
    },
    secure: false,
    xfwd: true
}));

app.use('/ng', createProxyMiddleware({
    target: NG_URL,
    changeOrigin: true,
    pathRewrite: {
        [`^/ng`]: '/ng',
    },
    secure: false,
    xfwd: true,
    ws: true
}));

app.use('/api', createProxyMiddleware({
    target: API_URL,
    changeOrigin: true,
    pathRewrite: {
        [`^/api`]: '/api',
    },
    secure: false,
    xfwd: true
}));

app.use('/oauth2/authorization/keycloak2', createProxyMiddleware({
    target: API_URL,
    changeOrigin: true,
    secure: false,
    xfwd: true
}))

// Start the Proxy
app.listen(PORT, HOST, () => {
    console.log(`Starting Proxy at ${HOST}:${PORT}`);
});
