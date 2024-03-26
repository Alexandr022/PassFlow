document.getElementById("registrationForm").addEventListener("submit", function(event) {
    event.preventDefault();

    var formData = new FormData(this);

    fetch('/auth/register', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text();
        })
        .then(data => {
            alert(data); // Show response message
        })
        .catch(error => {
            console.error('There was a problem with your fetch operation:', error);
        });
});

document.getElementById("password").addEventListener("keydown", function(event) {
    if (event.key === "Enter") {
        document.getElementById("registrationForm").dispatchEvent(new Event("submit"));
    }
});
