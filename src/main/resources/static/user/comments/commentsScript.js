let userLocal = "";
document.addEventListener ('DOMContentLoaded', function () {
    let form = document.getElementById("put-comment");
    form.addEventListener('submit', function (e) {
        e.preventDefault();
        let formData = new FormData(form);
        fetchInfo(formData.get('email'));
    })
})

function fetchInfo(email){
    fetch( `http://localhost:5050/user/findByEmail?email=${email}`)
        .then(response => {
            if (response.status === 302) {
                response.json().then((user) => {
                    userLocal = user
                    let infoContainer = document.getElementById('info-container')
                    infoContainer.innerHTML =""
                    let rateCard = document.createElement('div');
                    rateCard.classList.add('info-card');
                    rateCard.innerHTML = `
                            <div class="field-label">Fisrt Name:</div>
                            <div class="field-value">${user.firstName}</div>
                            <div class="field-label">Last Name:</div>
                            <div class="field-value">${user.lastName}</div>
                            <div class="field-label">Points:</div>
                            <div class="field-value">${user.points}</div>
                        `;
                    infoContainer.appendChild(rateCard);
                    let commentContainer = document.getElementById('comments-container')
                    commentContainer.innerHTML='';
                    let ratingContainer = document.getElementById('rating-container')
                    ratingContainer.innerHTML='';
                    fetchComment(user)
                    fetchRating(user)
                })
            }
        }).catch(error =>{
        console.log(error)
    })
}

function fetchComment(user){
    fetch(`http://localhost:5050/comment/getByUserEmail?userEmail=${user.email}`)
        .then(response =>{
            response.json().then(comments =>{
                comments.forEach(comment =>{
                    createCommentCard(comment)
                })
            })
        })
}

function fetchRating(user){
    let ratingContainer = document.getElementById('rating-container')
    ratingContainer.innerHTML='';
    fetch(`http://localhost:5050/rating/byUserEmail?userEmail=${user.email}`)
        .then(response =>{
            response.json().then(rates =>{
                rates.forEach(rate =>{
                    createRateCard(rate)
                })
            })
        })
}

