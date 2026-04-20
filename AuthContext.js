import React, { useState, useEffect } from 'react';
import { getAccounts, transfer } from '../services/api';

export default function TransferPage() {
  const [accounts, setAccounts] = useState([]);
  const [form, setForm] = useState({ fromAccountId: '', toAccountNumber: '', amount: '', description: '' });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    getAccounts().then(res => {
      setAccounts(res.data);
      if (res.data.length > 0) setForm(f => ({ ...f, fromAccountId: res.data[0].id }));
    });
  }, []);

  const fromAccount = accounts.find(a => String(a.id) === String(form.fromAccountId));

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(''); setSuccess('');
    if (!form.toAccountNumber || form.toAccountNumber.length < 10) {
      setError('Please enter a valid destination account number');
      return;
    }
    if (parseFloat(form.amount) <= 0) {
      setError('Amount must be greater than 0');
      return;
    }
    setLoading(true);
    try {
      await transfer({
        fromAccountId: parseInt(form.fromAccountId),
        toAccountNumber: form.toAccountNumber,
        amount: parseFloat(form.amount),
        description: form.description
      });
      setSuccess(`₹${parseFloat(form.amount).toLocaleString('en-IN', { minimumFractionDigits: 2 })} transferred successfully!`);
      setForm(f => ({ ...f, toAccountNumber: '', amount: '', description: '' }));
      // Refresh accounts
      const res = await getAccounts();
      setAccounts(res.data);
    } catch (err) {
      setError(err.response?.data?.error || 'Transfer failed');
    } finally {
      setLoading(false);
    }
  };

  const set = (field) => (e) => setForm({ ...form, [field]: e.target.value });

  return (
    <div>
      <div className="page-header">
        <h1>Transfer Money</h1>
        <p>Send funds to any account instantly</p>
      </div>

      <div className="grid-2" style={{ alignItems: 'start' }}>
        {/* Transfer Form */}
        <div className="card">
          {error   && <div className="alert alert-error">{error}</div>}
          {success && <div className="alert alert-success">✅ {success}</div>}

          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label>From Account</label>
              <select value={form.fromAccountId} onChange={set('fromAccountId')}>
                {accounts.map(acc => (
                  <option key={acc.id} value={acc.id}>
                    {acc.accountType} — •••• {acc.accountNumber?.slice(-4)} (₹{parseFloat(acc.balance).toLocaleString('en-IN', {minimumFractionDigits:2})})
                  </option>
                ))}
              </select>
            </div>

            <div className="form-group">
              <label>Destination Account Number</label>
              <input type="text" placeholder="Enter 10-digit account number" maxLength={10}
                value={form.toAccountNumber} onChange={set('toAccountNumber')} required />
            </div>

            <div className="form-group">
              <label>Amount (₹)</label>
              <input type="number" placeholder="0.00" min="1"
                value={form.amount} onChange={set('amount')} required />
            </div>

            <div className="form-group">
              <label>Remarks (Optional)</label>
              <input type="text" placeholder="e.g. Rent payment" value={form.description} onChange={set('description')} />
            </div>

            <button type="submit" className="btn btn-primary" style={{ width: '100%', padding: 13 }} disabled={loading || accounts.length === 0}>
              {loading ? 'Processing...' : '↔️ Transfer Now'}
            </button>
          </form>
        </div>

        {/* Summary Card */}
        <div>
          <div className="card" style={{ background: 'linear-gradient(135deg, #1a1a2e, #1a56db)', color: 'white', marginBottom: 16 }}>
            <div style={{ fontSize: 13, opacity: 0.7, marginBottom: 6, textTransform: 'uppercase', letterSpacing: '0.5px' }}>Available Balance</div>
            <div style={{ fontSize: 34, fontWeight: 800 }}>
              ₹{fromAccount ? parseFloat(fromAccount.balance).toLocaleString('en-IN', { minimumFractionDigits: 2 }) : '0.00'}
            </div>
            <div style={{ fontSize: 13, opacity: 0.7, marginTop: 8 }}>
              {fromAccount?.accountType} — {fromAccount?.accountNumber}
            </div>
          </div>

          <div className="card">
            <h3 style={{ fontWeight: 700, marginBottom: 16, fontSize: 15 }}>Transfer Tips</h3>
            <div style={{ display: 'flex', flexDirection: 'column', gap: 12 }}>
              {[
                ['🔒', 'All transfers are secured with bank-grade encryption'],
                ['⚡', 'Transfers within SmartBank are instant'],
                ['📋', 'You can view all transfers in Transaction History'],
                ['💡', 'Double-check the account number before transferring'],
              ].map(([icon, text]) => (
                <div key={text} style={{ display: 'flex', gap: 10, fontSize: 13, color: 'var(--text-light)' }}>
                  <span>{icon}</span><span>{text}</span>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
