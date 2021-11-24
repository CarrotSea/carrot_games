for (let i = 0; i < 1600; i++) {
    const div = document.createElement("div");
    div.setAttribute("class", "grid");
    const parent = document.getElementById("window");
    parent.appendChild(div);
}

class Node {
    constructor(grid) {
        this.grid = grid;
        this.next = null;
    }
}

const grids = [...document.getElementsByClassName('grid')];
const restart_button = document.getElementById('restart');
const game_over_text = document.getElementById('game-over-text');
const submit_button = document.getElementById('submit');
const data = document.getElementById('data');
const ROW = 40;
const COLUMN = 40;
const WINDOW = transform();
const SPAWN_COORDS = [20, 18];
let bearing;
let snake;
let interval;
let length = 5;

grids.forEach(grid => {
    const index = grids.indexOf(grid);
    if ((index / 40 < 1) || (index % 40 == 39) || (index % 40 == 0) || (index / 40 > 39)) {
        grid.classList.add('edge');
    }
});

restart_button.addEventListener('click', startGame);

function startGame() {
    initialize();
    interval = setInterval(orient, 100);
}

function initialize() {
    snake = null;
    bearing = null;
    length = 5;
    data.innerHTML = `${length}`;
    game_over_text.innerHTML = 'Go Snake Go!!!'
    submit_button.classList.remove('show');
    grids.forEach(grid => {
        grid.classList.remove('snake');
        grid.classList.remove('food');
    })
    document.removeEventListener('keyup', handleKey);
    bearing = [1, 0];
    document.addEventListener('keyup', handleKey);
    spawn();
    generateFood();
}

function handleKey(e) {
    const code = e.code;
    switch (code) {
        case "ArrowUp":
            if (bearing[1] !== 1)
                bearing = [0, -1];
            break;
        case "ArrowDown":
            if (bearing[1] !== -1)
                bearing = [0, 1];
            break;
        case "ArrowLeft":
            if (bearing[0] !== 1)
                bearing = [-1, 0];
            break;
        case "ArrowRight":
            if (bearing[0] !== - 1)
                bearing = [1, 0];
            break;
    }
}

function spawn() {
    snake = new Node(WINDOW[SPAWN_COORDS[0]][SPAWN_COORDS[1]]);
    snake.grid.classList.add('snake');
    let curr = snake;
    for (let i = 0; i < 5; i++) {
        curr.next = new Node(WINDOW[SPAWN_COORDS[0]][SPAWN_COORDS[1] - i]);
        curr.next.grid.classList.add('snake');
        curr = curr.next;
    }
}

function orient() {
    let coords = coordinates(snake.grid);
    let target = WINDOW[coords[0] + bearing[1]][coords[1] + bearing[0]];
    move(target);
}

function move(target) {
    if (snake.grid.classList.contains('food'))
        eatFood();
    if (target.classList.contains('edge') || target.classList.contains('snake'))
        endGame();

    let curr = snake;
    let temp = null;
    while (curr !== null) {
        temp = curr.grid;
        curr.grid = target;
        curr.grid.classList.add('snake');
        target = temp;
        target.classList.remove('snake');
        curr = curr.next;
    }
}

function generateFood() {
    let index;
    do {
        index = Math.floor(Math.random() * ROW * COLUMN);
    } while (grids[index].classList.contains('snake') || grids[index].classList.contains('edge'));
    grids[index].classList.add('food');
}

function endGame() {
    game_over_text.innerHTML = 'Game Over!'
    submit_button.classList.add('show');
    clearInterval(interval);
}

function eatFood() {
    snake.grid.classList.remove('food');
    let curr = snake;
    while (curr.next.next !== null) {
        curr = curr.next;
    }

    let coords_second = coordinates(curr.grid);
    curr = curr.next;
    let coords_last = coordinates(curr.grid);
    let append_pos = WINDOW[coords_last[0] + (coords_last[0] - coords_second[0])]
                            [coords_last[1] + (coords_last[1] - coords_second[1])];
    append_pos.classList.add('snake');
    curr.next = new Node(append_pos);
    length++;
    data.innerHTML = `${length}`;
    generateFood();
}

function coordinates(grid) {
    let index = [...grids].indexOf(grid);
    return [Math.floor(index / COLUMN), index % COLUMN];
}

function transform() {
    let window = []
    for (let i = 0; i < ROW; i++) {
        window.push(new Array(COLUMN));
    }
    for (let i = 0; i < ROW * COLUMN; i++) {
        window[Math.floor(i / COLUMN)][i % COLUMN] = grids[i];
    }
    return window;
}

