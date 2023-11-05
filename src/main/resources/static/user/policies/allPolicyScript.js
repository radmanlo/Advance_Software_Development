fetchApiPolicy()

function fetchApiPolicy(){
    fetch( "http://localhost:5050/policy/getAll")
        .then((response) => response.json())
        .then((data)=>{
            data.forEach((policy)=>{
                createPolicyCard(policy)
            })
        }).catch((error)=>{
        console.log("Error fetching data => ", error)
    })
}

function createPolicyCard(policy){
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
        <div class="field-label">Policy Category:</div>
        <div class="field-value">${policy.category}</div>
        <div class="field-label">Policy Likes:</div>
        <div class="field-value">${policy.likes}</div>
        <button class="comment-button">Comments</button>
    `;
    policyCard.querySelector(`.comment-button`).addEventListener('click', ()=> {
        event.preventDefault();
        localStorage.setItem("policy", JSON.stringify(policy));
        location.href = `../putComment/comment.html`;
    })
    let policyContainer = document.getElementById('policies-container')
    policyContainer.appendChild(policyCard);
}