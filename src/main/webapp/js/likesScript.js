async function toggleLike(button) {
    const postId = button.getAttribute('data-post-id');
    const likeCountElem = button.nextElementSibling;

    try {
        const response = await fetch(`/toggle-like`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ postId }),
        });

        if (!response.ok) {
            throw new Error('Ошибка при обработке лайка');
        }

        const result = await response.json();

        likeCountElem.textContent = result.likeCount;
        button.classList.toggle('liked', result.liked);
    } catch (error) {
        console.error('Ошибка:', error);
    }
}

document.addEventListener('DOMContentLoaded', function () {
    const likeButtons = document.querySelectorAll('.like-button');
    likeButtons.forEach(button => {
        button.addEventListener('click', function () {
            toggleLike(this);
        });
    });
});
