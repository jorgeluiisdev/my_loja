const AUTH_BASE_URL = "http://localhost:8080/api/v1/";

export async function loginUser(login, password) {
    const response = await fetch(`${AUTH_BASE_URL}login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ login, password }),
    });

    if (!response.ok) {
        throw new Error('Falha, login ou senha incorretos.');
    }

    return await response.json();
}