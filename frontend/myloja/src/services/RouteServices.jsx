
const BASE_URL = 'http://localhost:8080/api/products';
const BASE_ERROR_MESSAGE = 'Não foi possível';

export async function getAllProducts() {
    const response = await fetch(`${BASE_URL}/list-all`, { method: 'GET' });

    if (!response.ok) {
        throw new Error(`${BASE_ERROR_MESSAGE} obter os produtos`);
    }

    return await response.json();
}