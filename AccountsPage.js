.auth-page {
  display: flex;
  min-height: 100vh;
}

.auth-left {
  flex: 1;
  background: linear-gradient(135deg, #1a1a2e 0%, #1a56db 100%);
  color: white;
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 60px;
}

.auth-brand {
  margin-bottom: 48px;
}
.auth-brand-icon { font-size: 52px; display: block; margin-bottom: 16px; }
.auth-brand h1 { font-size: 36px; font-weight: 800; margin-bottom: 8px; }
.auth-brand p  { font-size: 16px; opacity: 0.8; }

.auth-features { display: flex; flex-direction: column; gap: 16px; }
.feature-item  { display: flex; align-items: center; gap: 12px; font-size: 15px; opacity: 0.9; }

.auth-right {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px;
  background: #f0f2f5;
}

.auth-card {
  background: white;
  border-radius: 16px;
  padding: 40px;
  width: 100%;
  max-width: 400px;
  box-shadow: 0 4px 24px rgba(0,0,0,0.08);
}

.auth-card-wide { max-width: 560px; }

.auth-card h2       { font-size: 26px; font-weight: 700; margin-bottom: 6px; }
.auth-subtitle      { color: var(--text-light); font-size: 14px; margin-bottom: 28px; }
.auth-submit        { width: 100%; padding: 13px; font-size: 15px; margin-top: 8px; }
.auth-switch        { text-align: center; font-size: 13px; color: var(--text-light); margin-top: 20px; }
.auth-switch a      { color: var(--primary); font-weight: 600; }

.form-row { display: grid; grid-template-columns: 1fr 1fr; gap: 16px; }

@media (max-width: 900px) {
  .auth-left   { display: none; }
  .auth-right  { padding: 24px 16px; }
  .form-row    { grid-template-columns: 1fr; }
}
