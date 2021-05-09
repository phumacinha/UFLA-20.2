const http = require('http');

const server = http.createServer((req, res) => {
    res.setHeader("Content-Type", "application/json");
    console.log('receiving request');
    res.writeHead(200);
    res.end("server-1");
});

server.listen(3001, 'localhost', () => {
    console.log('running on http://localhost:3001/');
});