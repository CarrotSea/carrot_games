const ROW = document.getElementById('num_of_row').value;
const COLUMN = document.getElementById('num_of_column').value;
const BOMB = document.getElementById('num_of_bomb').value;
const grids = [...document.querySelectorAll('[grid]')];
const face = document.getElementById('face');
const restart_button = document.getElementById('restart');
const submit_button = document.getElementById('submit');
const board = transform();
const timer = document.getElementById('data');
let time = 0;
let interval;

startGame();
restart_button.addEventListener('click', startGame);

function startGame() {
    initialize();
    populate();
    interval = setInterval(countTime, 1000);
}

function countTime() {
    time++;
    timer.innerHTML = `${time}`;
}

function initialize() {
    face.classList.remove('gameover');
    face.classList.add('smile');
    submit_button.classList.remove('show');
    time = 0;
    timer.innerHTML = '0';
    clearInterval(interval);
    grids.forEach(grid => {
        grid.classList.remove('clicked');
        grid.classList.remove('bomb');
        grid.classList.remove('normal');
        grid.classList.remove('flagged');
        grid.classList.remove('numbered');
        grid.classList.remove('bomb_show');
        grid.classList.remove('considered');
        grid.removeEventListener('click', handleLeftClick);
        grid.removeEventListener('contextmenu', handleRightClick);
        grid.innerHTML = '';
        grid.classList.add('normal');
        grid.addEventListener('click', handleLeftClick);
        grid.addEventListener('contextmenu', handleRightClick);
    })
}

function populate() {
    for (let i = 0; i < BOMB; i++) {
        let index = Math.floor(Math.random() * (ROW * COLUMN));
        grids[index].classList.add('bomb');

    }
}

function handleLeftClick(e) {
    const grid = e.target;
    if (grid.classList.contains('flagged')) {
        return;
    }
    if (grid.classList.contains('bomb')) {
        explode();
    }
    reveal(grid);
}

function reveal(grid) {
    grid.classList.add('considered');
    const index = grids.indexOf(grid);
    const row = Math.floor(index / COLUMN);
    const col = index % COLUMN;
    const neighbors = []

    if (row >= 1)
        neighbors.push(board[row - 1][col]);
    if (row <= ROW - 2)
        neighbors.push(board[row + 1][col]);
    if (col >= 1)
        neighbors.push(board[row][col - 1]);
    if (col <= COLUMN - 2)
        neighbors.push(board[row][col + 1]);
    if (row >= 1 && col >= 1)
        neighbors.push(board[row - 1][col - 1]);
    if (row >= 1 && col <= COLUMN - 2)
        neighbors.push(board[row - 1][col + 1]);
    if (row <= ROW - 2 && col >= 1)
        neighbors.push(board[row + 1][col - 1]);
    if (row <= ROW - 2 && col <= COLUMN - 2)
        neighbors.push(board[row + 1][col + 1]);

    if (!grid.classList.contains('bomb')) {
        if (!grid.classList.contains('clicked')) {
            grid.classList.add('clicked');
            if (!grid.classList.contains('numbered')) {
                let count = countBombs(neighbors);
                if (count !== 0) {
                    grid.innerHTML = `${count}`;
                    grid.classList.add('numbered');
                    return;
                }
            }
        }
    }

    neighbors.forEach(nb => {
        if (!nb.classList.contains('considered'))
            reveal(nb);
    })
}

function countBombs(neighbors) {
    let count = 0;
    for (let nb of neighbors) {
        if (nb.classList.contains('bomb'))
            count++;
    }
    return count;
}

function explode() {
    grids.forEach(grid => {
        if (grid.classList.contains('bomb')) {
            grid.classList.add('bomb_show');
        }
    })
    endGame(false);
}

function handleRightClick(e) {
    const grid = e.target;
    if (grid.classList.contains('clicked') || grid.classList.contains('flagged')) {
        return;
    }
    grid.classList.add('flagged');
    if (checkWin()) {
        endGame(true);
    }
}

function checkWin() {
    let bombs = [];
    grids.forEach(grid => {
        if (grid.classList.contains('bomb')) {
            bombs.push(grid);
        }
    })
    return bombs.every(bomb => {
        return bomb.classList.contains('flagged');
    })
}

function endGame(flag) {
    if (flag) {
        alert("win!!!");
        submit_button.classList.add('show');
    } else {
        face.classList.remove('smile');
        face.classList.add('gameover');
    }
    clearInterval(interval);
    grids.forEach(grid => {
        grid.removeEventListener('click', handleLeftClick());
        grid.removeEventListener('contextmenu', handleRightClick());
    })
}

function transform() {
    let board = [];
    for (let i = 0; i < ROW; i++) {
        board.push(new Array(COLUMN));
    }
    for (let i = 0; i < ROW * COLUMN; i++) {
        board[Math.floor(i / COLUMN)][i % COLUMN] = grids[i];
    }
    return board;
}

