async function fetchData() {
    try {
        const customerResponse = await fetch('/api/customer');
        const materialsResponse = await fetch('/api/materials');
        
        const customer = await customerResponse.json();
        const materials = await materialsResponse.json();

        displayCustomer(customer);
        displayMaterials(materials);
    } catch (error) {
        console.error('Error fetching data:', error);
    }
}

function displayCustomer(customer) {
    const customerInfo = document.getElementById('customer-info');
    customerInfo.innerHTML = `
        <div class="customer-info">
            <h2>Customer Details</h2>
            <p><strong>Name:</strong> ${customer.name}</p>
            <p><strong>Contact:</strong> ${customer.contact}</p>
        </div>
    `;
}

function displayMaterials(materials) {
    const materialsContainer = document.getElementById('materials');
    materials.forEach(material => {
        const materialCard = document.createElement('div');
        materialCard.classList.add('material-card');
        materialCard.innerHTML = `
            <h3>${material.materialName}</h3>
            <p><strong>Category:</strong> ${material.category}</p>
            <p><strong>Tips:</strong> ${material.tips}</p>
            <p><strong>Weight:</strong> ${material.weight} kg</p>
            <p><strong>Price per kg:</strong> $${material.pricePerKg}</p>
            <p><strong>Total Price:</strong> $${material.weight * material.pricePerKg}</p>
        `;
        materialsContainer.appendChild(materialCard);
    });
}
