const express = require('express');
const morgan = require("morgan");
const { createProxyMiddleware } = require('http-proxy-middleware');

// Create Express Server
const app = express();

// Configuration
const PORT = 3000;
const HOST = "localhost";

const KEYCLOAK_SERVER_URL = "https://localhost:31972";
const NG_URL = "http://localhost:4200"
const API_URL = "http://localhost:8888"

app.use(morgan(':method :url :req[authorization] :status :res[content-length] - :response-time ms'));

app.get('/info', (req, res, next) => {
    res.send('This is a proxy service which proxies requests for this project.');
});

app.use('/auth', createProxyMiddleware({
    target: KEYCLOAK_SERVER_URL,
    changeOrigin: true,
    pathRewrite: {
        [`^/auth`]: '/auth',
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
        [`^/api`]: '',
    },
    secure: false,
    xfwd: true,
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
