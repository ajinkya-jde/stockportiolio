async function loadDashboard() {
    try {
        const response = await fetch('/api/dashboard');
        const data = await response.json();
        
        if (!data.success) {
            window.location.href = 'index.html';
            return;
        }
        
        document.getElementById('welcome-text').textContent = `Welcome back, ${data.username}`;
        document.getElementById('user-type-badge').textContent = data.userType + ' USER';
        
        document.getElementById('cash-balance').textContent = `$${data.balance.toFixed(2)}`;
        document.getElementById('portfolio-value').textContent = `$${data.portfolioValue.toFixed(2)}`;
        document.getElementById('total-value').textContent = `$${data.totalValue.toFixed(2)}`;
        
        const returnEl = document.getElementById('total-return');
        const returnPercentEl = document.getElementById('total-return-percent');
        returnEl.textContent = `$${data.totalGainLoss.toFixed(2)}`;
        returnPercentEl.textContent = `${data.totalGainLossPercent >= 0 ? '+' : ''}${data.totalGainLossPercent.toFixed(2)}%`;
        
        if (data.totalGainLoss >= 0) {
            returnEl.classList.add('positive');
            returnPercentEl.classList.add('positive');
        } else {
            returnEl.classList.add('negative');
            returnPercentEl.classList.add('negative');
        }
        
        const holdingsBody = document.getElementById('holdings-body');
        if (data.holdings.length === 0) {
            holdingsBody.innerHTML = '<tr><td colspan="7" class="empty-state">No holdings yet. Visit the Market to buy stocks!</td></tr>';
        } else {
            holdingsBody.innerHTML = data.holdings.map(holding => `
                <tr>
                    <td>
                        <div class="stock-name">${holding.companyName}</div>
                        <div class="stock-symbol">${holding.symbol}</div>
                    </td>
                    <td>${holding.quantity}</td>
                    <td>$${holding.purchasePrice.toFixed(2)}</td>
                    <td>$${holding.currentPrice.toFixed(2)}</td>
                    <td>$${holding.currentValue.toFixed(2)}</td>
                    <td class="${holding.gainLoss >= 0 ? 'positive' : 'negative'}">
                        ${holding.gainLoss >= 0 ? '+' : ''}$${holding.gainLoss.toFixed(2)}
                    </td>
                    <td class="${holding.gainLossPercent >= 0 ? 'positive' : 'negative'}">
                        ${holding.gainLossPercent >= 0 ? '+' : ''}${holding.gainLossPercent.toFixed(2)}%
                    </td>
                </tr>
            `).join('');
        }
    } catch (error) {
        console.error('Error loading dashboard:', error);
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

loadDashboard();
