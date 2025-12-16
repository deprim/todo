var modal = document.getElementById("editTodoModal");
var closeBtn = document.getElementsByClassName("close-btn")[0];
var editForm = document.getElementById("editForm");
var editTitle = document.getElementById("editTitle");
var editDescription = document.getElementById("editDescription");
var editPriority = document.getElementById("editPriority");
var editDueDate = document.getElementById("editDueDate");

function openEditModal(button) {
    var id = button.getAttribute('data-id');
    var title = button.getAttribute('data-title');
    var description = button.getAttribute('data-description');
    var priority = button.getAttribute('data-priority');
    var dueDate = button.getAttribute('data-due-date');

    editTitle.value = title;
    editDescription.value = description && description !== 'null' ? description : '';
    editPriority.value = priority;

    editDueDate.value = dueDate && dueDate !== 'null' ? dueDate : '';


    editForm.setAttribute('action', '/todo/' + id + '/edit');

    modal.style.display = "block";
}

closeBtn.onclick = function() {
    modal.style.display = "none";
}

window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

window.addEventListener('load', function() {
    var items = document.querySelectorAll('.todo-item');
    var delay = 0;
    for(var i = 0; i < items.length; i++) {
        items[i].style.opacity = '0';
        items[i].style.marginLeft = '-20px';
        (function(item, delayTime) {
            setTimeout(function() {
                item.style.transition = 'opacity 0.4s, margin-left 0.4s';
                item.style.opacity = '1';
                item.style.marginLeft = '0';
            }, delayTime);
        })(items[i], delay);
        delay += 100;
    }
});

var buttons = document.querySelectorAll('.btn, .add-todo-btn, .logout-btn, .filter-buttons a');
for(var i = 0; i < buttons.length; i++) {
    buttons[i].addEventListener('mouseenter', function() {
        this.style.transform = 'scale(1.05)';
        this.style.transition = 'transform 0.2s';
    });
    buttons[i].addEventListener('mouseleave', function() {
        this.style.transform = 'scale(1)';
    });
}

document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.toggle-form').forEach(form => {
        const checkbox = form.querySelector('.todo-checkbox');
        const todoItem = form.closest('.todo-item');

        if (checkbox && todoItem) {
            checkbox.addEventListener('change', async (event) => {
                event.preventDefault();

                const isCheckedBeforeRequest = event.target.checked;
                const url = form.action;

                try {
                    const response = await fetch(url, {
                        method: 'POST',
                    });

                    if (response.ok || response.status === 204) {
                        if (isCheckedBeforeRequest) {
                            todoItem.classList.add('completed');
                        } else {
                            todoItem.classList.remove('completed');
                        }

                        event.target.checked = isCheckedBeforeRequest;

                    } else {
                        console.error('Ошибка переключения задачи:', response.status);
                        event.target.checked = !isCheckedBeforeRequest;
                    }

                } catch (error) {
                    console.error('Сетевая ошибка:', error);
                    event.target.checked = !isCheckedBeforeRequest;
                }
            });
        }
    });
});