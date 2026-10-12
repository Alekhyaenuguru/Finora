const API_BASE_URL = (
  import.meta.env.VITE_API_BASE_URL ||
  "http://localhost:8080/api"
).replace(/\/+$/, "");

export async function getTransactions() {
  const response = await fetch(`${API_BASE_URL}/transactions`);

  if (!response.ok) {
    throw new Error(`Failed to load transactions: ${response.status}`);
  }

  return response.json();
}

export async function createTransaction(transaction) {
  const response = await fetch(`${API_BASE_URL}/transactions`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(transaction),
  });

  if (!response.ok) {
    throw new Error(`Failed to create transaction: ${response.status}`);
  }

  return response.json();
}

export async function updateTransaction(id, transaction) {
  const response = await fetch(`${API_BASE_URL}/transactions/${id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(transaction),
  });

  if (!response.ok) {
    throw new Error(`Update failed: ${response.status}`);
  }

  return response.json();
}

export async function deleteTransaction(id) {
  const response = await fetch(`${API_BASE_URL}/transactions/${id}`, {
    method: "DELETE",
  });

  if (!response.ok) {
    throw new Error(`Delete failed: ${response.status}`);
  }
}