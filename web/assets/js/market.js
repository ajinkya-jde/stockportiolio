let currentStock = null;

async function loadMarket() {
    try {
        const response = await fetch('/api/market');
        const data = await response.json();
        
        const marketGrid = document.getElementById('market-grid');
        
        if (data.success && data.stocks.length > 0) {
            marketGrid.innerHTML = data.stocks.map(stock => `
                <div class="stock-card" onclick="openTradeModal('${stock.symbol}', '${stock.companyName}', ${stock.price}, ${stock.stockId})">
                    <div class="stock-header">
                        <div class="stock-info">
                            <h3>${stock.symbol}</h3>
                            <p>${stock.companyName}</p>
                        </div>
                        <div class="stock-change ${stock.changePercent >= 0 ? 'positive' : 'negative'}">
                            ${stock.changePercent >= 0 ? '+' : ''}${stock.changePercent.toFixed(2)}%
                        </div>
                    </div>
                    <div class="stock-price">$${stock.price.toFixed(2)}</div>
                </div>
            `).join('');
        } else {
            marketGrid.innerHTML = '<div class="loading">No stocks available</div>';
        }
    } catch (error) {
        console.error('Error loading market:', error);
    }
}

function openTradeModal(symbol, companyName, price, stockId) {
    currentStock = { symbol, companyName, price, stockId };
    
    document.getElementById('modal-title').textContent = `Trade ${symbol}`;
    document.getElementById('modal-stock-name').textContent = companyName;
    document.getElementById('modal-stock-symbol').textContent = symbol;
    document.getElementById('modal-price').textContent = `$${price.toFixed(2)}`;
    document.getElementById('quantity').value = 1;
    updateTotal();
    
    document.getElementById('tradeModal').classList.add('active');
    document.getElementById('trade-message').style.display = 'none';
}

function closeModal() {
    document.getElementById('tradeModal').classList.remove('active');
    currentStock = null;
}

function updateTotal() {
    if (currentStock) {
        const quantity = parseInt(document.getElementById('quantity').value) || 1;
        const total = currentStock.price * quantity;
        document.getElementById('modal-total').textContent = `$${total.toFixed(2)}`;
    }
}

document.getElementById('quantity').addEventListener('input', updateTotal);

async function executeBuy() {
    if (!currentStock) return;
    
    const quantity = parseInt(document.getElementById('quantity').value);
    if (quantity <= 0) {
        showTradeMessage('Please enter a valid quantity', false);
        return;
    }
    
    try {
        const formData = new URLSearchParams();
        formData.append('symbol', currentStock.symbol);
        formData.append('quantity', quantity);
        
        const response = await fetch('/api/buy', {
            method: 'POST',
            body: formData
        });
        
        const data = await response.json();
        
        if (data.success) {
            showTradeMessage('Purchase successful!', true);
            setTimeout(() => {
                closeModal();
                window.location.reload();
            }, 1500);
        } else {
            showTradeMessage(data.message, false);
        }
    } catch (error) {
        showTradeMessage('An error occurred. Please try again.', false);
    }
}

async function executeSell() {
    if (!currentStock) return;
    
    const quantity = parseInt(document.getElementById('quantity').value);
    if (quantity <= 0) {
        showTradeMessage('Please enter a valid quantity', false);
        return;
    }
    
    try {
        const formData = new URLSearchParams();
        formData.append('symbol', currentStock.symbol);
        formData.append('quantity', quantity);
        
        const response = await fetch('/api/sell', {
            method: 'POST',
            body: formData
        });
        
        const data = await response.json();
        
        if (data.success) {
            showTradeMessage('Sale successful!', true);
            setTimeout(() => {
                closeModal();
                window.location.reload();
            }, 1500);
        } else {
            showTradeMessage(data.message, false);
        }
    } catch (error) {
        showTradeMessage('An error occurred. Please try again.', false);
    }
}

function showTradeMessage(message, isSuccess) {
    const messageEl = document.getElementById('trade-message');
    messageEl.className = isSuccess ? 'message success' : 'message error';
    messageEl.textContent = message;
    messageEl.style.display = 'block';
}

async function refreshMarket() {
    await loadMarket();
}

async function logout() {
    try {
        await fetch('/api/logout', { method: 'POST' });
        window.location.href = 'index.html';
    } catch (error) {
        window.location.href = 'index.html';
    }
}

loadMarket();
