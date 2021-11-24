const submit = document.getElementById('submit');
submit.addEventListener('click', function() {
    let form = new FormData();
    let game = document.getElementById('game').getAttribute('value');
    let data = document.getElementById('data').innerHTML;
    form.append('action', 'score');
    form.append('game', game);
    form.append('data', data);
    let request = new XMLHttpRequest();
    request.onreadystatechange = function() {
        if (request.readyState == 4) {
            let text = request.responseText;
            alert(text);
        }
    }
    request.open('POST', '../../HelloServlet', true);
    request.send(form);
})