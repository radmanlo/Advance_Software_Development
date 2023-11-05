let userLocal = "";
document.addEventListener ('DOMContentLoaded', function () {
    let form = document.getElementById("put-comment");
    form.addEventListener('submit', function (e) {
        e.preventDefault();
        let formData = new FormData(form);
        fetch( `http://localhost:5050/user/findByEmail?email=${formData.get('email')}`)
            .then(response => {
                if (response.status === 302) {
                    response.json().then((user) => {
                        userLocal = user
                        let commentContainer = document.getElementById('comments-container')
                        commentContainer.innerHTML='';
                        fetchComment(user)
                    })
                }
            }).catch(error =>{
                console.log(error)
            })
    })
})

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

function createCommentCard(comment) {
    let commentCard = document.createElement('div');
    commentCard.classList.add('putComment-card');
    commentCard.innerHTML = `
        <div class="field-label">Comment Category:</div>
        <div class="field-value">${comment.category}</div>
        <div class="field-label">Comment:</div>
        <div class="field-value">${comment.commentBody}</div>
        <div class="field-label">Policy:</div>
        <div class="field-value">${comment.policyDto.policyId} ${comment.policyDto.name} ${comment.policyDto.category}</div>
        <div class="field-label">Policy Description:</div>
        <div class="field-value">${comment.policyDto.description}</div>
        <button class="delete-button">Delete Comment</button>
        <button class="update-button">Update Comment</button>
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
        commentCard.innerHTML = `
            <div class="field-label">Comment Category:</div>
            <div class="field-value">${comment.category}</div>
            <div class="field-label">Comment:</div>
            <div class="field-value">${comment.commentBody}</div>
            <div class="field-label">Policy:</div>
            <div class="field-value">${comment.policyDto.policyId} ${comment.policyDto.name} ${comment.policyDto.category}</div>
            <div class="field-label">Policy Description:</div>
            <div class="field-value">${comment.policyDto.description}</div>
            <label for="newCategory">New Category:</label>
            <input type="text" name="newCategory" id="newCategory">
            <label for="comment">New Comment:</label>
            <textarea id="comment" name="comment" rows="5"></textarea>
            <button class="submit-button" id="submit-button">Update Comment</button>
            <button class="cancel-button" id="cancel-button">Cancel</button>
        `;
        document.getElementById('submit-button').addEventListener('click', () => {
            event.preventDefault();
            let confirmation = window.confirm("Are you sure you want to Update this comment?");
            if (confirmation) {
                const newCategory = document.getElementById('newCategory').value
                const newCommentBody = document.getElementById('comment').value

                let newComment = {
                    commentId: comment.commentId,
                    commentBody: newCommentBody,
                    category: newCategory
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
        document.getElementById('cancel-button').addEventListener('click', () => {
            event.preventDefault()
            let commentContainer = document.getElementById('comments-container')
            commentContainer.innerHTML='';
            fetchComment(userLocal)
        })

    })
    let commentContainer = document.getElementById('comments-container')
    commentContainer.appendChild(commentCard);
}
