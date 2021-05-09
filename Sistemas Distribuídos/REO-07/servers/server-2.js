const http = require('http');

const server = http.createServer((req, res) => {
    res.setHeader("Content-Type", "application/json");
    console.log('receiving request');
    res.writeHead(200);
    res.end("server-2");
});

server.listen(3002, 'localhost', () => {
    console.log('running on http://localhost:3002/');
});