window.addEventListener('load', function() {
    var container = document.querySelector('.login-container');
    container.style.opacity = '0';
    container.style.marginTop = '40px';

    setTimeout(function() {
        container.style.transition = 'opacity 0.5s, margin-top 0.5s';
        container.style.opacity = '1';
        container.style.marginTop = '60px';
    }, 100);
});