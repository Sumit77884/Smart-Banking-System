import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { register } from '../services/api';
import './AuthPages.css';

export default function RegisterPage() {
  const [form, setForm] = useState({
    username: '', email: '', password: '', confirmPassword: '',
    fullName: '', phone: '', address: ''
  });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    if (form.password !== form.confirmPassword) {
      setError('Passwords do not match');
      return;
    }
    setLoading(true);
    try {
      await register(form);
      setSuccess('Account created! Redirecting to login...');
      setTimeout(() => navigate('/login'), 1500);
    } catch (err) {
      setError(err.response?.data?.error || 'Registration failed. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  const set = (field) => (e) => setForm({ ...form, [field]: e.target.value });

  return (
    <div className="auth-page">
      <div className="auth-left">
        <div className="auth-brand">
          <span className="auth-brand-icon">🏦</span>
          <h1>SmartBank</h1>
          <p>Open your account in minutes</p>
        </div>
        <div className="auth-features">
          <div className="feature-item"><span>🎉</span> Free account opening</div>
          <div className="feature-item"><span>🔒</span> Bank-grade security</div>
          <div className="feature-item"><span>📱</span> 24/7 online access</div>
          <div className="feature-item"><span>💰</span> Multiple account types</div>
        </div>
      </div>

      <div className="auth-right">
        <div className="auth-card auth-card-wide">
          <h2>Create Account</h2>
          <p className="auth-subtitle">Fill in the details below</p>

          {error   && <div className="alert alert-error">{error}</div>}
          {success && <div className="alert alert-success">{success}</div>}

          <form onSubmit={handleSubmit}>
            <div className="form-row">
              <div className="form-group">
                <label>Full Name</label>
                <input type="text" placeholder="John Doe" value={form.fullName} onChange={set('fullName')} required />
              </div>
              <div className="form-group">
                <label>Username</label>
                <input type="text" placeholder="johndoe" value={form.username} onChange={set('username')} required />
              </div>
            </div>
            <div className="form-group">
              <label>Email</label>
              <input type="email" placeholder="john@example.com" value={form.email} onChange={set('email')} required />
            </div>
            <div className="form-group">
              <label>Phone</label>
              <input type="text" placeholder="+91 9876543210" value={form.phone} onChange={set('phone')} />
            </div>
            <div className="form-group">
              <label>Address</label>
              <input type="text" placeholder="Your address" value={form.address} onChange={set('address')} />
            </div>
            <div className="form-row">
              <div className="form-group">
                <label>Password</label>
                <input type="password" placeholder="Min 6 characters" value={form.password} onChange={set('password')} required />
              </div>
              <div className="form-group">
                <label>Confirm Password</label>
                <input type="password" placeholder="Repeat password" value={form.confirmPassword} onChange={set('confirmPassword')} required />
              </div>
            </div>
            <button className="btn btn-primary auth-submit" type="submit" disabled={loading}>
              {loading ? 'Creating account...' : 'Create Account'}
            </button>
          </form>

          <p className="auth-switch">
            Already have an account? <Link to="/login">Sign in</Link>
          </p>
        </div>
      </div>
    </div>
  );
}
