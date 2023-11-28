document.addEventListener ('DOMContentLoaded', function () {
    let form = document.getElementById("new-policy");
    form.addEventListener('submit', function (e) {
        e.preventDefault();
        let formData = new FormData(form);
        let confirmation = window.confirm("Are you sure you want to post this policy?");
        if (confirmation) {
            let newPolicy = {
                name: formData.get("name"),
                category: formData.get("category"),
                description: formData.get("description"),
                duration: formData.get("duration")
            }
            fetch( `http://localhost:5050/policy/create`,
                {
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json'},
                    body: JSON.stringify(newPolicy)
                })
                .then(response => {
                    if (response.status === 201) {
                        alert("Comment is Created!")
                    }
                }).catch(error =>{
                console.log(error)
            })
        }


    })
})