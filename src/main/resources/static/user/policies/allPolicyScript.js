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
    let average = [0,0,0];
    fetch(`http://localhost:5050/rating/policyAverage?policyId=${policy.policyId}`)
        .then(response => {
            if (response.status === 302){
                response.json().then(data =>{
                    average = data;
                })
            }
        })
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
        <button class="comment-button">Reviews</button>
    `;
    policyCard.querySelector(`.comment-button`).addEventListener('click', ()=> {
        event.preventDefault();
        localStorage.setItem("policy", JSON.stringify(policy));
        location.href = `../putComment/comment.html`;
    })
    let policyContainer = document.getElementById('policies-container')
    policyContainer.appendChild(policyCard);
}