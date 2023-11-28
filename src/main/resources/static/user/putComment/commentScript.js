
let jsonData = localStorage.getItem("policy");
let policy = JSON.parse(jsonData);
console.log("policy => " + policy.policyId)
createInfoCard()
fetchComments()

document.addEventListener ('DOMContentLoaded', function () {
    let form = document.getElementById("put-rating");
    form.addEventListener('submit', function (e) {
        e.preventDefault();
        let formData = new FormData(form);
        let stisfactionR = formData.get("satisfaction")
        let clarityR = formData.get("clarity")
        let coverageR = formData.get("coverage")
        let confirmation = window.confirm(`Your Rates =>  
        satisfaction = ${stisfactionR}
        clarity = ${clarityR}
        coverage = ${coverageR}`);
        if (confirmation) {
            let newRating = {
                policyDto:{
                    policyId: policy.policyId
                },
                userDto: {
                    email: formData.get("emailR")
                },
                satisfaction: stisfactionR,
                clarity: clarityR,
                coverage:coverageR
            }
            fetch("http://localhost:5050/rating/create",{
                method: "POST",
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(newRating)
            }
        ).then(response => {
            if (response.status === 201){
                alert("Thank you for your rate")
                let commentContainer = document.getElementById('comments-container')
                commentContainer.innerHTML = '';
                let policyContainer = document.getElementById('policy-info-container')
                policyContainer.innerHTML = '';
                createInfoCard()
                fetchComments()
            }
            else if(response.status === 500)
                alert("Internal Server Error! Please try again later")
            else
                alert("You rate this policy before or your email is not register! Please try again")
            })
        }
    })
})

document.addEventListener ('DOMContentLoaded', function () {
    let form = document.getElementById("put-comment");
    form.addEventListener('submit', function (e) {
        e.preventDefault();
        let formData = new FormData(form);
        console.log( "Anonymous => " + formData.get('anonymous'))
        let confirmation = window.confirm("Are you sure?!?");
        if (confirmation) {
            fetch( `http://localhost:5050/user/findByEmail?email=${formData.get('email')}`)
                .then(response =>{
                    if (response.status === 302) {
                        response.json().then((user) => {
                            console.log("USer email =>" + user.email)
                            let newComment = {
                                userDto: user,
                                policyDto: policy,
                                commentBody: formData.get("comment"),
                                category: formData.get("category"),
                                anonymous: formData.get('anonymous') === '1',
                            }
                            fetch(`http://localhost:5050/comment/create`, {
                                method: "POST",
                                headers: {
                                    'Content-Type': 'application/json'
                                },
                                body: JSON.stringify(newComment)
                            }).then(response => {
                                if (response.status === 201) {
                                    alert("Commented successfully")
                                    let commentContainer = document.getElementById('comments-container')
                                    commentContainer.innerHTML = '';
                                    let policyContainer = document.getElementById('policy-info-container')
                                    policyContainer.innerHTML = '';
                                    createInfoCard ()
                                    fetchComments()
                                } else if (response.status === 400) {
                                    alert("Policy is not found")
                                } else {
                                    alert("Internal Server Error! Please try again later")
                                }
                            }).catch(error => {
                                alert("ERROR IN CREATION");
                                console.error('Error:', error);
                            })
                        })
                    }
                    else if(response.status === 404){
                        alert("User with this email address does not exist!")
                    }
                    else {
                        alert("Internal Server Error! Please try again later!")
                    }
                }).catch((error)=>{
                console.log(error)
            })
        }
    })
})

function createInfoCard () {
    fetch(`http://localhost:5050/policy/getById?policyId=${policy.policyId}`)
        .then(response => {
            if (response.status === 302) {
                response.json().then(policyInfo => {
                    fetch(`http://localhost:5050/rating/policyAverage?policyId=${policy.policyId}`)
                        .then(response => {
                            if (response.status === 302){
                                response.json().then(data =>{
                                    let policyCard = document.createElement('div');
                                    policyCard.classList.add('policy-card');
                                    policyCard.innerHTML = `
                                        <div class="field-label">Policy Name:</div>
                                        <div class="field-value">${policyInfo.name}</div>
                                        <div class="field-label">Policy Description:</div>
                                        <div class="field-value">${policyInfo.description}</div>
                                        <div class="field-label">Policy Category:</div>
                                        <div class="field-value">${policyInfo.category}</div>
                                        <div class="field-label">Policy Duration:</div>
                                        <div class="field-value">${policyInfo.duration}</div>
                                        <div class="field-label">Policy Category:</div>
                                        <div class="field-value">${policyInfo.category}</div>
                                        <div class="field-label">Satisfaction Rate:</div>
                                        <div class="field-value">${data[0]}</div>
                                        <div class="field-label">Clarity Rate:</div>
                                        <div class="field-value">${data[1]}</div>
                                        <div class="field-label">Coverage Rate:</div>
                                        <div class="field-value">${data[2]}</div>
                                    `;
                                    let policyContainer = document.getElementById('policy-info-container')
                                    policyContainer.appendChild(policyCard);
                                })
                            }
                        })
                })
            }
            else if (response.status === 404) {
                alert("Cannot find Policy!")
            }
            else {
                alert("Internal Server Error!")
            }
        }).catch(error =>{
        console.log(error)
    })
}

function fetchComments() {
    fetch(`http://localhost:5050/comment/getByPolicyId?policyId=${policy.policyId}`)
        .then(response => {
            if (response.status === 302) {
                response.json().then(comments => {
                    comments.forEach(comment => {
                        createCommentCard(comment)
                    })
                })
            }
            else if (response.status === 404){
                alert("Policy is not found!")
            }
            else
                alert("Comment Internal Server Error!")
        }).catch(err =>{
            console.log(err)
    })
}

function createCommentCard(comment) {
    let commentCard = document.createElement('div');
    commentCard.classList.add('comment-card');
    if (!comment.anonymous){
        commentCard.innerHTML = `
            <div class="field-label">Comment Category:</div>
            <div class="field-value">${comment.category}</div>
            <div class="field-label">Comment:</div>
            <div class="field-value">${comment.commentBody}</div>
            <div class="field-label">Writer:</div>
            <div class="field-value">${comment.userDto.firstName} ${comment.userDto.lastName}</div>
            <div class="field-label">Writer Points:</div>
            <div class="field-value">${comment.userDto.points}</div>
        `;
    }
    else{
        let anonymous = "Anonymous"
        commentCard.innerHTML = `
            <div class="field-label">Comment Category:</div>
            <div class="field-value">${comment.category}</div>
            <div class="field-label">Comment:</div>
            <div class="field-value">${comment.commentBody}</div>
            <div class="field-label">Writer:</div>
            <div class="field-value">${anonymous}</div>
            <div class="field-label">Writer Points:</div>
            <div class="field-value">${comment.userDto.points}</div>
        `;
    }
    let commentContainer = document.getElementById('comments-container')
    commentContainer.appendChild(commentCard);
}



