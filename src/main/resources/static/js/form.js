window.addEventListener('load', function() {
    var container = document.querySelector('.form-container');
    container.style.opacity = '0';
    container.style.transform = 'translateY(-15px)';

    setTimeout(function() {
        container.style.transition = 'opacity 0.4s, transform 0.4s';
        container.style.opacity = '1';
        container.style.transform = 'translateY(0)';
    }, 50);
});

var titleInput = document.getElementById('title');
if(titleInput && !titleInput.value) {
    setTimeout(function() {
        titleInput.focus();
    }, 100);
}

var form = document.getElementById('todoForm');
form.addEventListener('submit', function(e) {
    var title = document.getElementById('title').value;
    if(title.trim().length === 0) {
        alert('Please enter a title');
        e.preventDefault();
        return false;
    }
});