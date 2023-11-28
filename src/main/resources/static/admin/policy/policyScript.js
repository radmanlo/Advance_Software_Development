fetchApiPolicy();

function fetchApiPolicy(){
    fetch( "http://localhost:5050/policy/getAll")
        .then((response) => response.json())
        .then((data)=>{
            if (data.length === 0){
                alert("There is no policy!")
                location.href = `../index.html`;
            }
            data.forEach((policy)=>{
                createPolicyCard(policy)
            })
        }).catch((error)=>{
        console.log("Error fetching data => ", error)
    })
}

function createPolicyCard(policy){
    let average = [0,0,0];
    fetch(`http://localhost:5050/rating/policyAverage?policyId=${policy.policyId}`)
        .then(response => {
            if (response.status === 302){
                response.json().then(data =>{
                    average = data;
                    let policyCard = document.createElement('div');
                    policyCard.classList.add('policy-card');
                    policyCard.innerHTML = `
                        <div class="field-label">Policy Name:</div>
                        <div class="field-value">${policy.name}</div>
                        <div class="field-label">Policy Description:</div>
                        <div class="field-value">${policy.description}</div>
                        <div class="field-label">Policy Category:</div>
                        <div class="field-value">${policy.category}</div>
                        <div class="field-label">Policy Duration:</div>
                        <div class="field-value">${policy.duration}</div>
                        <div class="field-label">Satisfaction:</div>
                        <div class="field-value">${average[0]}</div>
                        <div class="field-label">Clarity Rate:</div>
                        <div class="field-value">${average[1]}</div>
                        <div class="field-label">Coverage Rate:</div>
                        <div class="field-value">${average[2]}</div>
                        <button class="comment-button">Comments</button>
                        <button class="update-button">Update Policy</button>
                        <button class="delete-button">Delete Policy</button>
                        <div class="extra-info-container"></div>
                    `;
                    policyCard.querySelector(`.comment-button`).addEventListener('click', ()=> {
                        event.preventDefault();
                        let extraInfoContainer = policyCard.querySelector('.extra-info-container');
                        extraInfoContainer.innerHTML =''
                        fetch(`http://localhost:5050/comment/getByPolicyId?policyId=${policy.policyId}`).then(
                            response => {
                                if (response.status === 302){
                                    response.json().then(
                                        data => {
                                            if(data.length !== 0) {
                                                data.forEach( comment =>{
                                                    let commentCard = document.createElement('div')
                                                    commentCard.classList.add('comment-card')
                                                    if (!comment.anonymous){
                                                        commentCard.innerHTML = `
                                                            <div class="field-label">Comment Category:</div>
                                                            <div class="field-value">${comment.category}</div>
                                                            <div class="field-label">Comment:</div>
                                                            <div class="field-value">${comment.commentBody}</div>
                                                            <div class="field-label">Writer:</div>
                                                            <div class="field-value">${comment.userDto.firstName} 
                                                                    ${comment.userDto.lastName}</div>
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
                                                    extraInfoContainer.appendChild(commentCard)
                                                })
                                            }
                                            else{
                                                let commentCard = document.createElement('div')
                                                commentCard.classList.add('comment-card')
                                                commentCard.innerHTML = `
                                                    <p>No Comment</p>
                                                `;
                                                extraInfoContainer.appendChild(commentCard)
                                            }
                                        }
                                    )
                                }
                                else{
                                    alert("Internal Server Error! Please try again later")
                                }
                            }
                        )

                    })
                    policyCard.querySelector(`.update-button`).addEventListener('click', ()=> {
                        event.preventDefault();
                        console.log("I am inside")
                        let extraInfoContainer = policyCard.querySelector('.extra-info-container');
                        extraInfoContainer.innerHTML =''
                        let updateCard = document.createElement('div')
                        updateCard.classList.add('update-card')
                        updateCard.innerHTML = `
                            <label for="newName">Name:</label>
                            <input type="text" name="newName" id="newName">
                            <br>
                            <label for="newCategory">Category:</label>
                            <input type="text" name="newCategory" id="newCategory">
                            <br>
                            <label for="newDescription">Description:</label>
                            <textarea id="newDescription" name="newDescription" rows="5"></textarea>
                            <br>
                            <label for="newDuration">Duration:</label>
                            <input type="text" name="newDuration" id="newDuration">
                            <button class="update-update-button" id="update-update-button">Update</button>
                            <button class="cancel-button">Cancel</button>
                        `;
                        updateCard.querySelector(`.update-update-button`).addEventListener('click', () => {
                            event.preventDefault();
                            let confirmation = window.confirm("Are you sure you want to update this policy?");
                            if (confirmation) {
                                const newCategory = document.getElementById('newCategory').value
                                const newName = document.getElementById('newName').value
                                const newDescription = document.getElementById('newDescription').value
                                const newDuration = document.getElementById('newDuration').value
                                let newPolicy = {
                                    policyId: policy.policyId,
                                    name: newName,
                                    category: newCategory,
                                    description: newDescription,
                                    duration: newDuration
                                }
                                fetch(`http://localhost:5050/policy/update`, {
                                    method: "PUT",
                                    headers:{
                                        'Content-Type': 'application/json'
                                    },
                                    body: JSON.stringify(newPolicy)
                                }).then(response =>{
                                    if (response.status === 202){
                                        alert("Policy is updated!")
                                        let policyContainer = document.getElementById('policies-container')
                                        policyContainer.innerHTML=''
                                        fetchApiPolicy()
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
                            event.preventDefault();
                            let extraInfoContainer = policyCard.querySelector('.extra-info-container');
                            extraInfoContainer.innerHTML =''
                        })
                        extraInfoContainer.appendChild(updateCard)
                    })
                    policyCard.querySelector(`.delete-button`).addEventListener('click', ()=> {
                        event.preventDefault();
                        let confirmation = window.confirm("Are you sure you want to delete this policy?");
                        if (confirmation) {
                            fetch(`http://localhost:5050/policy/delete?policyId=${policy.policyId}`,{
                                method: "DELETE"
                            })
                                .then(response => {
                                    if (response.status === 200){
                                        alert("Policy is Deleted!")
                                        let policyContainer = document.getElementById('policies-container')
                                        policyContainer.innerHTML=''
                                        fetchApiPolicy()
                                    }
                                    else{
                                        alert("Internal Server Error! Please try again later")
                                    }
                                })
                        }
                    })
                    let policyContainer = document.getElementById('policies-container')
                    policyContainer.appendChild(policyCard);
                })
            }
        })
}