function createRateCard(rate){
    let rateCard = document.createElement('div');
    rateCard.classList.add('rate-card');
    rateCard.innerHTML = `
        <div class="field-label">Policy Name:</div>
        <div class="field-value">${rate.policyDto.name}</div>
        <div class="field-label">Policy Category:</div>
        <div class="field-value">${rate.policyDto.category}</div>
        <div class="field-label">Policy Description:</div>
        <div class="field-value">${rate.policyDto.description}</div>
        <div class="field-label">Policy Duration:</div>
        <div class="field-value">${rate.policyDto.duration}</div>
        <div class="field-label">Your rate for policy Satisfaction:</div>
        <div class="field-value">${rate.satisfaction}</div>
        <div class="field-label">Your rate for policy Clarity:</div>
        <div class="field-value">${rate.clarity}</div>
        <div class="field-label">Your rate for policy Coverage:</div>
        <div class="field-value">${rate.coverage}</div>
        <button class="delete-button">Delete Rating</button>
        <button class="update-button">Update Rating</button>
        <div class="extra-info-container"></div>
    `;
    rateCard.querySelector(`.delete-button`).addEventListener('click', ()=> {
        event.preventDefault();
        let confirmation = window.confirm("Are you sure you want to Delete this Rating?");
        if (confirmation) {
            fetch(`http://localhost:5050/rating/delete?ratingId=${rate.ratingId}`, {
                method: "DELETE",
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(response => {
                if (response.status === 200){
                    alert("Rate deleted successfully")
                    fetchRating(userLocal)
                    fetchInfo(userLocal.email)
                }
                else if (response.status === 400){
                    alert("Rating does not found")
                }
                else {
                    alert("Internal Server Error! Please try again later!")
                }
            }).catch(error =>{
                console.log(error)
            })
        }
    })
    rateCard.querySelector(`.update-button`).addEventListener('click', ()=> {
        event.preventDefault();
        let updateCard = document.createElement('div')
        updateCard.classList.add('update-card')
        let extraInfoContainer = rateCard.querySelector('.extra-info-container');
        updateCard.innerHTML = `
            <label for="satisfaction">Satisfaction with the insurance policy and services</label>
            <input type="number" id="satisfaction" name="satisfaction" min="1" max="5" step="1" required>
            <br>
            <label for="clarity">Clarity and transparency of the insurance policy terms and conditions.</label>
            <input type="number" id="clarity" name="clarity" min="1" max="5" step="1" required>
            <br>
            <label for="coverage">Coverage met your needs and expectations.</label>
            <input type="number" id="coverage" name="coverage" min="1" max="5" step="1" required>
            <br>
            <button class="update-update-button" id="update-update-button">Update</button>
            <button class="cancel-button" id="cancel-button">Cancel</button>
        `;
        updateCard.querySelector(`.update-update-button`).addEventListener('click', () => {
            event.preventDefault();
            let confirmation = window.confirm("Are you sure you want to update this rating?");
            if (confirmation) {
                const newSatisfaction = document.getElementById('satisfaction').value
                const newClarity = document.getElementById('clarity').value
                const newCoverage = document.getElementById('coverage').value
                let newRating = {
                    ratingId: rate.ratingId,
                    satisfaction: newSatisfaction,
                    clarity: newClarity,
                    coverage: newCoverage
                }
                fetch(`http://localhost:5050/rating/update`, {
                    method: "PUT",
                    headers:{
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(newRating)
                }).then(response =>{
                    if (response.status === 200){
                        alert("Rating is updated!")
                        // let ratingContainer = document.getElementById('Rating-container')
                        // ratingContainer.innerHTML='';
                        fetchRating(userLocal)
                    }
                    else if (response.status === 400){
                        alert("Not Found Status Code!")
                    }
                    else{
                        alert("Internal Server Error! Please try again later.")
                    }
                })
            }
        })
        updateCard.querySelector(`.cancel-button`).addEventListener('click', () => {
            event.preventDefault()
            let extraInfoContainer = rateCard.querySelector('.extra-info-container');
            extraInfoContainer.innerHTML=''
        })
        extraInfoContainer.appendChild(updateCard)
    })
    let ratingContainer = document.getElementById('rating-container')
    ratingContainer.appendChild(rateCard);
}

function createCommentCard(comment) {
    let commentCard = document.createElement('div');
    commentCard.classList.add('comment-card');
    commentCard.innerHTML = `
        <div></div>
        <div class="field-label">Comment Category:</div>
        <div class="field-value">${comment.category}</div>
        <div class="field-label">Comment:</div>
        <div class="field-value">${comment.commentBody}</div>
        <div class="field-label">Visibility:</div>
        <div class="field-value">${comment.anonymous === true ? "Anonymous" : "Non-anonymous"}</div>
        <div class="field-label">Policy:</div>
        <div class="field-value">${comment.policyDto.name} ${comment.policyDto.category}</div>
        <div class="field-label">Policy Description:</div>
        <div class="field-value">${comment.policyDto.description}</div>
        <button class="delete-button">Delete Comment</button>
        <button class="update-button">Update Comment</button>
        <div class="extra-info-container"></div>
    `;
    commentCard.querySelector(`.delete-button`).addEventListener('click', ()=> {
        event.preventDefault();
        let confirmation = window.confirm("Are you sure you want to Delete this comment?");
        if (confirmation) {
            fetch(`http://localhost:5050/comment/delete?commentId=${comment.commentId}`, {
                method: "DELETE",
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(response => {
                if (response.status === 200){
                    alert("Comment deleted successfully")
                    let commentContainer = document.getElementById('comments-container')
                    commentContainer.innerHTML='';
                    fetchComment(userLocal)
                    fetchInfo(userLocal.email)
                }
                else if (response.status === 400){
                    alert("Comment does not found")
                }
                else {
                    alert("Internal Server Error! Please try again later!")
                }
            }).catch(error =>{
                console.log(error)
            })
        }
    })
    commentCard.querySelector(`.update-button`).addEventListener('click', ()=> {
        event.preventDefault();
        let updateCard = document.createElement('div')
        updateCard.classList.add('update-card')
        let extraInfoContainer = commentCard.querySelector('.extra-info-container');
        updateCard.innerHTML = `
            <label for="newCategory">New Category:</label>
            <input type="text" name="newCategory" id="newCategory">
            <br>
            <label for="comment">New Comment:</label>
            <textarea id="comment" name="comment" rows="5"></textarea>
            <label for="anonymous"> anonymous? </label>
            <input type="checkbox" id="anonymous" name="anonymous" value=1>
            <button class="update-update-button" id="update-update-button">Update</button>
            <button class="cancel-button" id="cancel-button">Cancel</button>
        `;
        updateCard.querySelector(`.update-update-button`).addEventListener('click', () => {
            event.preventDefault();
            let confirmation = window.confirm("Are you sure you want to Update this comment?");
            if (confirmation) {
                const newCategory = document.getElementById('newCategory').value
                const newCommentBody = document.getElementById('comment').value
                const newAnonymous = document.getElementById('anonymous').value == 1
                let newComment = {
                    commentId: comment.commentId,
                    commentBody: newCommentBody,
                    category: newCategory,
                    anonymous: newAnonymous
                }
                fetch(`http://localhost:5050/comment/update`, {
                    method: "PUT",
                    headers:{
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(newComment)
                }).then(response =>{
                    if (response.status === 200){
                        alert("Comment is updated!")
                        let commentContainer = document.getElementById('comments-container')
                        commentContainer.innerHTML='';
                        fetchComment(userLocal)
                    }
                    else if (response.status === 400){
                        alert("Not Found Status Code!")
                    }
                    else{
                        alert("Internal Server Error! Please try again later.")
                    }
                })
            }
        })
        updateCard.querySelector(`.cancel-button`).addEventListener('click', () => {
            event.preventDefault()
            let extraInfoContainer = commentCard.querySelector('.extra-info-container');
            extraInfoContainer.innerHTML=''
        })
        extraInfoContainer.appendChild(updateCard)
    })
    let commentContainer = document.getElementById('comments-container')
    commentContainer.appendChild(commentCard);
}
