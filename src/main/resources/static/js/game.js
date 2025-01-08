let stompClient = null;
let playerName = '';
let playerX = 0;
let playerY = 0;
let gameLoop;
let players = [];

function connectWebSocket() {
    const socket = new SockJS('/ws-game');
    stompClient = Stomp.over(socket);
    
    stompClient.connect({}, function(frame) {
        console.log('Connecté au WebSocket');
        
        stompClient.subscribe('/topic/game', function(message) {
            updateGame(JSON.parse(message.body));
        });
        
        stompClient.subscribe('/topic/players', function(message) {
            updatePlayersList(JSON.parse(message.body));
        });
        
        sendPlayerJoined();
        startGameLoop();
    });
}

function joinGame() {
    playerName = document.getElementById('playerName').value;
    if (playerName) {
        connectWebSocket();
    } else {
        alert('Veuillez entrer votre nom');
    }
}

function sendPlayerJoined() {
    stompClient.send("/app/join", {}, JSON.stringify({
        'name': playerName
    }));
}

function startGameLoop() {
    document.addEventListener('keydown', handleKeyPress);
    document.addEventListener('click', handleShoot);
    
    gameLoop = setInterval(() => {
        // Mise à jour continue du jeu
        drawGame();
    }, 1000 / 60); // 60 FPS
}

function handleKeyPress(event) {
    const speed = 5;
    let moved = false;

    switch(event.key) {
        case 'ArrowUp':
            playerY -= speed;
            moved = true;
            break;
        case 'ArrowDown':
            playerY += speed;
            moved = true;
            break;
        case 'ArrowLeft':
            playerX -= speed;
            moved = true;
            break;
        case 'ArrowRight':
            playerX += speed;
            moved = true;
            break;
    }

    if (moved) {
        sendPlayerMove();
    }
}

function handleShoot(event) {
    const gameArea = document.getElementById('gameArea');
    const rect = gameArea.getBoundingClientRect();
    const mouseX = event.clientX - rect.left;
    const mouseY = event.clientY - rect.top;
    
    // Calculer l'angle de tir
    const angle = Math.atan2(mouseY - playerY, mouseX - playerX);
    
    sendPlayerShoot(angle);
}

function sendPlayerMove() {
    stompClient.send("/app/move", {}, JSON.stringify({
        playerName: playerName,
        x: playerX,
        y: playerY
    }));
}

function sendPlayerShoot(angle) {
    stompClient.send("/app/shoot", {}, JSON.stringify({
        playerName: playerName,
        x: playerX,
        y: playerY,
        angle: angle
    }));
}

function updateGame(gameState) {
    players = gameState.players;
    const projectile = gameState.lastProjectile;
    
    // Mettre à jour les positions des joueurs
    players.forEach(player => {
        if (player.name === playerName) {
            playerX = player.x;
            playerY = player.y;
        }
    });
    
    if (projectile) {
        animateProjectile(projectile);
    }
    
    drawGame();
}

function updatePlayersList(players) {
    const playersList = document.getElementById('players');
    playersList.innerHTML = '';
    players.forEach(player => {
        const li = document.createElement('li');
        li.textContent = player.name;
        playersList.appendChild(li);
    });
}

function drawGame() {
    const canvas = document.getElementById('gameCanvas');
    const ctx = canvas.getContext('2d');
    
    // Effacer le canvas
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    
    // Dessiner tous les joueurs
    players.forEach(player => {
        ctx.beginPath();
        ctx.arc(player.x, player.y, 20, 0, Math.PI * 2);
        ctx.fillStyle = player.name === playerName ? 'blue' : 'red';
        ctx.fill();
        ctx.closePath();
        
        // Afficher le nom et la santé
        ctx.fillStyle = 'black';
        ctx.fillText(player.name, player.x - 20, player.y - 25);
        ctx.fillText(`HP: ${player.health}`, player.x - 20, player.y - 10);
    });
}

function animateProjectile(projectile) {
    const speed = 10;
    const dx = Math.cos(projectile.angle) * speed;
    const dy = Math.sin(projectile.angle) * speed;
    
    const ctx = document.getElementById('gameCanvas').getContext('2d');
    ctx.beginPath();
    ctx.arc(projectile.x, projectile.y, 5, 0, Math.PI * 2);
    ctx.fillStyle = 'black';
    ctx.fill();
    ctx.closePath();
}