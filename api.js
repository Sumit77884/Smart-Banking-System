import React, { useState, useEffect } from 'react';
import { getAccounts, getAccountTransactions } from '../services/api';

export default function TransactionsPage() {
  const [accounts, setAccounts] = useState([]);
  const [selectedAccount, setSelectedAccount] = useState('');
  const [transactions, setTransactions] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getAccounts().then(res => {
      setAccounts(res.data);
      if (res.data.length > 0) {
        setSelectedAccount(res.data[0].id);
        fetchTxns(res.data[0].id);
      } else {
        setLoading(false);
      }
    });
  }, []);

  const fetchTxns = async (accountId) => {
    setLoading(true);
    try {
      const res = await getAccountTransactions(accountId);
      setTransactions(res.data);
    } catch(e) { console.error(e); }
    finally { setLoading(false); }
  };

  const handleAccountChange = (id) => {
    setSelectedAccount(id);
    fetchTxns(id);
  };

  const txnColor = (type, amount) => {
    if (type === 'DEPOSIT') return 'var(--success)';
    if (type === 'WITHDRAWAL') return 'var(--danger)';
    return parseFloat(amount) >= 0 ? 'var(--success)' : 'var(--danger)';
  };

  const txnIcon = (type) => {
    const map = { DEPOSIT: '⬇️', WITHDRAWAL: '⬆️', TRANSFER: '↔️', PAYMENT: '💸' };
    return map[type] || '💰';
  };

  return (
    <div>
      <div className="page-header">
        <h1>Transactions</h1>
        <p>View your complete transaction history</p>
      </div>

      {accounts.length > 0 && (
        <div className="card" style={{ marginBottom: 24 }}>
          <div className="form-group" style={{ marginBottom: 0 }}>
            <label>Select Account</label>
            <select value={selectedAccount} onChange={e => handleAccountChange(e.target.value)} style={{ maxWidth: 360 }}>
              {accounts.map(acc => (
                <option key={acc.id} value={acc.id}>
                  {acc.accountType} — •••• {acc.accountNumber?.slice(-4)} (₹{parseFloat(acc.balance).toLocaleString('en-IN', { minimumFractionDigits: 2 })})
                </option>
              ))}
            </select>
          </div>
        </div>
      )}

      <div className="card">
        {loading ? (
          <div className="spinner" />
        ) : transactions.length === 0 ? (
          <div style={{ textAlign: 'center', padding: '60px 0', color: 'var(--text-light)' }}>
            <div style={{ fontSize: 60, marginBottom: 16 }}>📋</div>
            <h3>No transactions found</h3>
            <p style={{ marginTop: 8 }}>Make a deposit or transfer to see history here.</p>
          </div>
        ) : (
          <div style={{ overflowX: 'auto' }}>
            <table>
              <thead>
                <tr>
                  <th>Type</th>
                  <th>Description</th>
                  <th>Reference</th>
                  <th>Amount</th>
                  <th>Balance After</th>
                  <th>Status</th>
                  <th>Date</th>
                </tr>
              </thead>
              <tbody>
                {transactions.map(txn => (
                  <tr key={txn.id}>
                    <td>
                      <span style={{ display: 'flex', alignItems: 'center', gap: 8 }}>
                        <span style={{ fontSize: 18 }}>{txnIcon(txn.type)}</span>
                        <span style={{ fontWeight: 600 }}>{txn.type}</span>
                      </span>
                    </td>
                    <td style={{ color: 'var(--text-light)' }}>{txn.description || '-'}</td>
                    <td><code style={{ background: '#f3f4f6', padding: '2px 8px', borderRadius: 4, fontSize: 12 }}>{txn.referenceNumber}</code></td>
                    <td style={{ fontWeight: 700, color: txnColor(txn.type, txn.amount) }}>
                      {parseFloat(txn.amount) >= 0 ? '+' : ''}₹{Math.abs(parseFloat(txn.amount)).toLocaleString('en-IN', { minimumFractionDigits: 2 })}
                    </td>
                    <td style={{ fontWeight: 600 }}>
                      ₹{parseFloat(txn.balanceAfter).toLocaleString('en-IN', { minimumFractionDigits: 2 })}
                    </td>
                    <td>
                      <span className={`badge badge-${txn.status === 'SUCCESS' ? 'success' : txn.status === 'PENDING' ? 'warning' : 'danger'}`}>
                        {txn.status}
                      </span>
                    </td>
                    <td style={{ color: 'var(--text-light)', fontSize: 13 }}>
                      {new Date(txn.createdAt).toLocaleString('en-IN')}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
}
