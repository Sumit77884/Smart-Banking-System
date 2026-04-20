import React, { useState, useEffect } from 'react';
import { getAccounts, createAccount, deposit, withdraw } from '../services/api';

export default function AccountsPage() {
  const [accounts, setAccounts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showCreate, setShowCreate] = useState(false);
  const [showDeposit, setShowDeposit] = useState(null);
  const [showWithdraw, setShowWithdraw] = useState(null);
  const [form, setForm] = useState({ accountType: 'SAVINGS', initialDeposit: '' });
  const [txnForm, setTxnForm] = useState({ amount: '', description: '' });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const fetchAccounts = async () => {
    try {
      const res = await getAccounts();
      setAccounts(res.data);
    } catch (e) { console.error(e); }
    finally { setLoading(false); }
  };

  useEffect(() => { fetchAccounts(); }, []);

  const handleCreate = async (e) => {
    e.preventDefault(); setError('');
    try {
      await createAccount({ accountType: form.accountType, initialDeposit: parseFloat(form.initialDeposit) || 0 });
      setSuccess('Account created successfully!');
      setShowCreate(false);
      fetchAccounts();
    } catch (err) { setError(err.response?.data?.error || 'Failed to create account'); }
  };

  const handleDeposit = async (e) => {
    e.preventDefault(); setError('');
    try {
      await deposit({ accountId: showDeposit, amount: parseFloat(txnForm.amount), description: txnForm.description });
      setSuccess('Deposit successful!');
      setShowDeposit(null);
      setTxnForm({ amount: '', description: '' });
      fetchAccounts();
    } catch (err) { setError(err.response?.data?.error || 'Deposit failed'); }
  };

  const handleWithdraw = async (e) => {
    e.preventDefault(); setError('');
    try {
      await withdraw({ accountId: showWithdraw, amount: parseFloat(txnForm.amount), description: txnForm.description });
      setSuccess('Withdrawal successful!');
      setShowWithdraw(null);
      setTxnForm({ amount: '', description: '' });
      fetchAccounts();
    } catch (err) { setError(err.response?.data?.error || 'Withdrawal failed'); }
  };

  if (loading) return <div className="spinner" />;

  return (
    <div>
      <div className="page-header" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
        <div>
          <h1>Accounts</h1>
          <p>Manage your bank accounts</p>
        </div>
        <button className="btn btn-primary" onClick={() => { setShowCreate(!showCreate); setError(''); setSuccess(''); }}>
          + Open New Account
        </button>
      </div>

      {error   && <div className="alert alert-error">{error}</div>}
      {success && <div className="alert alert-success">{success}</div>}

      {/* Create Account Form */}
      {showCreate && (
        <div className="card" style={{ marginBottom: 24 }}>
          <h3 style={{ marginBottom: 16, fontWeight: 700 }}>Open New Account</h3>
          <form onSubmit={handleCreate}>
            <div className="grid-2">
              <div className="form-group">
                <label>Account Type</label>
                <select value={form.accountType} onChange={e => setForm({ ...form, accountType: e.target.value })}>
                  <option value="SAVINGS">Savings Account</option>
                  <option value="CHECKING">Checking Account</option>
                  <option value="FIXED_DEPOSIT">Fixed Deposit</option>
                </select>
              </div>
              <div className="form-group">
                <label>Initial Deposit (₹)</label>
                <input type="number" placeholder="0.00" min="0" value={form.initialDeposit}
                  onChange={e => setForm({ ...form, initialDeposit: e.target.value })} />
              </div>
            </div>
            <div style={{ display: 'flex', gap: 12 }}>
              <button type="submit" className="btn btn-primary">Create Account</button>
              <button type="button" className="btn btn-outline" onClick={() => setShowCreate(false)}>Cancel</button>
            </div>
          </form>
        </div>
      )}

      {/* Deposit / Withdraw modals */}
      {(showDeposit || showWithdraw) && (
        <div className="card" style={{ marginBottom: 24, border: `2px solid ${showDeposit ? 'var(--success)' : 'var(--danger)'}` }}>
          <h3 style={{ marginBottom: 16, fontWeight: 700 }}>{showDeposit ? '⬇️ Deposit' : '⬆️ Withdraw'}</h3>
          <form onSubmit={showDeposit ? handleDeposit : handleWithdraw}>
            <div className="grid-2">
              <div className="form-group">
                <label>Amount (₹)</label>
                <input type="number" placeholder="Enter amount" min="1" required value={txnForm.amount}
                  onChange={e => setTxnForm({ ...txnForm, amount: e.target.value })} />
              </div>
              <div className="form-group">
                <label>Description</label>
                <input type="text" placeholder="Optional note" value={txnForm.description}
                  onChange={e => setTxnForm({ ...txnForm, description: e.target.value })} />
              </div>
            </div>
            <div style={{ display: 'flex', gap: 12 }}>
              <button type="submit" className={`btn ${showDeposit ? 'btn-success' : 'btn-danger'}`}>
                {showDeposit ? 'Deposit' : 'Withdraw'}
              </button>
              <button type="button" className="btn btn-outline" onClick={() => { setShowDeposit(null); setShowWithdraw(null); setTxnForm({ amount: '', description: '' }); }}>
                Cancel
              </button>
            </div>
          </form>
        </div>
      )}

      {/* Accounts Grid */}
      {accounts.length === 0 ? (
        <div className="card" style={{ textAlign: 'center', padding: '60px 0' }}>
          <div style={{ fontSize: 60, marginBottom: 16 }}>💳</div>
          <h3>No accounts yet</h3>
          <p style={{ color: 'var(--text-light)', marginTop: 8 }}>Open your first account to get started.</p>
        </div>
      ) : (
        <div className="grid-2">
          {accounts.map(acc => (
            <div key={acc.id} className="card" style={{ border: '1px solid var(--border)' }}>
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', marginBottom: 20 }}>
                <div>
                  <div style={{ fontSize: 13, color: 'var(--text-light)', fontWeight: 600, textTransform: 'uppercase' }}>{acc.accountType}</div>
                  <div style={{ fontFamily: 'monospace', fontSize: 16, fontWeight: 600, marginTop: 4 }}>{acc.accountNumber}</div>
                </div>
                <span className={`badge badge-${acc.status === 'ACTIVE' ? 'success' : 'danger'}`}>{acc.status}</span>
              </div>
              <div style={{ fontSize: 32, fontWeight: 800, color: 'var(--primary)', marginBottom: 20 }}>
                ₹{parseFloat(acc.balance).toLocaleString('en-IN', { minimumFractionDigits: 2 })}
              </div>
              <div style={{ display: 'flex', gap: 10 }}>
                <button className="btn btn-success" style={{ flex: 1, padding: '8px' }} onClick={() => { setShowDeposit(acc.id); setShowWithdraw(null); setError(''); setSuccess(''); }}>
                  ⬇️ Deposit
                </button>
                <button className="btn btn-danger" style={{ flex: 1, padding: '8px' }} onClick={() => { setShowWithdraw(acc.id); setShowDeposit(null); setError(''); setSuccess(''); }}>
                  ⬆️ Withdraw
                </button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
