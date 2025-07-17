
const BASE_URL = 'http://localhost:8080/api/v1/products';
const BASE_ERROR_MESSAGE = 'Não foi possível';

export async function getAllProducts() {
    const response = await fetch(`${BASE_URL}/categorized-products`, { method: 'GET' });

    if (!response.ok) {
        throw new Error(`${BASE_ERROR_MESSAGE} obter os produtos`);
    }

    return await response.json();
}


// IMAGES

const IMAGE_BASE_URL = 'http://localhost:8080/api/v1/images';

export function getImageUrl(uuid) {
    return `${IMAGE_BASE_URL}/${uuid}`;
}