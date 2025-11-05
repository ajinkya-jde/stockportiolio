async function loadTransactions() {
    try {
        const response = await fetch('/api/transactions');
        const data = await response.json();
        
        if (!data.success) {
            window.location.href = 'index.html';
            return;
        }
        
        const transactionsBody = document.getElementById('transactions-body');
        
        if (data.transactions.length === 0) {
            transactionsBody.innerHTML = '<tr><td colspan="6" class="empty-state">No transactions yet</td></tr>';
        } else {
            transactionsBody.innerHTML = data.transactions.map(txn => `
                <tr>
                    <td>${new Date(txn.date).toLocaleString()}</td>
                    <td>
                        <span class="${txn.type === 'BUY' ? 'positive' : 'negative'}">
                            ${txn.type}
                        </span>
                    </td>
                    <td>${txn.stockSymbol}</td>
                    <td>${txn.quantity}</td>
                    <td>$${txn.price.toFixed(2)}</td>
                    <td class="${txn.type === 'BUY' ? 'negative' : 'positive'}">
                        ${txn.type === 'BUY' ? '-' : '+'}$${txn.totalAmount.toFixed(2)}
                    </td>
                </tr>
            `).join('');
        }
    } catch (error) {
        console.error('Error loading transactions:', error);
    }
}

async function logout() {
    try {
        await fetch('/api/logout', { method: 'POST' });
        window.location.href = 'index.html';
    } catch (error) {
        window.location.href = 'index.html';
    }
}

loadTransactions();
